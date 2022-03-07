package com.mega.dao.member;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mega.dto.member.MemberDto;

public class LoginDao {
	private SqlSession Mybatis;

	public LoginDao() {
		try {
			Reader reader = Resources.getResourceAsReader("com/mega/persistence/sql-map-config.xml");
			
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			mybatis = sessionFactory.openSession();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MemberDto getMemberByEmail(String email) {
		// 쿼리를 보내는 코드
		MemberDto memberDto = mybatis.selectOne("MemberDao.getMemberByEmail", email);
		
		return memberDto;
	}
}