package cn.tpson.kulu.gas.eqp.biz.dao;

import cn.tpson.kulu.gas.eqp.biz.domain.EqpDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Zhangka in 2018/07/18
 */
public interface EqpDao extends JpaRepository<EqpDO, Long> {
}
