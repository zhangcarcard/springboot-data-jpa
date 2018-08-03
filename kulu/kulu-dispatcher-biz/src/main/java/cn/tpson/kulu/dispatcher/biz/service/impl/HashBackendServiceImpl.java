package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.HashBackendDO;
import cn.tpson.kulu.dispatcher.biz.dto.HashBackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.query.HashBackendQUERY;
import cn.tpson.kulu.dispatcher.biz.repository.HashBackendRepository;
import cn.tpson.kulu.dispatcher.biz.service.HashBackendService;
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
public class HashBackendServiceImpl extends BaseServiceImpl<HashBackendDTO, HashBackendDO> implements HashBackendService {
    @Autowired
    private HashBackendRepository hashBackendRepository;

    @Override
    public Page<HashBackendDTO> findByNameContaining(HashBackendQUERY query, Sort sort, String name) {
        sort = (sort == null) ? Sort.by(Sort.Order.desc("gmtModified"), Sort.Order.desc("id")) : sort;
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), sort);
        Page<HashBackendDO> page = hashBackendRepository.findByNameContaining(pageable, "%" + name + "%");

        List<HashBackendDTO> list = BeanUtils.newAndCopyPropertiesForList(HashBackendDTO.class, page.getContent());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }
}
