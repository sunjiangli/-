package com.sun.consumer.service;

import com.sun.consumer.entity.TLogUser;

import java.util.List;

/**
 * (TLogUser)表服务接口
 *
 * @author makejava
 * @since 2020-09-30 14:44:33
 */
public interface TLogUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TLogUser queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TLogUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tLogUser 实例对象
     * @return 实例对象
     */
    TLogUser insert(TLogUser tLogUser);

    /**
     * 修改数据
     *
     * @param tLogUser 实例对象
     * @return 实例对象
     */
    TLogUser update(TLogUser tLogUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}