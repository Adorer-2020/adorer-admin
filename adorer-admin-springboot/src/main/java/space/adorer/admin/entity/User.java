package space.adorer.admin.entity;

import lombok.Data;
import space.adorer.admin.enums.Gender;
import space.adorer.admin.enums.LoginType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表实体类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:19:58
 */
@Data
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别 [0: 男, 1: 女]
     */
    private Gender gender;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 状态 [0:正常, 1:已锁定, 2: 不可用]
     */
    private Integer status;

    /**
     * 注册时间
     */
    private Date registTime;

    /**
     * 上次登录时间
     */
    private Date loginTime;

    /**
     * 删除标志 [0: 正常, 1: 已删除]
     */
    private Integer delFlag;

    /**
     * 根据登录方式获取登录账号
     */
    public String getAccount(LoginType type) {
        switch (type) {
            case USERNAME -> {
                return this.username;
            }
            case EMAIL -> {
                return this.email;
            }
            case MOBILE -> {
                return this.mobile;
            }
            default -> throw new RuntimeException("系统错误");
        }
    }
}
