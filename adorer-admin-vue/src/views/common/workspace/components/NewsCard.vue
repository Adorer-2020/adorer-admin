<script setup>
import {onMounted, ref} from 'vue';
import SvgIcon from '@/components/SvgIcon.vue'
import axios from '@/utils/request'

const news = ref([]);     // 新闻数据

onMounted(() => {
    getNews();
})

/**
 * 获取新闻
 */
function getNews() {
    axios.get('/api/welcome/news').then(({data}) => {
        if (data && data.code == 200) {
            news.value = data.news;
        } else {
            ElMessage.error('获取热点新闻数据失败！')
        }
    })
}
</script>

<template>
    <el-card class="news-card" shadow="never">
        <template #header>
            <div class="card-header-left">
                <SvgIcon name="hot"/>
                <h3>热点新闻</h3>
            </div>
            <el-button link type="primary">更多</el-button>
        </template>
        <el-table :data="news" :show-header="true" height="400" style="width: 100%">
            <el-table-column align="center" label="来源" min-width="80" prop="src">
                <template #default="scoped">
                    <span class="one-line">{{ scoped.row.src }}</span>
                </template>
            </el-table-column>
            <el-table-column header-align="center" label="标题" min-width="100" prop="title" show-overflow-tooltip>
                <template #default="scoped">
                    <el-link :href="scoped.row.weburl" :underline="false" class="one-line" target="_blank">
                        {{ scoped.row.title }}
                    </el-link>
                </template>
            </el-table-column>
            <el-table-column align="center" label="时间" prop="time">
                <template #default="scoped">
                    <span>{{ scoped.row.time.substring(0, 10) }}</span>
                </template>
            </el-table-column>
            <el-table-column align="center" label="操作" prop="url" width="60">
                <template #default="scoped">
                    <el-link :href="scoped.row.weburl" :underline="false" target="_blank" type="info">详情</el-link>
                </template>
            </el-table-column>
        </el-table>
    </el-card>
</template>

<style scoped>
.news-card :deep(.el-card__body) {
    padding: 0;
}

.one-line {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
</style>