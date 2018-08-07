package cn.tpson.kulu.dispatcher.biz.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;

/**
 * Created by Zhangka in 2018/08/01
 */
@Entity(name = "WeightBackend")
@Table(name = "t_backend_weight")
@SequenceGenerator(name = "generator", sequenceName = "t_backend_weight_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE t_backend_weight SET is_deleted = true, gmt_modified = now() WHERE id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@DynamicUpdate
@DynamicInsert
public class WeightBackendDO extends BackendDO {
    @Column(nullable = false)
    private Integer weight;

    public WeightBackendDO() {}
    public WeightBackendDO(String ip, Integer port, Integer weight) {
        super(ip, port);
        this.weight = weight;
    }

    public WeightBackendDO(BackendDO b, Integer weight) {
        this(b.getIp(), b.getPort(), weight);
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
