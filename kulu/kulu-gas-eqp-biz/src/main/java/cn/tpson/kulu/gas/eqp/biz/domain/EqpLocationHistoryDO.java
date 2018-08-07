package cn.tpson.kulu.gas.eqp.biz.domain;

import cn.tpson.kulu.common.jpa.db.dialect.JsonbType;
import cn.tpson.kulu.common.jpa.db.domain.BaseDO;
import com.alibaba.fastjson.JSON;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Zhangka in 2018/07/19
 */
@Entity(name = "EqpLocationHistory")
@Table(name = "t_eqp_location_history")
@SequenceGenerator(name = "generator", sequenceName = "t_eqp_location_history_id_seq", allocationSize = 1)
@TypeDef(name = "JsonbType", typeClass = JsonbType.class)
public class EqpLocationHistoryDO extends BaseDO {
    /**
     * 设备id(EqpDO.id),
     */
    private Long eqpId;

    @Column(columnDefinition = "jsonb")
    @Type(type = "JsonbType")
    private JSON location;

    ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////

    public Long getEqpId() {
        return eqpId;
    }

    public void setEqpId(Long eqpId) {
        this.eqpId = eqpId;
    }

    public JSON getLocation() {
        return location;
    }

    public void setLocation(JSON location) {
        this.location = location;
    }
}
