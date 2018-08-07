package cn.tpson.kulu.dispatcher.server.service;

import cn.tpson.kulu.dispatcher.server.vo.BackendVO;

/**
 * Created by Zhangka in 2018/08/07
 */
public interface RemoteBackendService {
    /**
     * 获取hash-key.
     * @param serverPort
     * @param hexMsg
     * @return
     */
    String getKey(Integer serverPort, String hexMsg);

    /**
     * 获取转发服务器.
     * @param serverPort
     * @param key
     * @return
     */
    BackendVO getBackend(Integer serverPort, String key);
}
