import {defineStore} from "pinia";
import {useNavStore} from "./module/navigation";
import {useUserStore} from "./module/user";

const useStore = defineStore("store", {
    state: () => ({
        collapse: false,    // 菜单折叠状态
    }),
    getters: {},
    actions: {}
})

export {useStore, useNavStore, useUserStore}

