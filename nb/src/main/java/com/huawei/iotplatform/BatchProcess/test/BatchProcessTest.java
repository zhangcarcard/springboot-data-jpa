package com.huawei.iotplatform.BatchProcess.test;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.dto.QueryOneTaskOutDTO;
import com.huawei.iotplatform.client.dto.QueryTaskDetailsInDTO;
import com.huawei.iotplatform.client.dto.QueryTaskDetailsOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.BatchProcess;

public class BatchProcessTest
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
        String deviceId = "af1a818f-c1e4-4a5b-b7c0-8c37c2e7fb69";
        
        BatchProcess bp = new BatchProcess(nac);
        
        // 4.6.1 创建批量任务
        /*
         * BatchTaskCreateInDTO btcid = new BatchTaskCreateInDTO(); btcid.setAppId(appId); btcid.setTimeout(1000); btcid.setTaskName("TestTask"); btcid.setTaskType("DeviceCmd");
         * AsynCommandDTO command11 = new AsynCommandDTO(); command11.setServiceId("Brightness"); ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"value\":\"100\"}");
         * command11.setParas(paras); command11.setMethod("SET_DEVICE_LEVEL"); Map<String, Object> param11 = new HashMap<>(); param11.put("type", "DeviceType");
         * param11.put("deviceType", "WaterMeter"); param11.put("manufacturerId", "CompanyId"); param11.put("model", "SDKDEMO"); param11.put("command", command11);
         * btcid.setParam(JsonUtil.convertObject2ObjectNode(param11)); BatchTaskCreateOutDTO btcod = bp.createBatchTask(btcid, accessToken); System.out.println(btcod.toString());
         */
        
        // 4.6.2 查询单个任务信息
        String taskID = "59fbc700e4b0765b0d18891f";
        
        QueryOneTaskOutDTO qotod = bp.queryOneTask(taskID, appId, accessToken);
        
        System.out.println(qotod.toString());
        
        // 4.6.3 查询任务详情信息
        QueryTaskDetailsInDTO qtdido = new QueryTaskDetailsInDTO();
        qtdido.setTaskId(taskID);
        
        QueryTaskDetailsOutDTO qtdodo = bp.queryTaskDetails(qtdido, appId, accessToken);
        
        System.out.println(qtdodo.toString());
        
        aaa.logoutAuthToken(accessToken);
    }
}
