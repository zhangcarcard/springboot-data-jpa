package cn.tpson.kulu.dispatcher.biz.service;

import cn.tpson.kulu.common.jpa.service.local.BaseService;
import cn.tpson.kulu.dispatcher.biz.domain.BackendDO;
import cn.tpson.kulu.dispatcher.biz.dto.BackendDTO;

import java.util.List;

/**
 * Created by Zhangka in 2018/08/01
 */
public interface BackendService extends BaseService<BackendDTO> {
    List<BackendDTO> findByGroupId(Long groupId);

    List<BackendDTO> findByEqpName(String eqpName);

    BackendDTO findByEqpNameAndGroupId(String eqpName, Long groupId);

    int countByEqpNameAndGroupId(String eqpName, Long groupId);

    List<String> findAllEqpName();

    List<String> findAllEqpNameByGroupId(Long groupId);
}
