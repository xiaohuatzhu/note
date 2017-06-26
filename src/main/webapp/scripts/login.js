/* scripts/login.js 编码为 utf-8*/
$(function() {
	// console.log("login.js"); // 测试



	$('#login').click(loginAction);
	$('#count').blur(checkName).focus();
	$('#password').blur(checkPassword);

	// 注册功能
	$('#regist_button').click(registAction);
	$('#regist_username').blur(checkRegistName);
	$('#regist_password').blur(checkRegistPassword);
	$('#final_password').blur(checkFinalPassword);

	$('#regist_username').focus(nextHide);
	$('#regist_password').focus(nextHide);
	$('#final_password').focus(nextHide);

});

function nextHide() {
	$(this).next().hide();
}

var nameRule = /^\w{4,10}$/;
var pwdRule = /^\w{6,12}$/;

function checkRegistName() {
	var name = $('#regist_username').val().trim();
	if (nameRule.test(name)) {
		$('#regist_username').next().hide();
		return true;
	}
	$('#regist_username').next().show().find('span').html('4~10个字符');
	return false;
}
function checkRegistPassword() {
	var pwd = $('#regist_password').val();
	if (pwdRule.test(pwd)) {
		$('#regist_password').next().hide();
		return true;
	}
	$('#regist_password').next().show().find('span').html('6~12个字符');
	return false;
}
function checkFinalPassword() {
	var rpwd = $('#regist_password').val();
	var fpwd = $('#final_password').val();
	if (rpwd && rpwd == fpwd) {
		$('#final_password').next().hide();
		return true;
	}
	$('#final_password').next().show().find('span').html('密码不一致');
	return false;
}

function registAction() {
	console.log('registAction()');
	// 检验界面参数
	var n = checkRegistName() + checkRegistPassword() + checkFinalPassword();
	if (n != 3) {
		return;
	}
	// 发起ajax请求
	// 得到响应后更新界面
	var name = $('#regist_username').val().trim();
	var nick = $('#nickname').val().trim();
	var password = $('#regist_password').val();
	var confirm = $('#final_password').val();
	var url = 'user/regist.do';
	var data = {
		'name' : name,
		'nick' : nick,
		'password' : password,
		'confirm' : confirm
	};
	$.post(url, data, function(result) {
		console.log(result);
		// 注册成功,退回登录页面,并自动添加用户名,但是不加密码
		if (result.state == 0) {
			// 清空注册表单
			$('#regist_username').val('');
			$('#nickname').val('');
			$('#regist_password').val('');
			$('#final_password').val('');

			$('#back').click();
			$('#count').val(result.data.name);
			$('#password').focus();
		} else if (result.state == 4) {
			$('#regist_username').next().show().find('span').html(
					result.message);
		} else if (result.state == 3) {
			$('#regist_password').next().show().find('span').html(
					result.message);
		}
	});
}

function checkName() {
	var name = $('#count').val();
	if (!nameRule.test(name)) {
		$('#count').next().html('4~10个字符');
		return false;
	}
	$('#count').next().empty();
	return true;
}

function checkPassword() {
	var password = $('#password').val();
	if (!pwdRule.test(password)) {
		$('#password').next().html('6~12个字符');
		return false;
	}
	$('#password').next().empty();
	return true;
}

function loginAction() {
	// console.log("loginAction()"); // 测试
	// 获取用户输入的用户名和密码
	var name = $('#count').val();
	var password = $('#password').val();

	// if (!checkName() || !checkPassword()) { return; }
	// var success = checkName() * checkPassword();
	var n = checkName() + checkPassword();
	if (n != 2) {
		return;
	}

	// data 对象中的属性名要与服务器控制器的参数名一致!!!
	var data = {
		'name' : name,
		'password' : password
	};
	$.ajax({
		url : 'user/login.do',
		type : 'post',
		data : data,
		dataType : 'json',
		success : function(result) {
			console.log(result);
			if (result.state == 0) {
				var user = result.data;
				console.log(user);
				addCookie('userId', user.id);
				addCookie('nickName',user.nick);
				addCookie('userName',user.name);
				location.href = 'edit.html';
			} else {
				var msg = result.message;
				if (result.state == 2) {
					$('#count').next().html(msg);
				} else if (result.state == 3) {
					$('#password').next().html(msg);
				} else {
					alert(msg);
				}
			}
		},
		error : function(e) {
			alert('通信失败!');
		}
	});
}

