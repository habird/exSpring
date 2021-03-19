package com.board.icia.bean;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.experimental.Accessors;

//@Alias("reply")
@Data
@Accessors
public class Reply {
	private int num;
	private int r_bnum;
	private String r_contents;
	private String r_id;
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	//private Timestamp r_date;
	private String r_date;
}
