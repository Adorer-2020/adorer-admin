<script setup>
import Header from './components/Header.vue'
import NewsCard from './components/NewsCard.vue';
import TodoCard from './components/TodoCard.vue';
import NavCard from './components/NavCard.vue';
import MessageCard from './components/MessageCard.vue';
</script>

<template>
    <div class="welcome-page-container">
        <el-row>
            <Header/>
        </el-row>
        <el-row :gutter="20">
            <el-col :md="16">
                <NewsCard/>
                <TodoCard/>
            </el-col>
            <el-col :md="8">
                <NavCard/>
                <MessageCard/>
            </el-col>
        </el-row>
    </div>
</template>

<style scoped>
.welcome-page-container {
    padding-inline: 20px;
    padding-bottom: 100px;
}

.welcome-page-container :deep(.el-card) {
    width: 100%;
    margin-top: 20px;
    border-radius: 0;
}

.welcome-page-container :deep(.el-card__header) {
    display: flex;
    justify-content: space-between;
    font-size: 16px;
    font-weight: 500;
}

.welcome-page-container :deep(.card-header-left) {
    display: inline-flex;
    align-items: center;
}

.welcome-page-container :deep(.card-header-left) svg {
    font-size: 20px;
}

.welcome-page-container :deep(.card-header-left) h3 {
    margin-left: 8px;
}
</style>