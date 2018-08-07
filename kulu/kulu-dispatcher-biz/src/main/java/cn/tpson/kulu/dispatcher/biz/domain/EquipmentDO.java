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
@Entity(name = "Equipment")
@Table(name = "t_equipment")
@SequenceGenerator(name = "generator", sequenceName = "t_equipment_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE t_equipment SET is_deleted = true, gmt_modified = now() WHERE id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@DynamicInsert
@DynamicUpdate
public class EquipmentDO extends BaseDO {
    /** 设备名称 */
    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 1000)
    private String comment;

    /** 转发服务端口 */
    @Column(nullable = false)
    private Integer port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
