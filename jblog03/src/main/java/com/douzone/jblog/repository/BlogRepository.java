package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.PageInfo;

@Repository
public class BlogRepository {
	private final SqlSession sqlSession;
	
	public BlogRepository(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}


	public boolean insert(BlogVo vo) {
		return sqlSession.insert("blog.insert", vo) > 0;
	}


	public BlogVo findByNo(Long no) {
		return sqlSession.selectOne("blog.findByNo", no);
	}


	public BlogVo findByUserId(String id) {
		return sqlSession.selectOne("blog.findByUserId", id);
	}
	
	public Long getCnt() {
		return sqlSession.selectOne("blog.getCnt");
	}

////////////////////////////////////////////////////////////////////////
	public List<BlogVo> findAll(BlogVo blog, PageInfo pageInfo) {
		Map<String, Object> map = new HashMap<>();
		map.put("blog", blog);
		map.put("pageInfo", pageInfo);
		return sqlSession.selectList("blog.findAll", map);
	}
	
	public boolean update(BlogVo blog) {
		return sqlSession.update("blog.update",blog) > 0;
	}

}
