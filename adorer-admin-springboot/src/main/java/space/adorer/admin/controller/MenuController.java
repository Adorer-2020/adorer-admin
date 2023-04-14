package space.adorer.admin.controller;

import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import space.adorer.admin.entity.Menu;
import space.adorer.admin.pojo.R;
import space.adorer.admin.service.MenuService;
import space.adorer.admin.utils.UserInfoUtils;

import java.util.List;

/**
 * 菜单相关请求控制器
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 15:14:01
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 获取菜单路由表
     */
    @GetMapping("routes")
    public R getRoutes() {
        List<Menu> routes = menuService.listAll(UserInfoUtils.getUserId());
        return R.ok().put("routes", routes);
    }

    /**
     * 获取菜单信息
     */
    @GetMapping("{id}")
    public R getDetails(@PathVariable int id) {
        Menu menu = menuService.getById(id);
        return R.ok().put("details", menu);
    }

    /**
     * 新增菜单
     */
    @PostMapping()
    public R save(@RequestBody Menu menu) {
        // 添加菜单
        menuService.save(menu);
        return R.ok();
    }

    /**
     * 修改菜单信息
     */
    @PutMapping()
    @PreAuthorize("hasAnyRole('sys', 'admin')")
    public R update(@RequestBody Menu menu) {
        // 更新菜单信息
        menuService.updateById(menu);
        return R.ok();
    }
}
