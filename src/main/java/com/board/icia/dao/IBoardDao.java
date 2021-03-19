package com.board.icia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.board.icia.bean.Board;
import com.board.icia.bean.Member;
import com.board.icia.bean.Reply;
import com.board.icia.entity.BoardFile;

public interface IBoardDao {

	List<Board> getBoardList(Integer pageNum);

	@Select("SELECT COUNT(*) FROM BLIST")
	int getBoardCount();

	Board getContents(Integer bNum);

	List<Reply> getReplyList(Integer bNum);
	
	boolean replyInsert(Reply r);

	boolean boardWrite(Board board);

	boolean fileInsert(Map<String, String> fMap);
	
	//@Select("SELECT * FROM BF WHERE BF_BNUM=#{bNum}")
	List<BoardFile> getBoardFileList(Integer bNum);

	@Delete("DELETE FROM BF WHERE BF_BNUM=#{bNum}")
	boolean bfDelete(Integer bNum);

	@Delete("DELETE FROM REPLY WHERE R_BNUM=#{bNum}")
	boolean replyDelete(Integer bNum);

	@Delete("DELETE FROM BOARD WHERE B_NUM=#{bNum}")
	boolean boardDelete(Integer bNum);

	List<Member> myBatisTest(@Param("cName") String cName, @Param("point") Integer search);

	List<Map<String, Object>> myBatisTestMap(Map<String, Object> hMap);
	
}
