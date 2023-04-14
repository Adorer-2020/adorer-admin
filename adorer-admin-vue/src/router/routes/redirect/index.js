import {MainRoute, NotFoundRoute} from "@/router/routes";
import {initMainRoute} from "@/router/routes/main";

/**
 * 重定向路由
 * @description 浏览器直接敲地址访问前端页面，此时路由还未准备好，将由此路由统一捕获，再进行逻辑判断
 */
export const RedirectRoute = {
    path: "/:pathMatch(.*)*",
    name: "redirect",
    meta: {title: "加载中..."},
    beforeEnter: async (to, _from, next) => {
        if (MainRoute.meta.status === 'init') {
            await initMainRoute();
            next({path: to.path});
        } else {
            next({name: NotFoundRoute.name});
        }
    }
};