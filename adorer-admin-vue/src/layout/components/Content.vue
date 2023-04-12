<template>
    <el-scrollbar height="calc(100vh - 80px)">
        <el-card v-if="!$route.path.startsWith('/workspace')" class="content-body">
            <!-- <router-view v-slot="{ Component }">
              <keep-alive>
                <component :is="Component" />
              </keep-alive>
                  </router-view> -->
            <router-view/>
        </el-card>
        <router-view v-else/>
    </el-scrollbar>
</template>

<style scoped>
.content-body {
    margin: 14px;
    min-height: calc(100vh - 110px);
}
</style>