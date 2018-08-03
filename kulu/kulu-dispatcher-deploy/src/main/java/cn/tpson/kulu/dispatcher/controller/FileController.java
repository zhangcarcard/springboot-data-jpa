package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.util.ExcelUtils;
import cn.tpson.kulu.dispatcher.biz.dto.HashBackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.ProtocalDTO;
import cn.tpson.kulu.dispatcher.biz.dto.RandomBackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.WeightBackendDTO;
import cn.tpson.kulu.dispatcher.biz.service.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangka in 2018/04/20
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    ProtocalService protocalService;
    @Autowired
    GroupService groupService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    HashBackendService hashBackendService;
    @Autowired
    WeightBackendService weightBackendService;
    @Autowired
    RandomBackendService randomBackendService;

    @RequestMapping(value = "/template/{type}.do", method = RequestMethod.GET)
    public void template(@PathVariable String type, HttpServletResponse resp) {
        try {
            switch (type) {
                case "protocal":
                    String[] p = {"名称", "起始标记", "结束标记", "分隔符(逗号用*代替)", "偏移量(分隔符和偏移量都填写，优先使用分隔符)", "长度", "设备名称"};
                    List<String[]> ps = new ArrayList<>(1);
                    ps.add(p);
                    writeTemplate("protocal.xlsx", "协议配置", ps, resp);
                    break;
                case "random":
                    String[] r = {"IP", "PORT", "协议名称", "分组名称"};
                    List<String[]> rs = new ArrayList<>(1);
                    rs.add(r);
                    writeTemplate("random.xlsx", "随机路由配置", rs, resp);
                    break;
                case "weight":
                    String[] w = {"IP", "PORT", "权重", "协议名称", "分组名称"};
                    List<String[]> ws = new ArrayList<>(1);
                    ws.add(w);
                    writeTemplate("weight.xlsx", "权重路由配置", ws, resp);
                    break;
                case "hash":
                    String[] h = {"IP", "PORT", "KEY", "协议名称", "分组名称"};
                    List<String[]> hs = new ArrayList<>(1);
                    hs.add(h);
                    writeTemplate("hash.xlsx", "HASH路由配置", hs, resp);
                    break;
                default:
                    throw new RuntimeException("无法下载模板.");
            }
        } catch (Exception e) {
            throw new RuntimeException("无法下载模板.");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/template/{type}.do", method = RequestMethod.POST)
    public ResultVO template(@PathVariable String type, MultipartFile p) {
        String filename = p.getOriginalFilename();
        if (filename.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
            boolean xlsx = filename.matches("^.+\\.(?i)(xlsx)$") ? true : false;

            try (InputStream in = p.getInputStream()) {
                List<String[]> rows = ExcelUtils.read(in, xlsx);
                if (rows.size() > 0) {
                    Long count = readTemplate(type, rows.subList(1, rows.size()));
                    return count != null && count > 0 ? ResultVO.successResult() : ResultVO.failResult("上传失败.");
                }
            } catch (IOException e) {
                throw new RuntimeException("无法导入文件.");
            }
        }

        return ResultVO.failResult("上传失败.");
    }

    public Long readTemplate(String type, List<String[]> rows) {
        Long count = 0L;
        switch (type) {
            case "protocal":
                List<ProtocalDTO> protocals = new ArrayList<>();
                rows.forEach(e -> {
                    // watch,@G#@,@R#@,*,,5
                    String[] array = e;
                    if (array.length == 6) {
                        ProtocalDTO protocal = new ProtocalDTO();
                        protocal.setName(array[0]);
                        protocal.setStartFlag(array[1]);
                        protocal.setEndFlag(array[2]);
                        protocal.setSplit("*".equals(array[3]) ? "," : array[3]);
                        protocal.setOffset(NumberUtils.isDigits(array[4]) ? Integer.valueOf(array[4]) : 0);
                        protocal.setCount(NumberUtils.isDigits(array[5]) ? Integer.valueOf(array[5]) : 0);
                        protocal.setEqpName(array[6]);
                        protocal.setEquipment(equipmentService.findByName(array[6]));
                        protocals.add(protocal);
                    }
                });
                if (protocals.size() > 0) {
                    count = protocalService.saveAll(protocals);
                }
                break;
            case "random":
                List<RandomBackendDTO> randoms = new ArrayList<>();
                rows.forEach(e -> {
                    // 192.168.1.249,8809,watch,watch_group1
                    String[] array = e;
                    if (array.length == 4) {
                        RandomBackendDTO b = new RandomBackendDTO();
                        b.setIp(array[0]);
                        b.setPort(NumberUtils.isDigits(array[1]) ? Integer.valueOf(array[1]) : 0);
                        b.setProtocal(protocalService.findByName(array[2]));
                        b.setGroup(groupService.findByName(array[3]));
                        b.setProtocalName(array[2]);
                        b.setGroupName(array[3]);
                        randoms.add(b);
                    }
                });
                if (randoms.size() > 0) {
                    count = randomBackendService.saveAll(randoms);
                }
                break;
            case "weight":
                List<WeightBackendDTO> weights = new ArrayList<>();
                rows.forEach(e -> {
                    // 192.168.1.249,8809,2,watch,watch_group1
                    String[] array = e;
                    if (array.length == 5) {
                        WeightBackendDTO b = new WeightBackendDTO();
                        b.setIp(array[0]);
                        b.setPort(NumberUtils.isDigits(array[1]) ? Integer.valueOf(array[1]) : 0);
                        b.setWeight(NumberUtils.isDigits(array[2]) ? Integer.valueOf(array[2]) : 0);
                        b.setProtocal(protocalService.findByName(array[3]));
                        b.setGroup(groupService.findByName(array[4]));
                        b.setProtocalName(array[3]);
                        b.setGroupName(array[4]);
                        weights.add(b);
                    }
                });
                if (weights.size() > 0) {
                    count = weightBackendService.saveAll(weights);
                }
                break;
            case "hash":
                List<HashBackendDTO> hashs = new ArrayList<>();
                rows.forEach(e -> {
                    // 192.168.1.249,8809,1234567890123456,watch,watch_group1
                    String[] array = e;
                    if (array.length == 5) {
                        HashBackendDTO b = new HashBackendDTO();
                        b.setIp(array[0]);
                        b.setPort(NumberUtils.isDigits(array[1]) ? Integer.valueOf(array[1]) : 0);
                        b.setKey(array[2]);
                        b.setProtocal(protocalService.findByName(array[3]));
                        b.setGroup(groupService.findByName(array[4]));
                        b.setProtocalName(array[3]);
                        b.setGroupName(array[4]);
                        hashs.add(b);
                    }
                });
                if (hashs.size() > 0) {
                    count = hashBackendService.saveAll(hashs);
                }
                break;
            default:
                throw new RuntimeException("类型错误.");
        }

        return count;
    }

    protected void writeTemplate(String filename, String sheetname, List<String[]> rows, HttpServletResponse resp) {
        resp.setContentType("application/octet-stream");
        resp.setCharacterEncoding("UTF-8");

        try (ServletOutputStream out = resp.getOutputStream()) {
            resp.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            ExcelUtils.write(sheetname, rows, out);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("无法下载模板.");
        }
    }
}
