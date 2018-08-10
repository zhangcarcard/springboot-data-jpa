package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.dispatcher.biz.dto.HashLoadBalanceDTO;
import cn.tpson.kulu.dispatcher.biz.service.HashLoadBalanceService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Zhangka in 2018/08/09
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    public static final String DISPATCHER_CONNECTION_HASH = "DISPATCHER_CONNECTION_HASH";

    @Autowired
    private HashLoadBalanceService hashBackendService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @return
     */
    @GetMapping("/connections.do")
    public ResultVO connections() {
        HashOperations<String, String, Integer> hashOperations = redisTemplate.opsForHash();
        Map<String, Integer> connectionMap = hashOperations.entries(DISPATCHER_CONNECTION_HASH);
        //设置连接状态
        List<HashLoadBalanceDTO> list = hashBackendService.findAll();
        list.forEach(e -> {
            Integer active = connectionMap.get(e.getKey());
            e.setActive((active != null && active == 1) ? true :false);
        });

        //分组
        Map<String, List<HashLoadBalanceDTO>> groupMap = list.stream().collect(Collectors.groupingBy(e -> e.getGroup().getName()));

        JSONArray groups = new JSONArray();
        groupMap.forEach((k, v) -> {
            //key分组
            Map<String, List<HashLoadBalanceDTO>> eqp = v.stream().collect(Collectors.groupingBy(HashLoadBalanceDTO::getEqpName));

            JSONArray eqps = new JSONArray();
            eqp.forEach((ek, ev) -> {
                JSONArray keys = new JSONArray();
//                ev.stream().map(HashBackendDO::getKey).collect(Collectors.toList());
                ev.stream().forEach(dto -> {
                    JSONObject key = new JSONObject();
                    key.put("text", dto.getKey() + "(" + (dto.isActive() ? "在线)" : "离线)"));
                    keys.add(key);
                });

                JSONObject e = new JSONObject();
                Integer evTotal = ev.size();
                long evActive = ev.stream().filter(HashLoadBalanceDTO::isActive).count();
                e.put("text", ek + "(" + evActive + "/" + evTotal + ")");
                e.put("nodes", keys);
                eqps.add(e);
            });

            JSONObject group = new JSONObject();
            Integer vTotal = v.size();
            long vActive = v.stream().filter(HashLoadBalanceDTO::isActive).count();
            group.put("text", k + "(" + vActive + "/" + vTotal + ")");
            group.put("nodes", eqps);
            groups.add(group);
        });

        return ResultVO.successResult(groups);
    }
}
