package cn.tpson.kulu.dispatcher.biz.repository;

import cn.tpson.kulu.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.dispatcher.biz.domain.GroupDO;
import cn.tpson.kulu.dispatcher.biz.domain.HashLoadBalanceDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface HashLoadBalanceRepository extends BaseRepository<HashLoadBalanceDO, Long> {
    @Query("select d from HashLoadBalance d where concat(coalesce(d.name, ''), coalesce(d.key, ''), coalesce(d.eqpName, ''), coalesce(d.group.name, '')) like :keyword")
    Page<HashLoadBalanceDO> findByKeywordContaining(Pageable pageable, @Param("keyword")String keyword);

    List<HashLoadBalanceDO> findByKey(String key);

    List<HashLoadBalanceDO> findByGroup(GroupDO group);

    HashLoadBalanceDO findByName(String name);

    int countByNameAndGroup(String name, GroupDO group);
}
