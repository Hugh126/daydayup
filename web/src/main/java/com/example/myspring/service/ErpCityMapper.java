package com.example.myspring.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.myspring.entity.ErpCity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ErpCityMapper extends BaseMapper<ErpCity> {

    @Select("select level from erp_city where id=#{id} for update")
    int queryLevel(@Param("id") Integer id);

    @Update("update erp_city set level=level-1 where id=#{id} ")
    boolean updateLevel(@Param("id") Integer id);


}
