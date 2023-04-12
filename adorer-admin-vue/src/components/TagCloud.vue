<script setup>
import {onMounted, reactive} from 'vue';

const prop = defineProps({tags: {type: Array, requrie: true}})

const coordinate = reactive({
    fontSize: 12,//当元素在X轴上字体大小
    radius: 80,//球体半径
    fallLength: 160,//球体直径，其实用于计算字体大小，如元素在Y轴最大值即160上,字体显示最大24px
    tags: [], //经过计算沿球表面均匀分布的元素
    angleX: 0,//沿X轴旋转角度
    angleY: 0,//沿Y轴旋转角度
    x: 0,
    y: 0,
    z: 0,
    CX: 0,//球体中心x轴
    CY: 0,//球体中心y轴
})

onMounted(() => {
    console.log(prop.tags);
    init();
    animate();
})

function init() {
    const background = document.getElementById('background');
    const tagEle = document.getElementsByClassName('.tag');
    // 球中心点取背景元素的中间
    coordinate.CX = background.offsetWidth / 2
    coordinate.CY = background.offsetHeight / 2
    coordinate.radius = Math.min(coordinate.CX, coordinate.CY) - 30;
    //初始化旋转角
    coordinate.angleX = ((Math.random() - 0.5) * Math.PI) / 250
    coordinate.angleY = ((Math.random() - 0.5) * Math.PI) / 250
    for (let i = 0; i < tagEle.length; i++) {
        let k = (2 * (i + 1) - 1) / tagEle.length - 1;
        let a = Math.acos(k)
        let b = a * Math.sqrt(tagEle.length * Math.PI)
        coordinate.x = coordinate.radius * Math.sin(a) * Math.cos(b)
        coordinate.y = coordinate.radius * Math.sin(a) * Math.sin(b)
        coordinate.z = coordinate.radius * Math.cos(a)
        let tag = {ele: tagEle[i], x: coordinate.x, y: coordinate.y, z: coordinate.z}
        coordinate.tags.push(tag)
        move(tag)
    }
}

function move(tag) {
    let {ele, x, y, z} = tag
    let scale = coordinate.fallLength / (coordinate.fallLength - z)
    let alpha = (z + coordinate.radius) / (2 * coordinate.radius)
    //让后面的元素文字小一些突出3d感
    ele.style.fontSize = coordinate.fontSize * scale + 'px'
    //让后面的元素淡一些突出3d感
    ele.style.opacity = alpha + 0.5
    ele.style.filter = 'alpha(opacity = ' + (alpha + 0.5) * 100 + ')'
    ele.style.zIndex = parseInt(scale * 100)
    //将元素移动到相应的点坐标上
    ele.style.transform = `translate(${x + coordinate.CX - ele.offsetWidth / 2}px, ${y + coordinate.CY - ele.offsetHeight / 2}px)`

}

function animate() {
    setInterval(() => {
        rotateX()
        rotateY()
        coordinate.tags.forEach((tag) => move(tag))
    }, 30)
}

function rotateX() {
    let cos = Math.cos(coordinate.angleX)
    let sin = Math.sin(coordinate.angleX)
    coordinate.tags.forEach((tag) => {
        let y1 = tag.y * cos - tag.z * sin
        let z1 = tag.z * cos + tag.y * sin
        tag.y = y1
        tag.z = z1
    })
}

function rotateY() {
    let cos = Math.cos(coordinate.angleY)
    let sin = Math.sin(coordinate.angleY)
    coordinate.tags.forEach((tag) => {
        let x1 = tag.x * cos - tag.z * sin
        let z1 = tag.z * cos + tag.x * sin
        tag.x = x1
        tag.z = z1
    })
}

</script>

<template>
    <div id="background" style="width: 100%;height: 350px;">
        <div v-for="tag in tags" class=".tag" style="position: absolute">{{ tag }}</div>
    </div>
</template>

<style scoped>
#background {
    position: relative;
}

.tag {
    position: absolute !important;
}
</style>