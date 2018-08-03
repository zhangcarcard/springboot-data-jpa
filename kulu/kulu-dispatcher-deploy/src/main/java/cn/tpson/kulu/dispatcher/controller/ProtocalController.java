package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.common.jpa.support.Like;
import cn.tpson.kulu.dispatcher.biz.dto.ProtocalDTO;
import cn.tpson.kulu.dispatcher.biz.dto.query.ProtocalQUERY;
import cn.tpson.kulu.dispatcher.biz.service.EquipmentService;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
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

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/protocal")
public class ProtocalController {
    @Autowired
    ProtocalService protocalService;
    @Autowired
    EquipmentService equipmentService;

    @RequestMapping(value = "/protocal.html", method = RequestMethod.GET)
    public String html(Model model) {
        model.addAttribute("equipments", equipmentService.findAll());
        return "protocal/protocal";
    }

    @ResponseBody
    @RequestMapping(value = "/protocal.do", method = RequestMethod.GET)
    public TableVO list(Integer offset, Integer limit, String search) {
        ProtocalQUERY query = new ProtocalQUERY(offset / limit, limit);
        Like like = null;
        if (StringUtils.isNotBlank(search)) {
            like = Like.by(
                    Like.Pair.by("name", "%" + search + "%"),
                    Like.Pair.by("eqpName", "%" + search + "%"),
                    Like.Pair.by("startFlag", "%" + search + "%"),
                    Like.Pair.by("endFlag", "%" + search + "%")
            );
        }
        Page<ProtocalDTO> page = protocalService.pageByExample(query, like, null);
        return TableVO.successResult(page.getTotalElements(), page.getContent());
    }

    @ResponseBody
    @RequestMapping(value = "/protocal.do", method = RequestMethod.POST)
    public ResultVO save(ProtocalDTO protocal) {
        if (protocal.getId() == null && StringUtils.isNotBlank(protocal.getName())) {
            ProtocalDTO dto = protocalService.findByName(protocal.getName());
            if (dto != null) {
                return ResultVO.failResult("名称不能重复.");
            }
        }

        protocal.setEquipment(equipmentService.findByName(protocal.getEqpName()));
        return protocalService.save(protocal) > 0
                ? ResultVO.successResult()
                : ResultVO.failResult("添加失败.");
    }

    @ResponseBody
    @RequestMapping(value = "/protocal.do", method = RequestMethod.DELETE)
    public ResultVO delete(@RequestBody ArrayList<ProtocalDTO> protocals) {
        protocalService.deleteAllEntity(protocals);
        return ResultVO.successResult();
    }
}
