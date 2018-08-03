package cn.tpson.kulu.dispatcher.biz.dto.query;

import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;

/**
 * Created by Zhangka in 2018/08/01
 */
public class GroupQUERY extends GroupDTO {
    public GroupQUERY() {
        super();
    }
    public GroupQUERY(Integer pageNumber, Integer pageSize) {
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }
}
