<script setup>
import '../assets/js/iconfont.js'

defineProps({name: {type: String, requrie: true}})
</script>

<template>
    <svg aria-hidden="true" class="icon">
        <use :xlink:href="'#icon-' + name"></use>
    </svg>
</template>

<style scoped>
.icon {
    width: 1em;
    height: 1em;
    vertical-align: -0.15em;
    fill: currentColor;
    overflow: hidden;
}
</style>