
# 启动zookeeper
bin/zookeeper-server-start.sh -daemon config/zookeeper.properties

# 启动kafka
bin/kafka-server-start.sh  -daemon config/server.properties

# 查看主题
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

# 创建主题
bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --partitions 3 --replication-factor 1 --topic test

# 消费消息
>  --from-beginning  
> 
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092  --topic test

# 发送消息
bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic test

# Broker工作流程
* 1) 启动Zookeeper客户端
* bin/zookeeper-shell.sh 2181
*
* 2) 通过ls命令可以查看kafka相关信息
* ls /kafka
*
# Kafka副本
*
* Kafka分区中的所有副本统称为AR
* AR = ISR + OSR
* ISR，表示和Leader保持同步的Follower集合。如果Follower长时间未向Leader发送通信请求或同步数据，则该Follower将被踢出ISR。该时间阈值由replica.lag.time.max.ms参数设定，默认30s。Leader发生故障之后，就会从ISR中选举新的Leader。
* OSR，表示Follower与Leader副本同步时，延迟过多的副本
*
* 查看Leader情况
* bin/kafka-topics.sh --bootstrap-server hadoop102:9092 --describe --topic atguigu1
*
*
# 文件存储
Topic是逻辑上的概念，
而partition是物理上的概念，
每个patition对应于一个log文件，该1og文件中存储的就是Prodicer生产的数据。
Producer生产的数据会被不断追加到该log文件末端，为防止10g文件过大导致数据定位效率低下，Kaka采取了分片和索引机制，将每个partition分为多个segment。
每个segment包括:“index”文件、“1og”文件和.tmeinde等文件。
这些文件位于一个文件夹下，该文件夹的命名规则为: topic名称+分区序号，例如: frst-0。

1. 文件存储机制
log.segment.bytes	Kafka中log日志是分成一块块存储的，此配置是指log日志划分 成块的大小，默认值1G。
log.index.interval.bytes	默认4kb，kafka里面每当写入了4kb大小的日志（.log），然后就往index文件里面记录一个索引。 稀疏索引。

2. 文件清理策略
Kafka中默认的日志保存时间为7天，超时有delete和compact两种策略

## 高效读写
PageCache页缓存: Kaka重度依赖底层操作系统提供的PageCache功能。当上层有写操作时，操作系统只是将数据写入PageCache。
当读操作发生时，先从PageCache中查找，如果找不到，再去磁盘中读取。实际上PageCache是把尽可能多的空闲内存都当做了磁盘缓存来使用。






