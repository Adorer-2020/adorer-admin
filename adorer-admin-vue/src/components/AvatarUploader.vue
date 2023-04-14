<script setup>
import {computed} from 'vue'
import {ElMessage} from 'element-plus';

// 组件属性
const props = defineProps({modelValue: String, size: {type: Number, default: 150}})
// 组件事件
const emit = defineEmits(['update:modelValue'])
// 图片 url
const imgUrl = computed({
    get() {
        return props.modelValue;
    },
    set(value) {
        emit('update:modelValue', value)
    }
})

// 文件上传请求的请求头
const headers = {
    token: localStorage.getItem('token')
}

/**
 * 校验文件格式是否为图片
 * @param file 文件
 */
function validate(file) {
    if (file.type !== 'image/jpeg' && file.type !== 'image/png') {
        ElMessage.error('仅支持 JPEG/PNG 类型的图片')
        return false;
    }
    return true;
}

/**
 * 上传完毕
 * @param data 响应
 */
function update(data) {
    if (data && data.code === 200) {
        ElMessage.success('上传成功');
        imgUrl.value = data.url;
    } else {
        ElMessage.error('上传失败');
    }
}
</script>

<template>
    <el-upload :before-upload="validate" :headers="headers"
               :on-success="update" :show-file-list="false" :style="{ 'width': size + 'px', 'height': size + 'px' }"
               action="/api/file/upload/avatar"
               class="image-uploader">
        <el-avatar v-if="imgUrl" :size="size" :src="imgUrl"/>
        <i v-else :style="{ 'width': size - 2 + 'px', 'height': size - 2 + 'px', 'line-height': size - 2 + 'px' }"
           class="iconfont icon-add image-uploader-icon"/>
    </el-upload>
</template>

<style scoped>
.image-uploader {
    display: block;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.image-uploader-icon {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    font-size: 28px;
    color: #8c939d;
    text-align: center;
}

.image-uploader-icon:hover {
    border-color: var(--el-color-primary);
}
</style>