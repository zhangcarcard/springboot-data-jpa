package cn.tpson.kulu.common.dto.vo;

public class TableVO {
	private Long total;
	private Object rows;
	
	public TableVO(Long total, Object rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public static TableVO successResult(Long total, Object rows) {
		return new TableVO(total, rows);
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}
}
