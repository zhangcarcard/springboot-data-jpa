package cn.tpson.kulu.dispatcher.biz.domain;

import cn.tpson.kulu.common.db.domain.BaseDO;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Zhangka in 2018/08/01
 */

@Entity(name = "Backend")
@Table(name = "t_backend")
@SequenceGenerator(name = "generator", sequenceName = "t_backend_seq", allocationSize = 1)
@SQLDelete(sql = "DELETE FROM t_backend SET is_deleted = true WHERE id = :id AND version = :version")
@Where(clause = "is_deleted = false")
public class BackendDO extends BaseDO {
    public BackendDO() {}
    public BackendDO(String ip, Integer port, String eqpName, String groupName) {
        this.ip = ip;
        this.port = port;
        this.eqpName = eqpName;
        this.groupName = groupName;
    }


    /** ip地址 */
    @Column(length = 19, nullable = false)
    private String ip;

    /** port */
    private Integer port;

    /** 所属设备名称 */
    @Column(length = 50, nullable = false)
    private String eqpName;

    /** 分组名称 */
    @Column(length = 50, nullable = false)
    private String groupName;

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

    public String getEqpName() {
        return eqpName;
    }

    public void setEqpName(String eqpName) {
        this.eqpName = eqpName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "BackendDO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", eqpName='" + eqpName + '\'' +
                ", groupName='" + groupName + '\'' +
                "} " + super.toString();
    }
}
