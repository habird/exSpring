package com.board.icia.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.board.icia.bean.Member;

@Repository //저장소
//@Component //거의 똑같다
public class MemberDao {

	//@Autowired
	private SqlSessionTemplate tpl; //이름을 맞춰주거나 아니면 타입으로 매칭한다. di종속 주입종속!
	
	public boolean getLoginResult(Member mb) {
		//String sql="SELECT .... ";
		
		
		return tpl.selectOne("memberMapper.getLoginResult", mb); //매퍼쪽으로 파라미터 mb를 던짐 // true 혹은 false를 반환
		// selectOne :결과가 하나 , selectList :결과가 여러개
	}
}
