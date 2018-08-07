package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.jpa.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.UserDO;
import cn.tpson.kulu.dispatcher.biz.dto.UserDTO;
import cn.tpson.kulu.dispatcher.biz.repository.UserRepository;
import cn.tpson.kulu.dispatcher.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zhangka in 2018/08/01
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl extends BaseServiceImpl<UserDTO, UserDO> implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO findByUsername(String username) {
        return BeanUtils.newAndCopyProperties(UserDTO.class, userRepository.findByUsername(username));
    }
}
