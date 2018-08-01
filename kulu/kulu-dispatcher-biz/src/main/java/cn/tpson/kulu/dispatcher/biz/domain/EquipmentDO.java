package cn.tpson.kulu.dispatcher.biz.domain;

import cn.tpson.kulu.common.db.domain.BaseDO;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
@Entity(name = "Equipment")
@Table(name = "t_equipment")
@SequenceGenerator(name = "generator", sequenceName = "t_equipment_seq", allocationSize = 1)
@SQLDelete(sql = "DELETE FROM t_equipment SET is_deleted = true WHERE id = :id AND version = :version")
@Where(clause = "is_deleted = false")
public class EquipmentDO extends BaseDO {
    /** 设备名称 */
    @Column(length = 50, nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "equipment")
    private List<ProtocalDO> protocals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProtocalDO> getProtocals() {
        return protocals;
    }

    public void setProtocals(List<ProtocalDO> protocals) {
        this.protocals = protocals;
    }
}
