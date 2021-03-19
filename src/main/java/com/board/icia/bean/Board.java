package com.board.icia.bean;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.board.icia.entity.BoardFile;

import lombok.Data;
import lombok.experimental.Accessors;

//@Alias("board")
@Data
@Accessors(chain = true)
public class Board { //Blist View

	private int b_num;
	private String b_title;
	private String b_contents;
	private String b_id;
	private Timestamp b_date; //java.sql 시분초까지 나옴
	private int b_views;
	
	//첨부 파일 리스트
	private List<BoardFile> bfList;

}
