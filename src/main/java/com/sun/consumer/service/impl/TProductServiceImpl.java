package com.sun.consumer.service.impl;


import com.sun.consumer.dao.TProductDao;
import com.sun.consumer.entity.TProduct;
import com.sun.consumer.service.TProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * (TProduct)表服务实现类
 *
 * @author makejava
 * @since 2020-09-27 10:45:54
 */

@Service
public class TProductServiceImpl implements TProductService {



    @Autowired
    private TProductDao tProductDao ;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TProduct queryById(Integer id) {
        return this.tProductDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<TProduct> queryAllByLimit(int offset, int limit) {
        return this.tProductDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param tProduct 实例对象
     * @return 实例对象
     */
    @Override
    public TProduct insert(TProduct tProduct) {
        this.tProductDao.insert(tProduct);
        return tProduct;
    }

    /**
     * 修改数据
     *
     * @param tProduct 实例对象
     * @return 实例对象
     */
    @Override
    public TProduct update(TProduct tProduct) {
        this.tProductDao.update(tProduct);
        return this.queryById(tProduct.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.tProductDao.deleteById(id) > 0;
    }
}