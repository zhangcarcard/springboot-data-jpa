package cn.tpson.kulu.dispatcher.biz.service;

import cn.tpson.kulu.common.jpa.service.local.BaseService;
import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface GroupService extends BaseService<GroupDTO> {
    GroupDTO findByName(String name);

    void deleteAllEntity(Iterable<GroupDTO> entities);
}
