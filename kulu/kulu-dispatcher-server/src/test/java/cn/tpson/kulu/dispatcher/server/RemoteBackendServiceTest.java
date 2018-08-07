package cn.tpson.kulu.dispatcher.server;

import cn.tpson.kulu.dispatcher.server.service.RemoteBackendService;
import cn.tpson.kulu.dispatcher.server.vo.BackendVO;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Zhangka in 2018/08/07
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RemoteBackendServiceTest {
    @Autowired
    private RemoteBackendService remoteBackendService;

    @Test
    public void test() {
        String key = remoteBackendService.getKey(8888, "7E01020010000019999999008132303138303731383030303030303032157E");
        System.out.println(key);
        BackendVO backendVO = remoteBackendService.getBackend(8888, "000019999999");
        System.out.println(JSON.toJSONString(backendVO));
    }
}
