<script setup>
import {useNavStore, useStore} from 'adorer-admin-vue/src/stores';
import MenuItem from '@/components/MenuItem.vue';

const store = useStore();
const navStore = useNavStore();
</script>

<template>
  <!-- logo -->
    <div class="logo-container" @click="$router.push({ path: '/' })">
        <img alt="logo" src="@/assets/imgs/logo.png">
        <div class="logo-title">后台管理系统</div>
    </div>
  <!-- 菜单 -->
    <el-scrollbar>
        <el-menu :collapse="store.collapse" :default-active="$route.path" class='menu-container' collapse-transition
                 router unique-openeds>
            <MenuItem v-for="menu in navStore.menus" :key="menu.id" :menu="menu"/>
        </el-menu>
    </el-scrollbar>
</template>

<style scoped>
.logo-container {
    display: flex;
    align-items: center;
    height: 48px;
    padding-inline: 16px;
    cursor: pointer;
    background-color: #001529;
}

.logo-container img {
    width: 24px;
    height: 24px;
    padding-inline: 4px;
}

.logo-container .logo-title {
    margin-left: 5px;
    color: #fff;
    font-size: 16px;
    font-weight: 700;
    line-height: normal;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.menu-container {
    height: 100%;
    border: none;
    overflow-x: hidden;
    overflow-y: auto;
}

.menu-container :deep(*:hover) {
    color: #fff !important;
}

.menu-container :deep(.el-sub-menu__title) {
    background: #001529 !important;
}

.menu-container :deep(.el-sub-menu__title:hover) {
    background: #0c2135 !important;
}

.menu-container :deep(.is-active) {
    color: #fff;
    background: #0960bd;
}

.el-scrollbar {
    background: #001529;
    height: calc(100vh - 48px) !important;
}
</style>