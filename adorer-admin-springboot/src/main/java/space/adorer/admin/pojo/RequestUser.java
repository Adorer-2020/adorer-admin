package space.adorer.admin.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import space.adorer.admin.entity.User;
import space.adorer.admin.enums.LoginType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 发起当前请求的用户信息
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 13:56:19
 */
@Data
public class RequestUser implements UserDetails {

    /**
     * 账号 (用户名、邮箱、手机号)
     */
    private User user;

    /**
     * 登录方式
     */
    private LoginType type;

    /**
     * 用户状态
     */
    private int status;

    /**
     * 用户权限信息
     */
    private List<String> roles;

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return user.getAccount(type);
    }

    /**
     * 获取用户密码
     *
     * @return 用户密码
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取用户权限信息
     *
     * @return 用户权限信息
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * 账户是否过期
     *
     * @return [ture:未过期, false:过期]
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否被锁定
     *
     * @return [ture:未锁定, false:锁定]
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户凭证是否过期
     *
     * @return [ture:未过期, false:过期]
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否可用
     *
     * @return [true:可用, false:不可用]
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}