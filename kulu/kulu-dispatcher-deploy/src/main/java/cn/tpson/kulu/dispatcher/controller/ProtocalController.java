package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.constant.ErrorCodeEnum;
import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.common.jpa.support.Like;
import cn.tpson.kulu.dispatcher.biz.dto.EquipmentDTO;
import cn.tpson.kulu.dispatcher.biz.dto.HashBackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.ProtocalDTO;
import cn.tpson.kulu.dispatcher.biz.dto.query.EquipmentQUERY;
import cn.tpson.kulu.dispatcher.biz.dto.query.ProtocalQUERY;
import cn.tpson.kulu.dispatcher.biz.service.EquipmentService;
import cn.tpson.kulu.dispatcher.biz.service.HashBackendService;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
import cn.tpson.kulu.dispatcher.util.ProtocalUtils;
import cn.tpson.kulu.dispatcher.vo.BackendVO;
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
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/protocal")
public class ProtocalController {
    @Autowired
    private ProtocalService protocalService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private HashBackendService hashBackendService;

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/protocal.html", method = RequestMethod.GET)
    public String html(Model model) {
        model.addAttribute("equipments", equipmentService.findAll());
        return "protocal/protocal";
    }

    /**
     *
     * @param offset
     * @param limit
     * @param search
     * @param order
     * @param sortName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/protocal.do", method = RequestMethod.GET)
    public TableVO list(Integer offset, Integer limit, String search, String order, String sortName) {
        Sort sort = null;
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortName)) {
            sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), sortName);
        }
        Page<ProtocalDTO> page = protocalService.pageByKeywordContaining(offset / limit, limit, sort, search);

        return TableVO.successResult(page.getTotalElements(), page.getContent());
    }

    /**
     *
     * @param protocal
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/protocal.do", method = RequestMethod.POST)
    public ResultVO save(ProtocalDTO protocal) {
        if (protocal.getId() == null && StringUtils.isNotBlank(protocal.getName())) {
            if (protocalService.findByName(protocal.getName()) != null) {
                return ResultVO.failResult("名称不能重复.");
            }
        }

        protocal.setEquipment(equipmentService.findByName(protocal.getEqpName()));
        return protocalService.save(protocal) > 0
                ? ResultVO.successResult()
                : ResultVO.failResult("添加失败.");
    }

    /**
     *
     * @param protocals
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/protocal.do", method = RequestMethod.DELETE)
    public ResultVO delete(@RequestBody ArrayList<ProtocalDTO> protocals) {
        protocalService.deleteAllEntity(protocals);
        return ResultVO.successResult();
    }

    /**
     *
     * @param serverPort
     * @param hexMsg
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/key.do", method = RequestMethod.GET)
    public ResultVO key(Integer serverPort, String hexMsg) {
        if (serverPort == null || StringUtils.isBlank(hexMsg)) {
            return ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND);
        }

        EquipmentDTO equipment = equipmentService.findByPort(serverPort);
        if (equipment == null) {
            return ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND);
        }

        ProtocalQUERY query = new ProtocalQUERY();
        query.setEquipment(equipment);
        List<ProtocalDTO> protocals = protocalService.findByExample(query);
        if (protocals.isEmpty()) {
            return ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND);
        }

        byte[] msg = DatatypeConverter.parseHexBinary(hexMsg);
        String key = ProtocalUtils.getKey(protocals.get(0), msg);

        return StringUtils.isBlank(key)
                ? ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND)
                : ResultVO.successResult(key);
    }

    /**
     *
     * @param serverPort
     * @param key
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/backend.do", method = RequestMethod.GET)
    public ResultVO backend(Integer serverPort, String key) {
        if (serverPort == null || StringUtils.isBlank(key)) {
            return ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND);
        }

        EquipmentDTO equipment = equipmentService.findByPort(serverPort);
        if (equipment == null) {
            return ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND);
        }

        ProtocalQUERY query = new ProtocalQUERY();
        query.setEquipment(equipment);
        List<ProtocalDTO> protocals = protocalService.findByExample(query);
        if (protocals.isEmpty()) {
            return ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND);
        }

        List<HashBackendDTO> backends = hashBackendService.findByKey(key);
        for (ProtocalDTO p : protocals) {
            for (HashBackendDTO b : backends) {
                if (Objects.equals(p.getId(), b.getProtocal().getId())) {
                    BackendVO vo = new BackendVO(b.getIp(), b.getPort(), key, b.getGroupName(), b.getProtocal().getName(), equipment.getName(), serverPort);
                    return ResultVO.successResult(vo);
                }
            }
        }

        return StringUtils.isBlank(key)
                ? ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND)
                : ResultVO.successResult(key);
    }
}
