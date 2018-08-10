package cn.tpson.kulu.dispatcher.biz.dto.query;

import cn.tpson.kulu.dispatcher.biz.dto.HashLoadBalanceDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public class HashLoadBalanceQUERY extends HashLoadBalanceDTO {
    public HashLoadBalanceQUERY() {
        super();
    }
    public HashLoadBalanceQUERY(Integer pageNumber, Integer pageSize) {
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }
}
