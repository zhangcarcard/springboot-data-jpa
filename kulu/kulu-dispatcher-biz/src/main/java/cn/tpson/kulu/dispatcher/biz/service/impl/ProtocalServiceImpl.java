package cn.tpson.kulu.dispatcher.biz.service.impl;

import cn.tpson.kulu.common.service.local.BaseServiceImpl;
import cn.tpson.kulu.common.util.BeanUtils;
import cn.tpson.kulu.dispatcher.biz.domain.ProtocalDO;
import cn.tpson.kulu.dispatcher.biz.dto.ProtocalDTO;
import cn.tpson.kulu.dispatcher.biz.repository.ProtocalRepository;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Zhangka in 2018/08/01
 */
@Service
@Transactional(readOnly = true)
public class ProtocalServiceImpl extends BaseServiceImpl<ProtocalDTO, ProtocalDO> implements ProtocalService {
    @Autowired
    private ProtocalRepository protocalRepository;

    @Override
    public ProtocalDTO findByName(String name) {
        return BeanUtils.newAndCopyProperties(ProtocalDTO.class, protocalRepository.findByName(name));
    }
}
