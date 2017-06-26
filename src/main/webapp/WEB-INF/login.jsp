<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="styles/login.css" />
<script type="text/javascript" src="js/jquery-1.4.3.js"></script>
<script type="text/javascript">
	$(function() {
		$('#login').click(login);
		$('#regist_button').click(regist);

		get('sig_in').onclick = function() {
			get('dl').className = 'log log_out';
			get('zc').className = 'sig sig_in';
		}
		get('back').onclick = function() {
			get('zc').className = 'sig sig_out';
			get('dl').className = 'log log_in';
		}
		var t = setTimeout("get('zc').style.visibility='visible'", 800);
		get('final_password').onblur = function() {
			var npassword = get('regist_password').value;
			var fpassword = get('final_password').value;
			if (npassword != fpassword) {
				get('warning_3').style.display = 'block';
			}
		}
		get('regist_password').onblur = function() {
			var npassword = get('regist_password').value.length;
			if (npassword<6&&npassword>0) {
				get('warning_2').style.display = 'block';
			}
		}
		get('regist_username').onblur = function() {
			var name = this.value;
			$.get('checkUsername.do', 'username=' + name, function(b) {
				if (!b || name == '') {
					get('warning_1').style.display = 'block';
				}
			});
		}
		get('regist_password').onfocus = function() {
			get('warning_2').style.display = 'none';
		}
		get('final_password').onfocus = function() {
			get('warning_3').style.display = 'none';
		}
		get('count').onfocus = function() {
			get('warning_name').style.display = 'none';
		}
		get('password').onfocus = function() {
			get('warning_pwd').style.display = 'none';
		}
		get('regist_username').onfocus = function() {
			get('warning_1').style.display = 'none';
		}
	});

	function login() {
		var name = $('#count').val();
		var pwd = $('#password').val();
		$.ajax({
			url : 'login.do',
			type : 'post',
			data : 'name=' + name + '&password=' + pwd,
			dataType : 'text',
			success : function(txt) {
				console.log(txt);
				if (txt == 'error_name') {
					$('#warning_name').css('display', 'block');
				} else if (txt == 'error_pwd') {
					$('#warning_pwd').css('display', 'block');
				} else if (txt == 'success') {
					location.href = 'toEdit.do';
				}
			},
			error : function() {
				alert('系统繁忙,请稍后重试!');
			}
		});
	}

	function regist() {
		var name = $('#regist_username').val();
		var nick = $('#nickname').val();
		var pwd = $('#regist_password').val();
		var fpwd = $('#final_password').val();

		if ($('#warning_1').css('display') != 'none'
				|| $('#warning_2').css('display') != 'none'
				|| $('#warning_3').css('display') != 'none' || name == ''
				|| nick == '' || pwd == '' || fpwd == '') {
			return;
		}

		var data = {
			'name' : name,
			'nick' : nick,
			'password' : pwd
		};
		$.ajax({
			url : 'regist.do',
			type : 'post',
			data : data,
			dataType : 'text',
			success : function(txt) {
				if (txt == 'success') {
					alert("注册成功!");
				} else {
					alert("注册失败!");
				}
			},
			error : function() {
				alert('系统繁忙,请稍后重试!');
			}
		});
	}

	function get(e) {
		return document.getElementById(e);
	}
</script>
</head>
<body>
	<div class="global">
		<div class="log log_in" tabindex='-1' id='dl'>
			<dl>
				<dt>
					<div class='header'>
						<h3>登&nbsp;录</h3>
					</div>
				</dt>
				<dt></dt>
				<dt>
					<div class='letter'>
						用户名:&nbsp;<input type="text" name="name" id="count" tabindex='1' />
						<div class='warning' id='warning_name'>
							<span>* 用户名错误</span>
						</div>
					</div>
				</dt>
				<dt>
					<div class='letter'>
						密&nbsp;&nbsp;&nbsp;码:&nbsp;<input type="password" name="password"
							id="password" tabindex='2' />
						<div class='warning' id='warning_pwd'>
							<span>* 密码错误</span>
						</div>
					</div>
				</dt>
				<dt>
					<div>
						<input type="button" name="" id="login" value='&nbsp登&nbsp录&nbsp'
							tabindex='3' /> <input type="button" name="" id="sig_in"
							value='&nbsp注&nbsp册&nbsp' tabindex='4' />
					</div>
				</dt>
			</dl>
		</div>
		<div class="sig sig_out" tabindex='-1' id='zc'
			style='visibility: hidden;'>
			<dl>
				<dt>
					<div class='header'>
						<h3>注&nbsp;册</h3>
					</div>
				</dt>
				<dt></dt>
				<dt>
					<div class='letter'>
						用户名:&nbsp;<input type="text" name="name" id="regist_username"
							tabindex='5' />
						<div class='warning' id='warning_1'>
							<span>该用户名不可用</span>
						</div>
					</div>
				</dt>
				<dt>
					<div class='letter'>
						昵&nbsp;&nbsp;&nbsp;称:&nbsp;<input type="text" name="nick"
							id="nickname" tabindex='6' />
					</div>
				</dt>
				<dt>
					<div class='letter'>
						密&nbsp;&nbsp;&nbsp;码:&nbsp;<input type="password" name="password"
							id="regist_password" tabindex='7' />
						<div class='warning' id='warning_2'>
							<span>密码长度过短</span>
						</div>
					</div>
				</dt>
				<dt>
					<div class='password'>
						&nbsp;&nbsp;&nbsp;确认密码:&nbsp;<input type="password"
							name="repassword" id="final_password" tabindex='8' />
						<div class='warning' id='warning_3'>
							<span>密码输入不一致</span>
						</div>
					</div>
				</dt>
				<dt>
					<div>
						<input type="button" name="" id="regist_button"
							value='&nbsp注&nbsp册&nbsp' tabindex='9' /> <input type="button"
							name="" id="back" value='&nbsp返&nbsp回&nbsp' tabindex='10' />
					</div>
				</dt>
			</dl>
		</div>
	</div>
</body>
</html>