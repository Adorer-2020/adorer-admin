package space.adorer.admin.service.impl;

import org.springframework.stereotype.Service;
import space.adorer.admin.entity.User;
import space.adorer.admin.service.UserService;

/**
 * 用户相关服务实体类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:28:18
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    /**
     * 根据用户 id 更新用户信息
     */
    @Override
    public void updateById(User user) {

    }
}
