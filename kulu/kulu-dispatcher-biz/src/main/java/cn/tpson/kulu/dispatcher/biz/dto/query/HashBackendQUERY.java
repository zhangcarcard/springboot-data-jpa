package cn.tpson.kulu.dispatcher.biz.dto.query;

import cn.tpson.kulu.dispatcher.biz.dto.HashBackendDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public class HashBackendQUERY extends HashBackendDTO {
    public HashBackendQUERY() {
        super();
    }
    public HashBackendQUERY(Integer pageNumber, Integer pageSize) {
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }
}
