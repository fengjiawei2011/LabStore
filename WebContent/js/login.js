$(document).ready(function() {
	//var $login_form = $('#login_form');

	$('#submit_1').click(function(e) {

		e.preventDefault();

		//var jsObj = $login_form.serializeObject();
		var ajaxObj = {
			type : "POST",
			url : "http://localhost:8080/LabStore/myrest/userservice/signup",
			dataType : "json",
			data : JSON.stringify($('#login_form').serializeObject()),
			contentType:"application/json",

			success : function(data) {
				console.log("success");
				console.log(data);
			},
			error : function() {
				console.log("error");
			}
		};
		$.ajax(ajaxObj);
	});

});