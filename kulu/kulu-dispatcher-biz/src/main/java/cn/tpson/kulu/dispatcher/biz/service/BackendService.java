package cn.tpson.kulu.dispatcher.biz.service;

import cn.tpson.kulu.common.jpa.service.local.BaseService;
import cn.tpson.kulu.dispatcher.biz.dto.BackendDTO;

import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface BackendService extends BaseService<BackendDTO> {
    List<BackendDTO> findByGroupId(Long groupId);
}
