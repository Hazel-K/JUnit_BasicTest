package kr.hkit.exam09.test;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.hkit.exam09.BoardVO;
import kr.hkit.exam09.Query;



class QueryTest {

	@BeforeAll
	static void start() {
		Query.createTable();
	}
	
	@AfterAll
	static void end() {
		Query.dropTable();
	}
	
	@BeforeEach
	void before() {
		Query.boardDelete(0);
		
		BoardVO bv1 = new BoardVO();
		bv1.setBtitle("타이틀1");
		bv1.setBcontent("내용1");
		Query.boardInsert(bv1);
		
		BoardVO bv2 = new BoardVO();
		bv2.setBtitle("타이틀2");
		bv2.setBcontent("내용2");
		Query.boardInsert(bv2);
	}
	
	@Test
	void testA() {
		List<BoardVO> list = Query.getAllBoardList();
		Assert.assertEquals(2, list.size());
		
		BoardVO vo1 = list.get(0);
		Assert.assertEquals("타이틀1", vo1.getBtitle());
		Assert.assertEquals("내용1", vo1.getBcontent());
		
		BoardVO vo2 = list.get(1);
		Assert.assertEquals("타이틀2", vo2.getBtitle());
		Assert.assertEquals("내용2", vo2.getBcontent());
	}
	
	@Test
	void testB() {
		List<BoardVO> list = Query.getAllBoardList();
		BoardVO vo = null;
		
		for(int i = 1; i <= list.size(); i++) {
			Query.boardDelete(i);
			vo = Query.getBoardDetail(i);
			Assert.assertEquals(0, vo.getBid());
			Assert.assertNull(vo.getBtitle());
			Assert.assertNull(vo.getBcontent());
			Assert.assertEquals(list.size() - i, Query.getAllBoardList().size());
		}
	}
	
	@Test
	void testC() {
		/*
		BoardVO vo3 = new BoardVO();
		vo3.setBid(1);
		vo3.setBtitle("가나");
		vo3.setBcontent("12");
		Query.boardUpdate(vo3);
		
		BoardVO vo4 = new BoardVO();
		vo4.setBid(2);
		vo4.setBtitle("다라");
		vo4.setBcontent("34");
		Query.boardUpdate(vo4);
		
		List<BoardVO> list = Query.getAllBoardList();
		BoardVO vo1 = list.get(0);
		BoardVO vo2 = list.get(1);
		
		Assert.assertEquals(vo1.getBid(), vo3.getBid());
		Assert.assertEquals(vo1.getBtitle(), vo3.getBtitle());
		Assert.assertEquals(vo1.getBcontent(), vo3.getBcontent());
		
		Assert.assertEquals(vo2.getBid(), vo4.getBid());
		Assert.assertEquals(vo2.getBtitle(), vo4.getBtitle());
		Assert.assertEquals(vo2.getBcontent(), vo4.getBcontent());
		*/
		
		String[] bTitle = {"가나", "다라"};
		String[] bContent = {"12", "34"};
		
		for(int i = 1; i <= Query.getAllBoardList().size(); i++) {
			BoardVO vo = new BoardVO();
			vo.setBid(i);
			vo.setBtitle(bTitle[i - 1]);
			vo.setBcontent(bContent[i - 1]);
			Query.boardUpdate(vo);
			
			BoardVO dbVO = Query.getBoardDetail(i);
			Assert.assertEquals(vo.getBid(), dbVO.getBid());
			Assert.assertEquals(vo.getBtitle(), dbVO.getBtitle());
			Assert.assertEquals(vo.getBcontent(), dbVO.getBcontent());
		}
	}

}