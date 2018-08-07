package cn.tpson.kulu.dispatcher.server.vo;

/**
 * Created by Zhangka in 2018/08/06
 */
public class BackendVO {
    private String ip;
    private Integer port;
    private String key;
    private String groupName;
    private String protocalName;
    private String eqpName;
    private Integer serverPort;

    public BackendVO() {}
    public BackendVO(String ip, Integer port, String key, String groupName, String protocalName, String eqpName, Integer serverPort) {
        this.ip = ip;
        this.port = port;
        this.key = key;
        this.groupName = groupName;
        this.protocalName = protocalName;
        this.eqpName = eqpName;
        this.serverPort = serverPort;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProtocalName() {
        return protocalName;
    }

    public void setProtocalName(String protocalName) {
        this.protocalName = protocalName;
    }

    public String getEqpName() {
        return eqpName;
    }

    public void setEqpName(String eqpName) {
        this.eqpName = eqpName;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public String toString() {
        return "BackendVO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", key='" + key + '\'' +
                ", groupName='" + groupName + '\'' +
                ", protocalName='" + protocalName + '\'' +
                ", eqpName='" + eqpName + '\'' +
                ", serverPort=" + serverPort +
                '}';
    }
}
