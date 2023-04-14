<script setup>
import {computed, onMounted} from 'vue';
import {ElNotification} from 'element-plus';
import {
    ArrowLeftBold,
    ArrowRightBold,
    CloseBold,
    RemoveFilled,
    SemiSelect,
    Tools,
    Unlock,
    UserFilled
} from '@element-plus/icons-vue';
import {useStore, useUserStore} from '@/stores';
import {useRoute} from 'vue-router';
import axios from '@/utils/request';

const route = useRoute();
const store = useStore();
const userStore = useUserStore();

onMounted(() => {
    // 获取基本用户信息
    axios.get('/api/user/info').then(({data}) => {
        if (data && data.code === 200) {
            userStore.setUserInfo(data.info)
        } else {
            ElNotification.error({title: '失败', message: '获取用户基本信息失败！'})
        }
    })
})

// 面包屑
const breadcrumbList = computed(() => {
    return route.matched.map(item => item.meta.title).filter(item => item !== '后台管理系统');
})
</script>

<template>
    <!-- 顶部信息栏 -->
    <div class="header-container">
        <!-- 左侧菜单 -->
        <div class="header-left">
            <!-- 折叠按钮 -->
            <div class="header-btn" @click="store.collapse = !store.collapse">
                <i :class="'iconfont icon-' + (store.collapse ? 'right' : 'left')"/>
            </div>
            <!-- 面包屑导航 -->
            <el-breadcrumb separator="/">
                <el-breadcrumb-item v-for="item of breadcrumbList" :key="item.path">
                    {{ item }}
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <!-- 右侧菜单 -->
        <div class="header-right">
            <!-- 消息按钮 -->
            <div class="header-btn">
                <i class="iconfont icon-search"/>
            </div>
            <!-- 消息按钮 -->
            <div class="header-btn">
                <i class="iconfont icon-message"/>
            </div>
            <!-- 全屏按钮 -->
            <div class="header-btn">
                <i class="iconfont icon-full-screen"/>
            </div>
            <!-- 用户头像 -->
            <el-dropdown style="height: 100%">
                <div class="user-dropdown">
                    <el-avatar :size="28" :src="userStore.getAvatar"/>
                    <span style="margin-left: 5px">{{ userStore.getNickname }}</span>
                </div>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item :icon="UserFilled">个人中心</el-dropdown-item>
                        <el-dropdown-item :icon="Tools">修改密码</el-dropdown-item>
                        <el-divider/>
                        <el-dropdown-item :icon="Unlock">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
            <!-- 设置按钮 -->
            <div class="header-btn">
                <i class="iconfont icon-setting"/>
            </div>
        </div>
    </div>
  <!-- 历史标签栏 -->
    <div class="tabs-container">
        <div class="tabs-wrapper">
            <el-scrollbar>
                <el-tag v-for="tab in store.tabs" :key="tab.path" :closable="tab.path !== '/workspace/welcome'"
                        :effect="store.activeTab.path === tab.path ? 'dark' : 'light'"
                        class='tabs-view-item'
                        @click="$router.push({ path: tab.path })"
                        @close="store.refreshTabList(tab, 'remove')">
                    {{ tab.name }}
                </el-tag>
            </el-scrollbar>
        </div>
        <div class="tabs-right-menu" style="float:right" @click="closeAllTab">
            <!-- 刷新按钮 -->
            <div class="header-btn">
                <i class="iconfont icon-refresh"/>
            </div>
            <!-- 下拉按钮 -->
            <el-dropdown>
                <div class="header-btn">
                    <i class="iconfont icon-down"/>
                </div>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item :icon="CloseBold" @click="store.refreshTabList(store.activeTab, 'remove')">
                            关闭当前标签页
                        </el-dropdown-item>
                        <el-divider/>
                        <el-dropdown-item :icon="ArrowLeftBold"
                                          @click="store.refreshTabList(store.activeTab, 'removeLeft')">
                            关闭左侧标签页
                        </el-dropdown-item>
                        <el-dropdown-item :icon="ArrowRightBold"
                                          @click="store.refreshTabList(store.activeTab, 'removeRight')">
                            关闭右侧标签页
                        </el-dropdown-item>
                        <el-divider/>
                        <el-dropdown-item :icon="SemiSelect"
                                          @click="store.refreshTabList(store.activeTab, 'removeOther')">
                            关闭其他标签页
                        </el-dropdown-item>
                        <el-dropdown-item :icon="RemoveFilled"
                                          @click="store.refreshTabList(store.activeTab, 'removeAll')">
                            关闭所有标签页
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
            <!-- 全屏按钮 -->
            <div class="header-btn">
                <i class="iconfont icon-full-screen"/>
            </div>
        </div>
    </div>
</template>

<style scoped>
.header-container {
    display: flex;
    height: 48px;
    padding: 0;
    line-height: 48px;
    background-color: #fff;
    align-items: center;
    justify-content: space-between;
}

.header-left,
.header-right {
    display: flex;
    height: 100%;
    align-items: center;
}

.header-btn {
    display: flex;
    height: 100%;
    padding-inline: 10px;
    cursor: pointer;
    align-items: center;
}

.header-btn:hover,
.user-dropdown:hover {
    background-color: whitesmoke;
}

.user-dropdown {
    display: flex;
    align-items: center;
    height: 100%;
    padding-inline: 10px;
    overflow: hidden;
    font-size: 14px;
    cursor: pointer;
}

.tabs-container {
    display: flex;
    position: relative;
    height: 30px;
    background: #fff;
    align-items: center;
    border-block: 1px solid #d8dce5;
}

.tabs-wrapper {
    overflow-x: auto;
    white-space: nowrap;
    width: 95%;
    position: relative;
}

.el-scrollbar {
    overflow-x: visible;
}

.tabs-wrapper :deep(.el-scrollbar__bar) {
    position: absolute;
    right: 2px;
    bottom: -1px;
    z-index: 1;
    border-radius: 4px;
}

.el-tag {
    border-radius: 0;
}

.tabs-view-item {
    display: inline-flex;
    cursor: pointer;
    height: 24px;
    line-height: 24px;
    padding: 0 8px;
    margin-left: 6px;
    font-size: 12px;
}


.tabs-right-menu {
    display: inline-flex;
    cursor: pointer;
    height: 30px;
    line-height: 30px;
    color: #495060;
    background: #fff;
    font-size: 12px;
}

.tabs-right-menu .header-btn {
    border-left: 1px solid #d8dce5;
}

.el-divider {
    margin-block: 3px !important;
}
</style>