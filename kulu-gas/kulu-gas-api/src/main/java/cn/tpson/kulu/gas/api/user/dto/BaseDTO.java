package cn.tpson.kulu.gas.api.user.dto;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Zhangka in 2018/07/18
 */
public class BaseDTO implements Serializable {
    private Long id;
    @Transient
    private Boolean deleted;
    private Date gmtCreate;
    private Date gmtModified;

    @Transient
    private Integer pageNumber;
    @Transient
    private Integer pageSize;

    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
