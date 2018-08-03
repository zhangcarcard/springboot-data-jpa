package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.common.jpa.support.Like;
import cn.tpson.kulu.dispatcher.biz.dto.RandomBackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.WeightBackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.query.RandomBackendQUERY;
import cn.tpson.kulu.dispatcher.biz.dto.query.WeightBackendQUERY;
import cn.tpson.kulu.dispatcher.biz.service.GroupService;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
import cn.tpson.kulu.dispatcher.biz.service.WeightBackendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/lb")
public class WeightBackendController {
    @Autowired
    WeightBackendService weightBackendService;
    @Autowired
    GroupService groupService;
    @Autowired
    ProtocalService protocalService;

    @RequestMapping("/weight.html")
    public String html(Model model) {
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("protocals", protocalService.findAll());
        return "loadbalance/weight/weight";
    }

    @ResponseBody
    @RequestMapping(value = "/weight.do", method = RequestMethod.GET)
    public TableVO keys(Integer offset, Integer limit, String search) {
        WeightBackendQUERY query = new WeightBackendQUERY(offset / limit, limit);
        Like like = null;
        if (StringUtils.isNotBlank(search)) {
            like = Like.by(
                    Like.Pair.by("weight", "%" + search + "%"),
                    Like.Pair.by("ip", "%" + search + "%"),
                    Like.Pair.by("port", "%" + search + "%"),
                    Like.Pair.by("ownerName", "%" + search + "%"),
                    Like.Pair.by("groupName", "%" + search + "%")
            );
        }
        Page<WeightBackendDTO> page = weightBackendService.pageByExample(query, like, null);
        return TableVO.successResult(page.getTotalElements(), page.getContent());
    }

    @ResponseBody
    @RequestMapping(value = "/weight.do", method = RequestMethod.POST)
    public ResultVO keys(WeightBackendDTO backend) {
        return weightBackendService.save(backend) > 0
                ? ResultVO.successResult()
                : ResultVO.failResult("添加失败.");
    }

    @ResponseBody
    @RequestMapping(value = "/weight.do", method = RequestMethod.DELETE)
    public ResultVO keys(@RequestBody ArrayList<WeightBackendDTO> backends) {
        weightBackendService.deleteAllEntity(backends);
        return ResultVO.successResult();
    }
}
