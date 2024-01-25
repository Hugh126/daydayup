package com.example.myspring;


import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 *  springboot 和 jetcache 版本存在一些争议，参考
 *  @link {https://github.com/alibaba/jetcache/blob/master/docs/CN/Compatibility.md}
 *
 *  EnableCreateCacheAnnotation 必须的
 */

@SpringBootApplication
@EnableMethodCache(basePackages = "com.example.myspring")
@EnableCreateCacheAnnotation
public class App 
{

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }

}
