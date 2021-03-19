package com.board.icia.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.board.icia.exception.IdCheckException;
import com.board.icia.exception.NoReplyException;

@RestControllerAdvice
public class ControllerAdviceRest { //충고하기 위해 만들어진 클래스

	//한글 깨짐 때문에 생성
	private HttpHeaders getHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","text/plain;charset=utf-8");
		return headers;
	}
	
	@ExceptionHandler(IdCheckException.class) //예외가 생겼을 때 조작해주는 것. 핸들러 메소드 안에 있는 파라미터가 받음
	public ResponseEntity<?> idDupExceptionHandler(IdCheckException ex){
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
	}

	@ExceptionHandler(NoReplyException.class) //예외가 생겼을 때 조작해주는 것. 핸들러 메소드 안에 있는 파라미터가 받음
	public ResponseEntity<?> ReplyExceptionHandler(NoReplyException ex){
		return new ResponseEntity<String>(ex.getMessage(), getHeader(), HttpStatus.EXPECTATION_FAILED);
	}

}
