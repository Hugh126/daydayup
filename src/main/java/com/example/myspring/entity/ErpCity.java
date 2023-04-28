package com.example.myspring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("erp_city")
public class ErpCity {

    @TableId(type = IdType.AUTO)
    private java.lang.Integer id;
    /**省份ID*/
    
    private java.lang.Integer provinceId;
    /**城市名*/
    
    private java.lang.String name;
    /**城市简称*/
    
    private java.lang.String abbr;
    
    private java.lang.Integer level;

}
