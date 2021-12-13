$(document).ready(function() {
	$("#codeDetailListBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/codedetails",
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

	$("#codeDetailReadBtn").on("click", function() {
		$.ajax({
			type : "GET",
			url : "/codedetails/" + $("#codeClassCode").val() + "/" + $("#codeValue").val(),
			contentType : "application/json; charset=UTF-8",
			headers : {
				"Authorization" : "Bearer " + ACCESS_TOKEN
			},
			success : function(data) {
				console.log(data);
				
				alert(JSON.stringify(data));
				
				$("#codeClassCode").val(data.classCode);
				$("#codeValue").val(data.codeValue);
				$("#codeName").val(data.codeName);
			},
			error : function(xhr, status, error) {
				alert("code:" + xhr.status + "\n"
					+ "message:" + xhr.responseText + "\n"
					+ "error:" + error);
			}
		});
	});

	$("#codeDetailRegisterBtn").on("click", function() {
		var codeClassObject = {
			classCode : $("#codeClassCode").val(),
			codeValue : $("#codeValue").val(),
			codeName : $("#codeName").val()
		};
		
		alert(JSON.stringify(codeClassObject));

		$.ajax({
			type : "POST",
			url : "/codedetails",
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

	$("#codeDetailDeleteBtn").on("click", function() {
		$.ajax({
			type : "DELETE",
			url : "/codedetails/" + $("#codeClassCode").val() + "/" + $("#codeValue").val(),
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

	$("#codeDetailModifyBtn").on("click", function() {
		var classCodeVal = $("#codeClassCode").val();
		var codeValueVal = $("#codeValue").val();
		
		var codeClassObject = {
			classCode : classCodeVal,
			codeValue : codeValueVal,
			codeName : $("#codeName").val()
		};

		$.ajax({
			type : "PUT",
			url : "/codedetails/" + classCodeVal + "/" + codeValueVal,
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
	
	$("#codeDetailResetBtn").on("click", function() {
		$("#codeClassCode").val("");
		$("#codeValue").val("");
		$("#codeName").val("");
	});
	
	$.getJSON("/codes/codeClass",function(list){
		$(list).each(function(){
			var str = "<option value='" + this.value + "'>" + this.label + "</option>";
			$("#codeClassCode").append(str);
		});
	});
});
