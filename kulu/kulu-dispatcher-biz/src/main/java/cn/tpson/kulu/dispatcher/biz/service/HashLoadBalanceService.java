package cn.tpson.kulu.dispatcher.biz.service;

import cn.tpson.kulu.common.jpa.service.local.BaseService;
import cn.tpson.kulu.dispatcher.biz.domain.GroupDO;
import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;
import cn.tpson.kulu.dispatcher.biz.dto.HashLoadBalanceDTO;

import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface HashLoadBalanceService extends BaseService<HashLoadBalanceDTO> {
    List<HashLoadBalanceDTO> findByKey(String key);

    List<HashLoadBalanceDTO> findByGroup(GroupDTO group);

    HashLoadBalanceDTO findByName(String name);

    int countByNameAndGroup(String name, GroupDO group);
}
