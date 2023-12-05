package com.example.myspring;

public class ErpCity {

    private Integer id;
    /**省份ID*/

    private Integer provinceId;
    /**城市名*/

   
    private String name;
    /**城市简称*/

   
    private String abbr;
    
    private Integer level;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ErpCity{");
        sb.append("id=").append(id);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", abbr='").append(abbr).append('\'');
        sb.append(", level=").append(level);
        sb.append('}');
        return sb.toString();
    }
}