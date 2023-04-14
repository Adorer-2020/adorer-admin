<script setup>
import {onMounted, ref} from 'vue';
import {ArrowLeft, ArrowRight, Edit} from '@element-plus/icons-vue'
import dayjs from 'dayjs';
import axios from '@/utils/request'

const lunar = ref({})
const calendar = ref({date: new Date()});

onMounted(() => {
    getLunar();
})

/**
 * 获取农历信息
 */
function getLunar() {
    axios.get('/api/welcome/lunar').then(({data}) => {
        if (data && data.code == 200) {
            lunar.value = data.lunar;
        } else {
            ElMessage.error('获取农历信息失败！')
        }
    })
}

/**
 * 判断日历选择的是否是今天
 */
function isToday() {
    return dayjs(calendar.value.date).format('YYYY-MM-DD') == dayjs(new Date()).format('YYYY-MM-DD')
}

</script>

<template>
    <el-card class="calendar-card" shadow="never">
        <template #header>
            <div class="calendar-header-left">
                <div class="calendar-date">
                    <h1>30</h1>
                    <span>3月</span>
                </div>
                <div class="calendar-lunar">
                    <h4>{{ lunar.lunarCalendar }}</h4>
                    <div>
                        <span>{{ lunar.yearTips }}</span>
                        <span style="margin-left: 15px;">{{ lunar.typeDes }}</span>
                    </div>
                    <span>{{ lunar.constellation }}</span>
                </div>
            </div>
            <div class="calendar-header-right">
                <el-button link type="primary">更多</el-button>
            </div>
        </template>
        <div class="calendar-todo-list">
            <div class="todo-list-header">
                <div class="todo-list-header-left">
                    <span>{{ dayjs(calendar.date).format('YYYY-MM-DD') }}</span>
                    <el-button v-if="!isToday()" :icon="Clock" link type="primary">回到今天</el-button>
                </div>
                <div class="todo-list-header-right">
                    <el-button :icon="Edit" link>新增日程</el-button>
                </div>
            </div>
            <div class="todo-list-body">
                <el-empty description="暂无日程" style="padding: 0"/>
            </div>
        </div>
        <el-calendar v-model="calendar.date" class="calendar-calendar">
            <template #header="{ date }">
                <el-button :icon="ArrowLeft" circle size="small"/>
                <span>{{ date }}</span>
                <el-button :icon="ArrowRight" circle size="small"/>
            </template>
        </el-calendar>
    </el-card>
</template>

<style scoped>
.calendar-card :deep(.el-card__header) {
    background-color: rgba(164, 205, 233, 0.212);
}

.calendar-header-left {
    width: 45%;
    display: flex;
}

.calendar-date {
    width: 50px;
    height: auto;
    text-align: center;
}

.calendar-date h1 {
    width: 46px;
    height: 40px;
    line-height: 40px;
    font-size: 35px;
    border: 2px solid var(--el-card-border-color);
    margin: 0;
    border-radius: 9px;
    margin-bottom: 5px;
}

.calendar-date span {
    width: 50px;
    height: 20px;
    line-height: 20px;
}

.calendar-lunar {
    margin-left: 20px;
}

.calendar-card :deep(.el-card__body) {
    display: flex;
    justify-content: space-between;
}

.calendar-todo-list {
    width: calc(100% - 270px);
    min-width: 280px;
    min-height: 200px;
}

.todo-list-header {
    width: 100%;
    display: flex;
    height: 30px;
    padding: 0;
    line-height: 30px;
    background-color: #fff;
    align-items: center;
    justify-content: space-between;
}

.todo-list-header-left {
    display: flex;
    align-items: center;
}

.calendar-calendar {
    margin-top: -80px;
    width: 250px;
    height: 300px;
    border: 1px solid var(--el-card-border-color);
    border-radius: 12px;
}

.calendar-calendar :deep(th) {
    padding-block: 2px;
}

.calendar-calendar :deep(.el-calendar-day) {
    height: 30px;
    width: 30px;
    padding: 5px;
    line-height: 20px;
    text-align: center;
}
</style>