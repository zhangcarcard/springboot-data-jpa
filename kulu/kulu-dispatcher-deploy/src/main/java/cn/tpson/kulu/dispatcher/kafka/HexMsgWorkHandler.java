package cn.tpson.kulu.dispatcher.kafka;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.dispatcher.biz.dto.HexMsgDTO;
import cn.tpson.kulu.dispatcher.biz.service.HexMsgService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Zhangka in 2018/08/21
 */
@Component
public class HexMsgWorkHandler implements WorkerHandler {
    private static final Logger log = LoggerFactory.getLogger(HexMsgWorkHandler.class);

    @Autowired
    private HexMsgService hexMsgService;

    @Override
    public void handle(ConsumerRecord<String, String> record) {
        try {
            String key = record.key();
            String value = record.value();
            log.info("key:{}, value:{}", key, value);
            JSONObject json = JSON.parseObject(value);

            HexMsgDTO hexMsg = new HexMsgDTO();
            hexMsg.setKey(key);
            hexMsg.setHexMsg(json.getString("msg"));
            hexMsg.setPort(json.getInteger("port"));
            hexMsgService.save(hexMsg);
        } catch (Exception e) {
            log.error("HexMsgWorkHandler.handle,record:{}", JSON.toJSONString(record));
        }
    }
}
