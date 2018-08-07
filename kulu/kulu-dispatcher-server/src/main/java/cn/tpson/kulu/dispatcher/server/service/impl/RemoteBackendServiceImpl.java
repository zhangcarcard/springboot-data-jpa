package cn.tpson.kulu.dispatcher.server.service.impl;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.server.service.RemoteBackendService;
import cn.tpson.kulu.dispatcher.server.vo.BackendVO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhangka in 2018/08/07
 */
@Service
public class RemoteBackendServiceImpl implements RemoteBackendService {
    @Value("${agent.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    /**
     *
     * @param serverPort
     * @param hexMsg
     * @return
     */
    @Override
    public String getKey(Integer serverPort, String hexMsg) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("serverPort", serverPort);
        params.put("hexMsg", hexMsg);
        ResultVO resultVO = restTemplate.getForObject(url + "/key.do?serverPort={serverPort}&hexMsg={hexMsg}", ResultVO.class, params);

        return resultVO.isSuccess()
                ? resultVO.getData().toString()
                : null;
    }

    /**
     *
     * @param serverPort
     * @param key
     * @return
     */
    @Override
    public BackendVO getBackend(Integer serverPort, String key) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("serverPort", serverPort);
        params.put("key", key);
        ResultVO resultVO = restTemplate.getForObject(url + "/backend.do?serverPort={serverPort}&key={key}", ResultVO.class, params);

        return resultVO.isSuccess()
                ? JSON.parseObject(JSON.toJSONString(resultVO.getData()), BackendVO.class)
                : null;
    }
}
