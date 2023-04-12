import {createRouter, createWebHistory} from "vue-router";
import {useStore} from "@/store";

import axios from "@/utils/request";

/**
 * 动态导入 views/module/ 路径下的所有组件
 */
const modules = import.meta.glob("../views/module/**/*.vue");

/**
 * 登录路由
 */
const LoginRoute = {
    path: "/login",
    name: "login",
    component: () => import("@/views/common/login/index.vue"),
    meta: {title: "登录页"},
    beforeEnter: (_to, _from, next) => {
        // 如果已登录，直接跳转到主页
        if (localStorage.getItem("token")) next({path: "/"});
        // 用户未登录，才能跳转到登录页
        else next();
    },
};

/**
 * 404 路由, 匹配所有其他路由没有匹配到的路由
 */
const NotFoundRoute = {
    path: "/:pathMatch(.*)*",
    name: "404",
    component: () => import("@/views/common/error/404.vue"),
    meta: {title: "404 Not Found"},
};

/**
 * 主路由
 */
const MainRoute = {
    path: "/",
    name: "main",
    component: () => import("@/layout"),
    redirect: {name: "workspace"},
    children: [
        {
            path: "/workspace",
            name: "workspace",
            component: () => import("@/views/common/workspace/index.vue"),
            meta: {title: "工作台"},
        },
    ],
};

/**
 * 创建路由
 */
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [LoginRoute, MainRoute, NotFoundRoute],
});

/**
 * 添加路由守卫
 */
router.beforeEach((to, _from, next) => {
    // 修改页面标题
    document.title = `${to.meta.title} · 博客后台`;
    // 访问 login 页面
    if (to.name === "login") {
        next();
    } else {
        // 没有登录, 跳转到登录页
        if (!localStorage.getItem("token")) {
            next({name: "login"});
        } else {
            // 第一次访问，发请求获取用户菜单
            const store = useStore();
            if (store.menuList.length === 0) {
                axios.get("/api/menu/tree").then(({data}) => {
                    if (data && data.code === 200) {
                        // 将菜单信息存入 store
                        store.menuList = data.menuList;
                        // 创建动态路由
                        createDynamicRouter(data.menuList);
                        next({path: to.path});
                    }
                });
            } else {
                store.refreshTabList({path: to.path, name: to.meta.title});
                next();
            }
        }
    }
});

/**
 * 根据菜单列表递归创建动态路由
 */
function createDynamicRouter(menuList = []) {
    for (let menu of menuList) {
        if (menu.children && menu.children.length > 0) {
            createDynamicRouter(menu.children);
        } else {
            const route = {
                path: menu.path,
                name: menu.path.replace(/^\//, "").replaceAll("/", "-"),
                component: modules[`../views/module${menu.component}.vue`],
                meta: {title: menu.name},
            };
            MainRoute.children.push(route);
        }
    }
    router.removeRoute("main");
    router.addRoute(MainRoute);
}

export default router;
