package cn.tpson.kulu.gas.eqp.biz.dao;

import cn.tpson.kulu.gas.eqp.biz.domain.EqpLocationHistoryDO;
import com.alibaba.fastjson.JSON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Zhangka in 2018/07/18
 */
public interface EqpLocationHistoryDao extends JpaRepository<EqpLocationHistoryDO, Long> {
    @Query(value = "select * from t_eqp_location_history where location @> cast(:name as jsonb)" , nativeQuery = true)
    List<EqpLocationHistoryDO> findByName(@Param("name") String name);

    @Query(value = "update t_eqp_location_history set location = location || :location where id = 1", nativeQuery = true)
    void addLocation(JSON location);
}
