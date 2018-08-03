package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.common.jpa.support.Like;
import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;
import cn.tpson.kulu.dispatcher.biz.dto.ProtocalDTO;
import cn.tpson.kulu.dispatcher.biz.dto.query.GroupQUERY;
import cn.tpson.kulu.dispatcher.biz.service.GroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupService groupService;

    @RequestMapping(value = "/group.html", method = RequestMethod.GET)
    public String html() {
        return "group/group";
    }

    @ResponseBody
    @RequestMapping(value = "/group.do", method = RequestMethod.GET)
    public TableVO list(Integer offset, Integer limit, String search, String order, String sortName) {
        GroupQUERY query = new GroupQUERY(offset / limit, limit);
        Like like = null;
        Sort sort = null;
        if (StringUtils.isNotBlank(search)) {
            like = Like.by(
                    Like.Pair.by("name", "%" + search + "%"),
                    Like.Pair.by("comment", "%" + search + "%")
            );
        }
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortName)) {
            sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), sortName);
        }
        Page<GroupDTO> page = groupService.pageByExample(query, like, sort);
        return TableVO.successResult(page.getTotalElements(), page.getContent());
    }

    @ResponseBody
    @RequestMapping(value = "/group.do", method = RequestMethod.POST)
    public ResultVO save(GroupDTO group) {
        if (group.getId() == null && StringUtils.isNotBlank(group.getName())) {
            GroupDTO dto = groupService.findByName(group.getName());
            if (dto != null) {
                return ResultVO.failResult("名称不能重复.");
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
