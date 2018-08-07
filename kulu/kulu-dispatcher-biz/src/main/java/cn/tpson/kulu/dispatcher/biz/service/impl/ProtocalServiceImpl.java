package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.jpa.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.ProtocalDO;
import cn.tpson.kulu.dispatcher.biz.dto.ProtocalDTO;
import cn.tpson.kulu.dispatcher.biz.repository.ProtocalRepository;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
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
public class ProtocalServiceImpl extends BaseServiceImpl<ProtocalDTO, ProtocalDO> implements ProtocalService {
    @Autowired
    private ProtocalRepository protocalRepository;

    @Override
    public Page<ProtocalDTO> pageByKeywordContaining(Integer pageNumber, Integer pageSize, Sort sort, String keyword) {
        sort = (sort == null) ? Sort.by(Sort.Order.desc("gmtModified"), Sort.Order.desc("id")) : sort;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<ProtocalDO> page = protocalRepository.findByKeywordContaining(pageable, "%" + keyword + "%");

        List<ProtocalDTO> list = BeanUtils.newAndCopyPropertiesForList(ProtocalDTO.class, page.getContent());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    public ProtocalDTO findByName(String name) {
        return BeanUtils.newAndCopyProperties(ProtocalDTO.class, protocalRepository.findByName(name));
    }
}
