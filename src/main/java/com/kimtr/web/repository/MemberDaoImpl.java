package com.kimtr.web.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kimtr.web.vo.MemberVO;
@Repository
public class MemberDaoImpl implements IF_MemberDao {

	//sqlSession이 필요합니다.  Mybatis에서제공하는 객체
	//주소가 올바르게 주입되려면 root-context.xml에서 설정이 잘 되어 있어야 한다.
	@Autowired
	SqlSession sqlSession;
	private static String mapperQuery="com.kimtr.web.repository.IF_MemberDao";
	@Override
	public void insertOne(MemberVO membervo) throws Exception {
		// sqlSessiomTemplate 의 객체의 메서드를  call하면된다.
		//sqlSession.insert("mapper와 매핑정보", "마라미터값");
		System.out.println("dao");
		System.out.println(membervo.getPass()+"/"+membervo.getRole());
		sqlSession.insert(mapperQuery+".insertOne",membervo);
		//   www.silver.dao.IF_MemberDao.insertOne  << 매핑의정보
	}
	@Override
	public MemberVO selectOne(String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(mapperQuery+".selectOne", id);
	}

}
