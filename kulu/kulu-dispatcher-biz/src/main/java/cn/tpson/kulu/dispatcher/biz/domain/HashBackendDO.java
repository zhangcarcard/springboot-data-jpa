package cn.tpson.kulu.dispatcher.biz.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Zhangka in 2018/08/01
 */
@Entity(name = "HashBackend")
@Table(name = "t_backend_hash")
@SequenceGenerator(name = "generator", sequenceName = "t_backend_hash_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE t_backend_hash SET is_deleted = true, gmt_modified = now() WHERE id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@DynamicUpdate
@DynamicInsert
public class HashBackendDO extends BackendDO {
    @Column(length = 50, nullable = false)
    private String key;

    public HashBackendDO() {}
    public HashBackendDO(String ip, Integer port) {
        super(ip, port);
    }

    public HashBackendDO(BackendDO b) {
        this(b.getIp(), b.getPort());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public String toString() {
        return "HashBackend{" +
                "key='" + key + '\'' +
                "} " + super.toString();
    }
}
