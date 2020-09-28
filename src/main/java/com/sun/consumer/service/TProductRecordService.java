package com.sun.consumer.service;


import com.sun.consumer.entity.TProductRecord;

import java.util.List;

/**
 * (TProductRecord)表服务接口
 *
 * @author makejava
 * @since 2020-09-27 10:46:51
 */
public interface TProductRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TProductRecord queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<TProductRecord> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param tProductRecord 实例对象
     * @return 实例对象
     */
    TProductRecord insert(TProductRecord tProductRecord);

    /**
     * 修改数据
     *
     * @param tProductRecord 实例对象
     * @return 实例对象
     */
    TProductRecord update(TProductRecord tProductRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}