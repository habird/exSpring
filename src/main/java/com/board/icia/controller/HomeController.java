package com.board.icia.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.bean.Member;
import com.board.icia.service.MemberMM;

//Model객체에서 mav or session에서 "mb"속성저장된 객체는 세선 영역에 저장후 필요없으면 별도록 제거해야 함
//@SessionAttributes("mb")
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private MemberMM mm; //회원관리 서비스 클래스
	//필드
	ModelAndView mav;
	
	@GetMapping(value = "/")
	public String home() {
		
		return "home"; //.jsp
	}
	@PostMapping(value = "/access") //@RequestMapping 비워두면 get, post 둘다 받는다
	public ModelAndView access(Member mb, HttpSession session) {
		mav=mm.access(mb, session);
		return mav; //.jsp
	}
	@PostMapping(value = "/logout") //@RequestMapping 비워두면 get, post 둘다 받는다
	//public String logout(SessionStatus session) {
	public String logout(HttpSession session) {
		System.out.println("logout call");
		//session.setComplete();
		session.invalidate(); //초기화
		return "redirect:/"; // post,get --> get만 url
	}
	@GetMapping(value = "/joinfrm")
	public String joinFrm() { //창만 띄우는거니까 파라미터 필요없음.
		logger.info("회원가입창으로 이동");
		return "joinFrm"; //.jsp
	}
	@PostMapping(value = "/memberjoin")
	public ModelAndView memberJoin(Member mb) {
		logger.info("회원가입");
		mav=mm.memberJoin(mb);
		return mav; //.jsp
	}

}
