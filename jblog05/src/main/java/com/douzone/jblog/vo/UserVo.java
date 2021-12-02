package com.douzone.jblog.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class UserVo {
	private Long no;
	@NotEmpty
	private String name;
	@Pattern(regexp = "^(?!^(admin|assets)$)[a-z][a-z0-9]{4,40}$")
	private String id;
	@Length(min = 4, max = 20)
	private String password;
	private String joinDate;

	private String blogTitle;

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	// public boolean matchIdByPattern(String pattern) {
//		if(pattern == null || this.id == null) return false;
//		return id.matches(pattern);
//	}
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "UserVo [no=" + no + ", name=" + name + ", id=" + id + ", password=" + password + ", joinDate="
				+ joinDate + "]";
	}
}
