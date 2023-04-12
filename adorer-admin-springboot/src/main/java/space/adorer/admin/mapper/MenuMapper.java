package space.adorer.admin.mapper;

import space.adorer.admin.entity.Menu;

/**
 * 菜单表数据库相关操作接口
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 14:01:15
 */
public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}
