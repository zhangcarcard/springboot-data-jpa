package cn.tpson.kulu.dispatcher.biz.service;

import cn.tpson.kulu.common.jpa.service.local.BaseService;
import cn.tpson.kulu.dispatcher.biz.dto.HashBackendDTO;

import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface HashBackendService extends BaseService<HashBackendDTO> {
    List<HashBackendDTO> findByKey(String key);
}
