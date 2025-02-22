package com.example.myspring.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//
// nacos 服务 id
@FeignClient(value = "springcloud-nacos-provider", configuration = MyFeignClientsConfiguration.class)
public interface FeignClientService {

    @GetMapping("/call/{name}") // 使用 get 方式，调用服务提供者的 /call/{name} 接口
    public String call(@PathVariable(value = "name") String name);

}