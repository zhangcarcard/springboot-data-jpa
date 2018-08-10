package cn.tpson.kulu.dispatcher.biz.dto.query;

import cn.tpson.kulu.dispatcher.biz.dto.BackendDTO;

/**
 * Created by Zhangka in 2018/08/10
 */
public class BackendQUERY extends BackendDTO {
    public BackendQUERY() {}
    public BackendQUERY(Integer pageNumber, Integer pageSize) {
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }
}
