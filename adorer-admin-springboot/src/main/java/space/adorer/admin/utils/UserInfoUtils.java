package space.adorer.admin.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import space.adorer.admin.entity.User;

/**
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 17:59:59
 */
public class UserInfoUtils {
    public static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getUserId() {
        return getUser().getId();
    }
}
