package com.board.icia.dao;

import com.board.icia.bean.Member;

public interface IMemberDao { //interface는 설계도
	public boolean getLoginResult(Member mb); //sql가 빠졌음.

	public boolean access(Member mb);

	public boolean memberJoin(Member mb);

	public String getSecurityPwd(String id);

	public Member getMemberInfo(String m_id);
	
	
}
