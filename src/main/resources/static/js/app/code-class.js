$(document).ready(function() {
	$("#codeClassListBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/codeclasses",
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
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

	$("#codeClassReadBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/codeclasses/" + $("#classCode").val(),
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function(data) {
				console.log(data);
				
				alert(JSON.stringify(data));
				
				$("#className").val(data.className);
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#codeClassRegisterBtn").on("click", function() {
		var codeClassObject = {
			classCode : $("#classCode").val(),
			className : $("#className").val()
		};
		
		alert(JSON.stringify(codeClassObject));

		$.ajax({
			type : "POST",
			url : "/codeclasses",
			data : JSON.stringify(codeClassObject),
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

	$("#codeClassDeleteBtn").on("click", function() {
		$.ajax({
			type : "DELETE",
			url : "/codeclasses/" + $("#classCode").val(),
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

	$("#codeClassModifyBtn").on("click", function() {
		var classCodeVal = $("#classCode").val();
		
		var codeClassObject = {
			classCode : classCodeVal,
			className : $("#className").val()
		};

		$.ajax({
			type : "PUT",
			url : "/codeclasses/" + classCodeVal,
			data : JSON.stringify(codeClassObject),
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
	
	$("#codeClassResetBtn").on("click", function() {
		$("#classCode").val("");
		$("#className").val("");
	});
});
