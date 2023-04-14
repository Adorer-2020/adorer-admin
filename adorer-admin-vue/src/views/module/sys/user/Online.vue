<script setup>
import {onMounted, ref} from 'vue';
import {Search, Tickets} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus';

import axios from '@/utils/request'
import dayjs from 'dayjs'

onMounted(() => {
    pageQuery()
})

const queryString = ref('')
const page = ref({currentPage: 1, pageSize: 10, total: 0})
const tableData = ref([])
const selectionIds = ref([])

function pageQuery() {
    axios({
        url: '/api/user/online',
        params: {
            'currentPage': page.value.currentPage,
            'pageSize': page.value.pageSize,
            'queryString': queryString.value
        },
        method: 'GET'
    }).then(({data}) => {
        if (data && data.code === 200) {
            tableData.value = data.userList;
            page.value.total = data.total;
        }
    })
}

function selectionChange(selection) {
    selectionIds.value = selection.map(select => select.id)
}

function handleDelete(userId) {
    let param;
    if (userId) {
        param = `${userId}`;
    } else {
        param = `/batch?userId=${selectionIds.value[0]}`;
        for (let i = 1; i < selectionIds.value.length; i++) {
            param += `&userId=${selectionIds.value[i]}`;
        }
    }
    axios({
        url: `/api/user/online/${param}`,
        method: 'DELETE'
    }).then(({data}) => {
        if (data && data.code === 200) {
            ElMessage.success('下线成功')
        }
    }).finally(() => {
        pageQuery();
    })
}

function sizeChange(pageSize) {
    page.value.pageSize = pageSize;
    pageQuery();
}

function pageChange(currentPage) {
    page.value.currentPage = currentPage;
    pageQuery();
}


</script>

<template>
    <div class="content-title">
        <span>在线用户</span>
    </div>
  <!-- 标题/按钮栏 -->
    <div class="operation-container">
        <div class="operation-left">
            <el-button :disabled="selectionIds.length === 0" :icon="Tickets" type="primary"
                       @click="handleDelete()">全部下线
            </el-button>
        </div>
        <div class="operation-right">
            <el-input v-model="queryString" :prefix-icon="Search" placeholder="用户名 / 昵称"/>
            <el-button :icon="Search" style="margin-left: 10px;" type="primary" @click="pageQuery()">搜索</el-button>
        </div>
    </div>
  <!-- 内容区 -->
    <el-table :data="tableData" border style="width: 100%" @selection-change="selectionChange">
        <el-table-column align="center" fixed="left" type="selection" width="55"/>
        <el-table-column align="center" label="用户名" min-width="80" property="username"/>
        <el-table-column align="center" label="头像" min-width="60" property="avatar">
            <template #default="scope">
                <el-avatar :size="40" :src="scope.row.avatar"/>
            </template>
        </el-table-column>
        <el-table-column align="center" label="昵称" min-width="80" property="nickname"/>
        <el-table-column align="center" label="登录方式" min-width="90" property="type">
            <template #default="scope">
                <el-tag>邮箱登录</el-tag>
            </template>
        </el-table-column>
        <el-table-column align="center" label="ip地址" min-width="120" property="ip"/>
        <el-table-column align="center" label="浏览器" min-width="120" property="browser"/>
        <el-table-column align="center" label="操作系统" min-width="120" property="OS"/>
        <el-table-column align="center" label="登录时间" min-width="110" property="loginTime">
            <template #default="scope">{{
                scope.row.loginTime ? dayjs(scope.row.loginTime).format("YYYY-MM-DD HH:mm:ss") : ''
                }}
            </template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" width="100">
            <template #default="scope">
                <el-button size="small" text type="primary" @click="handleDelete(scope.row.id)">下线</el-button>
            </template>
        </el-table-column>
    </el-table>
  <!-- 分页区 -->
    <el-pagination :current-page="page.currentPage" :page-size="page.pageSize" :page-sizes="[10, 25, 50, 100]"
                   :pager-count="7" :total="page.total" background
                   layout="total, sizes, prev, pager, next, jumper"
                   @size-change="sizeChange()" @current-change="pageChange()"/>
</template>

<style scoped></style>