package space.adorer.admin.service;

import java.awt.image.BufferedImage;

/**
 * 认证相关服务接口
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 17:19:18
 */
public interface AuthService {

    /**
     * 根据 uuid 生成图片验证码
     *
     * @param uuid 和图片验证码绑定的唯一标识
     * @return 验证码图片
     */
    BufferedImage createCaptcha(String uuid);

    /**
     * 校验验证码是否正确
     *
     * @param key  和验证码绑定的标识符
     * @param code 验证码
     */
    boolean checkCode(String key, String code);

    /**
     * 用户登录
     *
     * @param account  账号
     * @param password 密码
     * @return 登录成功，返回 Jwt；登录失败，返回 null
     */
    String login(String account, String password);

    /**
     * 用户登出
     *
     * @param userId 用户 id
     */
    void logout(Long userId);
}
