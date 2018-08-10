package cn.tpson.kulu.dispatcher.websocket;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.common.util.SpringContextUtils;
import cn.tpson.kulu.dispatcher.controller.StatisticsController;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint("/statistics/connections")
@Component
public class StatisticsWebSocket {
    private static final Logger log = LoggerFactory.getLogger(StatisticsWebSocket.class);

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * 连接成功.
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                if (session.isOpen()) {
                    StatisticsController statisticsController = SpringContextUtils.getBean(StatisticsController.class);
                    ResultVO resultVO = statisticsController.connections();
                    session.getBasicRemote().sendText(JSON.toJSONString(resultVO));
                } else {
                    scheduledExecutorService.shutdown();
                }
            } catch (Exception e) {
                log.error("统计数据时出错!", e);
            }
        }, 1L, 30L, TimeUnit.SECONDS);
    }
    
    /**
     * 断开连接.
     */
    @OnClose
    public void onClose() {
        log.info("websocket已经关闭.");
    }

    /**
     * 收到消息.
     *
     * @param message
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message) {
    }
    
    @OnError
    public void onError(Session session, Throwable t) {
        log.error("websocket出错!", t);
    }

    /**
     * 发送消息
     *
     * @param message
     * @throws IOException
     */
    private void sendMessage(String message, Session session) {
    	try {
            session.getBasicRemote().sendText(message);
    	} catch (IOException e) {
            log.error("发送websocket消息失败!", e);
		}
    }
}
