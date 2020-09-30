package com.sun.consumer.service.impl;

import com.sun.consumer.dao.TLogUserDao;
import com.sun.consumer.entity.TLogUser;
import com.sun.consumer.service.TLogUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TLogUser)表服务实现类
 *
 * @author makejava
 * @since 2020-09-30 14:44:34
 */
@Service("tLogUserService")
public class TLogUserServiceImpl implements TLogUserService {
    @Resource
    private TLogUserDao tLogUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TLogUser queryById(Integer id) {
        return this.tLogUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<TLogUser> queryAllByLimit(int offset, int limit) {
        return this.tLogUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tLogUser 实例对象
     * @return 实例对象
     */
    @Override
    public TLogUser insert(TLogUser tLogUser) {
        this.tLogUserDao.insert(tLogUser);
        return tLogUser;
    }

    /**
     * 修改数据
     *
     * @param tLogUser 实例对象
     * @return 实例对象
     */
    @Override
    public TLogUser update(TLogUser tLogUser) {
        this.tLogUserDao.update(tLogUser);
        return this.queryById(tLogUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tLogUserDao.deleteById(id) > 0;
    }
}