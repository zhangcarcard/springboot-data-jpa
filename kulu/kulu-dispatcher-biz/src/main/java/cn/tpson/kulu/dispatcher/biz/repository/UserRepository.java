package cn.tpson.kulu.dispatcher.biz.repository;

import cn.tpson.kulu.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.dispatcher.biz.domain.UserDO;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface UserRepository extends BaseRepository<UserDO, Long> {
    UserDO findByUsername(String username);
}
