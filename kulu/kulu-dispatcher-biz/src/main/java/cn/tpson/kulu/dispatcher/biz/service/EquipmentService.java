package cn.tpson.kulu.dispatcher.biz.service;

import cn.tpson.kulu.common.service.local.BaseService;
import cn.tpson.kulu.dispatcher.biz.dto.EquipmentDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface EquipmentService extends BaseService<EquipmentDTO> {
    EquipmentDTO findByName(String name);
}
