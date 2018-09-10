package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.dispatcher.biz.dto.BackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.query.BackendQUERY;
import cn.tpson.kulu.dispatcher.biz.service.BackendService;
import cn.tpson.kulu.dispatcher.biz.service.GroupService;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/backend")
public class BackendController {
    @Autowired
    BackendService backendService;
    @Autowired
    GroupService groupService;
    @Autowired
    ProtocalService protocalService;

    @ResponseBody
    @RequestMapping(value = "/backend.do", method = RequestMethod.GET)
    public TableVO list(Long groupId, Integer offset, Integer limit, String search, String order, String sortName) {
        Sort sort = null;
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortName)) {
            sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), sortName);
        }
        BackendQUERY query = new BackendQUERY();
        query.setPageNumber(offset / limit);
        query.setPageSize(limit);
        query.setGroupId(groupId);
//        Page<BackendDTO> page = backendService.pageByKeywordContaining(offset / limit, limit, sort, search);
        Page<BackendDTO> page = backendService.pageByExample(query, sort);

        return TableVO.successResult(page.getTotalElements(), page.getContent());
    }

    @ResponseBody
    @RequestMapping(value = "/backend.do", method = RequestMethod.POST)
    public ResultVO hash(BackendDTO backend) {
        if (StringUtils.isBlank(backend.getEqpName())) {
            return ResultVO.failResult("名称不能为空.");
        }

        int total = backendService.countByEqpNameAndGroupId(backend.getEqpName(), backend.getGroupId());
        if (backend.getId() == null) {
            if (total > 0) {
                return ResultVO.failResult("设备型号【" + backend.getEqpName() + "】在改分组下已存在.");
            }
        } else {
            if (total > 1) {
                return ResultVO.failResult("设备型号【" + backend.getEqpName() + "】在改分组下已存在.");
            }
        }

        return backendService.save(backend) > 0
                ? ResultVO.successResult()
                : ResultVO.failResult("添加失败.");
    }

    @ResponseBody
    @RequestMapping(value = "/backend.do", method = RequestMethod.DELETE)
    public ResultVO hash(@RequestBody ArrayList<BackendDTO> backends) {
        backendService.deleteAllEntity(backends);
        return ResultVO.successResult();
    }

    @ResponseBody
    @GetMapping("/eqpNames.do")
    public ResultVO eqpNames(Long groupId) {
        return groupId == null
                ? ResultVO.failResult("参数错误.")
                : ResultVO.successResult(backendService.findAllEqpNameByGroupId(groupId));
    }
}
