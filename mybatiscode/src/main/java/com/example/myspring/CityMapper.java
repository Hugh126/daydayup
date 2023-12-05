package com.example.myspring;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @Date 2023/11/27 15:14
 * @Created by hugh
 */
public interface CityMapper  {

    List<ErpCity> queryByName(String name);

    @Select("select * from erp_city where level =  #{level}")
    List<ErpCity> queryByLevel(int level);
}

