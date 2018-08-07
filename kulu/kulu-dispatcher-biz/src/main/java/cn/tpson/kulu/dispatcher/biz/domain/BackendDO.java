package cn.tpson.kulu.dispatcher.biz.domain;


import cn.tpson.kulu.common.jpa.db.domain.BaseDO;

import javax.persistence.*;

/**
 * Created by Zhangka in 2018/08/01
 */

@MappedSuperclass
public class BackendDO extends BaseDO {
    /** ip地址 */
    @Column(length = 19, nullable = false)
    private String ip;

    /** port */
    @Column(nullable = false)
    private Integer port;

    /** 协议名称 */
    @Column(length = 50, nullable = false)
    private String protocalName;

    /** 分组名称 */
    @Column(length = 50, nullable = false)
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "protocal_id", nullable = false)
    private ProtocalDO protocal;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupDO group;

    public BackendDO() {}
    public BackendDO(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
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

    public ProtocalDO getProtocal() {
        return protocal;
    }

    public void setProtocal(ProtocalDO protocal) {
        this.protocal = protocal;
    }

    public GroupDO getGroup() {
        return group;
    }

    public void setGroup(GroupDO group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "BackendDO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", protocalName='" + protocalName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", protocal=" + protocal +
                ", group=" + group +
                "} " + super.toString();
    }

    public String getProtocalName() {
        return protocalName;
    }

    public void setProtocalName(String protocalName) {
        this.protocalName = protocalName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
