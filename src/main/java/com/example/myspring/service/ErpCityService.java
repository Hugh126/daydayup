package com.example.myspring.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myspring.entity.ErpCity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


/**
 * 如果Service中抛出异常，则
 */
@Service
@Slf4j
public class ErpCityService extends ServiceImpl<ErpCityMapper, ErpCity> {

    @Autowired
    private ErpCityMapper cityMapper;

    @Transactional
    public boolean updateByName(Integer id, String name) {
        ErpCity city = getById(id);
        city.setName(name);
        boolean b = updateById(city);
        if (true) {
            throw new IllegalArgumentException("000");
        }
        System.err.println("---------------不会被打印------------------");
        return b;
    }

    /**
     * 并发减库存
     *
     * 实验结果表名 ，使用 for update 和不使用 ，并发执行的时候，数据库都会加行锁
     * lock_mode X, lock_type RECORD
     *
     * 注意：如果减库存的时候使用 x=#{param} 必须要要显示加锁
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean decreaseLevel() {
//        int level = cityMapper.queryLevel(1);
//        if (level <0) {
//            log.warn("---[WARNING....]---");
//            return false;
//        }
        boolean flag = cityMapper.updateLevel(1);
        if (!flag) {
            log.warn("update false");
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public int querySelect() {
        int level = cityMapper.queryLevel(1);
        try {
            TimeUnit.SECONDS.sleep(120L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return level;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateLevel() {
        boolean flag = cityMapper.updateLevel(1);
        return true;
    }


}
