package cn.tpson.kulu.dispatcher.biz.domain;

import cn.tpson.kulu.common.db.domain.BaseDO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by Zhangka in 2018/08/01
 */
@Entity(name = "Group")
@Table(name = "t_group", uniqueConstraints = @UniqueConstraint(name = "uk_t_group_name", columnNames = "name"))
@SequenceGenerator(name = "generator", sequenceName = "t_group_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE t_group SET is_deleted = true, gmt_modified = now() WHERE id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@DynamicInsert
@DynamicUpdate
public class GroupDO extends BaseDO {
    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 1000)
    private String comment;

    public GroupDO() {}
    public GroupDO(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "GroupDO{" +
                "name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                "} " + super.toString();
    }
}
