package cn.tpson.kulu.dispatcher.biz.service;

import cn.tpson.kulu.common.jpa.support.Like;
import cn.tpson.kulu.common.service.local.BaseService;
import cn.tpson.kulu.dispatcher.biz.domain.GroupDO;
import cn.tpson.kulu.dispatcher.biz.domain.HashBackendDO;
import cn.tpson.kulu.dispatcher.biz.dto.HashBackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.query.HashBackendQUERY;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface HashBackendService extends BaseService<HashBackendDTO> {
    Page<HashBackendDTO> findByNameContaining(HashBackendQUERY query, Sort sort, String name);
}
