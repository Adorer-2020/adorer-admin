import {defineStore} from "pinia";
import router from "@/router";

const workspace = {
    name: '工作台',
    path: '/workspace',
    children: [
        {name: '欢迎页', path: '/workspace/welcome'},
        {name: '分析页', path: '/workspace/analysis'}
    ]
}

const personal = {
    name: '个人中心',
    path: '/personal'
}

export const useNavStore = defineStore('navStore', {
    state: () => ({
        menus: [],
        activeTab: {name: '欢迎页', path: '/workspace/welcome'},
        tabs: [{name: '欢迎页', path: '/workspace/welcome'}],
        tabHistory: [],
    }),
    getters: {},
    actions: {
        /**
         * 初始化导航菜单
         * @param menus 动态菜单
         */
        initMenu(menus = []) {
            // 添加静态菜单 “工作台”
            this.menus.push(workspace);
            // 添加动态菜单
            menus.forEach(item => this.menus.push({name: item.name, path: item.path, children: item.children}))
            // 添加静态菜单 “个人中心”
            this.menu.push(personal);
        },

        /**
         * 刷新导航标签历史
         * @param tab 标签
         * @param method 方法
         */
        refreshTabList(tab, method = 'add') {
            switch (method) {
                // 新增标签
                case 'add': {
                    if (this.tabs.filter(item => item.path === tab.path).length === 0) {
                        this.tabs.push(tab)
                    }
                    // 记录当前标签记录
                    this.tabHistory.push(this.activeTab)
                    // 切换到新标签
                    this.activeTab = tab;
                    // console.log(tab);
                    break;
                }
                // 关闭标签
                case 'remove': {
                    // 从标签浏览历史中删除记录
                    this.tabHistory = this.tabHistory.filter(history => history.path !== tab.path);
                    // 如果关闭的是当前标签页，则回到上一个浏览记录
                    if (this.activeTab.path === tab.path) {
                        this.activeTab = this.tabHistory.pop();
                        router.push({path: this.activeTab.path})
                    }
                    // 从标签列表中删除标签
                    this.tabs = this.tabs.filter(item => item.path !== tab.path);
                    break;
                }
                // 关闭左侧标签
                case 'removeLeft': {
                    console.log(this.tabs);
                    console.log(tab);
                    // 从 1 开始，保留 "首页" 标签
                    while (this.tabs[1].path !== tab.path) {
                        // 删除标签历史记录
                        this.tabHistory = this.tabHistory.filter(history => history.path !== this.tabs[1].path);
                        // 删除标签
                        this.tabs.splice(1, 1);
                    }
                    break;
                }
                // 关闭右侧标签
                case 'removeRight': {
                    let i = 1;
                    // 从 1 开始，找到标签下标
                    while (i < this.tabs.length) {
                        if (this.tabs[i++].path === tab.path) {
                            break;
                        }
                    }
                    // 删除该标签之后的标签
                    while (i < this.tabs.length) {
                        // 删除标签历史记录
                        this.tabHistory = this.tabHistory.filter(history => history.path !== this.tabs[i].path);
                        // 删除标签
                        this.tabs.splice(i, 1);
                    }
                    break;
                }
                // 关闭其他标签
                case 'removeOther': {
                    if (this.activeTab.path === '/home') {
                        this.tabHistory = []
                    } else {
                        this.tabHistory = [{name: '欢迎页', path: '/workspace/welcome'}];
                    }
                    this.tabs = this.tabs.filter(item => (item.name === tab.name || item.path === '/'));
                    break;
                }
                // 关闭所有标签
                case 'removeAll': {
                    this.activeTab = {name: '欢迎页', path: '/workspace/welcome'};
                    this.tabHistory = []
                    this.tabs = [{name: '欢迎页', path: '/workspace/welcome'}];
                    router.push({path: '/workspace/welcome'});
                    break;
                }
                default: {
                    console.log(`不支持 ${method} 操作！`);
                    break;
                }
            }
        }
    }
})

