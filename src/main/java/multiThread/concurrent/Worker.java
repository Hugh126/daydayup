package multiThread.concurrent;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.xiaoju.uemc.tinyid.client.utils.TinyId;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 提供了闭锁，可以实现真正的并行
 *
 */
class Worker implements Runnable {

        private final CountDownLatch startSignal ;
        private final CountDownLatch doneSignal ;

        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                //等待主线程执行完毕，获得开始执行信号
                startSignal.await();

                doWork();

                //完成预期工作，发出完成信号
                doneSignal.countDown();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void doWork() {
            Long id = TinyId.nextId("test");
            System.out.println("线程:" +Thread.currentThread().getId() + " current id is: " + id);

//            HttpResponse response = HttpUtil.createGet("https://erpapi.helensbar.com/coupon/generateCouponForUser?time=1660270304905&authtoken=api4erp_bac12&p=1&platform=1&st=23nOOGda&uid=opaQk1qgbmHHOiF7E9MIeuMyXCZA&couponId=842314&openId=oFsO31bEhTEM3HPfQpFtgo3HZRGc&platform=1&source=html&ApiHolder=&lang=zh&ver=2.0").execute();
//            System.out.println("线程:" +Thread.currentThread().getId() + " 结果" + response.getStatus() + "返回" + response.body());
        }
    }