import {defineStore} from "pinia";

export const useUserStore = defineStore('userStore', {
    state: () => ({
        userInfo: {
            nickname: 'user',
            avatar: 'http://47.115.230.49/static/imgs/avatar/default.jpg',
            mobile: '',
            email: ''
        }
    }),
    getters: {
        getAvatar() {
            return this.userInfo.avatar;
        },
        getNickname() {
            return this.userInfo.nickname;
        },
        getMobile() {
            return this.userInfo.mobile;
        },
        getEmail() {
            return this.userInfo.email;
        }
    },
    actions: {
        setUserInfo(user) {
            for (let key in user) {
                this.userInfo[key] = user[key];
            }
        }
    }
})