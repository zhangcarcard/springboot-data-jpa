package cn.tpson.kulu.dispatcher.kafka;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.common.util.DateUtils;
import cn.tpson.kulu.dispatcher.biz.dto.HashLoadBalanceDTO;
import cn.tpson.kulu.dispatcher.biz.service.BackendService;
import cn.tpson.kulu.dispatcher.biz.service.HashLoadBalanceService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Zhangka in 2018/08/21
 */
@Component
public class ActiveWorkHandler implements WorkerHandler {
    private static final Logger log = LoggerFactory.getLogger(ActiveWorkHandler.class);

    @Autowired
    private HashLoadBalanceService hashLoadBalanceService;

    @Override
    public void handle(ConsumerRecord<String, String> record) {
        try {
            String key = record.key();
            String value = record.value();
            log.info("key:{}, value:{}", key, value);

            List<HashLoadBalanceDTO> list = hashLoadBalanceService.findByKey(key);
            log.info("list:{}", JSON.toJSONString(list));
            if (!list.isEmpty()) {
                HashLoadBalanceDTO hash = list.get(0);
                hash.setActive(Boolean.valueOf(value));
                hash.setGmtLastActive(DateUtils.now());
                hashLoadBalanceService.save(hash);
            }
        } catch (Exception e) {
            log.error("ActiveWorkHandler.handle,record:{}", JSON.toJSONString(record));
        }
    }
}
