package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.jpa.service.local.BaseServiceImpl;
import cn.tpson.kulu.dispatcher.biz.domain.WeightBackendDO;
import cn.tpson.kulu.dispatcher.biz.dto.WeightBackendDTO;
import cn.tpson.kulu.dispatcher.biz.service.WeightBackendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zhangka in 2018/08/01
 */
@Service
@Transactional(readOnly = true)
public class WeightBackendServiceImpl extends BaseServiceImpl<WeightBackendDTO, WeightBackendDO> implements WeightBackendService {
}
