package cn.tpson.kulu.gas.eqp.biz.domain;

import cn.tpson.kulu.common.constant.EqpDetectStatusEnum;
import cn.tpson.kulu.common.constant.EqpDetectTypeEnum;
import cn.tpson.kulu.common.db.domain.BaseDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Zhangka in 2018/07/19
 */
@Entity(name = "EqpDetectHistory")
@Table(name = "t_eqp_detect_history")
@SequenceGenerator(name = "generator", sequenceName = "t_eqp_detect_history_id_seq", allocationSize = 1)
public class EqpDetectHistoryDO extends BaseDO {
    /**
     * 设备id(t_eqp.id).
     */
    private Long eqpId;

    @Column(length = 20)
    private String detectValue;

    /**
     * 提交状态.
     * @see EqpDetectStatusEnum
     */
    private Byte status;

    /**
     * 检测类型.
     * @see EqpDetectTypeEnum
     */
    private Byte type;

    /**
     * 检测人id.
     */
    private Long uid;

    /**
     * 检测人姓名.
     */
    @Column(length = 20)
    private String realname;

    /**
     * 检测地址.
     */
    @Column(length = 50)
    private String address;

    /**
     * 检测定位纬度.
     */
    private Double lat;

    /**
     * 检测定位经度.
     */
    private Double lon;

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    public Long getEqpId() {
        return eqpId;
    }

    public void setEqpId(Long eqpId) {
        this.eqpId = eqpId;
    }

    public String getDetectValue() {
        return detectValue;
    }

    public void setDetectValue(String detectValue) {
        this.detectValue = detectValue;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
