package kr.co.jsp.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.jsp.board.model.BoardDAO_11_0518;
import kr.co.jsp.board.model.BoardVO_9_0518;

@WebServlet("*.board")
public class BoardController_13_0518 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardController_13_0518() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doRequest(request, response);
	
	
	}
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri=request.getRequestURI();
		String conPath=request.getContextPath();
		
									//  <= ~ < : 범위
		uri=uri.substring(conPath.length()+1, uri.lastIndexOf("."));
		
		System.out.println(uri);
		
		switch(uri){
			case "write":
				System.out.println("글쓰기 페이지로 이동 요청!");
				response.sendRedirect("board/14_board_write_0518.jsp");
				break;
			case "regist":
				System.out.println("글등록 요청이 들어옴!");
				String writer=request.getParameter("bWriter");
				String title=request.getParameter("bTitle");
				String content=request.getParameter("bContent");
				BoardDAO_11_0518.getInstance().regist(writer, title, content);
				
				//sendRedirect는 응답이 나가고 ,클라쪽에서 자동 재요청이 들어가는것 :: 클라가 list.board로 재요청보내라는 의미
 				//글 등록이 완료되면  , list.board라는 요청이 다시들어오게끔 설정한것 (컨트롤러가 요청을 받게끔하는것)
				//모든 데이터베이스의 요청은 컨트롤러 내에서 처리하게끔 하기 위해서 더이상은 jsp파일에 재요청하지않는다.
				//글 목록을 보기 위해선 데이터베이스 연결과정이 필요한데, 기존에는jspdptj <% %>로 연결과정이 있었지만 ,
				// 이제는 컨트롤러에서 그 행위를 대신한다.(왜냐며 jsp는 뷰 역할만 하게하기 위함)
				
				/*
				  왜 board_list.jsp로  바로 리다이렉트를 하면 안될까 ?
				  : board_list.jsp에는 데이터베이스로부터 전체 글 목록을 가져오는 로직이 없으니까요.
				  (jsp는 단순히 보여지는 역할만 할 뿐)
				  컨트롤러로 글 목록 요청이 다시 들어올 수 있게끔 
				  sendRedirect()를 사용해서 자동 목록 재요청이 들어오게 하는것 입니다. 
				 
				  redirect는 항상 새로운 요청을 하는것 
				 */
				response.sendRedirect("/MyWeb/list.board");
 				break;
			case "list":
				System.out.println("글 목록 요청이 들어옴!");
				List<BoardVO_9_0518> boardList=BoardDAO_11_0518.getInstance().listBoard();
				// response를 보내면 request객체가 사라지기때문에 ,request에 담는다
				request.setAttribute("bList", boardList);
				//sendRedirect를 하면 안되는 이유 
				//request객체에 list를 담아서 전달하려 하는데 , sendRedirect를 사용하면
				//응답이 나가면서 request 객체가 소멸해 버리기 때문. 
				
				//request객체를 다음 화면까지 운반하기 위한 forward(응답이 나가지않고 서버내에서 페이지 이동) 기능을 지원하는 객체
				//->RequestDispatcher :이 객체는 response와 request객체를  전달해 주는것 (응답이 나가는것이 아님)
				// 1번의 요청에 1번의 응답
				
				//forward 기능 									//보내고 싶은 목적지 
				RequestDispatcher dp=request.getRequestDispatcher("board/12_board_list_0518.jsp");
				dp.forward(request, response);
				break;
				
			case "content":
				System.out.println("글 상세보기 요청이 들어옴!");
				int bId=Integer.parseInt(request.getParameter("bId"));
				BoardVO_9_0518 vo=BoardDAO_11_0518.getInstance().contentBoard(bId);
				request.setAttribute("r_vo", vo);
				RequestDispatcher dp1=request.getRequestDispatcher("board/15_board_content_0518.jsp");
				dp1.forward(request, response);
				break;
				
				
				
		}
	}

}
