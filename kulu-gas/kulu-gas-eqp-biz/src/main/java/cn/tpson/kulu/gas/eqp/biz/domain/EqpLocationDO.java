package cn.tpson.kulu.gas.eqp.biz.domain;

import cn.tpson.kulu.gas.common.db.domain.BaseDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Zhangka in 2018/07/19
 */
@Entity(name = "EqpLocation")
@Table(name = "t_eqp_location")
@SequenceGenerator(name = "generator", sequenceName = "t_eqp_location_id_seq", allocationSize = 1)
public class EqpLocationDO extends BaseDO {
    /**
     * 纬度.
     */
    private Double lat;

    /**
     * 经度.
     */
    private Double lon;

    /**
     * 设备id(EqpDO.id),
     */
    private Long eqpId;

    /**
     * 省/市/区.
     */
    @Column(length = 20)
    private String area;

    /**
     * 详细地址.
     */
    @Column(length = 50)
    private String address;

    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
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

    public Long getEqpId() {
        return eqpId;
    }

    public void setEqpId(Long eqpId) {
        this.eqpId = eqpId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
