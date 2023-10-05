$(document).ready(function() {
	$("#conP").blur(function() {
		var newPassword = $("#newP").val();
		var conPassword = $("#conP").val();
		if($("#sub").is(":disabled"))
		{
			console.log("true");
		}else{
			if (conPassword != newPassword) {
			console.log("hello");
			//$('#espan').html('<span>Email Already exit</span>');
			$('#sub').css('background-color', 'red');
			$('#conP').css('border-color', 'red');
			$('#sub').prop("disabled", true);
		} else {
			//$('#espan').html("");
			$('#sub').prop("disabled", false);
		}
		}
	});
});