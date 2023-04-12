package space.adorer.admin.vo;

import lombok.Data;

/**
 * 登录表单 VO
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:27:34
 */
@Data
public class LoginVO {

    /**
     * UUID
     */
    private String uuid;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;
}
