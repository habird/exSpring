package com.board.icia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.board.icia.service.MemberMM;

@RestController //@responseBody 생략가능
@RequestMapping(value="/member")
public class MemberRestController {
	@Autowired
	private MemberMM mm;
	
	@GetMapping(value="/userid", produces = "text/plain;charset=utf-8")
	public ResponseEntity<?> idAvailable(String m_id){
		
/*		ResponseEntity<?> result=null;
		if(mm.idAvailable(m_id)) {
			result=ResponseEntity.ok().body("사용가능한 아이디");
		}else {//서버랑 통신성공했지만 원치않는 데이터를 err콜백함수로 반환
			result=ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("사용불가 아이디");
		}
		return result;
*/
		return ResponseEntity.ok(mm.idAvailable(m_id));
	}
	
	//일부업데이트 @PatchMapping
	//전체업데이트 @PutMapping
	
	//삭제
	@DeleteMapping(value="/delete", produces="text/plain;charset=utf-8")
	public String delete(Integer num){
		return "delete result:"+num;
	}
	@PutMapping(value="/put", produces="text/plain;charset=utf-8")
	public String put(Integer num){
		return "put result:"+num;
	}
	//삽입
	@PatchMapping(value="/patch", produces="text/plain;charset=utf-8")
	public String patch(Integer num){
		return "patch result:"+num;
	}
	//검색									//이름 같으면 () 생략가능
	@GetMapping(value="/{dept}/{emp}", produces="text/plain;charset=utf-8")
	public String pathVariable(@PathVariable("dept") String dept,
							   @PathVariable("emp") String emp){
		System.out.println("dept = "+dept);
		System.out.println("emp = "+emp);
		return dept+":"+emp;
	}
	
}
