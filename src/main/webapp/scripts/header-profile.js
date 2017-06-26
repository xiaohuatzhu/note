$(function() {

	$('div.header-profile').load('header-profile.html', function() {
		showUserName();
	});
	

});

function showUserName() {
	var name = getCookie('nickName');
	var userName = getCookie('userName');
	if (!name || name == 'null' || !name.trim().length) {
		if (!userName || userName == 'null' || !userName.trim().length) {
			name = '请登录';
		} else {
			name = userName;
		}
	}

	$('span.profile-username').html(name);
}

function logout() {
	var userId = getCookie('userId');
	if (!userId || userId == 'null' || !userId.trim().length) {
		return;
	}
	var url = 'user/logout.do';
	var data = {
		userId : userId
	};
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == 0) {
			logoutAction();
		} else {
			alert(result.message);
		}
	});
}

function logoutAction() {
	delCookie('userId');
	delCookie('nickName');
	delCookie('userName');
	location.href = 'log_in.html';
}


