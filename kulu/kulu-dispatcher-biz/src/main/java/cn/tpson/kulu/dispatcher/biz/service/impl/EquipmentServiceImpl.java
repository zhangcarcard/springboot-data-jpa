package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.jpa.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.EquipmentDO;
import cn.tpson.kulu.dispatcher.biz.dto.EquipmentDTO;
import cn.tpson.kulu.dispatcher.biz.repository.EquipmentRepository;
import cn.tpson.kulu.dispatcher.biz.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
@Service
@Transactional(readOnly = true)
public class EquipmentServiceImpl extends BaseServiceImpl<EquipmentDTO, EquipmentDO> implements EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public Page<EquipmentDTO> pageByKeywordContaining(Integer pageNumber, Integer pageSize, Sort sort, String search) {
        sort = (sort == null) ? Sort.by(Sort.Order.desc("gmtModified"), Sort.Order.desc("id")) : sort;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<EquipmentDO> page =  equipmentRepository.findByKeywordContaining(pageable, "%" + search + "%");
        List<EquipmentDTO> list = BeanUtils.newAndCopyPropertiesForList(EquipmentDTO.class, page.getContent());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    public EquipmentDTO findByName(String name) {
        return BeanUtils.newAndCopyProperties(EquipmentDTO.class, equipmentRepository.findByName(name));
    }

    @Override
    public EquipmentDTO findByPort(Integer port) {
        return BeanUtils.newAndCopyProperties(EquipmentDTO.class, equipmentRepository.findByPort(port));
    }
}
