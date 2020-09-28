package com.sun.consumer.dao;


import com.sun.consumer.entity.TProductRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TProductRecord)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-27 10:46:51
 */
@Mapper
public interface TProductRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TProductRecord queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TProductRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tProductRecord 实例对象
     * @return 对象列表
     */
    List<TProductRecord> queryAll(TProductRecord tProductRecord);

    /**
     * 新增数据
     *
     * @param tProductRecord 实例对象
     * @return 影响行数
     */
    int insert(TProductRecord tProductRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TProductRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TProductRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TProductRecord> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<TProductRecord> entities);

    /**
     * 修改数据
     *
     * @param tProductRecord 实例对象
     * @return 影响行数
     */
    int update(TProductRecord tProductRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}