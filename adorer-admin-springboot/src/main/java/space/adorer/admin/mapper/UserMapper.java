package space.adorer.admin.mapper;

import space.adorer.admin.entity.User;

/**
 * 用户表相关数据库操作接口
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:27:15
 */
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
