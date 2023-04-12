<script setup>
import {ref} from 'vue';
import SvgIcon from '@/components/SvgIcon.vue';

const navigations = ref([
    {icon: 'analyse', color: '#E5796A', label: '分析页', path: '/workspace/analyse'},
    {icon: 'publish', color: '#3DD2B8', label: '发布文章', path: '/article/edit'},
    {icon: 'attribute', color: '#3550D8', label: '文章列表', path: '/article/list'},
    {icon: 'home', color: '#24D1B2', label: '前台管理', path: '/website/mainPage'},
    {icon: 'page-add', color: '#B4BFF1', label: '发表说说', path: '/talk/edit'},
    {icon: 'personal', color: '#E9D759', label: '个人中心', path: '/user/info'}
])
</script>

<template>
    <el-card class="navigation-card" shadow="never">
        <template #header>
            <div class="card-header-left">
                <SvgIcon name="position"/>
                <h3>快捷导航</h3>
            </div>
        </template>
        <el-row>
            <el-col v-for="navigation in navigations" :key="navigation.path" :span="8">
                <RouterLink :to="{ path: navigation.path }">
                    <el-card class="navigation-item" shadow="hover" style="margin: 0;">
                        <div class="navigation-item-inner">
                            <div style="margin-inline: auto">
                                <i :class="'iconfont icon-' + navigation.icon" :style="{ color: navigation.color }"
                                   style="font-size: 20px"/>
                            </div>
                            <span style="font-size: 14px">{{ navigation.label }}</span>
                        </div>
                    </el-card>
                </RouterLink>
            </el-col>
        </el-row>
    </el-card>
</template>

<style scoped>
.navigation-card :deep(.el-card__body) {
    padding: 0;
}

.navigation-item {
    margin: -1px !important;
    padding-block: 20px;
}

.navigation-item-inner {
    width: 100%;
    height: 100%;
    display: block;
    align-items: center;
    text-align: center;
}
</style>