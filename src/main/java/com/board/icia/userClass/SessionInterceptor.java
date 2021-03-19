package com.board.icia.userClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandler Call");
		if(request.getSession().getAttribute("id")==null) { //기본값 true
			response.sendRedirect("./"); //home.jsp
			//return false; 줘두 되구 안줘두 됌
		}
		return true; // true를 줘야 controller에 진입가능
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		//super.postHandle(request, response, handler, modelAndView);
		System.out.println("post Handler Call");
	}
}
