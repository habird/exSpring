package com.board.icia;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.board.icia.bean.Member;
import com.board.icia.dao.IMemberDao;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class) //Spring에서 junit 실행할게
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/**/*-context.xml")
@Transactional //테스트 조작시 자동으로 rollback해줌
public class MemberDaoTest {
	private IMemberDao mDao;
	
	@Test
	public void loginTest() {
		log.info("mDao="+mDao);
		assertThat(mDao, is(notNullValue()));
		Member mb = new Member().setM_id("banana").setM_pw("1234");
		assertThat(mDao.getLoginResult(mb), is(true));
		mb=mDao.getMemberInfo("banana");
		assertThat(mb.getM_pw(), is(nullValue())); //mInfo에서 검색했기 때문에 pw가 없어서 Null
		assertThat(mb.getM_name(), is(equalTo("바나나")));
		assertThat(mb.getM_name(), is("바나나"));
		
	}
}
