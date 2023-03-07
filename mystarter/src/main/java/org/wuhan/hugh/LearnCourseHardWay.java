package org.wuhan.hugh;

public class LearnCourseHardWay implements LearnCourse{

    private Integer loopTimes;

    @Override
    public void sayHi(String name) {
        System.out.println(String.format("Hi, you will learn %s for %d times", name, loopTimes));
    }

    public LearnCourseHardWay() {
    }

    public LearnCourseHardWay(Integer loopTimes) {
        this.loopTimes = loopTimes;
    }

}
