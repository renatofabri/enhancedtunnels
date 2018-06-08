function showNotification() {
	var notf = $("#notification-bar");
	notf.slideDown();
	setTimeout(function() {
		notf.slideUp();
	}, 3000);
}