//$(document).ready(function() {
//	
//	
//	
//	$('#submit_2').click(function(e) {
//
//		e.preventDefault();
//
//		$.ajax({
//			type : "POST",
//			url : "http://localhost:8080/LabStore/myrest/shoppingcart/add",
//			dataType : "json",
//			data : JSON.stringify($('#login_form').serializeObject()),
//			contentType : "application/json",
//
//			success : function(data) {
//				console.log("success");
//				console.log(data);
//			},
//			error : function() {
//				console.log("error");
//			}
//		});
//	});
//});

var add = function(id){
	console.log(id);
	var price = $("#"+id+"_price").text();
	var name = $("#"+id+"_name").text();
	var des = $("#"+id+"_des").text();
	var quantity = $("#"+id+"_quantity").text();
	var user_id = $('#uid').text();
	var owner_id = $('#'+id+"_oid").text();
	var catalog_name = $('#'+id+"_catalg").text();
	alert(user_id);
	var toJson =  {
			"product_id": id,
			"product_price": price, 
			"product_name":name,
			"product_description":des,
			"product_quantity":quantity,
			"user_id":user_id,
			"owner_id":owner_id,
			"catalog_name": catalog_name
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