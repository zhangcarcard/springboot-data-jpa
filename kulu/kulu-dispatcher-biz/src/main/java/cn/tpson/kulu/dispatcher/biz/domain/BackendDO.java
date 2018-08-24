package cn.tpson.kulu.dispatcher.biz.domain;


import cn.tpson.kulu.common.jpa.db.domain.BaseDO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by Zhangka in 2018/08/01
 */

@Entity(name = "Backend")
@Table(name = "t_backend")
@SequenceGenerator(name = "generator", sequenceName = "t_backend_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE t_backend SET is_deleted = true, gmt_modified = now() WHERE id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@DynamicInsert
@DynamicUpdate
public class BackendDO extends BaseDO {
    /** ip或者域名地址 */
    @Column(length = 50, nullable = false)
    private String ip;

    /** port */
    @Column(nullable = false)
    private Integer port;

    /** 协议名称 */
    @Column(length = 50, nullable = false)
    private String protocalName;

    /** 设备名称 */
    @Column(length = 50, nullable = false)
    private String eqpName;

    @Column(nullable = false)
    private Long groupId;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "BackendDO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", protocalName='" + protocalName + '\'' +
                ", eqpName='" + eqpName + '\'' +
                ", groupId=" + groupId +
                "} " + super.toString();
    }
}
