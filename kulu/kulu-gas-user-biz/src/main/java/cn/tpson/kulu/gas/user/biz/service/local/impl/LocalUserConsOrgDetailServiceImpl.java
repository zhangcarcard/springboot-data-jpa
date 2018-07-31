package cn.tpson.kulu.gas.user.biz.service.local.impl;

import cn.tpson.kulu.gas.api.user.dto.UserConsOrgDetailDTO;
import cn.tpson.kulu.gas.common.service.local.BaseServiceImpl;
import cn.tpson.kulu.gas.user.biz.domain.UserConsOrgDetailDO;
import cn.tpson.kulu.gas.user.biz.service.local.LocalUserConsOrgDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zhangka in 2018/07/30
 */
@Service
@Transactional(readOnly = true)
public class LocalUserConsOrgDetailServiceImpl extends BaseServiceImpl<UserConsOrgDetailDTO, UserConsOrgDetailDO> implements LocalUserConsOrgDetailService {
}
