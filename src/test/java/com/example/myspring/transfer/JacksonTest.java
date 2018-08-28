package com.example.myspring.transfer;

import com.example.myspring.App;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class JacksonTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public static final String COCO = new String("coco");
    public static final String COCO_FINAL = "coco";
    public static final Stu STU_DOU = new Stu("001", "dou");

    Map map = new HashMap();

    @Test
    public void toJsonTest1() throws JsonProcessingException {
        // 多种类型可以先封装为Map
        map.put("alibaba", new App());
        map.put("tengxun", Arrays.asList("weixin", "wangzherongyao"));
        map.put("hugh", null);
        map.put("DOU", "DOU");
        System.out.println(objectMapper.writeValueAsString(map));
        // {"alibaba":{},"DOU":"DOU","tengxun":["weixin","wangzherongyao"],"hugh":null}
    }

    @Test
    public void toJsonTest2() throws IOException {
        // 新加非类Stu属性six，且值为null，依然可以被识别
        String likeStu = "{\"sto\":\"001\",\"name\":\"xx\", \"six\":null}";
        Map<String, Object> mock = objectMapper.readValue(likeStu, Map.class); // {"sto":"001","name":"xx","six":null}
        System.out.println(objectMapper.writeValueAsString(mock));
    }

    @Test
    public  void finalTest() {
        STU_DOU.setName("min");
        System.out.println(STU_DOU.getName());

        System.out.println(COCO.replaceAll("c", "m"));

        System.out.println(COCO);

        System.out.println("---");

    }

    @Test
    public void keyTest() {
        Stu stu1 = new Stu("001", "hong");
        Stu stu2 = new Stu("001", "zong");
        Stu stu3 = new Stu("001", "min");
        Map map = new HashMap();
        map.put(stu1, "001");
        map.put(stu2, "002");
        map.put(stu3, "003");
        System.out.println(map.put(stu2, "004"));
        Set set = new HashSet();
        System.out.println(set.add(1));;
        System.out.println(set.add(1));;
        System.out.println(map.get(stu2));
    }


    public static class Stu{

        private String sto;
        private String name;

        public Stu(String sto, String name) {
            this.sto = sto;
            this.name = name;
        }

        public String getSto() {
            return sto;
        }

        public void setSto(String sto) {
            this.sto = sto;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
