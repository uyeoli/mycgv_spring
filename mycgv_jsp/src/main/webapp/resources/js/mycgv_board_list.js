$(document).ready(function(){

	initAjax(1);
	
	function initAjax(page) {
		$.ajax({
			url : "board_list_json_data.do?page="+page,
			success : function(result){
				//alert(result); //문자
				let jdata = JSON.parse(result); // 문자데이터를 json데이터로 변환
				//alert(jdata.jlist[0].rno); //JSON 객체
				
				//dhtml을 활용하여 테이블로 출력
				let code = "<table class='board_list'>";
				code += "<tr><td colspan= '5'>";
				code += "<a href='board_write.do'>";
				code += "<button type='button' class='btn_style2'>글쓰기</button>";
				code += "</a></td></tr>";
				code += "<tr><th>번호</th><th>제목</th><th>조회수</th><th>작성자</th><th>작성일자</th></tr>";
				for(obj of jdata.jlist) {
					code += "<tr>";
					code += "<td>" + obj.rno + "</td>";
					code += "<td>" + obj.btitle + "</td>";
					code += "<td>" + obj.bhits + "</td>";
					code += "<td>" + obj.id + "</td>";
					code += "<td>" + obj.bdate + "</td>";
					code += "</tr>";
				}
				code += "<tr>";
				code += "<td colspan='5'><div id='ampaginationsm'></div></td>";
				code += "</tr>";
				code += "</table>";
				
				//code를 출력
				$("table.board_list").remove();
				$("h1").after(code);
							
				//페이징 처리 함수 호출		
				pager(jdata.totals, jdata.maxSize, jdata.pageSize, jdata.page);
				
				//페이징 번호 클릭 이벤트 처리
				jQuery('#ampaginationsm').on('am.pagination.change',function(e){
			   		jQuery('.showlabelsm').text('The selected page no: '+e.page);
	           		//$(location).attr('href', "http://localhost:9000/mycgv_jsp/board_list_json.do?page="+e.page);         
			   		initAjax(e.page);
	  			});
	  			
			}//success
		
		});//ajax
	}//initAjax
	
	
	/* 페이징 처리 함수 */
	function pager(totals, maxSize, pageSize, page){
		//alert(totals + "," + maxSize + "," + pageSize + "," + page);
		var pager = jQuery('#ampaginationsm').pagination({
		
		    maxSize: maxSize,	    		// max page size
		    totals: totals,	// total pages	
		    page: page,		// initial page		
		    pageSize: pageSize,			// max number items per page
		
		    // custom labels		
		    lastText: '&raquo;&raquo;', 		
		    firstText: '&laquo;&laquo;',		
		    prevText: '&laquo;',		
		    nextText: '&raquo;',
				     
		    btnSize:'sm'	// 'sm'  or 'lg'		
		});

		
	}
	


}); //ready