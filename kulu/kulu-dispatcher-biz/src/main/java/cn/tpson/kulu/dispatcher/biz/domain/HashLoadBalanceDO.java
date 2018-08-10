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
@Entity(name = "HashLoadBalance")
@Table(name = "t_load_balance_hash")
@SequenceGenerator(name = "generator", sequenceName = "t_load_balance_hash_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE t_load_balance_hash SET is_deleted = true, gmt_modified = now() WHERE id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@DynamicUpdate
@DynamicInsert
public class HashLoadBalanceDO extends BaseDO {
    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String key;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupDO group;

    @Column(length = 50, nullable = false)
    private String eqpName;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public GroupDO getGroup() {
        return group;
    }

    public void setGroup(GroupDO group) {
        this.group = group;
    }

    public String getEqpName() {
        return eqpName;
    }

    public void setEqpName(String eqpName) {
        this.eqpName = eqpName;
    }

    @Override
    public String toString() {
        return "HashLoadBalanceDO{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", group=" + group +
                ", eqpName='" + eqpName + '\'' +
                '}';
    }
}
