package cn.tpson.kulu.dispatcher.controller;

import cn.tpson.kulu.common.dto.vo.ResultVO;
import cn.tpson.kulu.common.util.ExcelUtils;
import cn.tpson.kulu.dispatcher.biz.dto.BackendDTO;
import cn.tpson.kulu.dispatcher.biz.dto.GroupDTO;
import cn.tpson.kulu.dispatcher.biz.dto.HashLoadBalanceDTO;
import cn.tpson.kulu.dispatcher.biz.dto.ProtocalDTO;
import cn.tpson.kulu.dispatcher.biz.service.BackendService;
import cn.tpson.kulu.dispatcher.biz.service.GroupService;
import cn.tpson.kulu.dispatcher.biz.service.HashLoadBalanceService;
import cn.tpson.kulu.dispatcher.biz.service.ProtocalService;
import cn.tpson.kulu.dispatcher.util.ProtocalUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private BackendService backendService;
    @Autowired
    HashLoadBalanceService hashLoadBalanceService;

    @RequestMapping(value = "/template/{type}.do", method = RequestMethod.GET)
    public void template(@PathVariable String type, HttpServletResponse resp) {
        try {
            switch (type) {
                case "protocal":
                    String[] p = {"名称", "接入端口", "起始标记", "结束标记", "分隔符(逗号用*代替)", "偏移量(分隔符和偏移量都填写，优先使用分隔符)", "长度"};
                    List<String[]> ps = new ArrayList<>(1);
                    ps.add(p);
                    writeTemplate("protocal.xlsx", "协议配置", ps, resp);
                    break;
                case "hash":
                    String[] h = {"名称", "KEY", "分组名称", "设备型号"};
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
                    return count > 0 ? ResultVO.successResult() : ResultVO.failResult("没有记录被导入.");
                }
            } catch (IOException e) {
                throw new RuntimeException("无法导入文件.");
            }
        }

        return ResultVO.failResult("没有记录被导入.");
    }

    public Long readTemplate(String type, List<String[]> rows) {
        Long count = 0L;
        switch (type) {
            case "protocal":
                List<ProtocalDTO> protocals = new ArrayList<>();
                rows.forEach(e -> {
                    // watch,@G#@,@R#@,*,,5
                    String[] array = e;
                    if (array.length == 7 && protocalService.findByName(array[0]) == null) {
                        ProtocalDTO protocal = new ProtocalDTO();
                        protocal.setName(array[0]);
                        protocal.setPort(Integer.valueOf(array[1]));
                        protocal.setStartFlag(array[2]);
                        protocal.setEndFlag(array[3]);
                        protocal.setSplit("*".equals(array[4]) ? "," : array[4]);
                        protocal.setOffset(NumberUtils.isDigits(array[5]) ? Integer.valueOf(array[5]) : null);
                        protocal.setOffsetType(protocal.getOffset() != null ? ProtocalUtils.OFFSET_TYPE_OFFSET : ProtocalUtils.OFFSET_TYPE_SPLIT);
                        protocal.setCount(NumberUtils.isDigits(array[6]) ? Integer.valueOf(array[6]) : 0);
                        protocals.add(protocal);
                    }
                });
                if (protocals.size() > 0) {
                    count = protocalService.saveAll(protocals);
                }
                break;
            case "hash":
                Map<String, HashLoadBalanceDTO> hashs = new HashMap<>();
                for (String[] e : rows) {
                    // name,1234567890123456,分组名称,设备名称
                    String[] array = e;
                    if (array.length == 4) {
                        GroupDTO group = groupService.findByName(array[2]);
                        if (group == null)
                            continue;

                        BackendDTO backend = backendService.findByEqpNameAndGroupId(array[3], group.getId());
                        if (backend == null)
                            continue;

                        int total = hashLoadBalanceService.countByNameAndGroup(array[0], group);
                        if (total == 0) {
                            HashLoadBalanceDTO b = new HashLoadBalanceDTO();
                            b.setName(array[0]);
                            b.setKey(array[1]);
                            b.setGroup(group);
                            b.setEqpName(array[3]);
                            hashs.put(array[0], b);
                        }
                    }
                }

                if (hashs.size() > 0) {
                    count = hashLoadBalanceService.saveAll(hashs.values());
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
