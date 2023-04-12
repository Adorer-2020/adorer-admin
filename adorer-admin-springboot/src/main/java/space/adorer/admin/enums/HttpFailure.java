package space.adorer.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误消息枚举
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 下午1:11
 */
@Getter
@AllArgsConstructor
public enum HttpFailure {

    BAD_REQUEST(400, "请求参数有误"),
    INVALID_TOKEN(401, "未授权或 token 无效"),
    FORBID(403, "禁止访问"),
    DISALLOW_OPERATE(40301, "不允许的操作"),
    DISALLOW_READ(40302, "不允许读"),
    DISALLOW_WRITE(40303, "不允许写"),
    SSL_ONLY(40304, "要求 ssl"),
    SSL128_ONLY(40305, "要求 ssl 128"),
    IP_LIMIT(40306, "ip 地址拒绝访问"),
    REQUIRE_CERT(40307, "需要携带证书"),
    FORBID_VISIT(40308, "站点禁止访问"),
    MORE_VISITOR(40309, "连接数过多"),
    INVALID_CONFIG(40310, "配置无效"),
    PASSWORD_UPDATED(40311, "密码已修改"),
    NOT_FOUND(404, "请求资源未找到"),
    DISALLOW_RESOURCE(405, "资源被禁止"),
    SERVER_ERROR(500, "服务器内部错误");

    /**
     * 状态码
     */
    final int code;

    /**
     * 描述
     */
    final String msg;
}
