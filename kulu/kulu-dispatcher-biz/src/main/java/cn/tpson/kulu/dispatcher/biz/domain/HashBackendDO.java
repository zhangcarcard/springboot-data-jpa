package cn.tpson.kulu.dispatcher.biz.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Zhangka in 2018/08/01
 */
@Entity(name = "HashBackend")
@Table(name = "t_hash_backend")
@SequenceGenerator(name = "generator", sequenceName = "t_hash_backend_seq", allocationSize = 1)
@SQLDelete(sql = "DELETE FROM t_hash_backend SET is_deleted = true WHERE id = :id AND version = :version")
@Where(clause = "is_deleted = false")
public class HashBackendDO extends BackendDO {
    private String key;

    public HashBackendDO() {}
    public HashBackendDO(String ip, Integer port, String ownerName, String groupName) {
        super(ip, port, ownerName, groupName);
    }

    public HashBackendDO(BackendDO b) {
        this(b.getIp(), b.getPort(), b.getEqpName(), b.getGroupName());
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
