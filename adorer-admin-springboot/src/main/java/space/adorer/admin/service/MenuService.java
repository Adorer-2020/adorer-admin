package space.adorer.admin.service;

import space.adorer.admin.entity.Menu;

import java.util.List;

/**
 * 菜单相关服务接口
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 17:46:39
 */
public interface MenuService {

    /**
     * 获取用户的菜单树
     *
     * @param userId 用户 id
     */
    List<Menu> listAll(Long userId);

    /**
     * 根据菜单 id 获取菜单数据
     *
     * @param id 菜单 id
     * @return 菜单详细信息
     */
    Menu getById(int id);

    /**
     * 新增菜单
     *
     * @param menu 菜单数据
     */
    void save(Menu menu);

    /**
     * 更新菜单数据
     *
     * @param menu 新数据
     */
    void updateById(Menu menu);

    /**
     * 根据菜单 id 删除菜单
     *
     * @param id 菜单 id
     */
    void deleteById(int id);
}
