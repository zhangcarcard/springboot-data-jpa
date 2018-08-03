package cn.tpson.kulu.dispatcher.biz.dto.query;

import cn.tpson.kulu.dispatcher.biz.dto.ProtocalDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public class ProtocalQUERY extends ProtocalDTO {
    public ProtocalQUERY() {}
    public ProtocalQUERY(Integer pageNumber, Integer pageSize) {
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }
}
