package cn.tpson.kulu.dispatcher.biz.dto.query;

import cn.tpson.kulu.dispatcher.biz.dto.WeightBackendDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public class WeightBackendQUERY extends WeightBackendDTO {
    public WeightBackendQUERY() {}
    public WeightBackendQUERY(Integer pageNumber, Integer pageSize) {
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }
}
