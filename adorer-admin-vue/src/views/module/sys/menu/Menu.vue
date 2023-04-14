<script setup>
import icons from '@/assets/json/icon.json'
import {onMounted, ref, watch} from 'vue';
import {ElMessage, ElNotification} from 'element-plus'
import {Delete, Edit, Plus, Search} from '@element-plus/icons-vue'
import {useStore} from 'adorer-admin-vue/src/stores'
import axios from '@/utils/request';
import dayjs from 'dayjs';

const store = useStore();
const queryString = ref('');
const dialog = ref({visible: false, title: ''});
const formData = ref({});
const menuList = ref([]);
const tableData = ref([]);
const formRef = ref();
const rules = ref({
    name: [{required: true, message: '请输入菜单名称', trigger: 'blur'}],
    parentId: [{required: false, message: '请选择父菜单', trigger: 'blur'}],
    icon: [{required: true, message: '请选择菜单图标', trigger: 'blur'}],
    status: [{required: true, message: '请选择菜单状态', trigger: 'blur'}],
    path: [{required: true, message: '请输入路由地址', trigger: 'blur'}],
    component: []
})

onMounted(() => {
    query()
})

/**
 * 监听 queryString, 当 queryString 为空时自动重新查数据
 */
watch(queryString, (newValue) => {
    if (newValue === '') {
        query()
    }
})

/**
 * 获取树形菜单列表
 */
function query() {
    axios({
        url: '/api/menu/tree',
        params: {queryString: queryString.value},
        method: 'GET'
    }).then(({data}) => {
        if (data && data.code === 200) {
            tableData.value = data.menuList;
        }
    })
}

/**
 * 获取菜单列表
 */
function getMenuList() {
    axios.get('/api/menu/list').then(({data}) => {
        if (data && data.code === 200) {
            menuList.value = data.menuList;
        }
    })
}

/**
 * 点击新增菜单按钮
 */
function handleAdd() {
    formData.value = {};
    getMenuList();
    dialog.value = {visible: true, title: '新增菜单'};
}

/**
 * 点击编辑菜单按钮
 */
function handlerEdit(row) {
    getMenuList();
    formData.value = {};
    Object.assign(formData.value, row);
    dialog.value = {visible: true, title: '编辑菜单'};
}

/**
 * 点击确认按钮
 */
function handlerOK() {
    const method = dialog.value.title === '新增菜单' ? 'POST' : 'PUT'
    axios({
        url: '/api/menu',
        data: formData.value,
        method
    }).then(({data}) => {
        if (data && data.code === 200) {
            ElMessage.success(data.msg)
        }
    }).finally(() => {
        dialog.value = {visible: false, title: ''};
        query();
        store.setMenuList(tableData.value);
    })
}

/**
 * 点击删除按钮
 */
function handlerDelete() {
    ElNotification.error({title: '失败', message: '菜单使用中，删除失败！'})
}
</script>

<template>
  <!-- 标题 -->
    <div class="content-title">
        <span>菜单管理</span>
    </div>
  <!-- 操作按钮 -->
    <div class="operation-container">
        <div class="operation-left">
            <el-button :icon="Plus" type="primary" @click="handleAdd">新增菜单</el-button>
        </div>
        <div class="operation-right">
            <el-input v-model="queryString" :prefix-icon="Search" placeholder="菜单名" @keyup.enter.native="query"/>
            <el-button :icon="Search" style="margin-left: 10px;" type="primary" @click="query">搜索</el-button>
        </div>
    </div>
  <!-- 内容区 -->
    <el-table :data="tableData" border row-key="id" style="width: 100%">
        <el-table-column align="left" label="菜单名称" property="name" width="200"/>
        <el-table-column align="center" label="图标" property="avatar" width="55">
            <template #default="scope"><i :class="`iconfont icon-${scope.row.icon}`"/></template>
        </el-table-column>
        <el-table-column align="center" label="路由地址" min-width="120" property="path"/>
        <el-table-column align="center" label="组件路径" min-width="120" property="component"/>
        <el-table-column align="center" label="状态" property="status" width="70">
            <template #default="scope">
                <el-tag v-if="scope.row.status === 0" type="success">启用</el-tag>
                <el-tag v-else type="danger">禁用</el-tag>
            </template>
        </el-table-column>
        <el-table-column align="center" label="排序" property="sort" width="55"/>
        <el-table-column align="center" label="创建时间" min-width="95" property="createTime">
            <template #default="scope">{{ dayjs(scope.row.createTime).format("YYYY-MM-DD HH:mm:ss") }}</template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" width="100">
            <template #default="scope">
                <el-button :icon="Edit" circle size="small" type="primary" @click="handlerEdit(scope.row)"/>
                <el-button :icon="Delete" circle size="small" type="danger" @click="handlerDelete(scope.row.id)"/>
            </template>
        </el-table-column>
    </el-table>
  <!-- 弹框 -->
    <el-dialog v-model="dialog.visible" :title="dialog.title" destroy-on-close draggable>
        <el-form ref="formRef" :model="formData" :rules="rules">
            <el-row :gutter="40">
                <el-col :md="12">
                    <el-form-item label="菜单名称" prop="name">
                        <el-input v-model="formData.name"/>
                    </el-form-item>
                </el-col>
                <el-col :md="12">
                    <el-form-item label="父菜单" prop="parentId">
                        <el-select v-model="formData.parentId">
                            <el-option :value="0" label="无"/>
                            <el-option v-for="item in menuList" :key="item.value" :label="item.name" :value="item.id"/>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="40">
                <el-col :md="10">
                    <el-form-item label="菜单图标" prop="icon">
                        <el-select v-model="formData.icon">
                            <template #prefix>
                                <i :class="'iconfont icon-' + formData.icon"/>
                            </template>
                            <el-option v-for="icon in icons" :key="icon.name" :value="icon.fontClass">
                                <i :class="'iconfont icon-' + icon.fontClass"/>
                                <span style="margin-left: 15px">{{ icon.name }}</span>
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :md="6">
                    <el-form-item label="菜单状态" prop="status">
                        <el-switch v-model="formData.status" :active-value="0" :inactive-value="1" active-text="启用"
                                   inactive-text="禁用"
                                   inline-prompt/>
                    </el-form-item>
                </el-col>
                <el-col :md="6">
                    <el-form-item label="排序" prop="sort">
                        <el-input v-model="formData.sort" type="number"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="40">
                <el-col :md="12">
                    <el-form-item label="路由地址" prop="path">
                        <el-input v-model="formData.path"/>
                    </el-form-item>
                </el-col>
                <el-col :md="12">
                    <el-form-item label="组件位置" prot="component">
                        <el-input v-model="formData.component"/>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <template #footer>
            <el-button type="primary" @click="handlerOK">确认</el-button>
            <el-button @click="dialog.visible = false">取消</el-button>
        </template>
    </el-dialog>
</template>

<style scoped></style>