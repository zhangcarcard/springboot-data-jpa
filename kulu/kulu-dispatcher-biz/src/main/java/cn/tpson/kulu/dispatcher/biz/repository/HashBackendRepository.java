package cn.tpson.kulu.dispatcher.biz.repository;

import cn.tpson.kulu.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.dispatcher.biz.domain.GroupDO;
import cn.tpson.kulu.dispatcher.biz.domain.HashBackendDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface HashBackendRepository extends BaseRepository<HashBackendDO, Long> {
    @Query("select d from HashBackend d where concat(coalesce(d.key, ''), coalesce(d.protocalName, ''), coalesce(d.groupName, ''), coalesce(d.protocal.eqpName, '')) like :keyword")
    Page<HashBackendDO> findByKeywordContaining(Pageable pageable, @Param("keyword")String keyword);

    List<HashBackendDO> findByKey(String key);
}
