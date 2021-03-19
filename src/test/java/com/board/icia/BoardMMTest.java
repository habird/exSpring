package com.board.icia;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.board.icia.bean.Board;
import com.board.icia.service.BoardMM;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Log4j
@RunWith(SpringJUnit4ClassRunner.class) //Spring에서 junit 실행할게
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/**/*-context.xml")
//@Transactional //테스트 조작시 자동으로 rollback해줌
public class BoardMMTest {
	
	@Autowired
	private BoardMM bm; //테스트를 할 mm
	
	@Test
	public void testExist() {
		log.info("bm="+bm); //주입이 됐다면 NULL이 아닌값
		assertThat(bm, is(notNullValue()));
		
	}
	
	@Test
	public void testInsert() {
		//더미 데이터 삽입가능
		log.info("bm="+bm); //주입이 됐다면 NULL이 아닌값
		assertThat(bm, is(notNullValue()));
		
	}
	
	@Test
	public void testGetList() {
		List<Board> bList=(List<Board>) bm.getBoardList(1).getModelMap().getAttribute("bList");
		bList.forEach(b->log.info("b="+b)); //람다식 , 스트림
		
		//for(Board b: bList) { //위에 람다식과 같은 것!
		//	log.info("b="+b);
		//}
		assertThat(bList.size(), is(10));
		assertThat(bm, notNullValue());
		
	}
}
