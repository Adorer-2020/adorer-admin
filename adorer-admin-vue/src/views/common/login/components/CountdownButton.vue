<script setup>
import {ref} from 'vue';

// 组件属性
const props = defineProps({
    text: {type: String, default: '获取验证码'},
    time: {type: Number, default: 60}
})

// 组件事件
const emit = defineEmits(['submit']);

// 按钮文字
const btnText = ref(props.text);
// 按钮禁用状态
const btnDisabled = ref(false);
// 倒计时
let countDown = props.time;
// 计时器
let timer = null;

/**
 * 点击按钮触发的事件
 */
function handleClick() {
    // 按钮不可用，直接返回
    if (!props.status || timer) return;
    // 按钮可用，进行相应的逻辑操作
    emit('submit');
    btnDisabled.value = true;
    timer = setInterval(() => {
        if (countDown.value === 0) {
            btnDisabled.value = false;
            clearInterval(timer);
            timer = null;
            countDown = props.time;
            btnText.value = '获取验证码';
        } else {
            countDown--
            btnText.value = `${countDown} s`
        }
    }, 1000)
}
</script>

<template>
    <el-button :disabled="btnDisabled" @click="handleClick">{{ btnText }}</el-button>
</template>

