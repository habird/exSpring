package com.board.icia.entity;

import lombok.Data;
import lombok.experimental.Accessors;

//@Alias("boardFile")
@Data
@Accessors(chain=true)
public class BoardFile {
	private int bf_num; //pk
	private int bf_bnum; //pk
	private String bf_origname;
	private String bf_sysname;
	
	
	
}
