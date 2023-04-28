package com.example.myspring.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myspring.common.Result;
import com.example.myspring.entity.ErpCity;
import com.example.myspring.service.ErpCityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping("/city")
public class ErpCityController {

    @Autowired
    private ErpCityService erpCityService;

    @RequestMapping("/list")
    public Result<IPage<ErpCity>> page( @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        QueryWrapper<ErpCity> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("env_type", envType);
        Page<ErpCity> page = new Page<>(pageNo, pageSize);
        IPage<ErpCity> pageList = erpCityService.page(page, queryWrapper);
        Result<IPage<ErpCity>> result = new Result();
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 异常测试
     * @param id
     * @param name
     * @return
     */
    @Transactional
    @RequestMapping("/{id}/updateName")
    public Result<?> update(@PathVariable Integer id, @RequestParam String name) throws ExecutionException, InterruptedException {
        ErpCity city = erpCityService.getById(id);
        city.setName("哈哈" + name);
        erpCityService.updateById(city);
        log.warn("[updateName1]{}", JSONUtil.toJsonStr(city));
        Future<?> future = Executors.newSingleThreadExecutor().submit(() -> {
            boolean b = erpCityService.updateByName(id, name);
            return b;
        });
        Object o = future.get();
        Result result = new Result();
        result.setSuccess(false);
        return result;
    }

}
