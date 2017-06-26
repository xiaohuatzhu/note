$(function() {
	$('#last_password').focus().focus(focusAction);
	$('#new_password').focus(focusAction);
	$('#final_password').focus(focusAction);

	$('#last_password').blur(checkLastPassowrdAction);
	$('#new_password').blur(checkNewPassowrdAction);
	$('#final_password').blur(checkFinalPassowrdAction);

	$('#changePassword').click(changePasswordAction);

	// 重写JS原生alert函数
	window.alertError = alertError;
	window.alertSuccess = alertSuccess;

	// 让弹出的error框消失
	$('#can').on('click', '.cancel,.close', closeDialog);

});

function focusAction() {
	$(this).next('div').hide();
}

function checkLastPassowrdAction() {
	var pwdReg = /^\w{6,12}$/;
	var pwd = $('#last_password').val();
	if (!pwdReg.test(pwd)) {
		$('#last_password').next('div').html('6~12个字符').show();
		return false;
	}
	return true;
}
function checkNewPassowrdAction() {
	var pwdReg = /^\w{6,12}$/;
	var pwd = $('#new_password').val();
	if (!pwdReg.test(pwd)) {
		$('#new_password').next('div').html('6~12个字符').show();
		return false;
	}
	return true;
}
function checkFinalPassowrdAction() {
	var pwd = $('#new_password').val();
	var pwd1 = $('#final_password').val();
	if (pwd != pwd1) {
		$('#final_password').next('div').html('密码不一致').show();
		return false;
	}
	return true;
}
function changePasswordAction() {
	var n = checkLastPassowrdAction() + checkNewPassowrdAction()
			+ checkFinalPassowrdAction();
	if (n != 3) {
		return;
	}

	var url = 'user/modifyPassword.do';
	var data = {
		name : getCookie('userName'),
		password : $('#last_password').val(),
		newPassword : $('#new_password').val(),
		confirm : $('#final_password').val()
	};
	$.post(url, data, function(result) {
		console.log(result);
		var state = result.state;
		if (state == 0) {
			clearCookieAction();
		} else {
			var msg = result.message;
			if (state == 3) {
				$('#last_password').next('div').html(msg).show();
			} else if (state == 5) {
				$('#new_password').next('div').html(msg).show();
			} else if (state == 6) {
				$('#final_password').next('div').html(msg).show();
			} else {
				alertError(msg);
			}
		}

	});
}

function clearCookieAction() {
	delCookie('userId');
	delCookie('userName');
	delCookie('nickName');
	alertSuccess('密码修改成功,请重新登录');
}

var intervalId;
function alertSuccess(msg) {
	var num = 3;
	$('#can').load('alert/alert_success.html', function() {
		$('#success_info').html(msg);
		$('.opacity_bg').show();
		$('.modal-footer .cancel').html('确 定(' + num-- + ')');
	});
	if (intervalId)
		clearInterval(intervalId);
	intervalId = setInterval(function() {
		$('.modal-footer .cancel').html('确 定(' + num-- + ')');
		console.log(num);
		if (num < 0) {
			closeDialog();
			clearInterval(intervalId);
		}
	}, 1000);
}

function alertError(e) {
	$('#can').load('./alert/alert_error.html', function() {
		$('#error_info').text(' ' + e);
		$('.opacity_bg').show();
	});
}

function closeDialog() {
	$('#can').empty();
	$('.opacity_bg').hide();
	if(intervalId){
		clearInterval(intervalId);
	}
	location.href = 'log_in.html';
}
