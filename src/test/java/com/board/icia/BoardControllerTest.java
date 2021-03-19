package com.board.icia;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.board.icia.bean.Board;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class) //Spring에서 junit 실행할게
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/**/*-context.xml")
@WebAppConfiguration //controller Test시
public class BoardControllerTest {
	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc; //목객체:가짜 객채
	
	@Before //테스트 전에 실행하는 어노테이션 @after도 있음
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test //테스트 실행
	public void listTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/boardlist").param("pageNum", "2")).andReturn();
		List<Board> bList = (List<Board>) result.getModelAndView().getModel().get("bList");
		assertThat(bList.size(), is(10)); //is(not(10)));
		log.info("1="+bList);  
	}

}
