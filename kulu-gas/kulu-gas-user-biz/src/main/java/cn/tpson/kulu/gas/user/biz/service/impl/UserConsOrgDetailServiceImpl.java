package cn.tpson.kulu.gas.user.biz.service.impl;

import cn.tpson.kulu.gas.common.jpa.service.impl.BaseServiceImpl;
import cn.tpson.kulu.gas.user.biz.domain.UserConsOrgDetailDO;
import cn.tpson.kulu.gas.user.biz.service.UserConsOrgDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zhangka in 2018/07/27
 */
@Service
@Transactional(readOnly = true)
public class UserConsOrgDetailServiceImpl extends BaseServiceImpl<UserConsOrgDetailDO, Long> implements UserConsOrgDetailService {
}
