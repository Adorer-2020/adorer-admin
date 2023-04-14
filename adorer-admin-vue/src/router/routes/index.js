import {MainRoute} from "./main";
import {LoginRoute} from "./login";
import {RedirectRoute} from "./redirect";
import {ForbiddenRoute, NotFoundRoute, ServerErrorRoute} from "@/router/routes/error";

/**
 * 默认暴露静态路由表
 */
export default [MainRoute, LoginRoute, RedirectRoute, NotFoundRoute, ForbiddenRoute, ServerErrorRoute]

/**
 * 将所有静态路由暴露出去，方便访问
 */
export {MainRoute, LoginRoute, RedirectRoute, ForbiddenRoute, NotFoundRoute, ServerErrorRoute}