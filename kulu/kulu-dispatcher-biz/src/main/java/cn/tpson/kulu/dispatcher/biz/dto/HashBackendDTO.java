package cn.tpson.kulu.dispatcher.biz.dto;

import cn.tpson.kulu.dispatcher.biz.domain.HashBackendDO;

/**
 * Created by Zhangka in 2018/08/01
 */
public class HashBackendDTO extends HashBackendDO {
    private String protocalName;
    private String groupName;

    public String getProtocalName() {
        return protocalName;
    }

    public void setProtocalName(String protocalName) {
        this.protocalName = protocalName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
