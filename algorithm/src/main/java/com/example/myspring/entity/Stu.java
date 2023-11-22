package com.example.myspring.entity;

public class Stu extends BaseEntity {

    private static boolean flag = false;

    private Integer id;
    private String name = "hh";
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Stu() {
        System.out.println("Default Construct action");
    }

    public Stu(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public void test() {
        if(flag) {
            System.out.println("----------------check false.");
            return;
        }
        flag = true;
        System.out.println("test" + Thread.currentThread().getId());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = false;
    }

    public static void main(String[] args) {
        System.out.println(new Stu().getTaskName());
        new Stu().ttt();
    }



}
