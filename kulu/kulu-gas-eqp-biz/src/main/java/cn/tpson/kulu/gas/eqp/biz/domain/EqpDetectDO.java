package cn.tpson.kulu.gas.eqp.biz.domain;

import cn.tpson.kulu.common.constant.EqpDetectStatusEnum;
import cn.tpson.kulu.common.constant.EqpDetectTypeEnum;
import cn.tpson.kulu.common.jpa.db.domain.BaseDO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Zhangka in 2018/07/19
 */
@Entity(name = "EqpDetect")
@Table(name = "t_eqp_detect")
@SequenceGenerator(name = "generator", sequenceName = "t_eqp_detect_id_seq", allocationSize = 1)
public class EqpDetectDO extends BaseDO {
    /**
     * 设备id(t_eqp.id).
     */
    private Long eqpId;

    /**
     * 历史检测记录.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id", foreignKey = @ForeignKey(name = "fk_t_eqp_detect_history_id"))
    private EqpDetectHistoryDO history;

    /**
     * 上次检测历史记录id.
     */
    private Long lastHistoryId;

    @Column(length = 20)
    private String detectValue;
//    private String detectNo;
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
     * 上次检测时间.
     */
    private Date gmtLastDetect;

    /**
     * 检测时间.
     */
    private Date gmtDetect;

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

    public EqpDetectHistoryDO getHistory() {
        return history;
    }

    public void setHistory(EqpDetectHistoryDO history) {
        this.history = history;
    }

    public Long getLastHistoryId() {
        return lastHistoryId;
    }

    public void setLastHistoryId(Long lastHistoryId) {
        this.lastHistoryId = lastHistoryId;
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

    public Date getGmtLastDetect() {
        return gmtLastDetect;
    }

    public void setGmtLastDetect(Date gmtLastDetect) {
        this.gmtLastDetect = gmtLastDetect;
    }

    public Date getGmtDetect() {
        return gmtDetect;
    }

    public void setGmtDetect(Date gmtDetect) {
        this.gmtDetect = gmtDetect;
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
