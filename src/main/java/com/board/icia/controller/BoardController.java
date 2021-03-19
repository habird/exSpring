package com.board.icia.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.icia.bean.Board;
import com.board.icia.entity.BoardFile;
import com.board.icia.service.BoardMM;

@Controller
public class BoardController {
	@Autowired
	private BoardMM bm; //게시판 서비스 클래스
	
	ModelAndView mav; 

	@RequestMapping(value="/boardlist") //안쓰면 get/post 둘다 받음!!!
	public ModelAndView getBoardList(Integer pageNum, HttpServletRequest req){
		//=null 파라미터가 없으면 널로 떨어지니까 integer로 함 //파라미터로 페이지넘버 보낼수 있음

		//System.out.println("pageNum="+pageNum); //파라미터가 넘어가는지 확인!
		mav=bm.getBoardList(pageNum); //파라미터 들고가야하니까 pageNum 하다못해 null이라도 들고감
		//mav.addObject("bNum",req.getParameter("bNum")); //삭제된 글번호를 리퀘스트에서 가져옴
		return mav; //jsp
	}
	@RequestMapping(value = "/contents", method = RequestMethod.GET)
	public ModelAndView getContents(Integer bNum, HttpSession session) {
		mav=bm.getContents(bNum,session);
		return mav;
	}
	@RequestMapping(value = "/writefrm", method = RequestMethod.GET)
	public String writeFrm() {
		return "writeFrm"; //jsp
	}
	@RequestMapping(value = "/boardwrite", method = RequestMethod.POST)
	public ModelAndView boardwrite(MultipartHttpServletRequest multi){//Board b, List<MultipartFile> file) {
		//System.out.println("지금 이순간 !! b_title = "+multi.getParameter("b_title"));
		//System.out.println("bf="+multi.getFile("files").getOriginalFilename());
		mav=bm.boardWritd(multi);
		
		return mav; //jsp
	}
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String,Object> params,HttpServletRequest req, HttpServletResponse resp) throws Exception{ 
								//빈과 달리 Map은 앞에 @RequestParam을 붙여줘야함
		System.out.println("1 = "+params.get("sysFileName"));
		System.out.println("2 = "+params.get("origFileName"));
		//다운로드하기 위한 물리적 주소
		params.put("root", req.getSession().getServletContext().getRealPath("/"));
		params.put("response", resp);
		bm.download(params);
	}
	@RequestMapping(value = "/boarddelete", method = RequestMethod.POST)
	public ModelAndView boardDelete(Integer bNum, RedirectAttributes attr) {
		System.out.println("bNum = " +bNum);
		//mav=bm.execute("/boarddelete");
		mav=bm.boardDelete(bNum, attr);
		return mav; //jsp
	}
	
	@GetMapping(value="/test")
	public ModelAndView myBatisTest(String cName, Integer search) {
		mav = bm.myBatisTest(cName,search);
		return mav;
	}
	@GetMapping(value="/testmap")
	//커맨드 객체가 Map인 경우 @RequestParam 생략 불가
	public ModelAndView myBatisTestMap(@RequestParam Map<String,Object> hMap) {
		System.out.println("cName"+hMap.get("cName"));
		System.out.println("search"+hMap.get("search"));
		mav = bm.myBatisTestMap(hMap);
		return mav;
	}
	
}
