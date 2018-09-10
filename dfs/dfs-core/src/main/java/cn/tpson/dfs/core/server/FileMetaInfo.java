package cn.tpson.dfs.core.server;

/**
 * Created by Zhangka in 2018/08/29
 */
public class FileMetaInfo {
    private Integer offset;
    private Integer fid;
    private Integer length;
    private String filename;

    public FileMetaInfo() {
    }

    public FileMetaInfo(Integer offset, Integer fid, Integer length, String filename) {
        this.offset = offset;
        this.fid = fid;
        this.length = length;
        this.filename = filename;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
