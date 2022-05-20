package kr.co.jsp.board.commons;

public class PageCreator_3_0520 {
	
	//페이지 번호와 한 페이지당 들어갈 게시물의 개수를 갖고 있느 ㄴ객체
	private PageVO_2_0520 paging;
	private int articleTotalCount; // 총 개시물의 개수 
	private int beginPage; //시작 페이지 번호
	private int endPage; //끝 페이지 번호
	private boolean prev; //이전 버튼 활성화 여부
	private boolean next; //다음 버튼 활성화 여부 
	
	//한 화면에 보여질 페이지 버튼 수 
	private final int displayBtn=10;

	
	//페이징 알고리즘을 수행할 메서드를  선언.
	private void calcDataOfPage() {
		//보정 전 끝 페이지 구하기
		endPage=(int)Math.ceil(paging.getPage()/(double)displayBtn)*displayBtn;
		
		//시작 페이지 번호 구하기
		beginPage=(endPage-displayBtn)+1;
		
		//현재 시작페이지가 1이라면 이전버튼 비 활성화
		 prev=(beginPage==1)?false:true;
		
		//마지막 페이지인지 여부 확인 후 다음 버튼 비활성 판다
		next=(articleTotalCount<=(endPage*paging.getCountPerPage()))?false:true;
		
		//다음 버튼 비활성화라면 끝 페이지 보정하기
		if(!next) endPage=(int)Math.ceil(articleTotalCount/(double)paging.getCountPerPage());
	}
	
	
	
	
	public PageVO_2_0520 getPaging() {
		return paging;
	}

	public void setPaging(PageVO_2_0520 paging) {
		this.paging = paging;
	}

	public int getArticleTotalCount() {
		return articleTotalCount;
	}

	public void setArticleTotalCount(int articleTotalCount) {
		this.articleTotalCount = articleTotalCount;
		//Service클래스가  이 메서드를 통해 총 게시물의 개수를 전달할 때 
		//즉시 알고리즘이 가동될 수 있도록 셋팅.
		calcDataOfPage();
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}




	@Override
	public String toString() {
		return "PageCreator_3_0520 [paging=" + paging + ", articleTotalCount=" + articleTotalCount + ", beginPage="
				+ beginPage + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next + ", displayBtn="
				+ displayBtn + "]";
	}
	
	
	
	
	

}
