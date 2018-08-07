package cn.tpson.kulu.dispatcher.biz.service;

import cn.tpson.kulu.common.jpa.service.local.BaseService;
import cn.tpson.kulu.dispatcher.biz.dto.ProtocalDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface ProtocalService extends BaseService<ProtocalDTO> {
    ProtocalDTO findByName(String name);
}
