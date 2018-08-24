package cn.tpson.kulu.dispatcher.kafka;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Zhangka in 2018/08/21
 */
@Component
public class Consumer {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @Value("${kafka.servers}")
    private String kafkaServers;
    @Value("${kafka.topic.hexmsg}")
    private String topicHexmsg;
    @Value("${kafka.topic.active}")
    private String topicActive;
    @Value("${kafka.group.hexmsg}")
    private String groupHexmsg;

    private KafkaConsumer<String, String> consumer;
    private ExecutorService bossExecutor = Executors.newFixedThreadPool(1);
    private ExecutorService workExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaServers);
        props.put("group.id", groupHexmsg);
        props.put("enable.auto.commit", "true");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topicHexmsg, topicActive));

        bossExecutor.execute(() -> {
            while (true) {
                try {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
                        workExecutor.execute(new WorkerRunnable(record));
                    }
                } catch (Throwable e) {
                    log.error("消费kafka消息是出错", e);
                }
            }
        });
    }

    @PreDestroy
    public void destroy() {
        if (consumer != null) {
            consumer.close();
        }
    }



    @Autowired
    private ActiveWorkHandler activeWorkHandler;
    @Autowired
    private HexMsgWorkHandler hexMsgWorkHandler;

    class WorkerRunnable implements Runnable {
        private ConsumerRecord record;
        public WorkerRunnable(ConsumerRecord record) {
            this.record = record;
        }

        @Override
        public void run() {
            String topic = record.topic();
            if (topicHexmsg.equals(topic)) {
                hexMsgWorkHandler.handle(record);
            } else if (topicActive.equals(topic)) {
                activeWorkHandler.handle(record);
            }
        }
    }

}
