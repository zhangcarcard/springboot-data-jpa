package cn.tpson.kulu.dispatcher.biz.repository;

import cn.tpson.kulu.common.jpa.repository.BaseRepository;
import cn.tpson.kulu.dispatcher.biz.domain.GroupDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface GroupRepository extends BaseRepository<GroupDO, Long> {
    @Query("select d from Group d where concat(coalesce(d.name, ''), coalesce(d.comment, '')) like :keyword")
    Page<GroupDO> findByKeywordContaining(Pageable pageable, @Param("keyword")String keyword);

    GroupDO findByName(String name);
}
