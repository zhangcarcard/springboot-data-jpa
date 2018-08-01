package cn.tpson.kulu.dispatcher.biz.service;

import cn.tpson.kulu.common.service.local.BaseService;
import cn.tpson.kulu.dispatcher.biz.domain.UserDO;
import cn.tpson.kulu.dispatcher.biz.dto.UserDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface UserService extends BaseService<UserDTO> {
    UserDTO findByUsername(String username);
}
