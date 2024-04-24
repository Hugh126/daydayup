package com.example.myspring;

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
@SpringBootTest(classes = App.class)
public class SimpleTest
{
    @Autowired
    private Simple simple;

    @Test
    public void test1() throws InterruptedException {
        simple.initFlowRules();
        int loop = 2;
        while (true) {
            try {
                simple.helloWorld();
            }catch (FlowException e){
                System.out.println("blocked!");
                if (loop-- >0){
                    TimeUnit.SECONDS.sleep(1L);
                }else{
                    break;
                }
            }
        }
    }
}
