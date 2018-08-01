package cn.tpson.kulu.gas.user.biz.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Zhangka in 2018/07/27
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserConsOrgDetailServiceTest {

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
        /*Date now = DateUtils.now();
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
*/
    }
}
