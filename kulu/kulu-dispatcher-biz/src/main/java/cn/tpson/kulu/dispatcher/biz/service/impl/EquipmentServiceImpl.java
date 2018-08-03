package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.EquipmentDO;
import cn.tpson.kulu.dispatcher.biz.dto.EquipmentDTO;
import cn.tpson.kulu.dispatcher.biz.repository.EquipmentRepository;
import cn.tpson.kulu.dispatcher.biz.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zhangka in 2018/08/01
 */
@Service
@Transactional(readOnly = true)
public class EquipmentServiceImpl extends BaseServiceImpl<EquipmentDTO, EquipmentDO> implements EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public EquipmentDTO findByName(String name) {
        return BeanUtils.newAndCopyProperties(EquipmentDTO.class, equipmentRepository.findByName(name));
    }
}
