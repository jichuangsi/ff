package cn.com.fintheircing.customer.common.model;

public abstract class PageModel {
	protected int startIndex = 1;// 起始位置，由1开始
	protected int pageSize = 10;

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
