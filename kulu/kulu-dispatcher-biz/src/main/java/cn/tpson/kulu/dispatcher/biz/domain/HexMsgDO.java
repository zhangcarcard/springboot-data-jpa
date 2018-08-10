package cn.tpson.kulu.dispatcher.biz.domain;

import cn.tpson.kulu.common.jpa.db.domain.BaseDO;
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
@Entity(name = "HexMsg")
@Table(name = "t_hex_msg")
@SequenceGenerator(name = "generator", sequenceName = "t_hex_msg_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE t_hex_msg SET is_deleted = true, gmt_modified = now() WHERE id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@DynamicInsert
@DynamicUpdate
public class HexMsgDO extends BaseDO {
    /** 转发服务端口 */
    @Column(nullable = false)
    private Integer port;

    /** hash key */
    @Column(length = 50, nullable = false)
    private String key;

    /** 十六进制编码字符串消息 */
    @Column(length = 1000, nullable = false)
    private String hexMsg;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHexMsg() {
        return hexMsg;
    }

    public void setHexMsg(String hexMsg) {
        this.hexMsg = hexMsg;
    }
}
