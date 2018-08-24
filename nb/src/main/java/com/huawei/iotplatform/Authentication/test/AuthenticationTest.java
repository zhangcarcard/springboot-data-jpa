package com.huawei.iotplatform.Authentication.test;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.AuthRefreshInDTO;
import com.huawei.iotplatform.client.dto.AuthRefreshOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.invokeapi.Authentication;

public class AuthenticationTest
{
    public static void main(String args[]) throws Exception
    {
        NorthApiClient nac = new NorthApiClient();
        
        ClientInfo ci = new ClientInfo();
        
        String appId = "5QtJ0E4CQU7snsCBTePgfD4pdLka";
        String secret = "Xkyln4R1Bj2R1cNrdxfB67auq8Ea";
        
        ci.setAppId(appId);
        ci.setPlatformIp("180.101.147.89");
        ci.setPlatformPort("8743");
        ci.setSecret(secret);
        
        nac.setClientInfo(ci);
        nac.initSSLConfig();
        
        // auth
        Authentication aaa = new Authentication(nac);
        
        // 4.1.1 鑾峰彇閴存潈Token
        AuthOutDTO aod = null;
        
        aod = aaa.getAuthToken();
        
        System.out.println(aod.toString());
        
        // 4.1.2 鍒锋柊閴存潈Token
        AuthRefreshOutDTO arod = null;
        AuthRefreshInDTO arid = new AuthRefreshInDTO();
        
        arid.setAppId(appId);
        arid.setSecret(secret);
        
        String refreshToken = aod.getRefreshToken();
        arid.setRefreshToken(refreshToken);
        
        arod = aaa.refreshAuthToken(arid);
        
        System.out.println(arod.toString());
        
        String accessToken = arod.getAccessToken();
        
        // 4.1.3 娉ㄩ攢閴存潈Token,鏃犺繑鍥炲��
        aaa.logoutAuthToken(accessToken);
        
    }
}
