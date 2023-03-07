package util;

import cn.hutool.core.io.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class OOM {

    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int i=1;
        while (true) {
            System.out.println(i);
            byte[] bytes = FileUtil.readBytes("D:\\tmp\\gc_ddy_0.log");
            list.add(new String(bytes));
            i++;
            try {
                TimeUnit.MILLISECONDS.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
