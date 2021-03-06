package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.dispatcher.biz.dto.EquipmentDTO;
import cn.tpson.kulu.dispatcher.biz.service.EquipmentService;
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
@RequestMapping("/equipment")
public class EquipmentController {
    @Autowired
    EquipmentService equipmentService;

    @RequestMapping(value = "/equipment.html", method = RequestMethod.GET)
    public String html() {
        return "equipment/equipment";
    }

    @ResponseBody
    @RequestMapping(value = "/equipment.do", method = RequestMethod.GET)
    public TableVO list(Integer offset, Integer limit, String search, String order, String sortName) {
        Sort sort = null;
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortName)) {
            sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), sortName);
        }
        Page<EquipmentDTO> page = equipmentService.pageByKeywordContaining(offset / limit, limit, sort, search);

        return TableVO.successResult(page.getTotalElements(), page.getContent());
    }

    @ResponseBody
    @RequestMapping(value = "/equipment.do", method = RequestMethod.POST)
    public ResultVO save(EquipmentDTO equipment) {
        if (equipment.getId() == null) {
            if (StringUtils.isNotBlank(equipment.getName())) {
                EquipmentDTO dto = equipmentService.findByName(equipment.getName());
                if (dto != null) {
                    return ResultVO.failResult("名称不能重复.");
                }
            }
            if (equipment.getPort() != null) {
                EquipmentDTO dto = equipmentService.findByPort(equipment.getPort());
                if (dto != null) {
                    return ResultVO.failResult("端口不能重复.");
                }
            }
        }

        return equipmentService.save(equipment) > 0
                ? ResultVO.successResult()
                : ResultVO.failResult("保存失败.");
    }

    @ResponseBody
    @RequestMapping(value = "/equipment.do", method = RequestMethod.DELETE)
    public ResultVO delete(@RequestBody ArrayList<EquipmentDTO> equipments) {
        equipmentService.deleteAllEntity(equipments);
        return ResultVO.successResult();
    }
}
