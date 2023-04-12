<script setup>
import {onMounted, ref} from 'vue';
import {Delete, DocumentCopy, Edit, Plus, Search} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox, ElNotification} from 'element-plus';
import axios from '@/utils/request';
import dayjs from 'dayjs';

const treeRef = ref()
const formRef = ref()
const queryString = ref('')
const page = ref({currentPage: 1, pageSize: 10, total: 0})
const dialog = ref({visible: false, status: 1, title: ''});
const selectionIds = ref([])
const tableData = ref([])
const formData = ref({})
const treeData = ref([])
const rules = ref({
    name: [{required: true, message: '角色名称为必填项', trigger: 'blur'}],
    roleKey: [{required: true, message: '权限字符串为必填项', trigger: 'blur'}]
})

onMounted(() => {
    pageQuery();
})

/**
 * 分页查询角色信息
 */
function pageQuery() {
    axios({
        url: '/api/role/list',
        params: {
            'currentPage': page.value.currentPage,
            'pageSize': page.value.pageSize,
            'queryString': queryString.value
        },
        method: 'GET'
    }).then(({data}) => {
        if (data && data.code == 200) {
            tableData.value = data.userList;
            page.value.total = data.total;
        } else {
            ElMessage.error('获取角色信息失败！')
        }
    })
}

/**
 * 选中的角色数据发生变化
 * @param {*} selection
 */
function selectionChange(selection) {
    selectionIds.value = selection.map(select => select.id)
}

/**
 * 启用/禁用 角色
 */
function changeStatus(row) {
    axios({
        url: '/api/role/status',
        data: {'id': row.id, 'status': row.status},
        method: 'PUT'
    }).then(({data}) => {
        if (data && data.code == 200) {
            ElNotification.success({title: '成功', message: '修改成功！'})
            return true;
        } else {
            ElNotification.error({title: '失败', message: '修改失败！'})
            return false;
        }
    })
}

/**
 * 页面大小改变
 * @param {*} pageSize
 */
function sizeChange(pageSize) {
    page.value.pageSize = pageSize;
    pageQuery();
}

/**
 * 翻页
 * @param {*} currentPage
 */
function pageChange(currentPage) {
    page.value.currentPage = currentPage;
    pageQuery();
}

/**
 * 点击 “新增” 按钮
 */
function handleAdd() {
    formData.value = {};
    axios.get('/api/role/menus').then(({data}) => {
        if (data && data.code) {
            treeData.value = data.menus;
        } else {
            ElMessage.error('获取菜单列表失败');
        }
    }).finally(() => {
        dialog.value = {visible: true, status: 1, title: '新增角色信息'}
    })
}

/**
 * 点击 “菜单权限” 按钮
 * @param {*} row
 */
function editMenuPermission(row) {
    formData.value = {};
    Object.assign(formData.value, row)
    axios.get(`/api/role/menus/${row.id}`).then(({data}) => {
        if (data && data.code == 200) {
            formData.value.menus = data.menuIds;
            treeData.value = data.menus;
        } else {
            ElMessage.error('获取菜单列表失败');
        }
    }).finally(() => {
        dialog.value = {visible: true, status: 2, title: '修改菜单权限'}
    })
}

/**
 * 点击 “资源权限” 按钮
 * @param {*} row
 */
function editResourcePermission(row) {
    formData.value = {};
    Object.assign(formData.value, row)
    axios.get(`/api/role/resources/${row.id}`).then(({data}) => {
        if (data && data.code == 200) {
            formData.value.resources = data.resourceIds;
            treeData.value = data.resources;
        } else {
            ElMessage.error('获取资源列表失败');
        }
    }).finally(() => {
        dialog.value = {visible: true, status: 3, title: '修改资源权限'}
    })
}

/**
 * 点击确认按钮
 */
function handleOK() {
    formRef.value.validate((valid) => {
        if (valid) {
            let url = '', method = '';
            const permissions = treeRef.value.getCheckedNodes().map(item => item.id);
            switch (dialog.value.status) {
                case 1: { // 新增角色
                    formData.value.menus = permissions;
                    url = '/api/role';
                    method = 'POST';
                    break;
                }
                case 2: { // 编辑菜单权限
                    formData.value.menus = permissions;
                    url = '/api/role/menu';
                    method = 'PUT';
                    break;
                }
                case 3: { // 编辑资源权限
                    formData.value.resources = permissions;
                    url = '/api/role/resource';
                    method = 'PUT';
                    break;
                }
                default: {
                    ElMessage.error('系统错误！');
                    return false;
                }
            }
            axios({url, data: formData.value, method}).then(({data}) => {
                if (data && data.code == 200) {
                    ElNotification.success({title: '成功', message: '操作成功！'});
                    dialog.value.visible = dialog.value.visible = false;
                    pageQuery();
                } else {
                    ElNotification.error({title: '失败', message: data.msg});
                }
            })
        }
    })

}

/**
 * 删除角色信息
 * @param {*} roleId 角色 id  为空时表示批量删除
 */
function handleDelete(roleId) {
    // 弹框内容
    const tips = {confirmButtonText: '确认', cancelButtonText: '取消', type: 'warning', draggable: true};
    // 弹框提示是否删除
    ElMessageBox.confirm('确定要删除选中的角色吗？', '提示', tips).then(() => {
        let param = ''
        // 根据 roleId 动态决定删除单个还是批量删除
        if (roleId) {
            param = `${roleId}`
        } else {  // 批量删除，处理参数
            param = `/batch?roleId=${selectionIds.value[0]}`
            for (let i = 1; i < selectionIds.value.length; i++) {
                param += `&roleId=${selectionIds.value[i]}`
            }
        }
        // 发请求到后端删除数据
        axios.delete(`/api/role/${param}`).then(({data}) => {
            if (data && data.code == 200) {
                let message = '', temp = 0;
                if (data.count != null) {   // 根据删除记录数生成提示信息
                    message = `成功删除 ${data.count} 条记录`;
                    if ((temp = selectionIds.value.length - data.count) != 0) {
                        message += `，有 ${temp} 条记录正在使用中，无法删除`;
                    }
                } else {
                    message = `删除成功！`
                }
                ElNotification.success({title: '成功', message});
                pageQuery();
            } else {          // 删除失败
                ElNotification.error({title: '失败', message: data.msg});
            }
        })
    }).catch(() => {
    }) // 点击 “取消” 按钮的业务逻辑
}
</script>

<template>
  <!-- 标题 -->
    <div class="content-title">
        <span>角色管理</span>
    </div>
  <!-- 按钮 -->
    <div class="operation-container">
        <div class="operation-left">
            <el-button :icon="Plus" type="primary" @click="handleAdd">创建角色</el-button>
            <el-button :disabled="selectionIds.length == 0" :icon="Delete" type="danger"
                       @click="handleDelete()">批量删除
            </el-button>
        </div>
        <div class="operation-right">
            <el-input v-model="queryString" :prefix-icon="Search" placeholder="角色名/权限字符串"/>
            <el-button :icon="Search" style="margin-left: 10px;" type="primary" @click="pageQuery">搜索</el-button>
        </div>
    </div>
  <!-- 页面主体 -->
    <el-table :data="tableData" border row-key="id" style="width: 100%" @selection-change="selectionChange">
        <el-table-column align="center" fixed="left" type="selection" width="55"/>
        <el-table-column align="center" label="角色名称" property="name" width="120"/>
        <el-table-column align="center" label="权限字符串" property="roleKey" width="120"/>
        <el-table-column align="center" label="状态" property="status" width="75">
            <template #default="scope">
                <el-switch v-model="scope.row.status" :active-value="0" :inactive-value="1" active-text="启用"
                           inactive-text="禁用"
                           inline-prompt @change="changeStatus(scope.row)"/>
            </template>
        </el-table-column>
        <el-table-column align="center" label="创建时间" min-width="95" property="createTime">
            <template #default="scope">{{ dayjs(scope.row.createTime).format("YYYY-MM-DD HH:mm:ss") }}</template>
        </el-table-column>
        <el-table-column align="center" label="备注" property="remark"/>
        <el-table-column align="center" fixed="right" label="操作" width="300">
            <template #default="scope">
                <el-button :icon="Edit" link size="small" type="primary" @click="editMenuPermission(scope.row)">
                    <span>菜单权限</span>
                </el-button>
                <el-button :icon="DocumentCopy" link size="small" type="primary"
                           @click="editResourcePermission(scope.row)">
                    <span>资源权限</span>
                </el-button>
                <el-button :icon="Delete" link size="small" type="danger" @click="handleDelete(scope.row.id)">
                    <span>删除角色</span>
                </el-button>
            </template>
        </el-table-column>
    </el-table>
  <!-- 分页 -->
    <el-pagination :current-page="page.currentPage" :page-size="page.pageSize" :page-sizes="[10, 25, 50, 100]"
                   :pager-count="7" :total="page.total" background
                   layout="total, sizes, prev, pager, next, jumper"
                   @size-change="sizeChange" @current-change="pageChange"/>
  <!-- 弹框 -->
    <el-dialog v-model="dialog.visible" :title="dialog.title" destroy-on-close draggable width="30%">
        <el-form ref="formRef" :model="formData" :rules="rules" label-width="80px">
            <el-form-item label="角色名" prop="name">
                <el-input v-model="formData.name" style="width:250px"/>
            </el-form-item>
            <el-form-item label="权限标签" prop="roleKey">
                <el-input v-model="formData.roleKey" style="width:250px"/>
            </el-form-item>
            <el-form-item :label="dialog.status == 3 ? '资源权限' : '菜单权限'">
                <el-tree ref="treeRef"
                         :data="treeData"
                         :default-checked-keys="dialog.status == 3 ? formData.resources : formData.menus"
                         :props="{ label: 'name' }" check-strictly node-key="id" show-checkbox/>
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button type="primary" @click="handleOK">确定</el-button>
            <el-button @click="dialog.visible = false">取消</el-button>
        </template>
    </el-dialog>
</template>

<style scoped></style>