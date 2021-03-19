package com.board.icia.bean;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.experimental.Accessors;

//import lombok.AllArgsConstructor;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;

//@Getter
//@Setter
//@AllArgsConstructor
//@ToString
//@EqualsAndHashCode

//@Alias("member")
@Data //위에 것 다 만들어줌
@Accessors(chain = true) //체이닝!짱좋아
public class Member { //minfo view와 Member테이블
	private String m_id;
	private String m_pw;
	private int m_point;
	private String g_name;
	
	private String m_name;	
	private String m_birth;
	private String m_addr;

}
