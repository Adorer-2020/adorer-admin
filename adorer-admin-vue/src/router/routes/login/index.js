/**
 * 登录路由
 * @type {{path: string, component: (function(): Promise<{}>), beforeEnter: LoginRoute.beforeEnter, meta: {title: string}, name: string}}
 */
export const LoginRoute = {
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