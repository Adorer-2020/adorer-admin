import {LoginRoute} from "@/router/routes";

export function setupRouterGuard(router) {
    router.beforeEach((to, _from, next) => {
        // 修改页面标题
        document.title = `${to.meta.title} · 博客后台`;
        // 如果用户未登录，则导航到登录界面
        if (localStorage.getItem('token') && to.name !== LoginRoute.name) {
            to.name = LoginRoute.name;
        }
        next({name: to.name});
    })
}