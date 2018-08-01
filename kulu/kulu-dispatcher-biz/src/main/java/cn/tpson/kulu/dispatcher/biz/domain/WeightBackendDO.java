package cn.tpson.kulu.dispatcher.biz.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Zhangka in 2018/08/01
 */
@Entity(name = "WeightBackend")
@Table(name = "t_weight_backend")
@SequenceGenerator(name = "generator", sequenceName = "t_weight_backend_seq", allocationSize = 1)
@SQLDelete(sql = "DELETE FROM t_weight_backend SET is_deleted = true WHERE id = :id AND version = :version")
@Where(clause = "is_deleted = false")
public class WeightBackendDO extends BackendDO {
    private Integer weight;

    public WeightBackendDO() {}
    public WeightBackendDO(String ip, Integer port, String ownerName, String groupName, Integer weight) {
        super(ip, port, ownerName, groupName);
        this.weight = weight;
    }

    public WeightBackendDO(BackendDO b, Integer weight) {
        this(b.getIp(), b.getPort(), b.getEqpName(), b.getGroupName(), weight);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "WeightBackendDO{" +
                "weight=" + weight +
                "} " + super.toString();
    }
}
