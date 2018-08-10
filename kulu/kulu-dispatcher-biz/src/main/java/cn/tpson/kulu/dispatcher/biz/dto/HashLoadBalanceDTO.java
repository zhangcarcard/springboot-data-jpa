package cn.tpson.kulu.dispatcher.biz.dto;

import cn.tpson.kulu.dispatcher.biz.domain.HashLoadBalanceDO;

/**
 * Created by Zhangka in 2018/08/01
 */
public class HashLoadBalanceDTO extends HashLoadBalanceDO {
    private Long groupId;

    private boolean active;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
