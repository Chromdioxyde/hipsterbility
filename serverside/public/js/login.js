$('a.btn').click(function(e) {

	e.preventDefault();

	var username_val = $('input#username').val();
	var password_val = $('input#password').val();

    console.log([username_val, password_val]);
	$.ajax({
		type: "POST",
		url: "/web-auth",
		data: { name: username_val, password: password_val },

		success: function(data) {

			// handle errors
		}
	});
});