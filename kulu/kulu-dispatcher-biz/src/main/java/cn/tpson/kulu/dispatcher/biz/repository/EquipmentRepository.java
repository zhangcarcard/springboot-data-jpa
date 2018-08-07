package cn.tpson.kulu.dispatcher.biz.repository;

import cn.tpson.kulu.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.dispatcher.biz.domain.EquipmentDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface EquipmentRepository extends BaseRepository<EquipmentDO, Long> {
    @Query("select d from Equipment d where concat(coalesce(d.name, ''), coalesce(d.port, 0), coalesce(d.comment, '')) like :keyword")
    Page<EquipmentDO> findByKeywordContaining(Pageable pageable, @Param("keyword")String keyword);

    EquipmentDO findByName(String name);

    EquipmentDO findByPort(Integer port);
}
