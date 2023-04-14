import {createRouter, createWebHistory} from "vue-router";
import {setupRouterGuard} from "@/router/guard";
import routes from './routes'

/**
 * 创建路由
 */
const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: routes
});

/**
 * 创建路由守卫
 */
setupRouterGuard(router);

export default router;
