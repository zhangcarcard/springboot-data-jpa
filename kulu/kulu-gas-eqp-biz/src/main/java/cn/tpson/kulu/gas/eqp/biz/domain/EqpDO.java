package cn.tpson.kulu.gas.eqp.biz.domain;

import cn.tpson.kulu.gas.common.db.domain.BaseDO;

import javax.persistence.*;

/**
 * Created by Zhangka in 2018/07/19
 */
@Entity(name = "Eqp")
@Table(name = "t_eqp", uniqueConstraints = @UniqueConstraint(name = "uk_t_eqp_eqpno", columnNames = "eqp_no"))
@SequenceGenerator(name = "generator", sequenceName = "t_eqp_id_seq", allocationSize = 1)
public class EqpDO extends BaseDO {
    /**
     * 设备唯一编号
     */
    @Column(name = "eqp_no", length = 20)
    private String eqpNo;

    /**
     * 设备品牌
     */
    @Column(length = 20)
    private String brand;

    /**
     * 设备类型
     *
     * @see cn.tpson.kulu.gas.common.constant.EqpTypeEnum
     */
    private Byte type;

    /**
     * 机龄
     */
    private Byte age;

    /**
     * 铭牌
     */
    @Column(length = 20)
    private String dataPlate;

    /**
     * 机主
     */
    @Column(length = 20)
    private String ownerName;

    /**
     * 申请者id
     */
    private Long applyId;

    /**
     * 申请者名称
     */
    @Column(length = 20)
    private String applyName;

    /**
     * 状态
     *
     * @see cn.tpson.kulu.gas.common.constant.EqpStatusEnum
     */
    private Byte status;

    /**
     * 所属工地名称
     */
    @Column(length = 50)
    private String bsName;

    /**
     * 所属工地id
     */
    private Long bsId;

    /**
     * 排放等级
     */
    @Column(length = 10)
    private String disLevel;

    /**
     * 审核者名称
     */
    @Column(length = 20)
    private String auditorName;

    /**
     * 审核者id
     */
    private Long auditorId;

    /**
     * 联系电话
     */
    @Column(length = 20)
    private String phoneNo;

    /**
     * 发动机制造商
     */
    @Column(length = 20)
    private String engineManufacturer;

    /**
     * 发动机型号
     */
    @Column(length = 20)
    private String engineType;

    /**
     * 发动机额定功率
     */
    private Byte enginePower;

    /**
     * 发动机额定转速
     */
    private Byte engineSpeed;

    /**
     * 出厂年份
     */
    @Column(length = 4)
    private String yearManufacture;

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    public String getEqpNo() {
        return eqpNo;
    }

    public void setEqpNo(String eqpNo) {
        this.eqpNo = eqpNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getDataPlate() {
        return dataPlate;
    }

    public void setDataPlate(String dataPlate) {
        this.dataPlate = dataPlate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getBsName() {
        return bsName;
    }

    public void setBsName(String bsName) {
        this.bsName = bsName;
    }

    public Long getBsId() {
        return bsId;
    }

    public void setBsId(Long bsId) {
        this.bsId = bsId;
    }

    public String getDisLevel() {
        return disLevel;
    }

    public void setDisLevel(String disLevel) {
        this.disLevel = disLevel;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEngineManufacturer() {
        return engineManufacturer;
    }

    public void setEngineManufacturer(String engineManufacturer) {
        this.engineManufacturer = engineManufacturer;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Byte getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Byte enginePower) {
        this.enginePower = enginePower;
    }

    public Byte getEngineSpeed() {
        return engineSpeed;
    }

    public void setEngineSpeed(Byte engineSpeed) {
        this.engineSpeed = engineSpeed;
    }

    public String getYearManufacture() {
        return yearManufacture;
    }

    public void setYearManufacture(String yearManufacture) {
        this.yearManufacture = yearManufacture;
    }
}
