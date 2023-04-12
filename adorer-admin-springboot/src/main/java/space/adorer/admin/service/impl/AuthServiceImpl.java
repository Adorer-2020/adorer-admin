package space.adorer.admin.service.impl;

import com.google.code.kaptcha.Producer;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import space.adorer.admin.entity.User;
import space.adorer.admin.pojo.RequestUser;
import space.adorer.admin.service.AuthService;
import space.adorer.admin.service.UserService;
import space.adorer.admin.utils.JwtUtils;
import space.adorer.admin.utils.RedisUtils;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 认证相关服务实体类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 17:20:06
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Resource
    private Producer producer;

    @Resource
    private UserService userService;

    @Resource
    private AuthenticationManager authenticationManager;


    /**
     * 根据 uuid 生成图片验证码
     *
     * @param uuid 和图片验证码绑定的唯一标识
     * @return 验证码图片
     */
    @Override
    public BufferedImage createCaptcha(String uuid) {
        // 获取图片验证码
        String code = producer.createText();
        // 存入 redis, 设置过期时间为五分钟
        RedisUtils.setObject("code:uuid:" + uuid, code, 5, TimeUnit.MINUTES);
        // 生成图片验证码并返回
        return producer.createImage(code);
    }

    /**
     * 校验验证码是否正确
     *
     * @param key  和验证码绑定的标识符
     * @param code 验证码
     */
    @Override
    public boolean checkCode(String key, String code) {
        String validation = RedisUtils.getObject("code:" + key);
        boolean result = code.equals(validation);
        // 验证成功，删除验证码
        if (result) {
            RedisUtils.deleteObject("code:" + key);
        }
        return result;
    }

    /**
     * 用户登录
     *
     * @param account  账号
     * @param password 密码
     * @return 登录成功，返回 Jwt；登录失败，返回 null
     */
    @Override
    public String login(String account, String password) {
        // 1、根据用户名和密码创建 Authentication 实例
        UsernamePasswordAuthenticationToken authenticationToken =
                UsernamePasswordAuthenticationToken.unauthenticated(account, password);
        // 2、使用 AuthenticationManager 对用户名和密码进行校验, 登录失败会抛出相应的异常
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3、获取到 UserDetails , 存入 Redis
        RequestUser userDetails = (RequestUser) authentication.getPrincipal();
        String userId = String.valueOf(userDetails.getUser().getId());
        RedisUtils.setObject("user:" + userId, userDetails);

        // 4、更新用户登录时间
        User user = new User();
        user.setId(userDetails.getUser().getId());
        user.setLoginTime(new Date());
        userService.updateById(user);

        // 5、根据 userId 生成 Jwt, 返回给前端
        return JwtUtils.generator(String.valueOf(userId));
    }

    /**
     * 用户登出
     *
     * @param userId 用户 id
     */
    @Override
    public void logout(Long userId) {
        System.out.println(userId);
    }
}
