package space.adorer.admin.mapper;

import space.adorer.admin.entity.Resource;

/**
 * 资源表数据库相关操作接口
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 14:01:29
 */
public interface ResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
}
