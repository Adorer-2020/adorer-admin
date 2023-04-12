package space.adorer.admin.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志表实体类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:24:18
 */
@Data
public class OperationLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 资源 id
     */
    private Integer resourceId;

    /**
     * 操作者
     */
    private Integer userId;

    /**
     * 操作时间
     */
    private Date time;

    /**
     * 参数
     */
    private String param;

    /**
     * 操作结果
     */
    private Integer result;

}