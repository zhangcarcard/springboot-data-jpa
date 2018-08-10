package cn.tpson.kulu.dispatcher.biz.repository;

import cn.tpson.kulu.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.dispatcher.biz.domain.BackendDO;
import cn.tpson.kulu.dispatcher.biz.domain.HashLoadBalanceDO;
import cn.tpson.kulu.dispatcher.biz.domain.ProtocalDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface BackendRepository extends BaseRepository<BackendDO, Long> {
    @Query("select d from Backend d where concat(coalesce(d.ip, ''), coalesce(d.port, 0), coalesce(d.protocalName, ''), coalesce(d.eqpName, '')) like :keyword")
    Page<BackendDO> findByKeywordContaining(Pageable pageable, @Param("keyword")String keyword);

    List<BackendDO> findByGroupId(Long groupId);
}
