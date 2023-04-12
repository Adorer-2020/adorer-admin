<script setup>
import {reactive, ref} from 'vue';
import CountdownButton from './CountdownButton.vue';
import axios from '@/utils/request'

// 注册表单
const registerFormRef = ref()
// 注册模式
const mode = ref('email');
// 倒计时按钮状态
const btnStatus = ref(false)
// 表单数据
const formData = reactive({
    username: '',         // 用户名
    email: '',            // 邮箱
    mobile: '',           // 手机号
    code: '',             // 验证码
    password: '',         // 密码
    confirmPassword: ''   // 确认密码
})
// 校验规则
const rules = {
    username: [],
    email: [],
    mobile: [],
    password: [],
}

/**
 * 切换注册模式
 */
function changeMode(value) {
    mode.value = value;
}

/**
 * 获取验证码
 */
function getCode() {
    registerFormRef.value.validateField(mode.value).then(valid => {
        if (valid) {
            axios.get('/api/auth/code?account=', formData[mode.value]);
        }
    })

}
</script>

<template>
    <div class="register-form">
        <div class="form-title">
            <div :class="mode == 'emial' ? 'active' : ''" class="register-select" @click="changeMode('emial')">
                手机号注册
            </div>
            <div :class="mode == 'mobile' ? 'active' : ''" class="register-select" @click="changeMode('mobile')">
                邮箱注册
            </div>
        </div>
        <el-form ref="registerFormRef" :model="formData" :rules="rules" label-width="100px" status-icon>
            <el-form-item label="用户名" prop="username">
                <el-input v-model="formData.username"/>
            </el-form-item>
            <el-form-item v-if="mode == 'emial'" label="邮箱" prop="email">
                <el-input v-model="formData.email"/>
            </el-form-item>
            <el-form-item v-else label="手机号" prop="mobile">
                <el-input v-model="formData.mobile"/>
            </el-form-item>
            <el-form-item label="验证码" prop="code">
                <el-input v-model="formData.code"/>
                <CountdownButton :status="btnStatus" @submit="getCode"/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="formData.password" type="password"/>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
                <el-input v-model="formData.confirmPassword" type="password"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('formData')">注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<style>
.register-form {
    background: #fff;
    padding: 170px 60px 180px;
    width: 350px;
}

.form-title {
    display: inline-flex;
    color: #303133;
    font-weight: bold;
    font-size: 1rem;
    margin-bottom: 1.25rem;
    display: flex;
    justify-content: flex-end;
}

.register-select {
    display: flex;
    width: 50%;
    justify-content: center;
    cursor: pointer;
}

.active {
    color: var(--el-color-primary)
}
</style>
