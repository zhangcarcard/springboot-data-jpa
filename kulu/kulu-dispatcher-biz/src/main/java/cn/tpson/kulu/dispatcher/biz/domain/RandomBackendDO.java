package cn.tpson.kulu.dispatcher.biz.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Zhangka in 2018/08/01
 */

@Entity(name = "RandomBackend")
@Table(name = "t_backend_random")
@SequenceGenerator(name = "generator", sequenceName = "t_backend_random_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE t_backend_random SET is_deleted = true, gmt_modified = now() WHERE id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@DynamicUpdate
@DynamicInsert
public class RandomBackendDO extends BackendDO {
    public RandomBackendDO() {}
    public RandomBackendDO(String ip, Integer port) {
        super(ip, port);
    }
}
