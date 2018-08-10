package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.jpa.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.GroupDO;
import cn.tpson.kulu.dispatcher.biz.domain.HashLoadBalanceDO;
import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;
import cn.tpson.kulu.dispatcher.biz.dto.HashLoadBalanceDTO;
import cn.tpson.kulu.dispatcher.biz.repository.HashLoadBalanceRepository;
import cn.tpson.kulu.dispatcher.biz.service.HashLoadBalanceService;
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
public class HashLoadBalanceServiceImpl extends BaseServiceImpl<HashLoadBalanceDTO, HashLoadBalanceDO> implements HashLoadBalanceService {
    @Autowired
    private HashLoadBalanceRepository hashBackendRepository;

    @Override
    public Page<HashLoadBalanceDTO> pageByKeywordContaining(Integer pageNumber, Integer pageSize, Sort sort, String keyword) {
        sort = (sort == null) ? Sort.by(Sort.Order.desc("gmtModified"), Sort.Order.desc("id")) : sort;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<HashLoadBalanceDO> page = hashBackendRepository.findByKeywordContaining(pageable, "%" + keyword + "%");

        List<HashLoadBalanceDTO> list = BeanUtils.newAndCopyPropertiesForList(HashLoadBalanceDTO.class, page.getContent());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    public List<HashLoadBalanceDTO> findByKey(String key) {
        return BeanUtils.newAndCopyPropertiesForList(HashLoadBalanceDTO.class, hashBackendRepository.findByKey(key));
    }

    @Override
    public List<HashLoadBalanceDTO> findByGroup(GroupDTO groupDTO) {
        GroupDO group = BeanUtils.newAndCopyProperties(GroupDO.class, groupDTO);
        return BeanUtils.newAndCopyPropertiesForList(HashLoadBalanceDTO.class, hashBackendRepository.findByGroup(group));
    }
}
