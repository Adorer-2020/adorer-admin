import {defineStore} from "pinia";
import {useUserStore} from "./user";
import router from "../router";

const useStore = defineStore('store', {
    state: () => ({
        collapse: false,
        menuList: [],
        tabs: [{name: '欢迎页', path: '/workspace/welcome'}],
        activeTab: {name: '欢迎页', path: '/workspace/welcome'},
        tabHistory: [],
    }),
    getters: {},
    actions: {
        setMenuList(menuList = []) {
            this.menuList = menuList;
        },
        refreshTabList(tab, method = 'add') {
            switch (method) {
                case 'add': {
                    if (this.tabs.filter(item => item.path == tab.path).length == 0) {
                        this.tabs.push(tab)
                    }
                    // 记录当前标签记录
                    this.tabHistory.push(this.activeTab)
                    // 切换到新标签
                    this.activeTab = tab;
                    // console.log(tab);
                    break;
                }
                    ;
                case 'remove': {
                    // 从标签浏览历史中删除记录
                    this.tabHistory = this.tabHistory.filter(history => history.path != tab.path);
                    // 如果关闭的是当前标签页，则回到上一个浏览记录
                    if (this.activeTab.path == tab.path) {
                        this.activeTab = this.tabHistory.pop();
                        router.push({path: this.activeTab.path})
                    }
                    // 从标签列表中删除标签
                    this.tabs = this.tabs.filter(item => item.path != tab.path);
                    break;
                }
                    ;
                case 'removeLeft': {
                    console.log(this.tabs);
                    console.log(tab);
                    // 从 1 开始，保留 "首页" 标签
                    while (this.tabs[1].path != tab.path) {
                        // 删除标签历史记录
                        this.tabHistory = this.tabHistory.filter(history => history.path != this.tabs[1].path);
                        // 删除标签
                        this.tabs.splice(1, 1);
                    }
                    break;
                }
                    ;
                case 'removeRight': {
                    let i = 1;
                    // 从 1 开始，找到标签下标
                    while (i < this.tabs.length) {
                        if (this.tabs[i++].path == tab.path) {
                            break;
                        }
                    }
                    // 删除该标签之后的标签
                    while (i < this.tabs.length) {
                        // 删除标签历史记录
                        this.tabHistory = this.tabHistory.filter(history => history.path != this.tabs[i].path);
                        // 删除标签
                        this.tabs.splice(i, 1);
                    }
                    break;
                }
                    ;
                case 'removeOther': {
                    if (this.activeTab.path == '/home') {
                        this.tabHistory = []
                    } else {
                        this.tabHistory = [{name: '欢迎页', path: '/workspace/welcome'}];
                    }
                    this.tabs = this.tabs.filter(item => (item.name == tab.name || item.path == '/'));
                    break;
                }
                    ;
                case 'removeAll': {
                    this.activeTab = {name: '欢迎页', path: '/workspace/welcome'};
                    this.tabHistory = []
                    this.tabs = [{name: '欢迎页', path: '/workspace/welcome'}];
                    router.push({path: '/workspace/welcome'});
                    break;
                }
                    ;
                default: {
                    console.log(`不支持 ${method} 操作！`);
                    break;
                }
                    ;
            }
        }
    }
})

export {useStore, useUserStore}

