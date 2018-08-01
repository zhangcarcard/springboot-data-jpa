package cn.tpson.kulu.api.user.service;

import cn.tpson.kulu.api.user.dto.UserConsOrgDetailDTO;
import cn.tpson.kulu.common.service.remote.RemoteBaseService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Zhangka in 2018/07/27
 */
@FeignClient(value = "USERSERVICE")
public interface RemoteUserConsOrgDetailService extends RemoteBaseService<UserConsOrgDetailDTO> {
}
