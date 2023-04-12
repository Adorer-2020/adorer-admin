package space.adorer.admin.handler;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import space.adorer.admin.annotation.AccessLimit;
import space.adorer.admin.pojo.R;
import space.adorer.admin.utils.IpUtils;
import space.adorer.admin.utils.RedisUtils;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 接口限流处理器
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 上午11:51
 */
@Slf4j
public class AccessLimitHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest httpServletRequest,
                             @NonNull HttpServletResponse httpServletResponse,
                             @NonNull Object handler) throws Exception {
        // 如果请求输入方法
        if (handler instanceof HandlerMethod method) {
            // 获取方法中的注解, 看是否有该注解
            AccessLimit accessLimit = method.getMethodAnnotation(AccessLimit.class);
            if (accessLimit != null) {
                long seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();
                // 根据 ip 和 请求方法生成 key
                String key = IpUtils.getIpAddress(httpServletRequest) + method.getMethod().getName();
                try {
                    // 从 redis 中获取用户单位时间内访问的次数
                    long q = RedisUtils.incrExpire(key, seconds);
                    if (q > maxCount) {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        OutputStream out = httpServletResponse.getOutputStream();
                        String str = JSON.toJSONString(R.err("请求过于频繁，请稍候再试"));
                        out.write(str.getBytes(StandardCharsets.UTF_8));
                        out.flush();
                        out.close();
                        log.warn(key + "请求次数超过每" + seconds + "秒" + maxCount + "次");
                        return false;
                    }
                    return true;
                } catch (RedisConnectionFailureException e) {
                    log.warn("redis错误: " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }
}
