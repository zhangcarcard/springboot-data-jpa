package cn.tpson.kulu.dispatcher.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Created by Zhangka in 2018/08/21
 */
public interface WorkerHandler {
    void handle(ConsumerRecord<String, String> record);
}
