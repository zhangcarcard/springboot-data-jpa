package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.common.jpa.support.Like;
import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;
import cn.tpson.kulu.dispatcher.biz.dto.HashBackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.query.GroupQUERY;
import cn.tpson.kulu.dispatcher.biz.dto.query.HashBackendQUERY;
import cn.tpson.kulu.dispatcher.biz.service.GroupService;
import cn.tpson.kulu.dispatcher.biz.service.HashBackendService;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
public class HashBackendController {
    @Autowired
    HashBackendService hashBackendService;
    @Autowired
    GroupService groupService;
    @Autowired
    ProtocalService protocalService;

    @RequestMapping("/hash.html")
    public String html(Model model) {
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("protocals", protocalService.findAll());
        return "loadbalance/hash/hash";
    }

    @ResponseBody
    @RequestMapping(value = "/hash.do", method = RequestMethod.GET)
    public TableVO list(Integer offset, Integer limit, String search, String order, String sortName) {
        Sort sort = null;
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortName)) {
            sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), sortName);
        }
        Page<HashBackendDTO> page = hashBackendService.pageByKeywordContaining(offset / limit, limit, sort, search);

        return TableVO.successResult(page.getTotalElements(), page.getContent());
    }

    @ResponseBody
    @RequestMapping(value = "/hash.do", method = RequestMethod.POST)
    public ResultVO hash(HashBackendDTO backend) {
        backend.setGroup(groupService.findByName(backend.getGroupName()));
        backend.setProtocal(protocalService.findByName(backend.getProtocalName()));
        return hashBackendService.save(backend) > 0
                ? ResultVO.successResult()
                : ResultVO.failResult("添加失败.");
    }

    @ResponseBody
    @RequestMapping(value = "/hash.do", method = RequestMethod.DELETE)
    public ResultVO hash(@RequestBody ArrayList<HashBackendDTO> backends) {
        hashBackendService.deleteAllEntity(backends);
        return ResultVO.successResult();
    }
}
