package com.kimtr.web.repository;

import com.kimtr.web.vo.MemberVO;

public interface IF_MemberDao {
	//디비 작업을 메서드로 정의 합니다.
	public void insertOne(MemberVO membervo)throws Exception;
	public MemberVO selectOne(String id) throws Exception;

}