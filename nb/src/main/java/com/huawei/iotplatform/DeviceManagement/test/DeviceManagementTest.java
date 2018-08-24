package com.huawei.iotplatform.DeviceManagement.test;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.dto.ModifyDeviceInfoInDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceStatusOutDTO;
import com.huawei.iotplatform.client.dto.RegDirectDeviceInDTO;
import com.huawei.iotplatform.client.dto.RegDirectDeviceOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;

public class DeviceManagementTest
{
    public static void main(String args[]) throws Exception
    {
        NorthApiClient nac = new NorthApiClient();
        
        ClientInfo ci = new ClientInfo();
        
        String appId = "ax2MtpcLWicDfuqPTquJIEXHXJEa";
        String secret = "u3rJWxzRWsivf830MKGlag0s35oa";
        
        ci.setAppId(appId);
        ci.setPlatformIp("100.115.140.0");
        ci.setPlatformPort("8743");
        ci.setSecret(secret);
        
        nac.setClientInfo(ci);
        nac.initSSLConfig();
        
        DeviceManagement dm = new DeviceManagement(nac);
        
        // auth
        Authentication aaa = new Authentication(nac);
        
        // 4.1.1 鑾峰彇閴存潈Token
        AuthOutDTO aod = null;
        
        aod = aaa.getAuthToken();
        
        String accessToken = aod.getAccessToken();
        
        // 4.2.1 娉ㄥ唽鐩磋繛璁惧
        
        RegDirectDeviceInDTO rddid = new RegDirectDeviceInDTO();
        String nodeid = "3703030485442";
        String verifyCode = nodeid;
        rddid.setNodeId(nodeid);
        rddid.setVerifyCode(verifyCode);
        rddid.setTimeout(0);
        RegDirectDeviceOutDTO rddod = null;
        rddod = dm.regDirectDevice(rddid, appId, accessToken);
        System.out.println(rddod.toString());
        String deviceId = rddod.getDeviceId();
        
        // String deviceId = "c057a931-cff8-4a2b-bd76-141ccde8d16f";
        
        // 4.2.2 鏌ヨ璁惧婵�娲荤姸鎬�
        QueryDeviceStatusOutDTO qdsod = dm.queryDeviceStatus(deviceId, appId, accessToken);
        System.out.println(qdsod.toString());
        
        // 4.2.4 淇敼璁惧淇℃伅
        // String deviceId = "18a99eaa-f420-4ee1-b76c-b0f7550504a7";
        
        ModifyDeviceInfoInDTO mdiid = new ModifyDeviceInfoInDTO();
        mdiid.setName("newDeviceName");
        mdiid.setDeviceId(deviceId);
        mdiid.setManufacturerId("CompanyId");
        mdiid.setManufacturerName("CompanyName");
        mdiid.setDeviceType("WaterMeter");
        mdiid.setModel("SDKDEMO");
        mdiid.setProtocolType("CoAP");
        dm.modifyDeviceInfo(mdiid, appId, accessToken);
        
        // 4.2.5 鍒锋柊璁惧瀵嗛挜
        
        /*
         * RefreshVerifyCodeInDTO rdsid = new RefreshVerifyCodeInDTO(); RefreshVerifyCodeDTO request = new RefreshVerifyCodeDTO(); rdsid.setDeviceId(deviceId); String new_nodeid =
         * "TEST$_00387209XX"; request.setNodeId(new_nodeid); request.setVerifyCode(new_nodeid); rdsid.setRequest(request); RefreshVerifyCodeOutDTO rdsod =
         * dm.refreshDeviceSecret(rdsid, appId, accessToken); System.out.println(rdsod.toString());
         */
        
        // 4.2.6 璁剧疆鍔犲瘑
        
        /*
         * EncryptSetInDTO esid = new EncryptSetInDTO(); esid.setDeviceId(deviceId); String serviceType = "carEncrypt"; esid.setServiceType(serviceType); ObjectNode data =
         * JsonUtil.convertObject2ObjectNode("{\"period\":\"12\"}"); DmServicesRequestDTO request1 = new DmServicesRequestDTO(); request1.setData(data); esid.setRequest(request1);
         * dm.setEncrypt(esid, accessToken);
         */
        
        // 4.2.3 鍒犻櫎鐩磋繛璁惧
        // dm.deleteDirectDevice(deviceId, appId, accessToken);
        
        aaa.logoutAuthToken(accessToken);
    }
}
