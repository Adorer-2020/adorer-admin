import axios from "@/utils/request";
import {useStore} from "adorer-admin-vue/src/stores";
import {list2Tree} from "@/utils";
import router from "@/router";

const modules = import.meta.glob("../views/module/**/*.vue");

/**
 * 主路由
 * @type {{redirect: {name: string}, path: string, component: (function(): Promise<{}>), children: [{path: string, component: (function(): Promise<{}>), meta: {title: string}, name: string},{path: string, component: (function(): Promise<*>), meta: {title: string}, name: string}], name: string}}
 */
export const MainRoute = {
    path: "/",
    name: "main",
    component: () => import("@/layout"),
    meta: {status: 'init'},
    redirect: {name: "welcome"},
    children: [
        {
            path: "/workspace/welcome",
            name: "welcome",
            component: () => import("@/views/common/workspace/welcome"),
            meta: {title: "欢迎页"},
        },
        {
            path: "/workspace/analysis",
            name: "analysis",
            component: () => import("@/views/common/workspace/analysis"),
            meta: {title: "分析页"},
        },
        {
            path: '/personal',
            name: 'personal',
            component: () => import("@/views/common/personal"),
            meta: {title: "个人中心"}
        }
    ],
};

/**
 * 初始化 Mainroute
 * @description 加上动态路由
 */
export async function initMainRoute() {
    // 发请求获取动态路由
    await axios.get('/api/menu/routes').then(({data}) => {
        if (data && data.code === 200) {
            const store = useStore();
            // 初始化菜单
            store.initMenu(list2Tree(data.routes));
            // 创建动态路由
            createDynamicRoute(data.routes);
            // 修改主路由状态
            MainRoute.meta.status = 'dynamic';
        }
    })
}


/**
 * 创建动态路由
 * @param routes 路由表
 */
function createDynamicRoute(routes = []) {
    // 遍历 routes，将符合规则的路由添加到 MainRoute 的子路由中
    for (let i = 0; i < routes.length; i++) {
        // 如果路由不规则不符，跳过这条记录
        if (!routes[i].component) {
            continue;
        }
        // 否则创建路由，并添加到 MainRoute 的子路由里
        const route = {
            name: routes[i].path.replace("/^\//", "").replaceAll("/", "-"),
            path: routes[i].path,
            component: modules[`../views/module${routes[i].component}`],
            meta: {title: routes[i].name}
        }
        MainRoute.children.push(route);
    }
    // 重新设置 MainRoute
    router.removeRoute(MainRoute.name);
    router.addRoute(MainRoute)
}