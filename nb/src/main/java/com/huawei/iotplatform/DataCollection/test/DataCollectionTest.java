package com.huawei.iotplatform.DataCollection.test;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.dto.QueryDataHistoryInDTO;
import com.huawei.iotplatform.client.dto.QueryDataHistoryOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceCapabilitiesInDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceCapabilitiesOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDataOutDTO;
import com.huawei.iotplatform.client.dto.QueryDevicesInDTO;
import com.huawei.iotplatform.client.dto.QueryDevicesOutDTO;
import com.huawei.iotplatform.client.dto.SubscribeInDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.DataCollection;

public class DataCollectionTest {
    public static void main(String args[]) throws Exception {
        NorthApiClient nac = new NorthApiClient();

        ClientInfo ci = new ClientInfo();

        String appId = "LMJyllGYVFJc35yZU4RVKK5HZjca";
        String secret = "BOdThfoERyGFOmKx3bIgo4uy2tga";

        ci.setAppId(appId);
        ci.setPlatformIp("180.101.147.89");
        ci.setPlatformPort("8743");
        ci.setSecret(secret);

        nac.setClientInfo(ci);
        nac.initSSLConfig();

        DataCollection dc = new DataCollection(nac);

        // auth
        Authentication aaa = new Authentication(nac);

        // 4.1.1 获取鉴权Token
        AuthOutDTO aod = null;

        aod = aaa.getAuthToken();
        String accessToken = aod.getAccessToken();
//        String deviceId = "f8801e7c-65a7-4da7-90e4-44b2555a0039";

        // 4.3.1 按条件批量查询设备信息列表
        QueryDevicesInDTO qdido = new QueryDevicesInDTO();
//        String gatewayId = deviceId;
//        qdido.setGatewayId(gatewayId);
        qdido.setPageNo(0);
        QueryDevicesOutDTO qdodo = dc.queryDevices(qdido, appId, accessToken);
        System.out.println(qdodo.toString());

        // 4.3.2 查询单个设备信息
        /*QueryDeviceDataOutDTO qddodto = dc.queryDeviceData(deviceId, appId, accessToken);
        System.out.println(qddodto.toString());

        // 4.3.3 查询设备历史数据
        QueryDataHistoryInDTO qdhid = new QueryDataHistoryInDTO();
        qdhid.setDeviceId(deviceId);
        qdhid.setGatewayId(gatewayId);
        qdhid.setPageNo(0);
        qdhid.setPageSize(20);
        QueryDataHistoryOutDTO qdhod = dc.queryDataHistory(qdhid, appId, accessToken);
        System.out.println(qdhod.toString());

        // 4.3.4 查询设备能力
        QueryDeviceCapabilitiesInDTO qdcid = new QueryDeviceCapabilitiesInDTO();
        qdcid.setDeviceId(deviceId);
        QueryDeviceCapabilitiesOutDTO qdcod = dc.queryDeviceCapabilities(qdcid, appId, accessToken);
        System.out.println(qdcod.toString());
*/
        // 4.3.5 订阅平台数据
        SubscribeInDTO sid = new SubscribeInDTO();
        String notifyType = "deviceAdded";
        String callbackurl = "http://172.31.126.82:8080/RESTfulWS/rest/UserInfoService/subscriber1";
        sid.setNotifyType(notifyType);
        sid.setCallbackurl(callbackurl);

        dc.subscribeNotify(sid, accessToken);

        // 4.1.3 注销鉴权Token,无返回值
        aaa.logoutAuthToken(accessToken);
    }
}
