package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;
import cn.tpson.kulu.dispatcher.biz.service.GroupService;
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

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private ProtocalService protocalService;

    @RequestMapping(value = "/group.html", method = RequestMethod.GET)
    public String html(Model model) {
        model.addAttribute("protocals", protocalService.findAll());
        return "group/group";
    }

    @ResponseBody
    @RequestMapping(value = "/group.do", method = RequestMethod.GET)
    public TableVO list(Integer offset, Integer limit, String search, String order, String sortName) {
        Sort sort = null;
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortName)) {
            sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), sortName);
        }
        Page<GroupDTO> page = groupService.pageByKeywordContaining(offset / limit, limit, sort, search);

        return TableVO.successResult(page.getTotalElements(), page.getContent());
    }

    @ResponseBody
    @RequestMapping(value = "/group.do", method = RequestMethod.POST)
    public ResultVO save(GroupDTO group) {
        if (group.getId() == null && StringUtils.isNotBlank(group.getName())) {
            GroupDTO g = groupService.findByName(group.getName());
            if (g != null) {
                return ResultVO.failResult("名称【" + group.getName() + "】已存在.");
            }
        }

        return groupService.save(group) > 0
                ? ResultVO.successResult()
                : ResultVO.failResult("保存失败.");
    }

    @ResponseBody
    @RequestMapping(value = "/group.do", method = RequestMethod.DELETE)
    public ResultVO delete(@RequestBody ArrayList<GroupDTO> groups) {
        groupService.deleteAllEntity(groups);
        return ResultVO.successResult();
    }
}
