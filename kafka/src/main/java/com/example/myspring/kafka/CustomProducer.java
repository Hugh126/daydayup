package com.example.myspring.kafka;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.apache.kafka.clients.producer.*;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Date 2023/9/7 15:47
 * @Created by hugh
 *
 */
public class CustomProducer {

    static {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        List<Logger> loggerList = loggerContext.getLoggerList();
        loggerList.forEach(logger -> {
            logger.setLevel(Level.WARN);
        });
    }

    public static void main(String[] args) throws InterruptedException {
        // 1. 创建kafka生产者的配置对象
        Properties properties = new Properties();

        // 2. 给kafka配置对象添加配置信息：bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.90.164:9092");

        // key,value序列化（必须）：key.serializer，value.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");


        // 2.1 自定义分区器
//        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.example.myspring.kafka.MyPartitioner");

        /**
         * 2.2  提供生产者吞吐量 [[ 尽量一次性多拉一些消息 ]]
         */
        // batch.size：批次大小，默认16K
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // linger.ms：等待时间，默认0
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // RecordAccumulator：缓冲区大小，默认32M：buffer.memory
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        // compression.type：压缩，默认none，可配置值gzip、snappy、lz4和zstd
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");

        /**
         * 2.3 消息可靠性
         * ACK 应答级别：（消息生产可靠性）
         * -1 集群所有成员都收到
         *  1 需要leader收到
         *  0 不需要应答
         *
         *  数据完全可靠条件 =ACK级别设置为-1+ 分区副本大于等于2+ ISR里应答的最小副本数量大于等于2
         */
        // 设置acks
//        properties.put(ProducerConfig.ACKS_CONFIG, "0");
        // 重试次数retries，默认是int最大值，2147483647
//        properties.put(ProducerConfig.RETRIES_CONFIG, 3);


        /**
         * 2.4 幂等性
         * enable.idempotence 默认为true
         * 判断标准:具有<PID，Partition，SeqNumber>   ==> 只能保证单分区、单对话内不重复
         */
//        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        /**
         * 2.5 分区内有序性
         * 1）未开启幂等
         *
         * 2）开启幂等
         * max,in.flight.requests.per.connection <= 5
         */
//        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);


        // 3. 创建kafka生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        // 该方法在Producer收到ack时调用，为异步调用
//        注意：消息发送失败会自动重试，不需要我们在回调函数中手动重试
        Callback callM = (metadata, exception) -> {

            if (exception == null) {
                // 没有异常,输出信息到控制台
                System.out.println("主题：" + metadata.topic()  + " ->"  + "分区：" + metadata.partition() );
            } else {
                // 出现异常打印
                System.out.println("成产消息异常" + exception.getMessage());
                exception.printStackTrace();
            }
        };



        // 4. 调用send方法,发送消息
        for (int i = 0; i < 990; i++) {
            /**
             * 如果需要同步调用，直接future.get()即可
             * ProducerRecord 的构造函数中可以指定分区 详情见
             * @see org.apache.kafka.clients.producer.internals.DefaultPartitioner
             */
//            Future<RecordMetadata> future = kafkaProducer.send(new ProducerRecord<>("test", "你好 " + i), callM);
            String msg = "[序号]"+i + "[时间]" + LocalDateTime.now().toString();
            Future<RecordMetadata> future = kafkaProducer.send(new ProducerRecord<>("third", i%3, String.valueOf(i), msg), callM);
            TimeUnit.MILLISECONDS.sleep(100L);
        }

        // 5. 关闭资源
        kafkaProducer.close();
    }


}
