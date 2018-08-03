package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.service.local.BaseServiceImpl;
import cn.tpson.kulu.dispatcher.biz.domain.RandomBackendDO;
import cn.tpson.kulu.dispatcher.biz.dto.RandomBackendDTO;
import cn.tpson.kulu.dispatcher.biz.service.RandomBackendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zhangka in 2018/08/01
 */
@Service
@Transactional(readOnly = true)
public class RandomBackendServiceImpl extends BaseServiceImpl<RandomBackendDTO, RandomBackendDO> implements RandomBackendService {
}
