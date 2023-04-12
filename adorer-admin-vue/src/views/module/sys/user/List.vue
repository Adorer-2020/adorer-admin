<script setup>
import {onMounted, ref} from 'vue';
import {ElMessage, ElNotification} from 'element-plus';
import {Delete, Edit, Plus, Search} from '@element-plus/icons-vue'
import AvatarUploader from '@/components/AvatarUploader.vue';
import axios from '@/utils/request'
import dayjs from 'dayjs'

const queryString = ref('');
const page = ref({currentPage: 1, pageSize: 10, total: 0});
const dialog = ref({visible: false, title: ''});
const roles = ref([]);
const tableData = ref([]);
const selectionIds = ref([]);
const formData = ref({});

const dialogForm = ref();
const rules = ref({
    username: [{required: true, message: '用户名为必填项', trigger: 'blur'}, {
        pattern: /^[a-zA-Z0-9_-]{4,16}$/,
        message: '手机号不合法',
        trigger: 'change'
    }],
    mobile: [{
        pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
        message: '手机号不合法',
        trigger: 'change'
    }],
    email: [{
        pattern: /^([a-zA-Z0-9\._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/,
        message: '邮箱地址不合法',
        trigger: 'change'
    }]
});

onMounted(() => {
    pageQuery()
})

/**
 * 分页查询用户信息
 */
function pageQuery() {
    axios({
        url: '/api/user/list',
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
        }
    })
}

/**
 * 选中的数据改变
 * @param {*} selection
 */
function selectionChange(selection) {
    selectionIds.value = selection.map(select => select.id)
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
 * 禁用/启用用户
 * @param {*} row
 */
function changeStatus(row) {
    axios({
        url: '/api/user/status',
        data: {'id': row.id, 'status': row.status},
        method: 'PUT'
    }).then(({data}) => {
        if (data && data.code == 200) {
            ElNotification.success({title: '成功', message: '修改成功！'})
            return true;
        } else {
            ElNotification.error({title: '失败', message: '操作失败！'})
            return false;
        }
    })
}

/**
 * 点击 “新增用户” 按钮
 */
function handleAdd() {
    formData.value = {};
    axios.get('/api/role/list/info').then(({data}) => {
        if (data && data.code == 200) {
            roles.value = data.roles;
        } else {
            ElMessage.error('获取角色列表失败！');
        }
    }).finally(() => {
        dialog.value = {visible: true, title: '新增用户'}
    })
}

/**
 * 点击 “编辑” 按钮
 */
function handleEdit(row) {
    formData.value = {}
    Object.assign(formData.value, row)
    axios.get(`/api/user/role/${row.id}`).then(({data}) => {
        if (data && data.code == 200) {
            formData.value.roles = data.roleIds;
            roles.value = data.roles;
        } else {
            ElMessage.error('获取角色列表失败！');
        }
    }).finally(() => {
        dialog.value = {visible: true, title: '编辑用户信息'}
    })
}

/**
 * 点击 “确认” 按钮
 */
function handleOK() {
    // 校验表单，校验通过才发送请求
    dialogForm.value.validate((valid) => {
        if (valid) {
            axios({
                url: '/api/user',
                data: formData.value,
                method: dialog.value.title == '新增用户' ? 'POST' : 'PUT'
            }).then(({data}) => {
                if (data && data.code == 200) {
                    ElNotification.success({title: '成功', message: `${dialog.value.title}成功！`})
                    dialog.value = {visible: false, title: ''};
                    pageQuery();
                } else {
                    ElNotification.error({title: '失败', message: `${dialog.value.title}失败！` + data.msg})
                }
            })
        }
    })
}

/**
 * 删除用户信息
 * @param {*} userId
 */
function handleDelete(userId) {
    let param = ''
    if (userId) {
        param = `${userId}`
    } else {
        param = `/batch?userId=${selectionIds.value[0]}`
        for (let i = 1; i < selectionIds.value.length; i++) {
            param += `&userId=${selectionIds.value[i]}`
        }
    }
    axios({
        url: `/api/user/${param}`,
        method: 'DELETE'
    }).then(({data}) => {
        if (data && data.code == 200) {
            ElMessage.success('删除成功')
        }
    }).finally(() => {
        pageQuery();
    })
}
</script>

<template>
  <!-- 标题栏 -->
    <div class="content-title">
        <span>用户列表</span>
    </div>
  <!-- 按钮栏 -->
    <div class="operation-container">
        <div class="operation-left">
            <el-button :icon="Plus" type="primary" @click="handleAdd">新增用户</el-button>
            <el-button :disabled="selectionIds.length == 0" :icon="Delete" type="danger"
                       @click="handleDelete()">批量删除
            </el-button>
        </div>
        <div class="operation-right">
            <el-input v-model="queryString" :prefix-icon="Search" placeholder="用户名/手机号/邮箱"/>
            <el-button :icon="Search" style="margin-left: 10px;" type="primary" @click="pageQuery">搜索</el-button>
        </div>
    </div>
  <!-- 内容区 -->
    <el-table :data="tableData" border style="width: 100%" @selection-change="selectionChange">
        <el-table-column align="center" fixed="left" type="selection" width="55"/>
        <el-table-column align="center" label="用户名" property="username" width="120"/>
        <el-table-column align="center" label="头像" min-width="60" property="avatar">
            <template #default="scope">
                <el-avatar :size="40" :src="scope.row.avatar"/>
            </template>
        </el-table-column>
        <el-table-column align="center" label="昵称" min-width="120" property="nickname"/>
        <el-table-column align="center" label="性别" property="gender" width="60">
            <template #default="scope">{{ scope.row.gender == 0 ? '男' : '女' }}</template>
        </el-table-column>
        <el-table-column align="center" label="邮箱" min-width="120" property="email"/>
        <el-table-column align="center" label="手机号" property="mobile" width="110"/>
        <el-table-column align="center" label="状态" property="status" width="75">
            <template #default="scope">
                <el-switch v-model="scope.row.status" :active-value="0" :inactive-value="1" active-text="启用"
                           inactive-text="禁用"
                           inline-prompt @change="changeStatus(scope.row)"/>
            </template>
        </el-table-column>
        <el-table-column align="center" label="注册时间" min-width="95" property="registTime">
            <template #default="scope">{{ dayjs(scope.row.registTime).format("YYYY-MM-DD HH:mm:ss") }}</template>
        </el-table-column>
        <el-table-column align="center" label="上次登录" min-width="95" property="loginTime">
            <template #default="scope">
                {{ scope.row.loginTime ? dayjs(scope.row.loginTime).format("YYYY-MM-DD HH:mm:ss") : '' }}
            </template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" width="100">
            <template #default="scope">
                <el-button :icon="Edit" circle size="small" type="primary" @click="handleEdit(scope.row)"/>
                <el-button :icon="Delete" circle size="small" type="danger" @click="handleDelete(scope.row.id)"/>
            </template>
        </el-table-column>
    </el-table>
  <!-- 分页区 -->
    <el-pagination :current-page="page.currentPage" :page-size="page.pageSize" :page-sizes="[10, 25, 50, 100]"
                   :pager-count="7" :total="page.total" background
                   layout="total, sizes, prev, pager, next, jumper"
                   @size-change="sizeChange" @current-change="pageChange"/>
  <!-- 弹框 -->
    <el-dialog v-model="dialog.visible" :title="dialog.title" destroy-on-close draggable width="35%">
        <el-form ref="dialogForm" :model="formData" :rules="rules" status-icon style="padding-inline: 20px">
            <el-row :gutter="20">
                <el-col :md="12" style="margin-left: -11px">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="formData.username"/>
                    </el-form-item>
                </el-col>
                <el-col :md="12">
                    <el-form-item label="昵称" pror="nickname">
                        <el-input v-model="formData.nickname"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item label="性别" pror="gender">
                <el-radio-group v-model="formData.gender" style="margin-left: 10px">
                    <el-radio :label="0" style="margin-right: 20px;">男</el-radio>
                    <el-radio :label="1">女</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="头像" prop="avatar">
                <AvatarUploader v-model="formData.avatar" style="margin-left: 10px"/>
            </el-form-item>
            <el-row :gutter="20">
                <el-col :md="12">
                    <el-form-item label="手机号" prop="mobile">
                        <el-input v-model="formData.mobile"/>
                    </el-form-item>
                </el-col>
                <el-col :md="12">
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="formData.email"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-form-item label="角色">
                    <el-checkbox-group v-model="formData.roles">
                        <el-checkbox v-for="role in roles" :key="role.id" :label="role.id">
                            {{ role.name }}
                        </el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
            </el-row>
        </el-form>
        <template #footer>
            <el-button type="primary" @click="handleOK">确认</el-button>
            <el-button @click="dialog.visible = false">取消</el-button>
        </template>
    </el-dialog>
</template>

<style scoped></style>