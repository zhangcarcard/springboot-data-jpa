package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.jpa.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.HashBackendDO;
import cn.tpson.kulu.dispatcher.biz.dto.HashBackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.query.HashBackendQUERY;
import cn.tpson.kulu.dispatcher.biz.repository.HashBackendRepository;
import cn.tpson.kulu.dispatcher.biz.service.HashBackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    public Page<HashBackendDTO> pageByKeywordContaining(Integer pageNumber, Integer pageSize, Sort sort, String keyword) {
        sort = (sort == null) ? Sort.by(Sort.Order.desc("gmtModified"), Sort.Order.desc("id")) : sort;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<HashBackendDO> page = hashBackendRepository.findByKeywordContaining(pageable, "%" + keyword + "%");

        List<HashBackendDTO> list = BeanUtils.newAndCopyPropertiesForList(HashBackendDTO.class, page.getContent());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    public List<HashBackendDTO> findByKey(String key) {
        return BeanUtils.newAndCopyPropertiesForList(HashBackendDTO.class, hashBackendRepository.findByKey(key));
    }
}
