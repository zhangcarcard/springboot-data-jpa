package cn.tpson.kulu.dispatcher.biz.repository;

import cn.tpson.kulu.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.dispatcher.biz.domain.GroupDO;
import cn.tpson.kulu.dispatcher.biz.domain.HashBackendDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface HashBackendRepository extends BaseRepository<HashBackendDO, Long> {
    @Query("select h from HashBackend h where concat(h.key, h.protocalName, h.groupName, h.protocal.eqpName) like :name")
    Page<HashBackendDO> findByNameContaining(Pageable pageable, @Param("name")String name);
}
