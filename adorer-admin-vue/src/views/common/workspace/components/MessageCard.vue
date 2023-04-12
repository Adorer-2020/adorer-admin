<script setup>
import SvgIcon from '@/components/SvgIcon.vue'
</script>

<template>
    <el-card class="message-card" shadow="never">
        <template #header>
            <div class="card-header-left">
                <SvgIcon name="remark"/>
                <h3>消息通知</h3>
            </div>
        </template>
        <el-empty :image-size="300" description="暂无通知"
                  image="https://vben.vvbin.cn/assets/illustration.8e82152d.svg"
                  style="padding: 0"/>
    </el-card>
</template>

<style scoped>
.message-card {
    min-height: 400px;
}
</style>