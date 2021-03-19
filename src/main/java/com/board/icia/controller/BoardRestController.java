package com.board.icia.controller;

import java.awt.PageAttributes.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.board.icia.bean.Reply;
import com.board.icia.bean.SampleVO;
import com.board.icia.service.BoardMM;


@RestController //메소드마다 @ResponseBody 생략가능
@RequestMapping(value="/rest") //여기에 선언해주면 아래에 /rest/를 지워도 됌
public class BoardRestController {
	
	@Autowired
	private BoardMM bm; //게시판 서비스 클래스
	
	ModelAndView mav;
	//Gson(annotation-driven) 또는 jacson 변환기를 이용하는 객체
/*	@PostMapping(value = "/rest/replyinsert")
	public List<Reply> replyInsert(Reply r, HttpSession session) {
		//System.out.println("r_bnum:"+r.getR_bnum());
		//System.out.println("r_con:"+r.getR_contents());
		r.setR_id(session.getAttribute("id").toString()); //object니까 문자열로바꿈!
		List<Reply> rList = bm.replyInsert(r); //다 가져올꺼니까 리스트

		
		return rList; //json 반환.
		//return new Gson().toJson(rList);
		//new ObjectMapper().readValue(out, value); json-->객체
	}
*/	
	//순수하게 jacson 변환기 만 이용하는 객체 (속도 빠름, 어노테이션 세팅필요 없음)
/*	@PostMapping(value="/rest/replyinsert")
	public Map<String, String> replayInsert(@RequestBody Reply r, HttpSession session){
		Map<String, String> tMap = new HashMap<>();
		tMap.put("key", "value");
		return tMap;
		// return "value"; //json으로 변환되지 않아서 Map에 저장후 반환한다.
	}
*/
	
	@PostMapping(value = "/replyinsert", produces = "application/json;charset=utf-8") //위에 requestMapping에 대분류 /rest/ 밸류를 선언하여 주소가 짧아짐
	public ResponseEntity<?> replyInsert(@RequestBody Reply r, HttpSession session) {
		r.setR_id(session.getAttribute("id").toString());
		List<Reply> rList = bm.replyInsert(r);
		return ResponseEntity.ok(rList);
	}
	
/*	// map저장후 jackson으로 변환해볼것
	@PostMapping(value = "/replyinsert") //위에 requestMapping에 대분류 /rest/ 밸류를 선언하여 주소가 짧아짐
	public Map<String, List<Reply>> replyInsert(@RequestBody Reply r, HttpSession session) {
		r.setR_id(session.getAttribute("id").toString());
		Map<String, List<Reply>> rMap = bm.replyInsert(r);
		return rMap;
		//rMap = {'rList', ArrayList<rList>} 키 값-->json={"rList":[{},{},{}]} 속성 값

		
		//HashMap<String,String> tMap = new HashMap<>();
		//tMap.put("key", "result");
		//return tMap; //json 반환.
		
	}
*/	
	@PostMapping(value="/boardwrite")
	public Map<String, String> boardWrite(MultipartHttpServletRequest multi) {
		System.out.println("title = "+multi.getParameter("b_title"));
		System.out.println("contents = "+multi.getParameter("b_contents"));
		System.out.println("fileCheck = "+multi.getParameter("fileCheck"));
		List<MultipartFile> files = multi.getFiles("files");
		System.out.println("files1 = "+files.get(0).getOriginalFilename());
		System.out.println("files2 = "+files.get(1).getOriginalFilename());
		Map<String,String> map = new HashMap<>();
		map.put("msg", "파일업로드 성공");
		return map; //jackson이 json으로 변환 못하면 map저장할 것
	}

	@PostMapping(value="/getSample", produces={"text/plain;charset=UTF-8","application/json;charset=utf-8"}) //글자깨짐 방지 : {문자열, JSON}
	public String getSample() {
		System.out.println("type:"+org.springframework.http.MediaType.TEXT_PLAIN_VALUE);
		System.out.println("type:"+org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
		//return new SampleVO(100,"하","성운");
		return "안녕하세요";
	}
	
	
}
