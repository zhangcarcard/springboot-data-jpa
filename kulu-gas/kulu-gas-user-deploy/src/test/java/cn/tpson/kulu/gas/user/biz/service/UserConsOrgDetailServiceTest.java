package cn.tpson.kulu.gas.user.biz.service;

import cn.tpson.kulu.gas.common.jpa.support.ExampleRangeSpecification;
import cn.tpson.kulu.gas.common.jpa.support.Range;
import cn.tpson.kulu.gas.common.jpa.support.RangeSpecification;
import cn.tpson.kulu.gas.common.util.DateUtils;
import cn.tpson.kulu.gas.user.biz.domain.UserConsOrgDetailDO;
import com.alibaba.fastjson.JSON;
import org.apache.catalina.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by Zhangka in 2018/07/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserConsOrgDetailServiceTest {
    @Autowired
    private UserConsOrgDetailService userConsOrgDetailService;

    @Test
    public void test() {
        /*UserConsOrgDetailDO u = new UserConsOrgDetailDO();
        Date now = new Date();
        u.setConsOrgName("中国");
        u.setGmtCreate(now);
        u.setGmtModified(now);
        u.setDeleted(false);
        userConsOrgDetailService.saveAndFlush(u);
        System.out.println("==================================");*/
        Date now = DateUtils.now();
        Range<UserConsOrgDetailDO> r1 = new Range<>("gmtCreate", now, new Date());
        Range<UserConsOrgDetailDO> r2 = new Range<>("gmtModified", now, new Date());

        UserConsOrgDetailDO ex = new UserConsOrgDetailDO();
        ex.setConsOrgName("中国");
        Example<UserConsOrgDetailDO> example = Example.of(ex);
        ExampleRangeSpecification<UserConsOrgDetailDO> spec = ExampleRangeSpecification.of(example, r1, r2);

        Pageable p = PageRequest.of(1, 10, Sort.by(Sort.Order.desc("gmtModified"), Sort.Order.asc("id")));
        Page<UserConsOrgDetailDO> page = userConsOrgDetailService.findAll(spec, p);
        System.out.println(JSON.toJSONString(page));
        System.out.println("==================================");
    }
}
