package cn.tpson.kulu.dispatcher.biz.dto.query;

import cn.tpson.kulu.dispatcher.biz.dto.EquipmentDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public class EquipmentQUERY extends EquipmentDTO {
    public EquipmentQUERY() {}
    public EquipmentQUERY(Integer pageNumber, Integer pageSize) {
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }
}
