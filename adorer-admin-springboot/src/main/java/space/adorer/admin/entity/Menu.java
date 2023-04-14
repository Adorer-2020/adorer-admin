package space.adorer.admin.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 菜单表实体类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:22:15
 */
@Data
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单 id
     */
    private Integer id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 父菜单 id
     */
    private Integer parentId;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 菜单状态 [0: 正常, 1: 禁用]
     */
    private Integer status;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

}
