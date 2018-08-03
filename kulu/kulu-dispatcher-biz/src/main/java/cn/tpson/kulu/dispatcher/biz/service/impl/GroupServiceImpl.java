package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.GroupDO;
import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;
import cn.tpson.kulu.dispatcher.biz.repository.GroupRepository;
import cn.tpson.kulu.dispatcher.biz.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zhangka in 2018/08/01
 */
@Service
@Transactional(readOnly = true)
public class GroupServiceImpl extends BaseServiceImpl<GroupDTO, GroupDO> implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public GroupDTO findByName(String name) {
        return BeanUtils.newAndCopyProperties(GroupDTO.class, groupRepository.findByName(name));
    }
}
