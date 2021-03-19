package com.board.icia.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.bean.Member;
import com.board.icia.dao.IMemberDao;
import com.board.icia.dao.MemberDao;
import com.board.icia.exception.IdCheckException;

@Service //가독성면에서 좋은듯..
//@Component //미묘한 차이는 있다....?
public class MemberMM {

	@Autowired
	private IMemberDao mDao;
	//필드
	ModelAndView mav;
	
	public boolean getLoginResult(Member mb) {
		return mDao.getLoginResult(mb);
	}

	public ModelAndView access(Member mb, HttpSession session) {
		mav = new ModelAndView();
		String view=null;
		//스프링 복호화 안되지만 비교는 해줌.
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		String encodedPwd = mDao.getSecurityPwd(mb.getM_id()); //암호화된pw를 가져옴
		System.out.println("pw="+encodedPwd);
		if(encodedPwd != null) { //아이디 존재
			if(pwdEncoder.matches(mb.getM_pw(), encodedPwd)) {
				//로그인 성공 마킹
				session.setAttribute("id", mb.getM_id());
				mb=mDao.getMemberInfo(mb.getM_id());
				//mav.addObject("mb",mb); //request영역에 저장
				session.setAttribute("mb", mb);
				//System.out.println("여기야여기mb =" +mb);
				view="redirect:/boardlist";
				//view="redirect:/boardlist?pageNum=2"; //post,get-->get방식으로 리다이렉트함 //url(redirect:/)임! jsp아님
				//view="forward:/boardlist"; //post-->post, get-->get으로만 포워딩함 //url(redirect:/)임! jsp아님
			}else { //비번 불일치
				view="home";
				mav.addObject("check",2); //1:회원가입성공, 2:로그인실패 
			}
		}else {
			view="home";
			mav.addObject("check",2); //리퀘스트영역에 저장
		}
		/*
		if(mDao.access(mb)) {
			view="main"; //메인페이지
			mav.addObject("msg", "로그인 성공");
		}else {
			view="home"; //로그인페이지
		}*/
		mav.setViewName(view);
		return mav;
	}

	public ModelAndView memberJoin(Member mb) {
		mav= new ModelAndView();
		String view=null;
		//Encoder(암호화)<--->Decoder(복호화)
		//스프링에서 제공하는 암호화는 복호화 불가능
		BCryptPasswordEncoder pwdEncoder = new BCryptPasswordEncoder();
		//시간상 비번만 암호화
		mb.setM_pw(pwdEncoder.encode(mb.getM_pw()));
		if(mDao.memberJoin(mb)) {
			view="home"; //회원가입 성공시 로그인
			mav.addObject("check",1); //성공
		}else {
			view="joingFrm";
		}
		mav.setViewName(view);
		return mav;
	}

/*	public boolean idAvailable(String m_id) {
		Member mb = mDao.getMemberInfo(m_id);
		if(mb==null)
			return true;
		return false;
	}
*/
	public String idAvailable(String m_id) {
		Member mb = mDao.getMemberInfo(m_id);
		if(mb!=null)
			throw new IdCheckException("사용불가 아이디");
			//return "사용불가 아이디"; //통신성공이지만 원치 않는 데이터
		return "사용가능 아이디";
	}
}
