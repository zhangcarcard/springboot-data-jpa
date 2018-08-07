package cn.tpson.kulu.gas.eqp.biz.domain;

import cn.tpson.kulu.common.constant.CommonEnum;
import cn.tpson.kulu.common.jpa.db.domain.BaseDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Zhangka in 2018/07/19
 * 超时未检.
 */
@Entity(name = "EqpDetectTimeout")
@Table(name = "t_eqp_detect_timeout")
@SequenceGenerator(name = "generator", sequenceName = "t_eqp_detect_timeout_seq", allocationSize = 1)
public class EqpDetectTimeoutDO extends BaseDO {
    /**
     * 设备id(EqpDO.id).
     */
    private Long eqpId;

    /**
     * 设备编号.
     */
    @Column(length = 20)
    private String eqpNo;

    /**
     * 超时原因.
     */
    @Column(length = 50)
    private String reason;

    /**
     * 超时小时数.
     */
    private Integer timeoutHours;

    /**
     * 年度未检次数.
     */
    private Byte numberPerYear;

    /**
     * 施工单位id(SysUserBuildDetailDO.id)
     */
    private Integer bsId;

    /**
     * 施工单位名称.
     */
    @Column(length = 50)
    private String bsName;

    /**
     * 联系人.
     */
    @Column(length = 20)
    private String contactPerson;

    /**
     * 联系电话.
     */
    @Column(length = 20)
    private String phoneNo;

    /**
     * 最后一次检测时间.
     */
    private Date gmtDetect;

    /**
     * 登记时间.
     */
    private Date gmtCheckin;

    /**
     * 状态.
     * @see CommonEnum
     */
    private Byte status;

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    public Long getEqpId() {
        return eqpId;
    }

    public void setEqpId(Long eqpId) {
        this.eqpId = eqpId;
    }

    public String getEqpNo() {
        return eqpNo;
    }

    public void setEqpNo(String eqpNo) {
        this.eqpNo = eqpNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getTimeoutHours() {
        return timeoutHours;
    }

    public void setTimeoutHours(Integer timeoutHours) {
        this.timeoutHours = timeoutHours;
    }

    public Byte getNumberPerYear() {
        return numberPerYear;
    }

    public void setNumberPerYear(Byte numberPerYear) {
        this.numberPerYear = numberPerYear;
    }

    public Integer getBsId() {
        return bsId;
    }

    public void setBsId(Integer bsId) {
        this.bsId = bsId;
    }

    public String getBsName() {
        return bsName;
    }

    public void setBsName(String bsName) {
        this.bsName = bsName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getGmtDetect() {
        return gmtDetect;
    }

    public void setGmtDetect(Date gmtDetect) {
        this.gmtDetect = gmtDetect;
    }

    public Date getGmtCheckin() {
        return gmtCheckin;
    }

    public void setGmtCheckin(Date gmtCheckin) {
        this.gmtCheckin = gmtCheckin;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
