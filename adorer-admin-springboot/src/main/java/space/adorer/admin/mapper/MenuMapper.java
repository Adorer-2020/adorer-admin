package space.adorer.admin.mapper;

import space.adorer.admin.entity.Menu;

import java.util.List;

/**
 * 菜单表数据库相关操作接口
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 14:01:15
 */
public interface MenuMapper {

    /**
     * 新增菜单，返回自增主键
     *
     * @param menu 菜单信息
     * @return 新增结果，为 1 表示新增成功，否则新增失败
     */
    int insert(Menu menu);

    /**
     * 新增菜单（部分字段），返回自增主键
     *
     * @param menu 菜单信息
     * @return 新增结果，为 1 表示新增成功，否则新增失败
     */
    int insertSelective(Menu menu);

    /**
     * 根据 id 获取菜单数据
     *
     * @param id 菜单 id
     * @return 菜单详细数据
     */
    Menu selectById(Integer id);

    /**
     * 根据用户 id 获取菜单列表
     *
     * @param userId 用户 id
     * @return 用户拥有的所有菜单
     */
    List<Menu> listByUserId(Long userId);

    /**
     * 根据菜单 id 修改菜单数据
     *
     * @param menu 新菜单数据， id 必须不为空
     * @return 修改结果，为 1 表示修改成功，否则修改失败
     */
    int updateById(Menu menu);

    /**
     * 通过 id 删除菜单数据
     *
     * @param id 菜单 id
     * @return 删除结果，为 1 表示删除成功，否则删除失败
     */
    int deleteById(Integer id);
}
