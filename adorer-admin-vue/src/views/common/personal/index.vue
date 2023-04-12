<script setup>
import {reactive} from 'vue'

const userInfo = reactive({
    username: '',
    email: '',
    phone: '',
    avatar: '',
    introduction: ''
})

function update() {

}

</script>

<template>
    <div class="user-container">
        <el-card>
            <div slot="header" class="clearfix">
                <span>个人中心</span>
            </div>
            <div class="user-info">
                <el-form :model="form" class="user-form" label-width="100px">
                    <el-form-item label="用户名">
                        <el-input v-model="form.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱">
                        <el-input v-model="form.email" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="手机号">
                        <el-input v-model="form.phone" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="头像">
                        <el-avatar :src="form.avatar" size="large"></el-avatar>
                    </el-form-item>
                    <el-form-item label="个人简介">
                        <el-input v-model="form.introduction" type="textarea"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="update('form')">保存</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
    </div>
</template>

