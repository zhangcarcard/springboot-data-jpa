package cn.tpson.kulu.dispatcher.biz.domain;

import cn.tpson.kulu.common.db.domain.BaseDO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Zhangka in 2018/08/01
 */
@Entity(name = "User")
@Table(name = "t_user")
@SequenceGenerator(name = "generator", sequenceName = "t_user_seq", allocationSize = 1)
@SQLDelete(sql = "UPDATE t_user SET is_deleted = true, gmt_modified = now() WHERE id = ? AND version = ?")
@Where(clause = "is_deleted = false")
@DynamicUpdate
@DynamicInsert
public class UserDO extends BaseDO {
    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 1000)
    private String password;

    public UserDO() {}
    public UserDO(String username, String password) {
        this.username = username;
        this.password = password;
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                "} " + super.toString();
    }
}
