package space.adorer.admin.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 资源表实体类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:25:06
 */
@Data
public class Resource implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 资源名
     */
    private String name;

    /**
     * 权限路径
     */
    private String url;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 父资源 id
     */
    private Integer parentId;

    /**
     * 是否匿名访问 [0: 否, 1: 是]
     */
    private Integer isAnonymous;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

}