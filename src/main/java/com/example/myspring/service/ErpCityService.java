package com.example.myspring.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myspring.entity.ErpCity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 如果Service中抛出异常，则
 */
@Service
public class ErpCityService extends ServiceImpl<ErpCityMapper, ErpCity> {

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
}
