package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PageInfo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Repository
public class PostRepository {
	private final SqlSession sqlSession;

	public PostRepository(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}
	
	public boolean insert(PostVo vo) {
		return insert(vo, false) != null;
	}

	public Long insert(PostVo vo, boolean b) {
		sqlSession.insert("post.insert", vo);
		return vo.getNo();
	}
	
	public UserVo getUserByPostNo(Long postNo) {
		return sqlSession.selectOne("post.getUserByPostNo", postNo);
	}

	public PostVo findByNo(Long postNo) {
		return sqlSession.selectOne("post.findByNo", postNo);
	}

	public List<PostVo> findAllByCategoryNo(Long categoryNo) {
		return sqlSession.selectList("post.findAllByCategoryNo", categoryNo);
	}
	

	public boolean update(PostVo post) {
		return sqlSession.update("post.update", post) > 0;
	}

	public boolean delete(Long no) {
		return sqlSession.delete("post.delete", no) > 0;
	}

	
	//////////////////////////////////////////////////////////////////////////////////// 테스트 안함
	public List<PostVo> findAll(PostVo post, PageInfo pageInfo) {
		Map<String, Object> map = new HashMap<>();
		map.put("post", post);
		map.put("pageInfo", pageInfo);
		return sqlSession.selectList("post.findAll", map);
	}
	
	public Long getCntOfSearchResult(PostVo post) {
		return sqlSession.selectOne("post.getCntOfSearchResult");
	}

	public PostVo getLatest(Long categoryNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("post.getLatest", categoryNo);
	}
}
