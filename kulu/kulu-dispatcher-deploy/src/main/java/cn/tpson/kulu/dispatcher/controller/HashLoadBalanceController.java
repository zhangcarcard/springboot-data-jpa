package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.constant.ErrorCodeEnum;
import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.common.util.CheckUtils;
import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;
import cn.tpson.kulu.dispatcher.biz.dto.HashLoadBalanceDTO;
import cn.tpson.kulu.dispatcher.biz.service.BackendService;
import cn.tpson.kulu.dispatcher.biz.service.GroupService;
import cn.tpson.kulu.dispatcher.biz.service.HashLoadBalanceService;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/lb")
public class HashLoadBalanceController {
    @Autowired
    HashLoadBalanceService hashLoadBalanceService;
    @Autowired
    GroupService groupService;
    @Autowired
    ProtocalService protocalService;
    @Autowired
    BackendService backendService;

    @RequestMapping("/hash.html")
    public String html(Model model) {
        List<GroupDTO> groups = groupService.findAll();
        model.addAttribute("groups", groups);

//        model.addAttribute("protocals", protocalService.findAll());
        if (!groups.isEmpty()) {
            List<String> eqpNames = backendService.findAllEqpNameByGroupId(groups.get(0).getId());
            model.addAttribute("eqpNames", eqpNames);
        }
        return "loadbalance/hash/hash";
    }

    @ResponseBody
    @RequestMapping(value = "/hash.do", method = RequestMethod.GET)
    public TableVO list(Integer offset, Integer limit, String search, String order, String sortName) {
        Sort sort = null;
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortName)) {
            sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), sortName);
        }
        Page<HashLoadBalanceDTO> page = hashLoadBalanceService.pageByKeywordContaining(offset / limit, limit, sort, search);

        return TableVO.successResult(page.getTotalElements(), page.getContent());
    }

    @ResponseBody
    @RequestMapping(value = "/hash.do", method = RequestMethod.POST)
    public ResultVO hash(HashLoadBalanceDTO hash) {
        Assert.hasLength(hash.getName(), ResultVO.failResult(ErrorCodeEnum.PARAM_ERROR).toString());
        Assert.notNull(hash.getGroupId(), ResultVO.failResult(ErrorCodeEnum.PARAM_ERROR).toString());

        GroupDTO group = groupService.findById(hash.getGroupId());
        int total = hashLoadBalanceService.countByNameAndGroup(hash.getName(), group);
        if (hash.getId() == null) { //新建
            if (total > 0) {
                return ResultVO.failResult("名称【" + hash.getName() + "】在该分组下已存在.");
            }
        } else if (total > 1) {     //编辑
            return ResultVO.failResult("名称【" + hash.getName() + "】在该分组下已存在.");
        }

        hash.setGroup(group);
        return hashLoadBalanceService.save(hash) > 0
                ? ResultVO.successResult()
                : ResultVO.failResult("添加失败.");
    }

    @ResponseBody
    @RequestMapping(value = "/hash.do", method = RequestMethod.DELETE)
    public ResultVO hash(@RequestBody ArrayList<HashLoadBalanceDTO> backends) {
        hashLoadBalanceService.deleteAllEntity(backends);
        return ResultVO.successResult();
    }
}
