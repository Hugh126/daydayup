package com.example.myspring.annotation;

import java.lang.annotation.*;

/**
 * @author hugh
 * @Title: Desc
 * @ProjectName springboottest
 * @Description: TODO
 * @date 2018/10/1411:44
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Desc {

    String description();
}
