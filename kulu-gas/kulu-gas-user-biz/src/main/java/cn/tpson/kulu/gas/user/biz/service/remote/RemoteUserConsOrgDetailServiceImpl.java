package cn.tpson.kulu.gas.user.biz.service.remote;

import cn.tpson.kulu.gas.api.user.dto.UserConsOrgDetailDTO;
import cn.tpson.kulu.gas.api.user.query.UserConsOrgDetailQUERY;
import cn.tpson.kulu.gas.api.user.service.RemoteUserConsOrgDetailService;
import cn.tpson.kulu.gas.common.remote.BaseRemoteServiceImpl;
import cn.tpson.kulu.gas.user.biz.domain.UserConsOrgDetailDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zhangka in 2018/07/27
 */
@Service
@Transactional(readOnly = true)
public class RemoteUserConsOrgDetailServiceImpl extends BaseRemoteServiceImpl<UserConsOrgDetailDTO, UserConsOrgDetailQUERY, UserConsOrgDetailDO> implements RemoteUserConsOrgDetailService {
}