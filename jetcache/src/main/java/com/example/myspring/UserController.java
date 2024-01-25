package com.example.myspring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserById/{id}")
    public User getUserById(@PathVariable Long id){
        User u =  userService.getUserById(id);
        return u;
    }


    @RequestMapping("/ok")
    public String ok() {
        return "OK";
    }

}
