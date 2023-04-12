package space.adorer.admin.filter;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import space.adorer.admin.enums.HttpFailure;
import space.adorer.admin.pojo.R;
import space.adorer.admin.pojo.RequestUser;
import space.adorer.admin.utils.JwtUtils;
import space.adorer.admin.utils.RedisUtils;

import java.io.IOException;

/**
 * Jwt 校验过滤器
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 上午11:37
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String token = request.getHeader("token");
        // 登录请求，直接放行
        if (servletPath.startsWith("/auth") || !StringUtils.hasText(token) || token.equals("null")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            // 解析 JWT ，获取 userId
            String userId = JwtUtils.parse(token).getSubject();
            // 从 redis 中获取该 userId 所对应的用户信息
            RequestUser userDetails = JSON.parseObject(
                    RedisUtils.getObject("user:" + userId).toString(), RequestUser.class);
            // 创建 Authentication（Principal, Credentials, Authorities）
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()
            );
            // 将 Authentication 存入 SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSON.toJSONString(R.err(HttpFailure.INVALID_TOKEN)));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
