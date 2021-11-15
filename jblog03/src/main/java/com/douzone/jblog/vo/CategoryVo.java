package com.douzone.jblog.vo;

public class CategoryVo {
	private Long no;
	private String name;
	private String desc;
	private boolean isDefault;
	private Long blogNo;
	
	private Long postCount;
	
	
	public Long getPostCount() {
		return postCount;
	}
	public void setPostCount(Long postCount) {
		this.postCount = postCount;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public Long getBlogNo() {
		return blogNo;
	}
	public void setBlogNo(Long blogNo) {
		this.blogNo = blogNo;
	}
	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", name=" + name + ", desc=" + desc + ", isDefault=" + isDefault + ", blogNo="
				+ blogNo + "]";
	}
	
}
