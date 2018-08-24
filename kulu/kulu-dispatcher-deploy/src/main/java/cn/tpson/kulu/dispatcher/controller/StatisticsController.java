package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.dispatcher.biz.dto.HashLoadBalanceDTO;
import cn.tpson.kulu.dispatcher.biz.service.HashLoadBalanceService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Zhangka in 2018/08/09
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private HashLoadBalanceService hashBackendService;

    /**
     *
     * @return
     */
    @GetMapping("/connections.do")
    public ResultVO connections() {
        //设置连接状态
        List<HashLoadBalanceDTO> list = hashBackendService.findAll();

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
                ev.stream()
                        .filter(HashLoadBalanceDTO::getActive)
                        .sorted((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()))
                        .forEach(dto -> {
                            JSONObject key = new JSONObject();
                            key.put("text", dto.getName());
                            key.put("icon", "glyphicon glyphicon-ok-sign");
                            keys.add(key);
                        });

                int activeTotal = keys.size();
                ev.stream()
                        .filter(e -> !e.getActive())
                        .sorted((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()))
                        .forEach(dto -> {
                            JSONObject key = new JSONObject();
                            key.put("text", dto.getName());
                            key.put("icon", "glyphicon glyphicon-remove-sign");
                            keys.add(key);
                        });

                JSONObject e = new JSONObject();
                Integer evTotal = keys.size();
                e.put("text", ek + "(" + activeTotal + "/" + evTotal + ")");
                e.put("nodes", keys);
                eqps.add(e);
            });

            JSONObject group = new JSONObject();
            Integer vTotal = v.size();
            long vActive = v.stream().filter(HashLoadBalanceDTO::getActive).count();
            group.put("text", k + "(" + vActive + "/" + vTotal + ")");
            group.put("nodes", eqps);
            groups.add(group);
        });

        return ResultVO.successResult(groups);
    }
}
