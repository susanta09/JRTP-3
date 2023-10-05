$(document).ready(function() {
	$("#tPassword").blur(function(){
		var pass = $("#tPassword").val();
		console.log(pass);
		jQuery.ajax({
			url: 'checkPassword',
			data: { "pass":pass},
			type: "POST",
			success: function(data) {
				console.log(data);
				var result = JSON.parse(data);
				console.log(result);
				if (result != pass) {
					console.log("tpass");
					console.log(pass);
					$('#espan').html('<span>Temporary Password not exit</span>');
					$('#sub').prop("disabled", true);
				} else {
					$('#espan').html("");
					$('#sub').prop("disabled", false);
				}
			}
		})
	});
});