<script setup>
import {reactive, ref} from 'vue';
import {useRouter} from 'vue-router';
import {ElMessage} from 'element-plus';
import {Avatar, Lock, Notification} from '@element-plus/icons-vue'
import {getUUID} from '@/utils';
import axios from '@/utils/request'

// 登录表单
const loginFormRef = ref();
// 表单数据
const formData = reactive({
    uuid: getUUID(),    // uuid，和 code 绑定
    username: '',       // 用户名
    password: '',       // 密码
    code: ''            // 验证码
})
// 表单校验规则
const rules = {
    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
    password: [{required: true, message: '请输入密码', trigger: 'blur'}],
    code: [{required: true, message: "验证码不能为空", trigger: "blur"}]
}

/**
 * 点击 “登录” 按钮
 */
function login() {
    // 校验
    loginFormRef.value.validate(valid => {
        if (valid) {
            axios.post('/api/auth/login', formData).then(({data}) => {
                if (data && data.code == 200) {
                    // 登录成功，保存 token 并跳转到主页
                    const router = useRouter();
                    ElNotification.success('登录成功！');
                    localStorage.setItem('token', data.token);
                    router.push({path: '/'});
                } else {
                    // 登录失败，提示用户为什么登录失败
                    ElMessage.error(data.msg);
                }
            })
        } else {
            return false
        }
    })
}

/**
 * 获取验证码
 */
function getCaptcha() {
    // 刷新 uuid, 触发验证码图片重新加载，重新获取验证码
    formData.uuid = getUUID();
}
</script>

<template>
    <div class="login-form">
        <div class="form-title">管理员登录</div>
        <el-form ref="loginFormRef" :model="formData" :rules="rules" status-icon>
            <el-form-item prop="username">
                <el-input v-model="formData.username" :prefix-icon="Avatar" placeholder="用户名/手机号/邮箱"/>
            </el-form-item>
            <el-form-item prop="password">
                <el-input v-model="formData.password" :prefix-icon="Lock" placeholder="密码" show-password/>
            </el-form-item>
            <el-form-item prop="code">
                <el-input v-model="formData.code" :prefix-icon="Notification" placeholder="验证码"/>
                <el-image :src="`/api/auth/captcha.jpg?uuid=${formData.uuid}`" @click="getCaptcha"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="login">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<style>
.login-form {
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    background: #fff;
    padding: 170px 60px 180px;
    width: 350px;
}

.form-title {
    color: #303133;
    font-weight: bold;
    font-size: 1rem;
    margin-bottom: 1.25rem;
}

.login-form button {
    margin-top: 1rem;
    width: 100%;
}
</style>
