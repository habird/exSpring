package com.board.icia.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.icia.bean.Board;
import com.board.icia.bean.Member;
import com.board.icia.bean.Reply;
import com.board.icia.dao.IBoardDao;
import com.board.icia.entity.BoardFile;
import com.board.icia.exception.CommonException;
import com.board.icia.exception.DbException;
import com.board.icia.exception.NoReplyException;
import com.board.icia.userClass.FileManager;
import com.board.icia.userClass.Paging;

@Service //서비스 혹은 컴포넌트! 가독성을 위해 서비스라고 쓴다.
public class BoardMM {
	@Autowired //주입받기 위해서 와이어드!
	private IBoardDao bDao; //스프링 매퍼랑 결합해서 구현체 주입해줄 것임.
	@Autowired
	private FileManager fm;

	ModelAndView mav;
	public ModelAndView getBoardList(Integer pageNum) {
		mav=new ModelAndView();
		String view=null;
		List<Board> bList = null;
		
		pageNum = (pageNum==null)? 1 : pageNum;
		
		if(pageNum<=0) {
			throw new CommonException("잘못된 페이지번호 입니다."); //개발자가 직접 만들어야함!
		}
		//로그인해야 리스트를 볼 수 있다면 아래를 이용하면 되지만
		//if(session.getAttribute("id")!=null) {	}
		
		//로그인 없이도 볼수 있다고 가정하고 아래처럼 진행
		bList=bDao.getBoardList(pageNum);
		//System.out.println("bList = "+bList.size()); //잘담겨있는지 확인!
		if(bList!=null && bList.size()!=0) {
			mav.addObject("bList", bList); //jstl
			//mav.addObject("bList2", new Gson().toJson(bList)); //js
			//makeHtml
			mav.addObject("paging", getPaging(pageNum));
			view="boardList"; //jsp
		}else {
			view="home"; //jsp
		}
		mav.setViewName(view);
		
		return mav;
	}
	
	private String getPaging(Integer pageNum) {
		int maxNum = bDao.getBoardCount(); //전체 글의 갯수 읽어오기
		int listCount = 10;
		int pageCount = 2;
		String boardName = "boardlist"; //url
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
		
		return paging.makeHtmlPaging(); //문자열 <이전><a href=3 4><다움>
	}

	public ModelAndView getContents(Integer bNum, HttpSession session) {
		mav = new ModelAndView();
		String view = null;
		//정책 : 로그인후 상세내역을 볼수 있다면 로그인여부 체크. --> if(session.get..)
		Board board = bDao.getContents(bNum);
		mav.addObject("board", board);
		//System.out.println("여기야여기! board = " + board);
		
		//map.put("board",board);
		//map.put("rList",rList); //map에 리스트들을 넣고 fromjson으로 변환해서 ajax로 반환해줘두 됌
		//mav.addObject("json", new Gson().toJson(map)); // ${json} 파싱하면 자바스크립트로 깔수있음..
		
		//댓글 리스트도 같이 가져오기!
		List<Reply> rList = bDao.getReplyList(bNum); //파라미터 가지고 가야 그 글의 댓글을 가져올수 있어
		mav.addObject("rList", rList);
		//파일 리스트 resultMap, collection 사용하지 않을 때
			//List<BoardFile> bfList = bDao.getBoardFileList(bNum);
			//mav.addObject("bfList", bfList);
		//본인글일 때 삭제 버튼 생성
		if(session.getAttribute("id").toString().equals(board.getB_id())) {
			mav.addObject("delBtn", makeHtmlDelBtn(board.getB_num())); //삭제하려면 글번호가 필요함
		}
		//System.out.println("왜 오류ㅇ ㅑ~!" + bfList);
		view = "ajaxContents"; //jsp
		mav.setViewName(view);
		return mav;
	}
	
	private String makeHtmlDelBtn(int bNum) {
		StringBuilder sb = new StringBuilder();
		sb.append("<form action='boarddelete' method='post'>");
		sb.append("<input type='hidden' name='bNum' value='"+bNum+"'>");
		sb.append("<button>삭제</button>");
		sb.append("</form>");
		return sb.toString();
	}

	//Gson 또는 Jackson
	public List<Reply> replyInsert(Reply r) {
		List<Reply> rList = null;
		if(!bDao.replyInsert(r)) { //댓글 등록 성공
			rList = bDao.getReplyList(r.getR_bnum());
		}else {
			rList=null;
			//@ControllerAdvice 타기 위해서
			throw new NoReplyException("⚠댓글을 가져올 수 없습니다⚠");
		}
		return rList; //자바객체 --> json변환
		//스프링의 메세지 컨버터 : 서버에서 클라이언트로 데이터(객체->json)를 변환해서 보내준다.
	}

	//map과 Jackson을 이용
/*	public Map<String, List<Reply>> replyInsert(Reply r) {
		Map<String, List<Reply>> rMap = null;
		if(bDao.replyInsert(r)) { //댓글 등록 성공
			rMap = new HashMap<>();
			rMap.put("rList", bDao.getReplyList(r.getR_bnum()));
		}
		return rMap; //자바객체 --> json변환
		//스프링의 메세지 컨버터 : 서버에서 클라이언트로 데이터(객체->json)를 변환해서 보내준다.
	}
*/
	public ModelAndView boardWritd(MultipartHttpServletRequest multi) {
		//1개의 file태그를 이용해서 여러파일을 첨부할 때
	/*	List<MultipartFile> files = multi.getFiles("files");
		System.out.println("파일 개수 = " + files.size());
		for(int i=0; i<files.size();i++) {
			System.out.println("file name = "+ files.get(i).getOriginalFilename());
		}
	*/	
		mav = new ModelAndView();
		String view = null;
		String title = multi.getParameter("b_title"); //빈으로 가져올수도 있다!
		String contents = multi.getParameter("b_contents");
		int check = Integer.parseInt(multi.getParameter("fileCheck"));
		String id = multi.getSession().getAttribute("id").toString();
		System.out.println("check = "+check); //첨부 : 1
		System.out.println("id = " +id);
		System.out.println("title = " +title);
		System.out.println("contents = " +contents);
		
		String fullPath = multi.getParameter("");
		
		Board board = new Board();
		board.setB_title(title).setB_contents(contents).setB_id(id); //체이닝!!!!!!!!!!
		
		boolean b = bDao.boardWrite(board); //글쓰기 성공:true , 실패:false
		System.out.println("bnum = "+board.getB_num());
		
		if(b){
			view = "redirect:/boardlist"; //true
		}else {
			view = "redirect:/writefrm"; //false //주소 떄문에
			check=0;
		}
		
		if(check == 1) { //첨부가 되었다면 1
			if(fm.fileUp(multi, board.getB_num()));
				System.out.println("upload 성공했다~!");
				view="redirect:/boardlist";
		//	fm.download(fullPath, oriFileName, resp);
		}else {
			view="redirect:/writefrm";
		}
		mav.setViewName(view);
		return mav;
	}

	public void download(Map<String, Object> params) throws Exception {
		String origFileName = (String)params.get("origFileName");
		String sysFileName = (String)params.get("sysFileName");
		String root = (String)params.get("root"); //(String):캐스팅
		String fullPath = root + "/resources/upload/"+sysFileName;
		
		//Map있으므로 생략
		//String origFileName = bDao.getOrigFileName(sysFileName);
		
		HttpServletResponse resp = (HttpServletResponse)params.get("response");
		fm.download(fullPath, origFileName, resp);
	}
	//FK : on delete cascade 하면 되지만 트랜잭션 해보고 싶으니까~~~!
	//파일삭제(있다면) --> 댓글삭제(있다면) --> 원글삭제
	//그런데, 삭제 중에 삭제가 안된 부분이 있을수도 있으면 ?? 트랜잭션이 롤백 해줌
	//다~~ 삭제 되었다면 ?? 트랜잭션이 커밋 해줌
	@Transactional
	public ModelAndView boardDelete(Integer bNum, RedirectAttributes attr) {
		mav=new ModelAndView();
		String view = null;
		//첨부파일 여부
		List<BoardFile> bfList = bDao.getBoardFileList(bNum); //기존 dao사용
		System.out.println("bfList = "+bfList.size());
		boolean f,r,b;
		f=r=b=true;
		//첨부파일 삭제
		if(bfList.size() != 0) { //0이 아니면 파일있음
			f=bDao.bfDelete(bNum); //삭제 실패 : false or 0
		}
		System.out.println("f = "+f);
		//댓글 삭제
		List<Reply> rList = bDao.getReplyList(bNum);
		if(rList.size()!=0) {
			r = bDao.replyDelete(bNum);
		}
		System.out.println("r = "+r);
		//게시글 삭제 (이미 글번호가 있으니까 가져올필요없음)
		b = bDao.boardDelete(bNum);
		System.out.println("b = "+b);
		
		//RedirectAttributes는 Redirect전 session영역에 저장한 뒤 redirect후 즉시 삭제한다.
		//삭제직전에 request객체에 저장한다.
		//addAttribute : session영역에 저장후 --> request객체 저장 --> session영역삭제
		//addFlashAttribute : session영역에 저장후 1번 사용하면 삭제함
		if(f&&r&&b) {
			if(f) {
				fm.delete(bfList); //웹서버에서 파일 삭제
			}
			attr.addFlashAttribute("bNum", bNum); //session에 저장하여 1번 사용후 삭제가됨
			//attr.addAttribute("bNum", bNum); //request객체에 저장한다.
			System.out.println("삭제 트랜잭션 성공");
		}else {
			System.out.println("삭제 트랜잭션 실패");
			throw new DbException(); //예외 발생 --> Rollback
		}
		mav.setViewName("redirect:/boardlist"); //기존객체(영역)삭제후 --> 새로운 request생성
		//System.out.println("result =" + bDao.getContents(bNum));
		
		return mav;
	}
	//controller method

	public ModelAndView myBatisTest(String cName, Integer search) {
		mav = new ModelAndView();
		List<Member> mList = bDao.myBatisTest(cName,search);
		mav.addObject("mList", mList);
		mav.setViewName("test"); //jsp
		return mav;
	}

	public ModelAndView myBatisTestMap(Map<String, Object> hMap) {
		mav = new ModelAndView();
		List<Map<String,Object>> mList = bDao.myBatisTestMap(hMap);
		mav.addObject("mList", mList);
		mav.setViewName("test"); //jsp
		return mav;
	}

}
