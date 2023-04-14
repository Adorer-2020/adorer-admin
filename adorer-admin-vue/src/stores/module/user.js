import {defineStore} from "pinia";

export const useUserStore = defineStore('userStore', {
    state: () => ({
        userInfo: {
            // 昵称
            nickname: 'user',
            // 头像
            avatar: 'http://47.115.230.49/static/imgs/avatar/default.jpg',
            // 手机号
            mobile: '',
            // 邮箱
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
        setUserInfo(user = {}) {
            for (let key in user) {
                this.userInfo[key] = user[key];
            }
        }
    }
})