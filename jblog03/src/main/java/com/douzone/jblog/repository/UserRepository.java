package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVo;

@Repository
public class UserRepository {
	private final SqlSession sqlSession;
	
	public UserRepository(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}

	public UserVo findByIdAndPassword(String id, String password) {
		UserVo vo = new UserVo();
		vo.setId(id);
		vo.setPassword(password);
		return sqlSession.selectOne("user.findByIdAndPassword", vo);
	}

	public UserVo findById(String userId) {
		return sqlSession.selectOne("user.findById", userId);
	}
	
	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo", no);
	}

	public boolean insert(UserVo vo) {
		return insert(vo, false) != null;
	}
	
	public Long insert(UserVo vo, boolean getGeneratedKey) {
		sqlSession.insert("user.insert", vo);
		return vo.getNo();
	}

	public boolean delete(Long no) {
		return sqlSession.delete("user.delete", no) > 0;
	}

	public boolean update(UserVo vo) {
		return sqlSession.update("user.update", vo) > 0;
	}

	public Long getCnt() {
		return sqlSession.selectOne("user.getCnt");
	}
}
