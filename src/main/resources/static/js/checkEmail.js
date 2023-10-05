$(document).ready(function() {
	$("#emailId").blur(function(){
		var email = $("#emailId").val();
		jQuery.ajax({
			url: 'checkEmail',
			data: { "email": email },
			type: "POST",
			success: function(data) {
				var result = JSON.parse(data);
				console.log(result);
				if (result != 0) {
					console.log("hello");
					$('#espan').html('<span>Email Already exit</span>');
					$('#sub').prop("disabled", true);
				} else {
					$('#espan').html("");
					$('#sub').prop("disabled", false);
				}
			}
		})
	});
});