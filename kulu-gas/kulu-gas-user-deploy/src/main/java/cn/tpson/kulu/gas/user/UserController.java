package cn.tpson.kulu.gas.user;

import cn.tpson.kulu.gas.api.user.dto.UserConsOrgDetailDTO;
import cn.tpson.kulu.gas.api.user.service.RemoteUserConsOrgDetailService;
import cn.tpson.kulu.gas.common.service.remote.RemoteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Zhangka in 2018/07/30
 */
@RestController
public class UserController {
    @Autowired
    RemoteUserConsOrgDetailService remoteUserConsOrgDetailService;

    @GetMapping("/findById")
    public RemoteResult<UserConsOrgDetailDTO> findById(Long id) {
        return remoteUserConsOrgDetailService.findById(id);
    }
}
