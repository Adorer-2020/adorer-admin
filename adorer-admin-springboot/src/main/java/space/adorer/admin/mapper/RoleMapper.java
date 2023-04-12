package space.adorer.admin.mapper;

import space.adorer.admin.entity.Role;

/**
 * 角色表相关数据库操作接口
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 14:00:14
 */
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}
