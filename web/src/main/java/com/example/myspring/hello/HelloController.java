package com.example.myspring.hello;

import com.example.myspring.entity.Stu;
import com.example.myspring.filter.AbstractHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController implements DisposableBean {


    @Autowired
    List<AbstractHandle> handles;

    @RequestMapping("/c/{name}")
    String chain(@PathVariable("name") String name) {
        String reName = name;
        for (AbstractHandle handle : handles) {
            reName = (String) handle.handle(reName);
            if (reName == null) {
                break;
            }
        }
        return "Hello  " + reName;
    }

    /**
     * 使用PathVariable 接受参数
     */
    @RequestMapping("/{name}")
    String sayHello(@PathVariable("name") String name) {
        return "Hello  " + name;
    }

    /**
     * 使用RequestParam获取参数
     */
    @RequestMapping("/hehe")
    String sayHello2(@RequestParam("name") String name) {
        return "hehe  " + name;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("---------DisposableBean destroy-----");
    }

    @RequestMapping(value = "/dd", method= RequestMethod.POST)
    public String tt(@RequestBody Stu stu, HttpServletRequest req) {
        System.out.println(req.getParameter("name"));
        System.out.println(stu.toString());
        return "succ";
    }
}
