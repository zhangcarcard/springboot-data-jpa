package com.huawei.iotplatform.SignalDelivery.test;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.dto.QueryDeviceCommandInDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceCommandOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.SignalDelivery;

public class SignalDeliveryTest
{
    public static void main(String args[]) throws Exception
    {
        NorthApiClient nac = new NorthApiClient();
        
        ClientInfo ci = new ClientInfo();
        
        String appId = "f3275607-a6d5-450a-a251-0b1f4ce33b4f";
        String secret = "aaf37c6b51670b3df0d2";
        
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
        String deviceId = "93e68b58-e00e-4376-aff4-a28808590917";
        
        SignalDelivery sd = new SignalDelivery();
        sd.setNorthApiClient(nac);
        
        // 4.4.1 创建设备命令V4
        
        /*
         * PostDeviceCommandInDTO pdcid = new PostDeviceCommandInDTO(); pdcid.setDeviceId(deviceId); AsynCommandDTO acdo = new AsynCommandDTO(); String serviceId = "Brightness";
         * String method = "SET_DEVICE_LEVEL"; ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"value\":\"100\"}"); acdo.setServiceId(serviceId); acdo.setMethod(method);
         * acdo.setParas(paras); pdcid.setCommand(acdo); PostDeviceCommandOutDTO pdcod = sd.postDeviceCommand(pdcid, appId, accessToken); System.out.println(pdcod.toString());
         */
        
        // 4.4.2 查询设备命令V4
        
        QueryDeviceCommandInDTO qdcid = new QueryDeviceCommandInDTO();
        qdcid.setDeviceId(deviceId);
        QueryDeviceCommandOutDTO qdcod = sd.queryDeviceCommand(qdcid, appId, accessToken);
        System.out.println(qdcod.toString());
        
        // 4.4.3 修改设备命令V4
        /*
         * UpdateDeviceCmdInDTO udcid = new UpdateDeviceCmdInDTO(); String deviceCommandId = "bfeb4ba5c2644aae87b3188beb13539f"; udcid.setDeviceCommandId(deviceCommandId);
         * UpdateDeviceCmdReq udcr = new UpdateDeviceCmdReq(); udcr.setStatus("CANCELED"); udcid.setUpdateDeviceCommandReq(udcr); UpdateDeviceCmdOutDTO udcod =
         * sd.updateDeviceCommand(udcid, appId, accessToken); System.out.println(udcod.toString());
         */
        
        // 4.4.4 创建设备命令撤销任务V4
        
        /*
         * DeviceCmdCancelTaskInDTO dcctod = new DeviceCmdCancelTaskInDTO(); dcctod.setDeviceId(deviceId); DeviceCmdCancelTaskOutDTO dcctoud =
         * sd.createDeviceCmdCancelTaskV4(dcctod, appId, accessToken); System.out.println(dcctoud.toString());
         */
        
        // 4.4.5 查询设备命令撤销任务V4
        
        /*
         * QueryDeviceCmdCancelTaskInDTO qdcctid = new QueryDeviceCmdCancelTaskInDTO(); qdcctid.setDeviceId(deviceId); QueryDeviceCmdCancelTaskOutDTO qdcctod =
         * sd.queryDeviceCmdCancelTask(qdcctid, appId, accessToken); System.out.println(qdcctod.toString());
         */
        
        // 4.1.3 注销鉴权Token,无返回值
        aaa.logoutAuthToken(accessToken);
    }
}
