package cn.tpson.kulu.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

public class DituUtils {
    private static final RestTemplate restTemplate = new RestTemplate();

    private DituUtils() {
        throw new AssertionError("No DituUtils instances for you!");
    }

    public static Area getArea(Double lat, Double lon) {
        String url = "http://gc.ditu.aliyun.com/regeocoding?l=${lat},${lon}&type=010";
        String json = restTemplate.getForEntity(url, String.class, lat, lon).getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("addrList");
        JSONObject obj = jsonArray.getJSONObject(0);
        String area = obj.getString("admName");
        String addr = obj.getString("name");

        return new Area(area, addr);
    }

    public static class Area {
        private String area;
        private String addr;

        public Area(String area, String addr) {
            this.area = area;
            this.addr = addr;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }
}
