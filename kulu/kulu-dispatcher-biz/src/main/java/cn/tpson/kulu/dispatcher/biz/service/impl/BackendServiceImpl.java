package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.jpa.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.BackendDO;
import cn.tpson.kulu.dispatcher.biz.domain.HashLoadBalanceDO;
import cn.tpson.kulu.dispatcher.biz.dto.BackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.HashLoadBalanceDTO;
import cn.tpson.kulu.dispatcher.biz.repository.BackendRepository;
import cn.tpson.kulu.dispatcher.biz.repository.HashLoadBalanceRepository;
import cn.tpson.kulu.dispatcher.biz.service.BackendService;
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
public class BackendServiceImpl extends BaseServiceImpl<BackendDTO, BackendDO> implements BackendService {
    @Autowired
    private BackendRepository backendRepository;

    @Override
    public Page<BackendDTO> pageByKeywordContaining(Integer pageNumber, Integer pageSize, Sort sort, String keyword) {
        sort = (sort == null) ? Sort.by(Sort.Order.desc("gmtModified"), Sort.Order.desc("id")) : sort;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<BackendDO> page = backendRepository.findByKeywordContaining(pageable, "%" + keyword + "%");

        List<BackendDTO> list = BeanUtils.newAndCopyPropertiesForList(BackendDTO.class, page.getContent());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    public List<BackendDTO> findByGroupId(Long groupId) {
        return BeanUtils.newAndCopyPropertiesForList(BackendDTO.class, backendRepository.findByGroupId(groupId));
    }

    @Override
    public List<BackendDTO> findByEqpName(String eqpName) {
        return BeanUtils.newAndCopyPropertiesForList(BackendDTO.class, backendRepository.findByEqpName(eqpName));
    }

    @Override
    public int countByEqpNameAndGroupId(String eqpName, Long groupId) {
        return backendRepository.countByEqpNameAndGroupId(eqpName, groupId);
    }

    @Override
    public List<String> findAllEqpName() {
        return backendRepository.findAllEqpName();
    }

    @Override
    public List<String> findAllEqpNameByGroupId(Long groupId) {
        return backendRepository.findAllEqpNameByGroupId(groupId);
    }

    @Override
    public BackendDTO findByEqpNameAndGroupId(String eqpName, Long groupId) {
        return BeanUtils.newAndCopyProperties(BackendDTO.class, backendRepository.findByEqpNameAndGroupId(eqpName, groupId));
    }
}
