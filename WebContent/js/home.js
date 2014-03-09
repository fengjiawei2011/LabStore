$(document).ready(
		function() {
			$('#sign_up').click(function() {
				// console.log("sign up!");
				// alert("aaa");
				window.location = "myrest/home/register";
			});

			$('#signup_submit').submit(
					function(event) {
						alert("form submit");
						var pw = $('#password').val();
						var pw_1 = $('#password_1').val();
						var lastname = $('#lastname').val();
						var firstname = $('#firstname').val();
						var email = $('#email').val;

						if (lastname == "" || lastname == null
								|| firstname == "" || firstname == null
								|| email == "" || email == null || pw == ""
								|| pw == null || pw_1 == "" || pw_1 == null) {
							$('#error_msg').text("Please don't let one of field be empty!").show();
							event.preventDefault();
							return false;
						}

						if (pw !== pw_1) {
							$('#password_validate').text("two password are not same!").show();
							event.preventDefault();
							return false;
						}

					});

		});




var add = function(id) {
	console.log(id);
	var price = $("#" + id + "_price").text();
	var name = $("#" + id + "_name").text();
	var des = $("#" + id + "_des").text();
	var quantity = $("#" + id + "_quantity").text();
	var user_id = $('#uid').text();
	var owner_id = $('#' + id + "_oid").text();
	var catalog_name = $('#' + id + "_catalg").text();
	alert(user_id);
	var toJson = {
		"product_id" : id,
		"product_price" : price,
		"product_name" : name,
		"product_description" : des,
		"product_quantity" : quantity,
		"user_id" : user_id,
		"owner_id" : owner_id,
		"catalog_name" : catalog_name
	};

	$.ajax({
		type : "POST",
		url : "http://localhost:8080/LabStore/myrest/sc/add",
		dataType : "json",
		data : JSON.stringify(toJson),
		contentType : "application/json",

		success : function(data) {
			console.log("success");
			console.log(data);
		},
		error : function() {
			console.log("error");
		}
	});
	return false;
};
