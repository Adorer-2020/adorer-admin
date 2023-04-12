<script setup>
import SideBar from './components/SideBar.vue';
import NavBar from './components/NavBar.vue';
import Content from './components/Content.vue';

import {useStore} from '@/store'

const store = useStore();
</script>

<template>
    <el-container class="main-container">
        <!-- 侧边栏（菜单） -->
        <el-aside :width="store.collapse ? '64px' : '210px'" class="side-container">
            <SideBar/>
        </el-aside>
        <el-container style="height: 100vh">
            <!-- 顶部导航栏 -->
            <el-header class="header-container">
                <NavBar/>
            </el-header>
            <!-- 页面主题 -->
            <el-main class="content-container">
                <Content/>
            </el-main>
        </el-container>
    </el-container>
</template>

<style scoped>
.main-container {
    width: 100vw;
    height: 100vh;
    color: #000000d9;
    background-color: #F6F6F8;
}

.side-container {
    height: 100vh;
    transition: all .5s;
}

.header-container {
    height: 80px;
    padding: 0;
}

.content-container {
    padding: 0;
}
</style>
