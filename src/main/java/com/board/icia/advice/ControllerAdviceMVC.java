package com.board.icia.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.icia.exception.CommonException;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;



@ControllerAdvice
@Log4j
public class ControllerAdviceMVC {
	
/*  //주의 주석처리하지 않으면 다 받아버린다.
	//boardlist?pageNum=a라고 입력시 스프링(java)예외 처리함
	@ExceptionHandler(Exception.class) //조상급
	public String except2(CommonException ex, Model model) {
		log.error("Exception....."+ex.getMessage());
		model.addAttribute("exception",ex);
		log.error(model);
		return "error/error_pagenum";
		//return "redirect:/boardlist"; //?pageNum=1
	}
*/
	@ExceptionHandler(CommonException.class)
	public String except(CommonException ex, RedirectAttributes attr) {
		attr.addFlashAttribute("msg",ex.getMessage());                                                                                                           
		return "redirect:/boardlist"; //?pageNum=1
	}

}
