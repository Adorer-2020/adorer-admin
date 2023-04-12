<script setup>
import {computed} from 'vue'
import {ElMessage} from 'element-plus';

const props = defineProps({
    modelValue: String,
    width: {type: Number, default: 320},
    height: {type: Number, default: 180},
    type: {type: String, default: 'image'}
})

const emit = defineEmits(['update:modelValue'])

const headers = {
    token: localStorage.getItem('token')
}

const imgUrl = computed({
    get() {
        return props.modelValue;
    },
    set(value) {
        emit('update:modelValue', value)
    }
})

function validate(file) {
    if (file.type != 'image/jpeg' && file.type != 'image/png') {
        ElMessage.error('仅支持 JPEG/PNG 类型的图片')
        return false;
    }
    return true;
}

function update(data) {
    console.log(data);
    if (data && data.code == 200) {
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