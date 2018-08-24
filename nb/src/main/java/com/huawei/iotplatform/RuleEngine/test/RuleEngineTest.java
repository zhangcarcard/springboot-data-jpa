package com.huawei.iotplatform.RuleEngine.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.dto.RuleCreateOrUpdateOutDTO;
import com.huawei.iotplatform.client.dto.RuleDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.RuleEngine;
import com.huawei.iotplatform.utils.JsonUtil;

public class RuleEngineTest
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
        
        String author = "IoT_RemoteLab170";
        String rulename = "AAA12313331111144";
        RuleEngine re = new RuleEngine(nac);
        
        // 4.7.1 创建规则
        
        List<ObjectNode> actions = new ArrayList<>();
        List<ObjectNode> conditions = new ArrayList<>();
        Map<String, Object> condition = new HashMap<>();
        
        condition.put("type", "DEVICE_DATA");
        
        Map<String, Object> deviceInfo = new HashMap<>();
        deviceInfo.put("deviceId", deviceId);
        deviceInfo.put("path", "Brightness/brightness");
        condition.put("deviceInfo", deviceInfo);
        condition.put("operator", ">");
        condition.put("value", "90");
        
        Map<String, Object> strategy = new HashMap<>();
        strategy.put("trigger", "pulse");
        strategy.put("eValidTime", 10);
        condition.put("strategy", strategy);
        
        ObjectNode nodeCondition = JsonUtil.convertObject2ObjectNode(condition);
        conditions.add(nodeCondition);
        
        Map<String, Object> action = new HashMap<>();
        
        /*
         * action.put("type", "EMAIL"); action.put("email", "test@huawei.com"); action.put("content", "test"); action.put("subject", "[TEST]test123,test123,test123");
         * action.put("title", "test123"); action.put("accountId", author);
         */
        
        action.put("type", "DEVICE_ALARM");
        action.put("name", "alarm001");
        action.put("status", "fault");
        action.put("severity", "minor");
        action.put("description", "test alarm123");
        
        /*
         * action.put("type", "DEVICE_CMD"); Map<String, Object> CMD = new HashMap<>(); CMD.put("messageType", "SET_DEVICE_LEVEL"); CMD.put("serviceId", "Brightness"); ObjectNode
         * messageBody = JsonUtil.convertObject2ObjectNode("{\"value\":\"100\"}"); CMD.put("messageBody", messageBody); action.put("cmd", CMD);
         */
        
        ObjectNode nodeAction = JsonUtil.convertObject2ObjectNode(action);
        actions.add(nodeAction);
        
        RuleDTO rcid = new RuleDTO();
        rcid.setName(rulename);
        rcid.setAppKey(appId);
        rcid.setAuthor(author);
        rcid.setStatus("active");
        rcid.setLogic("and");
        rcid.setConditions(conditions);
        rcid.setActions(actions);
        
        RuleCreateOrUpdateOutDTO rcod = re.createRule(rcid, accessToken);
        System.out.println(rcod.toString());
        
        // 4.7.2 更新规则
        
        /*
         * String ruleid = "5a0055e9e4b0765b0d1a2fcf"; List<ObjectNode> conditions_new = new ArrayList<>(); Map<String, Object> condition_new = new HashMap<>();
         * condition_new.put("type", "DEVICE_DATA"); Map<String, Object> deviceInfo_new = new HashMap<>(); deviceInfo_new.put("deviceId", deviceId); deviceInfo_new.put("path",
         * "Brightness/brightness"); condition_new.put("deviceInfo", deviceInfo_new); condition_new.put("operator", "="); condition_new.put("value", "30"); Map<String, Object>
         * strategy1 = new HashMap<>(); strategy1.put("trigger", "pulse"); strategy1.put("eValidTime", 10); condition_new.put("strategy", strategy1); ObjectNode nodeCondition_new =
         * JsonUtil.convertObject2ObjectNode(condition_new); conditions_new.add(nodeCondition_new); rcid.setConditions(conditions_new); rcid.setRuleId(ruleid);
         * RuleCreateOrUpdateOutDTO rcuod = re.updateRule(rcid, accessToken); System.out.println(rcuod.toString());
         */
        
        // 4.7.3 修改规则状态
        
        /*
         * RuleStatusChangeInDTO rscid = new RuleStatusChangeInDTO(); String ruleId = "5a0055e9e4b0765b0d1a2fcf"; String status = "inactive"; rscid.setRuleId(ruleId);
         * rscid.setStatus(status); re.changeRuleStatus(rscid, accessToken);
         */
        
        // 4.7.4 删除规则
        // String ruleid = "5a000b6de4b0765b0d1a1bc8";
        // re.deleteRule(ruleid, accessToken);
        
        // 4.7.5 查询规则
        
        /*
         * QueryRulesInDTO qrid = new QueryRulesInDTO(); qrid.setAuthor(author); qrid.setName(rulename); List<RuleDTO> ruledtos = re.queryRules(qrid, accessToken);
         * System.out.println(ruledtos.toString());
         */
        
        // 4.7.5 批量修改规则状态
        
        /*
         * String ruleid1 = "5a0055efe4b0765b0d1a2fd2"; String ruleid2 = "5a0055e9e4b0765b0d1a2fcf"; RuleStatusBatchChangeInDTO rsbcid = new RuleStatusBatchChangeInDTO();
         * List<RuleStatusChangeInDTO> requests = new ArrayList<>(); RuleStatusChangeInDTO rscid1 = new RuleStatusChangeInDTO(); rscid1.setRuleId(ruleid1);
         * rscid1.setStatus("active"); RuleStatusChangeInDTO rscid2 = new RuleStatusChangeInDTO(); rscid2.setRuleId(ruleid2); rscid2.setStatus("active"); requests.add(rscid1);
         * requests.add(rscid2); rsbcid.setRequests(requests); RuleStatusBatchChangeOutDTO rsbcod = re.batchChangeRuleStatus(rsbcid, accessToken);
         * System.out.println(rsbcod.toString());
         */
        
        aaa.logoutAuthToken(accessToken);
    }
}
