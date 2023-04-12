package space.adorer.admin.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import space.adorer.admin.entity.User;
import space.adorer.admin.pojo.R;
import space.adorer.admin.service.AuthService;
import space.adorer.admin.utils.UserInfoUtils;
import space.adorer.admin.vo.LoginVO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * 认证相关请求控制器
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:25:39
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private AuthService authService;

    /**
     * 获取图片验证码     GET "/auth/captcha.jpg"
     */
    @GetMapping("captcha.jpg")
    public void getCaptcha(@RequestParam("uuid") String uuid, HttpServletResponse response) {
        if (!StringUtils.hasText(uuid)) {
            throw new RuntimeException("uuid 不能为空");
        }
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        try {
            BufferedImage image = authService.createCaptcha(uuid);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            // 关闭 io 流
            IOUtils.closeQuietly(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取短信/邮箱验证码
     */
    @GetMapping("code")
    public R getCode() {
        return R.ok();
    }

    /**
     * 登录
     */
    @PostMapping("login")
    public R login(@RequestBody LoginVO vo) {
        if (!authService.checkCode("uuid:" + vo.getUuid(), vo.getCode())) {
            return R.err(1400, "验证码错误");
        }
        User user = new User();
        try {
            String token = authService.login(vo.getAccount(), vo.getPassword());
            return R.ok("登录成功").put("token", token);
        } catch (AuthenticationException loginFailed) {
            // 抛出 AuthenticationException 异常，说明验证失败
            return R.err(4000, loginFailed.getMessage());
        } catch (Exception other) {
            return R.err();
        }
    }

    /**
     * 用户登出
     */
    @PostMapping("logout")
    public R logout() {
        Long userId = UserInfoUtils.getUserId();
        authService.logout(userId);
        return R.ok();
    }


}
