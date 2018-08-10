package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.dispatcher.biz.dto.BackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.query.BackendQUERY;
import cn.tpson.kulu.dispatcher.biz.service.BackendService;
import cn.tpson.kulu.dispatcher.biz.service.GroupService;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

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
    public TableVO list(Integer offset, Integer limit, String search, String order, String sortName) {
        Sort sort = null;
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortName)) {
            sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), sortName);
        }
        BackendQUERY query = new BackendQUERY();
        query.setPageNumber(offset / limit);
        query.setPageSize(limit);
//        Page<BackendDTO> page = backendService.pageByKeywordContaining(offset / limit, limit, sort, search);
        Page<BackendDTO> page = backendService.pageByExample(query, sort);

        return TableVO.successResult(page.getTotalElements(), page.getContent());
    }

    @ResponseBody
    @RequestMapping(value = "/backend.do", method = RequestMethod.POST)
    public ResultVO hash(BackendDTO backend) {
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
}
