package com.example.myspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
//        SpringApplication.run(App.class, args);
        SpringApplication springApplication = new SpringApplication(App.class);
        springApplication.run(args);


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("----Thread ShutDown Hook-----");
        }));
    }

}
