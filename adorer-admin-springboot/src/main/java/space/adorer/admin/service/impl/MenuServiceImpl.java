package space.adorer.admin.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import space.adorer.admin.entity.Menu;
import space.adorer.admin.mapper.MenuMapper;
import space.adorer.admin.service.MenuService;
import space.adorer.admin.utils.AssertUtils;
import space.adorer.admin.utils.UserInfoUtils;

import java.util.Date;
import java.util.List;

/**
 * 菜单相关服务实现类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 17:47:32
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 获取用户菜单列表
     *
     * @param userId 用户 id
     */
    @Override
    public List<Menu> listAll(Long userId) {
        return menuMapper.listByUserId(userId);
    }

    /**
     * 根据菜单 id 获取菜单数据
     *
     * @param id 菜单 id
     * @return 菜单详细信息
     */
    @Override
    public Menu getById(int id) {
        return menuMapper.selectById(id);
    }

    /**
     * 新增菜单
     *
     * @param menu 菜单数据
     */
    @Override
    public void save(Menu menu) {
        // 获取创建者和创建时间
        menu.setCreateBy(UserInfoUtils.getUserId());
        menu.setCreateTime(new Date());
        // 插入数据库
        menuMapper.insert(menu);
    }

    /**
     * 更新菜单数据
     *
     * @param menu 新数据
     */
    @Override
    public void updateById(Menu menu) {
        AssertUtils.requireNonNull(menu.getId());
        menuMapper.updateById(menu);
    }

    /**
     * 根据菜单 id 删除菜单
     *
     * @param id 菜单 id
     */
    @Override
    public void deleteById(int id) {
        menuMapper.deleteById(id);
    }
}
