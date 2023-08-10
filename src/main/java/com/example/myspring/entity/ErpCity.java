package com.example.myspring.entity;

import annotation.Desc;
import annotation.Name;
import annotation.SortOrder;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("erp_city")
@SortOrder({"name", "provinceId", "level"})
public class ErpCity {

    @TableId(type = IdType.AUTO)
    private java.lang.Integer id;
    /**省份ID*/

    @Name("省份代号")
    private java.lang.Integer provinceId;
    /**城市名*/

    @Name("省份名称")
    private java.lang.String name;
    /**城市简称*/

    @Name("省份地址")
    private java.lang.String abbr;
    
    private java.lang.Integer level;

}
