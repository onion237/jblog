package com.douzone.jblog.vo;

public class PageInfo {
	private long cur;
	private long total;
	private long begin;
	private long end;
	private long next;
	private long prev;

	private int resultPerPage;
	private int pageRange;
	
	private long offset;
	
	
	public PageInfo() {
		super();
		this.cur = 1;
		this.pageRange = 5;
		this.resultPerPage = 5;
	}
	public int getResultPerPage() {
		return resultPerPage;
	}
	public void setResultPerPage(int resultPerPage) {
		this.resultPerPage = resultPerPage;
	}
	public int getPageRange() {
		return pageRange;
	}
	public void setPageRange(int pageRange) {
		this.pageRange = pageRange;
	}
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	public long getBegin() {
		return begin;
	}
	public void setBegin(long begin) {
		this.begin = begin;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public long getCur() {
		return cur;
	}
	public void setCur(long cur) {
		this.cur = cur;
	}
	public long getNext() {
		return next;
	}
	public void setNext(long next) {
		this.next = next;
	}
	public long getPrev() {
		return prev;
	}
	public void setPrev(long prev) {
		this.prev = prev;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	@Override
	public String toString() {
		return "PageInfo [begin=" + begin + ", end=" + end + ", cur=" + cur + ", next=" + next + ", prev=" + prev
				+ ", total=" + total + "]";
	}
}