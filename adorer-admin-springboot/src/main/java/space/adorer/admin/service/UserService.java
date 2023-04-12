package space.adorer.admin.service;

import space.adorer.admin.entity.User;

/**
 * 用户相关服务接口
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:27:12
 */
public interface UserService {

    /**
     * 根据用户 id 更新用户信息
     */
    void updateById(User user);
}
