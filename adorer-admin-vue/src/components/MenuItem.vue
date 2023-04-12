<script setup>
defineProps({menu: Object})
</script>

<template>
    <el-sub-menu v-if="menu.children && menu.children.length >= 1" :index="menu.path" role="menu">
        <template #title>
            <i :class="`iconfont icon-${menu.icon}`" class="menu-icon"/>
            <span style="line-height:50px">{{ menu.name }}</span>
        </template>
        <menu-item v-for="item in menu.children" :key="item.id" :menu="item"/>
    </el-sub-menu>
    <template v-else>
        <el-menu-item v-if="menu.type == 0" :index="menu.path">
            <i :class="`iconfont icon-${menu.icon}`" class="menu-icon"/>
            <span slot="title" style="line-height:50px">{{ menu.name }}</span>
        </el-menu-item>
    </template>
</template>

<style>
.menu-icon {
    display: flex;
    height: 100%;
    align-items: center;
    padding-inline: 4px;
    margin-right: 5px;
}
</style>