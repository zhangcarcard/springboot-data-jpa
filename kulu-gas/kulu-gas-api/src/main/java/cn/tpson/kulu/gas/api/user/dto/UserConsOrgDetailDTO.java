package cn.tpson.kulu.gas.api.user.dto;

/**
 * Created by Zhangka in 2018/07/19
 */
public class UserConsOrgDetailDTO extends BaseDTO {
    private String busiLicenceImg;

    private String contactPerson;

    private String consLicenceCode;

    private String consLicenceImg;

    private String consOrgName;

    private String locationAddr;

    private Double locationLat;

    private Double locationLon;

    private String pmName;

    private String projectAddr;

    private String projectName;

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
