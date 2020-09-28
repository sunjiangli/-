package com.sun.consumer.dao;


import com.sun.consumer.entity.TProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TProduct)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-27 10:45:51
 */

@Mapper
public interface TProductDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TProduct queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TProduct> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tProduct 实例对象
     * @return 对象列表
     */
    List<TProduct> queryAll(TProduct tProduct);

    /**
     * 新增数据
     *
     * @param tProduct 实例对象
     * @return 影响行数
     */
    int insert(TProduct tProduct);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TProduct> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TProduct> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TProduct> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<TProduct> entities);

    /**
     * 修改数据
     *
     * @param tProduct 实例对象
     * @return 影响行数
     */
    int update(TProduct tProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}