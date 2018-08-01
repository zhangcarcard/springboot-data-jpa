package cn.tpson.kulu.dispatcher.biz.domain;

import cn.tpson.kulu.common.db.domain.BaseDO;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by Zhangka in 2018/08/01
 */
@Entity(name = "Protocal")
@Table(name = "t_protocal")
@SequenceGenerator(name = "generator", sequenceName = "t_protocal_seq", allocationSize = 1)
@SQLDelete(sql = "DELETE FROM t_protocal SET is_deleted = true WHERE id = :id AND version = :version")
@Where(clause = "is_deleted = false")
public class ProtocalDO extends BaseDO {
    @Column(length = 50)
    private String name;

    @Column(length = 10)
    private String startFlag;

    @Column(length = 10)
    private String endFlag;

    @Column(length = 10)
    private String split;

    @Column(name = "offset_num")
    private Integer offset;
    private Integer length;

    @ManyToOne
    @JoinColumn(name = "eqp_id", foreignKey = @ForeignKey(name = "fk_t_equipment_id"))
    private EquipmentDO equipment;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(String startFlag) {
        this.startFlag = startFlag;
    }

    public String getEndFlag() {
        return endFlag;
    }

    public void setEndFlag(String endFlag) {
        this.endFlag = endFlag;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public EquipmentDO getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentDO equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "ProtocalDO{" +
                "name='" + name + '\'' +
                ", startFlag='" + startFlag + '\'' +
                ", endFlag='" + endFlag + '\'' +
                ", split='" + split + '\'' +
                ", offset=" + offset +
                ", length=" + length +
                ", equipment=" + equipment +
                "} " + super.toString();
    }
}
