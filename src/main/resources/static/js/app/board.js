$(document).ready(function() {
	$("#boardListBtn").on("click", function() {
		var page = $("#page");
		var sizePerPage = $("#sizePerPage");
		var searchType = $("#searchType");
		var keyword = $("#keyword");
		
		var pageVal = page.val();
		var sizePerPageVal = sizePerPage.val();
		var searchTypeVal = searchType.val();
		var keywordVal = keyword.val();
		
		var pageRequestObject = {
			page : pageVal,
			sizePerPage : sizePerPageVal,
			searchType : searchTypeVal,
			keyword : keywordVal
		};

		$.ajax({
			type : "GET",
			url : "/boards?page=" + pageVal + "&sizePerPage=" + sizePerPageVal + "&searchType=" + searchTypeVal + "&keyword=" + keywordVal,
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				console.log(data);
				
				alert(JSON.stringify(data));
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#boardReadBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/boards/" + $("#boardNo").val(),
			contentType : "application/json; charset=UTF-8",
			success : function(data) {
				console.log(data);
				
				alert(JSON.stringify(data));
				
				$("#boardTitle").val(data.title);
				$("#boardContent").val(data.content);
				$("#boardWriter").val(data.writer);
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#boardRegisterBtn").on("click", function() {
		var boardObject = {
			title : $("#boardTitle").val(),
			content : $("#boardContent").val(),
			writer : $("#boardWriter").val()
		};

		$.ajax({
			type : "POST",
			url : "/boards",
			data : JSON.stringify(boardObject),
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function() {
				alert("Created");
			},
			error : function(xhr, textStatus, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#boardDeleteBtn").on("click", function() {
		$.ajax({
			type : "DELETE",
			url : "/boards/" + $("#boardNo").val(),
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function() {
				alert("Deleted");
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#boardModifyBtn").on("click", function() {
		var boardNoVal = $("#boardNo").val();
		
		var boardObject = {
			boardNo : boardNoVal,
			title : $("#boardTitle").val(),
			content : $("#boardContent").val(),
			writer : $("#boardWriter").val()
		};

		$.ajax({
			type : "PUT",
			url : "/boards/" + boardNoVal,
			data : JSON.stringify(boardObject),
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function() {
				alert("Modified");
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});
	
	$("#boardResetBtn").on("click", function() {
		$("#boardNo").val("");
		$("#boardTitle").val("");
		$("#boardContent").val("");
		$("#boardWriter").val("");
		$("#page").val("");
		$("#sizePerPage").val("");
		$("#searchType").val("");
		$("#keyword").val("");
	});
	
	$.getJSON("/codes/searchType",function(list){
		$(list).each(function(){
			var str = "<option value='" + this.value + "'>" + this.label + "</option>";
			$("#searchType").append(str);
		});
	});
});
