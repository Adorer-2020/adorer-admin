export const ForbiddenRoute = {
    path: "/403",
    name: "403",
    component: () => import("@/views/common/error/403.vue"),
    meta: {title: "资源拒绝访问..."},
};

export const NotFoundRoute = {
    path: "/404",
    name: "404",
    component: () => import("@/views/common/error/404.vue"),
    meta: {title: "页面找不到了呢..."},
};

export const ServerErrorRoute = {
    path: "/500",
    name: "500",
    component: () => import("@/views/common/error/500.vue"),
    meta: {title: "服务器出错啦..."},
};