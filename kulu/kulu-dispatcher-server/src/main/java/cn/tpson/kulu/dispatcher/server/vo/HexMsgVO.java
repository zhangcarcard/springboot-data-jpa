package cn.tpson.kulu.dispatcher.server.vo;

import java.io.Serializable;

/**
 * Created by Zhangka in 2018/08/09
 */
public class HexMsgVO implements Serializable {
    private Integer port;
    private String key;
    private String hexMsg;

    public HexMsgVO(Integer port, String key, String hexMsg) {
        this.port = port;
        this.key = key;
        this.hexMsg = hexMsg;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHexMsg() {
        return hexMsg;
    }

    public void setHexMsg(String hexMsg) {
        this.hexMsg = hexMsg;
    }
}
