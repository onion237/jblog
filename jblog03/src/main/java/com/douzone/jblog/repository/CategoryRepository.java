package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	private final SqlSession sqlSession;

	public CategoryRepository(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}

	public boolean insert(CategoryVo vo) {
		return insert(vo, false) != null;
	}

	public Long insert(CategoryVo vo, boolean getGeneratedKey) {		
		sqlSession.insert("category.insert", vo);
		return vo.getNo();
	}

	public CategoryVo findByNo(Long no) {
		return sqlSession.selectOne("category.findByNo", no);
	}
	public List<CategoryVo> findByBlogNo(Long no) {
		return sqlSession.selectList("category.findByBlogNo", no);
	}

	public boolean delete(Long no) {
		return sqlSession.delete("category.delete",no) > 0;
	}

	public Long getUserNo(Long categoryNo) {
		return sqlSession.selectOne("category.getUserNo", categoryNo);
	}

	public List<CategoryVo> findByBlogId(String blogId) {
		return sqlSession.selectList("category.findByBlogId", blogId);
	}


}
