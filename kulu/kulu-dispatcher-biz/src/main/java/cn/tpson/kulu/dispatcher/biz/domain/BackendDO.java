package cn.tpson.kulu.dispatcher.biz.domain;

import cn.tpson.kulu.common.db.domain.BaseDO;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by Zhangka in 2018/08/01
 */

@MappedSuperclass
public class BackendDO extends BaseDO {
    public BackendDO() {}
    public BackendDO(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    /** ip地址 */
    @Column(length = 19, nullable = false)
    private String ip;

    /** port */
    private Integer port;

    /** 协议名称 */
    private String protocalName;

    /** 分组名称 */
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "protocal_id", foreignKey = @ForeignKey(name = "fk_t_protocal_id"))
    private ProtocalDO protocal;

    @ManyToOne
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "fk_t_group_id"))
    private GroupDO group;

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
