package cn.tpson.kulu.gas.user.biz.domain;

import cn.tpson.kulu.common.jpa.db.domain.BaseDO;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Zhangka in 2018/07/19
 */
@Entity(name = "UserConsOrgDetail")
@Table(name = "t_user_cons_org_detail")
@SequenceGenerator(name = "generator", sequenceName = "t_user_cons_org_detail_seq", allocationSize = 1)
@SQLDelete(sql = "DELETE FROM t_user_cons_org_detail SET is_deleted = true WHERE id = :id AND version = :version")
@Where(clause = "is_deleted = false")
public class UserConsOrgDetailDO extends BaseDO {
    @Column(name = "busi_licence_img", length = 1000)
    private String busiLicenceImg;

    @Column(name = "contact_person", length = 20)
    private String contactPerson;

    @Column(name = "cons_licence_code", length = 30)
    private String consLicenceCode;

    @Column(name = "cons_licence_img", length = 1000)
    private String consLicenceImg;

    @Column(name = "cons_org_name", length = 50)
    private String consOrgName;

    @Column(name = "location_addr", length = 100)
    private String locationAddr;

    @Column(name = "location_lat")
    private Double locationLat;

    @Column(name = "location_lon")
    private Double locationLon;

    @Column(name = "pm_name", length = 20)
    private String pmName;

    @Column(name = "project_addr", length = 100)
    private String projectAddr;

    @Column(name = "project_name", length = 50)
    private String projectName;

    @Column(name = "tax_id", length = 50)
    private String taxId;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    public String getBusiLicenceImg() {
        return busiLicenceImg;
    }

    public void setBusiLicenceImg(String busiLicenceImg) {
        this.busiLicenceImg = busiLicenceImg;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getConsLicenceCode() {
        return consLicenceCode;
    }

    public void setConsLicenceCode(String consLicenceCode) {
        this.consLicenceCode = consLicenceCode;
    }

    public String getConsLicenceImg() {
        return consLicenceImg;
    }

    public void setConsLicenceImg(String consLicenceImg) {
        this.consLicenceImg = consLicenceImg;
    }

    public String getConsOrgName() {
        return consOrgName;
    }

    public void setConsOrgName(String consOrgName) {
        this.consOrgName = consOrgName;
    }

    public String getLocationAddr() {
        return locationAddr;
    }

    public void setLocationAddr(String locationAddr) {
        this.locationAddr = locationAddr;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Double getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(Double locationLon) {
        this.locationLon = locationLon;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public String getProjectAddr() {
        return projectAddr;
    }

    public void setProjectAddr(String projectAddr) {
        this.projectAddr = projectAddr;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
}
