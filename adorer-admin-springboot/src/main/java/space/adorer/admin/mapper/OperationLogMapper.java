package space.adorer.admin.mapper;

import space.adorer.admin.entity.OperationLog;

/**
 * 操作日志表相关数据库操作接口
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 17:06:16
 */
public interface OperationLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OperationLog record);

    int insertSelective(OperationLog record);

    OperationLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OperationLog record);

    int updateByPrimaryKey(OperationLog record);
}
