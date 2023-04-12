<script setup>

</script>


<template>
    <div class="flex justify-center items-center h-[640px]">
        <img src="../../assets/status/404.svg"/>
        <div class="ml-12">
            <p v-motion :enter="{ opacity: 1, y: 0, ransition: { delay: 100 } }" :initial="{ opacity: 0, y: 100 }"
               class="font-medium text-4xl mb-4 dark:text-white">
                404
            </p>
            <p v-motion :enter="{ opacity: 1, y: 0, transition: { delay: 300 } }" :initial="{ opacity: 0, y: 100 }"
               class="mb-4 text-gray-500">
                抱歉，你访问的页面不存在
            </p>
            <el-button v-motion :enter="{ opacity: 1, y: 0, transition: { delay: 500 } }"
                       :initial="{ opacity: 0, y: 100 }" type="primary"
                       @click="$router.push('/')">
                返回首页
            </el-button>
        </div>
    </div>
</template>
