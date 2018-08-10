package cn.tpson.kulu.dispatcher.mq.impl;

import cn.tpson.kulu.dispatcher.biz.dto.HexMsgDTO;
import cn.tpson.kulu.dispatcher.biz.service.HexMsgService;
import cn.tpson.kulu.dispatcher.mq.MessageQueueListener;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Zhangka in 2018/08/09
 */
@Component
public class HexMsgMessageQueueListener implements MessageQueueListener {
    public static final String DISPATCHER_HEXMSG_LIST = "DISPATCHER_HEXMSG_LIST";

    @Autowired
    private HexMsgService hexMsgService;

    @Override
    public String getQueueName() {
        return DISPATCHER_HEXMSG_LIST;
    }

    @Override
    public void onMessage(String message) {
        HexMsgDTO hexMsg = JSON.parseObject(message, HexMsgDTO.class);
        hexMsgService.save(hexMsg);
    }
}
