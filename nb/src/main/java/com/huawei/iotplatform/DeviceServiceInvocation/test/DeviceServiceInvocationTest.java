package com.huawei.iotplatform.DeviceServiceInvocation.test;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.dto.CommandDTO;
import com.huawei.iotplatform.client.dto.CommandNA2CloudHeader;
import com.huawei.iotplatform.client.dto.DeviceServiceInvocationInDTO;
import com.huawei.iotplatform.client.dto.DeviceServiceInvocationOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.DeviceServiceInvocation;
import com.huawei.iotplatform.utils.JsonUtil;

public class DeviceServiceInvocationTest
{
    public static void main(String args[]) throws Exception
    {
        NorthApiClient nac = new NorthApiClient();
        
        ClientInfo ci = new ClientInfo();
        
        String appId = "b17f23bd-0904-4507-800e-855e01481c7c";
        String secret = "5905f82621698b037a3c";
        
        ci.setAppId(appId);
        ci.setPlatformIp("218.4.33.72");
        ci.setPlatformPort("8743");
        ci.setSecret(secret);
        
        nac.setClientInfo(ci);
        nac.initSSLConfig();
        
        Authentication aaa = new Authentication(nac);
        
        AuthOutDTO aod = null;
        
        aod = aaa.getAuthToken();
        
        String accessToken = aod.getAccessToken();
        String gate_deviceId = "386f71c7-27e5-46ca-9cbb-6f7960ab8f4c";
        String serviceId = "Battery";
        // 4.5.1 设备服务调用
        DeviceServiceInvocation dsi = new DeviceServiceInvocation(nac);
        
        DeviceServiceInvocationInDTO dsiid = new DeviceServiceInvocationInDTO();
        dsiid.setDeviceId(gate_deviceId);
        dsiid.setServiceId(serviceId);
        
        CommandDTO message = new CommandDTO();
        
        CommandNA2CloudHeader header = new CommandNA2CloudHeader();
        
        header.setMode("ACK");
        header.setMethod("SET_REPORT_PERIOD_BATTERY");
        header.setToType("GATEWAY");
        header.setCallbackURL("http://172.31.126.82:8443/RESTfulWS/rest/UserInfoService/subscriber1");
        
        Map<String, Integer> commandNA2CloudBody = new HashMap<>();
        commandNA2CloudBody.put("Period", 100);
        ObjectNode body = JsonUtil.convertObject2ObjectNode(commandNA2CloudBody);
        
        message.setBody(body);
        message.setHeader(header);
        
        dsiid.setMessage(message);
        
        DeviceServiceInvocationOutDTO dsiod = dsi.invocateDeviceService(dsiid, appId, accessToken);
        System.out.println(dsiod.toString());
    }
}
