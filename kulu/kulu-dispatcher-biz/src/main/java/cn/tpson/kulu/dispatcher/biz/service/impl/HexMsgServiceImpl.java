package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.jpa.service.local.BaseServiceImpl;
import cn.tpson.kulu.dispatcher.biz.domain.HexMsgDO;
import cn.tpson.kulu.dispatcher.biz.dto.HexMsgDTO;
import cn.tpson.kulu.dispatcher.biz.service.HexMsgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zhangka in 2018/08/01
 */
@Service
@Transactional(readOnly = true)
public class HexMsgServiceImpl extends BaseServiceImpl<HexMsgDTO, HexMsgDO> implements HexMsgService {
}
