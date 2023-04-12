<script setup>
import {reactive, ref} from 'vue';
import {ElMessage} from 'element-plus';
import axios from '@/utils/request'
import CountdownButton from './CountdownButton.vue';

// 组件状态 1、用户信息待验证  2、验证通过
const status = ref(1);
// 用户身份验证表单
const authFormRef = ref();
// 重置密码表单
const resetPasswordFromRef = ref();
// 用户信息认证表单
const authForm = reactive({
    account: '',   // 手机号/邮箱
    code: ''    // 验证码
})
// 重置密码表单
const resetPasswordForm = reactive({
    password: '',         // 密码
    confirmPassword: ''   // 确认密码
})
// 表单校验规则
const rules = {
    account: [],
    code: [],
    password: [],
    confirmPassword: []
}

/**
 * 检查用户身份
 */
function checkAccount() {
    authFormRef.value.validate(valid => {
        if (valid) {
            axios.post('/api/auth/account/check').then(({data}) => {
                if (data && data.code == 200) {
                    ElMessage.success('验证通过！')
                    status.value = 2;
                } else {
                    ElMessage.error(data.msg);
                }
            })
        }
    })
}

/**
 * 重置用户密码
 */
function resetPassword() {
    resetPasswordFromRef.value.validate(valid => {
        if (valid) {
            const data = {account: authForm.account, password: resetPasswordForm.password};
            axios.put('/api/auth/account/password', data).then(({data}) => {
                if (data && data.code == 200) {
                    ElMessage.success('修改成功');
                } else {
                    ElMessage.error(data.msg);
                }
            })
        }
    })
}
</script>

<template>
    <el-form v-if="status == 1" ref="authFormRef" :model="authForm" :rules="rules" label-width="100px">
        <el-form-item label="账号" prop="email">
            <el-input v-model="authForm.account"/>
        </el-form-item>
        <el-form-item label="验证码" prop="code">
            <el-input v-model="authForm.code"/>
            <CountdownButton/>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="checkAccount">确认</el-button>
        </el-form-item>
    </el-form>
    <el-form v-else ref="resetPasswordFromRef" :model="resetPasswordForm" :rules="rules" label-width="100px">
        <el-form-item label="密码" prop="password">
            <el-input v-model="resetPasswordForm.password"/>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="resetPasswordForm.confirmPassword"/>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="resetPassword">确认</el-button>
        </el-form-item>
    </el-form>
</template>
