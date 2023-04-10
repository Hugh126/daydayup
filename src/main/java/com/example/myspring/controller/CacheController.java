package com.example.myspring.controller;

import com.example.myspring.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * https://blog.csdn.net/qq_32448349/article/details/101696892
 *
 * 使用心得
 * 1、Spring Cache使用还是很方便的，但是要特别注意，不然容易出bug
 * 2、使用统一的CacheConfig, 明确定义查增改的key，或使用统一keyGenerator
 * 3、由于get和update使用了一样的key，那么update方法的返回必须和get一致
 * 4、注意key对应方法返回值就是key对应value！！尽量少的ttl，不然服务重启依旧取不到最新值
 * 5、对于一般非联动缓存，直接用@Cacheable即可。方法cacheNames会覆盖类上的
 *
 * 源码:
 * Cache配置？
 * org.springframework.cache.annotation.ProxyCachingConfiguration
 * Cache注解怎么产生作用?
 * org.springframework.cache.interceptor.BeanFactoryCacheOperationSourceAdvisor
 * cache匹配成功去哪里寻找Value?
 * org.springframework.cache.interceptor.CacheAspectSupport
 *
 */
@RequestMapping("/user")
@RestController
@CacheConfig(cacheNames = "userCache")
@Slf4j
public class CacheController {

    private static Map<Integer, String> data_map =
            new HashMap(){{
                put(1, "Lily");
                put(2, "Linger");
            }};

    @RequestMapping("/getAll")
//    @Cacheable(key = "#id")
    @Cacheable
    public String getAll() {
        log.warn("originally getAll");
        return data_map.values().stream().collect(Collectors.joining(","));
    }

    @RequestMapping("/getWithAspectJ/{id}")
    public String getWithAspectJ(@PathVariable Integer id) {
        // 如果本地方法调用可以被拦截，那么就不会打印get
        log.warn("getWithAspectJ {}", id);
        return get(id);
    }


    @RequestMapping("/get/{id}")
    @Cacheable
//    @Cacheable(key = "#id")
    public String get(@PathVariable Integer id) {
      log.warn("originally get {}", id);
      return data_map.get(id);
    }

    @RequestMapping("/add")
//    @CachePut(key = "#id")
    @CachePut
    public String add(@RequestParam Integer id, @RequestParam String name) {
        log.warn("originally add {} {}", id, name);
        data_map.put(id, name);
        return name;
    }

    @RequestMapping("/update")
//    @CachePut(key = "#id")
    @CachePut
    public String update(@RequestParam Integer id, @RequestParam String name) {
        log.warn("originally update {} {}", id, name);
        data_map.put(id, name);
        return name;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @CachePut
    public User save(@RequestBody User user) {
        log.warn("originally save {}", user.getId());
        data_map.put(user.getId(), user.getName());
        return user;
    }


//    @CacheEvict(key = "#id")
    @CacheEvict
    @RequestMapping("/del/{id}")
    public String del(@PathVariable Integer id) {
        log.warn("originally del {}", id);
        return data_map.remove(id);
    }

    @CacheEvict(allEntries = true)
    @RequestMapping("/delAll")
    public void delAll() {
        log.warn("delAll");
    }


}
