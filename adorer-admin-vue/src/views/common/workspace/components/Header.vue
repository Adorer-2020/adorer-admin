<script setup>
import {useUserStore} from '@/store';
import {onMounted, ref} from 'vue'
import tips from '@/assets/json/message.json'
import axios from '@/utils/request'

const userStore = useUserStore();
const tip = ref('');
const work = ref({
    todo: {title: '待办', count: 0},
    audit: {title: '审核', count: 0},
    message: {title: '消息', count: 0}
})
const address = ref({});  // 地址
const weather = ref({});  // 天气

onMounted(async () => {
    // 初始化提示语
    const hour = new Date().getHours();
    tip.value = tips[hour].time + "，" + userStore.getNickname + "，" + tips[hour].message;
    await getAddress();
    getWeather();
})

/**
 * 获取根据 ip 地址，获取用户地址
 */
async function getAddress() {
    await axios.get('/api/welcome/address').then(({data}) => {
        if (data && data.code == 200) {
            address.value = data;
        } else {
            ElMessage.error('获取定位失败！请手动定位以获取前期预报。')
        }
    })
}

/**
 * 获取天气信息
 */
function getWeather() {
    axios.get(`/api/welcome/weather/${address.value.adcode}`).then(({data}) => {
        if (data && data.code == 200) {
            weather.value = data.lives[0];
        } else {
            ElMessage.error('获取天气信息失败！')
        }
    })
}
</script>

<template>
    <el-card shadow="never">
        <el-row :gutter="20">
            <el-col :md="2">
                <el-avatar :size="72" :src="userStore.getAvatar" style="margin-inline: 0"/>
            </el-col>
            <el-col :md="10" style="height: 100%; margin-block: auto">
                <h1 class="tip">{{ tip }}</h1>
                <span class="weather">今日{{ weather.weather }}, 当前气温 {{ weather.temperature_float }}℃</span>
            </el-col>
            <el-col :md="12" class="todo">
                <div v-for="(value, key) in work" :index="key" class="work">
                    <div class="work-title">{{ value.title }}</div>
                    <div class="work-count">{{ value.count }}</div>
                </div>
            </el-col>
        </el-row>
    </el-card>
</template>

<style scoped>
.tip {
    margin-top: 0;
    margin-bottom: 0.5em;
    color: #000000d9;
    font-weight: 500;
}

.weather {
    font-size: 14px;
    line-height: 1.5;
    color: #00000073
}

.todo {
    display: flex;
    align-items: center;
    justify-content: flex-end;
}

.work {
    margin-inline: 15px;
    text-align: center;
}

.work-title {
    font-size: 14px;
    line-height: 1.5;
    color: #00000073
}

.work-count {
    font-size: 1.5rem;
    line-height: 2rem;
}
</style>