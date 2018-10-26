package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.constant.ErrorCodeEnum;
import cn.tpson.kulu.common.ds.ByteArray;
import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.dto.vo.TableVO;
import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import cn.tpson.kulu.dispatcher.biz.dto.*;
import cn.tpson.kulu.dispatcher.biz.dto.query.ProtocalQUERY;
import cn.tpson.kulu.dispatcher.biz.service.BackendService;
import cn.tpson.kulu.dispatcher.biz.service.EquipmentService;
import cn.tpson.kulu.dispatcher.biz.service.HashLoadBalanceService;
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

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Zhangka in 2018/08/01
 */
@Controller
@RequestMapping("/protocal")
public class ProtocalController {
    private static final Logger log = LoggerFactory.getLogger(ProtocalController.class);

    @Autowired
    private ProtocalService protocalService;
    @Autowired
    private HashLoadBalanceService hashLoadBalanceService;
    @Autowired
    private BackendService backendService;

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/protocal.html", method = RequestMethod.GET)
    public String html(Model model) {
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
        if (protocal.getId() == null) {
            if (StringUtils.isNotBlank(protocal.getName())) {
                ProtocalDTO p = protocalService.findByName(protocal.getName());
                if (p != null) {
                    return ResultVO.failResult("名称【" + protocal.getName() + "】已存在.");
                }
            }

            /*if (protocal.getPort() != null) {
                ProtocalDTO p = protocalService.findByPort(protocal.getPort());
                if (p != null) {
                    return ResultVO.failResult("端口【" + p.getPort() + "】下已存在协议【" + p.getName() + "】");
                }
            }*/
        }



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
    @RequestMapping(value = "/key.do", method = RequestMethod.GET, produces="application/json")
    public ResultVO key(Integer serverPort, String hexMsg) {
        log.info("serverPort:{},hexMsg:{}", serverPort, hexMsg);

        if (serverPort == null || StringUtils.isBlank(hexMsg)) {
            return ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND);
        }

        ProtocalQUERY query = new ProtocalQUERY();
        query.setPort(serverPort);
        List<ProtocalDTO> protocals = protocalService.findByExample(query);
        if (protocals.isEmpty()) {
            return ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND);
        }

        log.info("protocals.size:{}", protocals.size());
        byte[] msg = DatatypeConverter.parseHexBinary(hexMsg);
        String key = null;
        if (protocals.size() == 1) {
            key = ProtocalUtils.getKey(protocals.get(0), msg);
        } else {
            ByteArray byteArray = ByteArray.asByteArray(msg);
            for (ProtocalDTO p : protocals) {
                if (byteArray.startsWith(p.getStartFlag().getBytes())) {
                    key = ProtocalUtils.getKey(p, msg);
                    break;
                }
            }
        }

        log.info("key.key:{}", key);
        return StringUtils.isBlank(key)
                ? ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND)
                : ResultVO.successResult(key);
    }

    /**
     *
     * @param key
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/backend.do", method = RequestMethod.GET)
    public ResultVO backend(String key) {
        log.info("backend.key:{}", key);
        if (StringUtils.isBlank(key)) {
            return ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND);
        }

        //转发策略
        List<HashLoadBalanceDTO> list = hashLoadBalanceService.findByKey(key);
        if (list.isEmpty()) {
            return ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND);
        }

        BackendVO vo = null;
        HashLoadBalanceDTO hash = list.get(0);
        List<BackendDTO> backends = backendService.findByGroupId(hash.getGroup().getId());
        for (BackendDTO b : backends) {
            if (Objects.equals(hash.getEqpName(), b.getEqpName())) {
                vo = new BackendVO(b.getIp(), b.getPort(), key, hash.getGroup().getName(), null, b.getEqpName(), null);
                break;
            }
        }

        return vo == null
                ? ResultVO.failResult(ErrorCodeEnum.RESULT_NOT_FOUND)
                : ResultVO.successResult(vo);
    }
}
