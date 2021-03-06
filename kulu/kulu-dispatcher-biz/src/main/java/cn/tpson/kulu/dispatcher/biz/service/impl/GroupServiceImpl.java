package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.jpa.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.GroupDO;
import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;
import cn.tpson.kulu.dispatcher.biz.repository.GroupRepository;
import cn.tpson.kulu.dispatcher.biz.service.BackendService;
import cn.tpson.kulu.dispatcher.biz.service.GroupService;
import cn.tpson.kulu.dispatcher.biz.service.HashLoadBalanceService;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
@Service
@Transactional(readOnly = true)
public class GroupServiceImpl extends BaseServiceImpl<GroupDTO, GroupDO> implements GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private HashLoadBalanceService hashLoadBalanceService;
    @Autowired
    private BackendService backendService;

    @Override
    public Page<GroupDTO> pageByKeywordContaining(Integer pageNumber, Integer pageSize, Sort sort, String search) {
        sort = (sort == null) ? Sort.by(Sort.Order.desc("gmtModified"), Sort.Order.desc("id")) : sort;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<GroupDO> page =  groupRepository.findByKeywordContaining(pageable, "%" + search + "%");
        List<GroupDTO> list = BeanUtils.newAndCopyPropertiesForList(GroupDTO.class, page.getContent());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    public GroupDTO findByName(String name) {
        return BeanUtils.newAndCopyProperties(GroupDTO.class, groupRepository.findByName(name));
    }

    @Transactional
    @Override
    public void deleteAllEntity(Iterable<GroupDTO> entities) {
        Iterator<GroupDTO> iterator = entities.iterator();
        while (iterator.hasNext()) {
            GroupDTO group = iterator.next();
            hashLoadBalanceService.deleteAllEntity(hashLoadBalanceService.findByGroup(group));
            backendService.deleteAllEntity(backendService.findByGroupId(group.getId()));
        }
        super.deleteAllEntity(entities);
    }
}
