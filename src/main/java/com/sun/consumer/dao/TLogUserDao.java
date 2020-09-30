package com.sun.consumer.dao;

import com.sun.consumer.entity.TLogUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TLogUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-30 14:44:31
 */
@Mapper
public interface TLogUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TLogUser queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TLogUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tLogUser 实例对象
     * @return 对象列表
     */
    List<TLogUser> queryAll(TLogUser tLogUser);

    /**
     * 新增数据
     *
     * @param tLogUser 实例对象
     * @return 影响行数
     */
    int insert(TLogUser tLogUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TLogUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TLogUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TLogUser> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<TLogUser> entities);

    /**
     * 修改数据
     *
     * @param tLogUser 实例对象
     * @return 影响行数
     */
    int update(TLogUser tLogUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}