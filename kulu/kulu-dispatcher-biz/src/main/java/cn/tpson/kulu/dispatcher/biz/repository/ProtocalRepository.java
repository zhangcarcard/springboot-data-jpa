package cn.tpson.kulu.dispatcher.biz.repository;

import cn.tpson.kulu.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.dispatcher.biz.domain.ProtocalDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface ProtocalRepository extends BaseRepository<ProtocalDO, Long> {
    @Query("select d from Protocal d where concat(coalesce(d.name, ''), coalesce(d.startFlag, ''), coalesce(d.endFlag, ''), coalesce(d.split, ''), coalesce(d.offset, 0), coalesce(d.offsetType, ''), coalesce(d.count, 0), coalesce(d.port, 0)) like :keyword")
    Page<ProtocalDO> findByKeywordContaining(Pageable pageable, @Param("keyword")String keyword);

    ProtocalDO findByName(String name);

    ProtocalDO findByPort(Integer port);
}
