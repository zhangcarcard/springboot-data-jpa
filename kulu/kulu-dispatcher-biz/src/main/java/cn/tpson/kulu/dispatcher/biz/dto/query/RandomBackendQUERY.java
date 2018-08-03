package cn.tpson.kulu.dispatcher.biz.dto.query;

import cn.tpson.kulu.dispatcher.biz.dto.RandomBackendDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public class RandomBackendQUERY extends RandomBackendDTO {
    public RandomBackendQUERY() {}
    public RandomBackendQUERY(Integer pageNumber, Integer pageSize) {
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }
}
