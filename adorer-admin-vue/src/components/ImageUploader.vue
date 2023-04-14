<script setup>
import {computed} from 'vue'
import {ElMessage} from 'element-plus';

// 组件属性
const props = defineProps({
    modelValue: String,                         // 图片地址，双向绑定
    width: {type: Number, default: 320},        // 组件宽度
    height: {type: Number, default: 180},       // 组件高度
    type: {type: String, default: 'image'}      // 文件类型，决定了后端文件存储路径
})
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
 */
function validate(file) {
    if (!isImage(file)) {
        ElMessage.error('图片格式有误！')
        return false;
    }
    return true;
}

/**
 * 上传结束后的处理
 * @param data 响应
 */
function update(data) {
    console.log(data);
    if (data && data.code === 200) {
        ElMessage.success('上传成功');
        imgUrl.value = data.url;
    } else {
        ElMessage.error('上传失败');
    }
}
</script>

<template>
    <el-upload :action="`/api/file/upload/${type}`" :before-upload="validate" :headers="headers"
               :on-success="update" :show-file-list="false" :style="{ 'width': width + 'px', 'height': height + 'px' }"
               class="uploader-container"
               drag>
        <div v-if="!imgUrl" class="uploader-tips">
            <i class="iconfont icon-upload uploader-icon"/>
            <div class="el-upload__text">
                将文件拖到此处，或<em>点击上传</em>
            </div>
        </div>
        <el-image v-else :src="imgUrl" fit="cover" style="height: 100%; width: 100%"/>
    </el-upload>
</template>

<style scoped>
.uploader-container {
    display: block;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.uploader-container:hover .uploader-icon {
    color: var(--el-color-primary);
}

.uploader-container :deep(.el-upload) {
    height: 100% !important;
}

.uploader-container :deep(.el-upload-dragger) {
    padding: 0;
    display: flex;
    align-items: center;
    height: 100% !important;
}

.uploader-tips {
    text-align: center;
    width: 100%;
}

.uploader-icon {
    font-size: 40px;
    color: #8c939d;
    text-align: center;
}
</style>