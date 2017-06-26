<%@page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>活动列表</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="x-pjax-version" content="v173">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="ico/favico-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="ico/favico-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="ico/favico-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="ico/favico-57-precomposed.png">
        <link rel="shortcut icon" href="ico/favico.png">
        <link rel="shortcut icon" href="ico/favico.ico">
        <link rel="stylesheet" href="styles/icon.css"/>
        <link rel="stylesheet" href="styles/main.css"/>
        <link rel="stylesheet" href="styles/prettify.css"/>
    </head>
    <body class="animated fadeIn">
        <header class="header">
           <%@include file="header.jsp" %>
            <ul class="hidden-xs header-menu pull-right">
                <li>
                    <a href="toEdit.do" title="我的笔记">我的笔记</a>
                </li>
            </ul>
        </header>
		<div class="row" style='padding:0;'>
			<!-- 活动第三列 -->
			<div class="col-sm-12" id='action_part'>
				<aside class="side-right" id='side_right'>
					<div class="module" data-toggle="niceScroll">
						<div class="chat-contact">
							<div class="contact-body clear_margin">
								<h3 class="lead"><i class="fa fa-rss"></i> 近期活动</h3>
								<div class="row">
									<div class="col-sm-4" id='col_0'>
										
									</div>
									<div class="col-sm-4" id='col_1'>
										
									</div>
									<div class="col-sm-4" id='col_2'>
										
									</div>
								</div>
							</div>
						</div>
					</div>
				</aside>
			</div>
			<!-- 活动第三列 -->
		</div>
        <footer>
            <p>&copy; 2014 Stilearning</p>
        </footer>
        <script type="text/javascript" src="scripts/jquery.min.js"></script>
		<!-- Bootstrap框架JS -->
		<script type="text/javascript" src="scripts/bootstrap.min.js"></script>   
        <script type="text/javascript" src="scripts/theme-setup.js"></script>
		<!-- 全局变量&&AJAX&&回调函数&&COOKIE -->
		<script type="text/javascript" src="scripts/BaseValues.js"></script>
		<script type="text/javascript" src="scripts/activity/activity.js"></script>
		<script type="text/javascript" src="scripts/activity/activity_callback.js"></script>
		<script type="text/javascript" src="scripts/cookie_util.js"></script>
		<script type="text/javascript" src="scripts/utils.js"></script>
		<!-- 页面事件处理JS -->
		<script type="text/javascript" src='scripts/local.js'></script>
		<script type="text/javascript">
			function get_dom(e){
				return document.getElementById(e);
			}
			function set_height(){
				var pc_height=window.innerHeight;
				pc_height=pc_height-100;
				get_dom('side_right').style.height=pc_height+'px';
			}
			set_height();
			window.onresize=function(){
				set_height();
			}
			window.onload=function(){
				activity_list();
			}
		</script>
    </body>		
</html>