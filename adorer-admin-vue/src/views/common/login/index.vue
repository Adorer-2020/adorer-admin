<script setup>
import LoginForm from './components/LoginForm.vue';
import RegisterForm from './components/RegisterForm.vue';
import ForgetPasswordForm from './components/ForgetPasswordForm.vue';
</script>

<template>
    <div class="login-container">
        <LoginForm/>
        <RegisterForm/>
        <ForgetPasswordForm/>
    </div>
</template>

<style scoped>
.login-container {
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    background: url('@/assets/imgs/bg.jpg') center center / cover no-repeat;
}
</style>