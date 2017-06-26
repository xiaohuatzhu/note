<%@page pageEncoding="utf-8"%>
<div class="header-brand">
	<a data-pjax=".content-body" href="edit.html"> <img
		class="brand-logo" src="images/dummy/8986f28e.stilearn-logo.png"
		alt="Stilearn Admin Sample Logo">
	</a>
</div>
<div class="header-profile">
	<div class="profile-nav">
		<span class="profile-username">${username}</span> <a class="dropdown-toggle"
			data-toggle="dropdown"> <span class="fa fa-angle-down"></span>
		</a>
		<ul class="dropdown-menu animated flipInX pull-right" role="menu">
			<li><a href="toChangePassword.do"><i class="fa fa-user"></i>
					修改密码</a></li>
			<li class="divider"></li>
			<li><a href="logout.do"><i class="fa fa-sign-out"></i>
					退出登录</a></li>
		</ul>
	</div>
</div>