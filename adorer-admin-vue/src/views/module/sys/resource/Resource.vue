<script setup>
import {onMounted, ref} from 'vue';
import {ElMessage} from 'element-plus';
import {Delete, Edit, Plus, Search} from '@element-plus/icons-vue'
import dayjs from 'dayjs'
import axios from '@/utils/request'

onMounted(() => {
    pageQuery()
})

const queryString = ref('')
const dialog = ref({visible: false, title: ''});
const tableData = ref([])
const formData = ref({})
const tags = {'GET': '', 'POST': 'success', 'PUT': 'warning', 'DELETE': 'danger'}

function pageQuery() {
    axios.get('/api/resource/tree').then(({data}) => {
        if (data && data.code == 200) {
            tableData.value = data.resources;
        }
    })
}

/**
 * 修改资源访问状态
 */
function changeStatus(row) {
    axios.put('/api/resource', {id: row.id, status: row.status}).then(({data}) => {
        if (data && data.code == 200) {
            ElMessage.success('修改成功！')
        } else {
            ElMessage.error('修改失败！');
        }
    })
}

function handleAdd(row) {
    formData.value = {};
    if (row) {
        formData.value.parentId = row.id;
        dialog.value = {visible: true, title: '新增资源'}
    } else {
        dialog.value = {visible: true, title: '新增模块'}
    }
}

function handleEdit(row) {
    formData.value = row;
    dialog.value = {visible: true, title: row.children ? '修改资源' : '修改模块'}
}

function handleOK() {
    axios({
        url: '/api/resource',
        data: formData.value,
        method: dialog.value.title.startsWith('新增') ? 'POST' : 'PUT'
    }).then(({data}) => {
        if (data && data.code == 200) {
            ElMessage.success(data.msg)
        }
    }).finally(() => {
        dialog.value = {visible: false, title: ''};
        pageQuery();
    })
}

function handleDelete(userId) {
    axios.delete(`/api/resource/${userId}`).then(({data}) => {
        if (data && data.code == 200) {
            ElMessage.success('删除成功')
        }
    }).finally(() => {
        pageQuery();
    })
}
</script>

<template>
    <div class="content-title">
        <span>资源管理</span>
    </div>
  <!-- 标题/按钮栏 -->
    <div class="operation-container">
        <div class="operation-left">
            <el-button :icon="Plus" type="primary" @click="handleAdd()">新增模块</el-button>
        </div>
        <div class="operation-right">
            <el-input v-model="queryString" :prefix-icon="Search" placeholder="资源名/访问路径"/>
            <el-button :icon="Search" style="margin-left: 10px;" type="primary" @click="pageQuery">搜索</el-button>
        </div>
    </div>
  <!-- 内容区 -->
    <el-table :data="tableData" row-key="id" style="width: 100%">
        <el-table-column align="left" label="资源名" min-width="220" property="name"/>
        <el-table-column align="center" label="资源路径" min-width="300" property="url"/>
        <el-table-column align="center" label="请求方式" min-width="100" property="method">
            <template #default="scope">
                <el-tag v-if="scope.row.method" :type="tags[scope.row.method]">{{ scope.row.method }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column align="center" label="匿名访问" min-width="80" property="isAnonymous">
            <template #default="scope">
                <el-switch v-if="scope.row.parentId != 0" v-model="scope.row.isAnonymous" :active-value="1"
                           :inactive-value="0"
                           active-text="允许" inactive-text="禁止" inline-prompt
                           @change="changeStatus(scope.row)"/>
                <span v-else/>
            </template>
        </el-table-column>
        <el-table-column align="center" label="创建时间" min-width="150" property="createTime">
            <template #default="scope">{{ dayjs(scope.row.createTime).format("YYYY-MM-DD HH:mm:ss") }}</template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" width="150">
            <template #default="scope">
                <el-button v-if="scope.row.parentId == 0" :icon="Plus" circle size="small" type="primary"
                           @click="handleAdd(scope.row)"/>
                <el-button :icon="Edit" circle size="small" type="primary" @click="handleEdit(scope.row)"/>
                <el-button :icon="Delete" circle size="small" type="danger" @click="handleDelete(scope.row.id)"/>
            </template>
        </el-table-column>
    </el-table>
  <!-- 新增模块弹框 -->
    <el-dialog v-model="dialog.visible" :title="dialog.title" draggable width="35%">
        <el-form :model="formData" label-width="80px">
            <el-form-item :label="dialog.title.substring(2, 4) + '名'">
                <el-input v-model="formData.name" style="width:220px"/>
            </el-form-item>
            <el-form-item v-if="dialog.title.endsWith('资源')" label="资源路径">
                <el-input v-model="formData.url" style="width:220px"/>
            </el-form-item>
            <el-form-item v-if="dialog.title.endsWith('资源')" label="请求方式">
                <el-radio-group v-model="formData.method">
                    <el-radio :label="'GET'">GET</el-radio>
                    <el-radio :label="'POST'">POST</el-radio>
                    <el-radio :label="'PUT'">PUT</el-radio>
                    <el-radio :label="'DELETE'">DELETE</el-radio>
                </el-radio-group>
            </el-form-item>
        </el-form>
        <template #footer>
            <el-button type="primary" @click="handleOK()">确认</el-button>
            <el-button @click="dialog.visible = false">取消</el-button>
        </template>
    </el-dialog>
</template>


<style scoped></style>