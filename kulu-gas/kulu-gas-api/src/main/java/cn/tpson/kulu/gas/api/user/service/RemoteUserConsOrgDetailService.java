package cn.tpson.kulu.gas.api.user.service;

import cn.tpson.kulu.gas.api.user.dto.UserConsOrgDetailDTO;
import cn.tpson.kulu.gas.common.service.remote.BaseRemoteService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Zhangka in 2018/07/27
 */
@FeignClient(value = "USERSERVICE")
public interface RemoteUserConsOrgDetailService extends BaseRemoteService<UserConsOrgDetailDTO> {
}
