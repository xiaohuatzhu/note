// scripts/note.js , 编码 utf-8

/** 笔记本每页的大小 */
var notebookPageSize = 5;
/** result为0,作业成功 */
var SUCCESS = 0;
/** result为1,作业成功 */
var ERROR = 1;
/** 笔记本列表中的li模板内容 */
var notebookTemplate = '<li class="online notebook">'
		+ '<a>'
		+ '<i class="fa fa-book" title="online" rel="tooltip-bottom"></i> #[name]'
		// + '<button style="display:none" type="button" class="btn btn-default
		// btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button>'
		+ '<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button>'
		+ '</a>' + '</li>';
/** 笔记列表中的li模板内容 */
var noteTemplate = '<li class="online note">'
		+ '<a>'
		+ '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>#[title]<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>'
		+ '</a>'
		+ '<div class="note_menu" tabindex="-1">'
		+ '<dl>'
		+ '<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random "></i></button></dt>'
		+ '<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'
		+ '<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'
		+ '<dt><button type="button" class="btn btn-default btn-xs btn_download" title="下载"><i class="fa fa-download"></i></button></dt>'
		+ '</dl>' + '</div>' + '</li>';
/** 回收站笔记的 li 内容 */
var delNoteTemplate = '<li class="disable note">'
		+ '<a >'
		+ '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i> #[title]'
		+ '<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button>'
		+ '<button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay"><i class="fa fa-reply"></i></button>'
		+ '</a>' + '</li>';
/** 批量删除笔记时,在笔记列表第一个元素位置显示的操作li */
var delNotesLi = '<li class="online ">'
		+ '&nbsp;&nbsp;&nbsp;&nbsp;<span class="delete_notes mt_select_all">全选</span>&nbsp;&nbsp;&nbsp;&nbsp;'
		+ '<span class="delete_notes mt_cancel_all">全不选</span>&nbsp;&nbsp;&nbsp;&nbsp;'
		+ '<span class="delete_notes mt_cancel_select">取消</span>&nbsp;&nbsp;&nbsp;&nbsp;'
		+ '<span class="delete_notes mt_delete_select">删除选中笔记</span>' + '</li>';

/** 用于分页more的模板 */
var moreTemplate = '<li class="online more">'
		+ '<a>'
		+ '<i class="fa fa-plus" title="online" rel="tooltip-bottom"></i> 加载更多...'
		+ '</a>' + '</li>';

/** 网页加载完,执行此方法 */
$(function() {
	// var userId = getCookie('userId');
	// console.log(userId);

	// 网页一加载就绑定笔记本当前页为0,以后每次加1
	$(document).data('notebookPage', 0);

	// 网页加载完成后,立即读取笔记本列表
	// loadNotebooks();
	loadPageNotebooks();
	// 点击笔记本的li加载该笔记本中的笔记列表,如果列表为空,则可以删除,显示删除按钮,如果不为空,则不能删除,不显示删除按钮
	$('#notebook-list ul').empty().on('click', '.notebook', loadNotes);
	// 点击笔记本的加载更多显示下一页
	$('#notebook-list ul').on('click', '.more', loadPageNotebooks);
	// 双击笔记本的li弹出重命名框
	$('#notebook-list ul').on('dblclick', '.notebook', alertRenameNotebook);
	// 点击笔记本上的删除按钮,弹出对话框
	$('#notebook-list ul').on('click', '.btn_delete', alertDeleteNotebook);
	// 点击导出笔记信息, 将下载所有笔记本及其中笔记的信息, Excel格式
	$('#notebook-list').on('click', '.download_notebooks', downloadNotebooks);

	// 点击笔记li读取笔记内容
	$('#note-list ul').empty().on('click', '.note', loadNoteContents);
	// 点击笔记下拉按钮,出现操作选项
	$('#note-list ul').on('click', '.btn_slide_down', noteButtonSlideDown);
	// note的操作事件
	$('#note-list ul').on('click', '.note_menu', noteMenuAction);
	// 点击批量删除笔记
	$('#note-list h3').on('click', '.delete_notes', deleteNotesList);
	// 点击全选
	$('body').on('click', '.mt_select_all', selectAll);
	// 点击全不选
	$('body').on('click', '.mt_cancel_all', cancelAll);
	// 点击取消批量删除
	$('body').on('click', '.mt_cancel_select', cancelSelect);
	// 点击全不选
	$('body').on('click', '.mt_delete_select', alertDeleteSelect);
	// 点击上传笔记
	$('#note-list h3').on('click', '.upload_note', alertUploadNote)
	// 点击下载笔记
	$('#note-list ul').on('click', '.btn_download', downloadNote)

	// 点击回收站
	$('#rollback_button').click(loadTrashNotes);
	// 点击回收站的笔记,显示预览内容
	$('#trash-list ul').on('click', '.note', loadPreviewsNote);
	// 删除回收站中的笔记
	$('#trash-list ul').on('click', '.btn_delete', alertDeleteTrashNote);
	// 恢复回收站中的笔记到指定笔记本,这个笔记本不一定是它之前存在的笔记本
	$('#trash-list ul').on('click', '.btn_replay', alertReplayTrashNote);

	// 保存更新note
	$('#save_note').click(updateNote);

	// 让弹出的error框消失
	$('#can').on('click', '.cancel,.close', closeDialog);

	// 点击添加笔记按钮
	$('#add_note').click(showAddNoteDialog);
	$('#can').on('click', '.create_note', addNote);

	// 点击添加笔记本按钮
	$('#add_notebook').click(showAddNotebookDialog);
	$('#can').on('click', '.create_notebook', addNotebook);

	// 重写alert事件
	window.alertSuccess = alertSuccess;

	// body的点击事件
	$('body').on('click', bodyClick);

	// 监听搜索框的change事件,如果不为空,则触发搜索
	$('#search_note').change(searchNote);

	// 开启心跳检测,保持与服务器的通信
	startHeartBeat();
});

/** 分页加载笔记本列表 */
function loadPageNotebooks() {
	var notebookPage = $(document).data('notebookPage');
	var userId = getCookie('userId');
	// 从服务器上获取数据
	var url = 'notebook/page.do';
	var data = {
		userId : userId,
		page : notebookPage
	};
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			var notebooks = result.data;
			showPageNotebooks(notebooks, notebookPage);
			$(document).data('notebookPage', notebookPage + 1);
		} else {
			alert(result.message);
		}
	});
}

/** 显示笔记本列表 */
function showPageNotebooks(notebooks, page) {
	var ul = $('#notebook-list ul');
	if (page == 0) {
		ul.empty(); // 第一页,清空列表
	} else {
		// 删除more
		ul.find('.more').remove();
	}
	for (var i = 0; i < notebooks.length; i++) {
		var notebook = notebooks[i];
		var li = $(notebookTemplate.replace('#[name]', notebook.name));
		li.data('notebookId', notebook.id).data('name', notebook.name);
		ul.append(li);
	}
	if (notebooks.length == notebookPageSize) {
		ul.append(moreTemplate);
	}
}

/** 从服务器下载全部笔记本及其中的笔记信息 */
function downloadNotebooks() {
	var userId = getCookie('userId');
	var url = 'notebook/downloadNotebooks.do?userId=' + userId;
	location.href = url;
}

/** 点击下载按钮,将笔记下载到本地 */
function downloadNote() {
	var noteId = $(this).closest('li').data('noteId');
	var userId = getCookie('userId');
	var data = {
		userId : userId,
		noteId : noteId
	}
	console.log(data);
	var url = 'note/downloadNote.do?userId=' + userId + '&noteId=' + noteId;
	location.href = url;
	$(this).closest('.note_menu').hide();
}

/** 点击上传笔记, 弹出会话框 */
function alertUploadNote() {
	$('#can').load('alert/alert_upload_note.html', function() {
		$('.opacity_bg').show();
		$('#can .sure').click(function() {
			$('#can form').ajaxSubmit({
				beforeSubmit : uploadNote(),
				dataType : 'json',
				success : function(result) {
					console.log(result);
					if (result.state == SUCCESS) {
						$('#notebook-list .checked').closest('li').click();
						alertSuccess("上传笔记成功!");
					} else {
						alert(result.message);
					}
				},
				error : function() {
					alert('通信失败!');
				}
			});
			return false;// 防止自动提交
		});
	});
}

/** 利用form表单上传笔记文件到服务器 */
function uploadNote() {
	var userId = getCookie('userId');
	var notebookId = $('#notebook-list .checked').closest('li').data(
			'notebookId');
	if (!notebookId) {
		alert('未选中笔记本!');
		return;
	}
	if (!userId) {
		alert('未登录!');
		return;
	}
	$('#can .note_data').val(userId + ',' + notebookId);
	console.log($('#can .note_data').val());
}

/** 弹出是否删除批量笔记, 点击确定删除 */
function alertDeleteSelect() {
	var lis = $(this).closest('ul').find('.checked').closest('li');
	var len = lis.length
	if (!len) {
		return;
	}
	$('#can').load('alert/alert_delete_note.html', function() {
		$('.opacity_bg').show();
		$('#can .sure').click(function() {
			deleteSelect(lis);
		});
	});
}

/** 批量删除 全选 */
function selectAll() {
	$(this).closest('li').siblings('li').find('a').addClass('checked');
}

/** 批量删除 全不选 */
function cancelAll() {
	$(this).closest('li').siblings('li').find('a').removeClass('checked');
}

/** 批量删除 取消 */
function cancelSelect() {
	$('#notebook-list .checked').closest('li').click();
}

/** 批量删除 删除选中 */
function deleteSelect(lis) {
	var noteIds = new Array();
	for (var i = 0; i < lis.length; i++) {
		noteIds.push(lis.eq(i).data('noteId'));
	}
	var url = 'note/deleteNotes.do';
	var data = {
		noteIds : noteIds
	};
	console.log(data);
	$.ajax({
		url : url,
		data : data,
		dataType : 'json',
		// 这里设置为true,
		// 如果traditional不设为true,则传送的数据名称为:noteIds[]:...,服务器无法接受noteIds[]
		// 如果traditional设为true,则传送的数据名称为:noteIds:...,服务器可以接受noteIds
		// 可以在network中查看格式
		traditional : true,
		success : function(result) {
			console.log(result);
			if (result.state == SUCCESS) {
				lis.remove();
			} else {
				alert(result.message);
			}
		},
		error : function() {
			alert("通信失败");
		}
	});
}

/** 点击删除笔记, 更改笔记列表 */
function deleteNotesList() {
	var notebookLi = $('#notebook-list .checked');
	if (!notebookLi.length) {
		alert("请先选择笔记本!")
		return;
	}
	$('#note-list .btn_slide_down').hide();
	var ul = $('#note-list ul');
	// 判断是否已经为批量删除状态
	var isDelete = ul.find('span').length == 0 ? false : true;
	if (isDelete) {
		return;
	}
	ul.prepend(delNotesLi);
	var lis = ul.find('.note');
	showContent($('#previews_note'));
	console.log(1);
	// ul.off('click', '.note');
	ul.on('click', '.note', function() {
		var a = $(this).find('a');
		if (a.hasClass('checked')) {
			a.removeClass('checked');
		} else {
			a.addClass('checked');
		}
	});

}

/** 利用ajax查询匹配的分享笔记 */
function searchNote() {
	if ($(this).val().trim() == '') {
		$('#note-list ul').empty();
		$('#notebook-list .checked').closest('li').click();
		showContent($('#edit_note'));
		return;
	}
	$('#note-list ul').empty().append($('<li>正在所搜...</li>'));
	showContent($('#previews_note'));
	url = 'share/search.do';
	data = {
		words : $('#search_note').val()
	};
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			var notes = result.data;
			if (notes.length == 0) {
				$('#note-list')
			}
		} else {
			alert(result.message);
		}
	});

}

/** 弹出 创建新笔记本 对话框 */
function showAddNotebookDialog() {
	$('#can').load('alert/alert_notebook.html', function() {
		$('.opacity_bg').show();
		$('#input_notebook').focus();
	});
}

/** 利用ajax 创建新笔记本 */
function addNotebook() {
	var userId = getCookie('userId');
	if (!userId) {
		alert('未登录');
		return;
	}
	var name = $('#input_notebook').val();
	if (!name.trim().length) {
		alert('笔记本名称不能为空');
		return;
	}
	var url = 'notebook/addNotebook.do';
	var data = {
		userId : userId,
		name : name
	};
	$.getJSON(url, data, function(result) {
		var state = result.state;
		if (state == 0) {
			showNewNotebook(result.data);
		} else {
			alert(result.message);
		}
	});
}

/** 显示新创建的笔记本,放在首位并被选中,清空笔记列表和编辑区域 */
function showNewNotebook(notebook) {
	$('#note-list ul').empty();
	clearEdit();
	var li = $(notebookTemplate.replace('#[name]', notebook.name)).data(
			'notebookId', notebook.id);
	$('#notebook-list ul').prepend(li).find('a').removeClass('checked');
	li.find('a').addClass('checked');
	closeDialog();
}

/** 删除笔记本的对话框,如果笔记本中有笔记,则提示先删除笔记,否则提示是否删除笔记本 */
function alertDeleteNotebook() {
	var li = $(this).closest('li');
	$('#can').load('alert/alert_delete_notebook.html', function() {
		$('.opacity_bg').show();
		$('#can .sure').click(function() {
			deleteNotebook(li);
		});
	});
}

/** 利用ajax 删除笔记本,如果笔记本中有笔记,则提示先删除笔记,否则提示是否删除笔记本 */
function deleteNotebook(li) {
	var url = 'notebook/deleteNotebook.do';
	var data = {
		userId : getCookie('userId'),
		notebookId : li.data('notebookId')
	}
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			li.remove();
			$('#note-list ul').empty();
		} else {
			alert(result.message);
		}
	});
}

/** 加载 重命名笔记本 对话框 */
function alertRenameNotebook() {
	var li = $('#notebook-list .checked').closest('li');
	$('#can').load('alert/alert_rename.html', function() {
		$('.opacity_bg').show();
		$('#input_notebook_rename').focus().val(li.data('name'));
		$('#can .sure').click(function() {
			renameNotebook(li);
		});
	});
}

/** 利用ajax重命名笔记本, 并将notebook list中的名称改掉 */
function renameNotebook(li) {
	var notebookId = li.data('notebookId');
	var oldName = li.data('name');
	var newName = $('#input_notebook_rename').val().trim();
	if (oldName == newName) {
		return;
	}
	var url = 'notebook/rename.do';
	var data = {
		userId : getCookie('userId'),
		notebookId : notebookId,
		name : newName
	};
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			li.remove();
			var newLi = $(notebookTemplate.replace('#[name]', newName)).data(
					'notebookId', notebookId).data('name', newName);
			newLi.find('a').addClass('checked')
			$('#notebook-list ul').prepend(newLi);
		} else {
			alert(result.message);
		}
	});

}

/** 利用ajax从服务器获取(get)数据,并显示笔记本列表 */
function loadNotebooks() {
	console.log('loadNotebooks()');
	var url = 'notebook/list.do';
	var data = {
		'userId' : getCookie('userId')
	};
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			var notebooks = result.data;
			// 全部笔记本区域 id='notebook-list'; 在其子孙元素中的唯一 ul 中显示列表
			showNotebooks(notebooks);
		} else {
			alert(result.message);
		}
	});
}

/** 全部笔记本区域 id='notebook-list'; 在其子孙元素中的唯一 ul 中显示列表 */
function showNotebooks(notebooks) {
	// 找到显示笔记本列表的区域,遍历notebooks数组,将为每个对象创建一个li元素,添加到ul元素中.
	var ul = $('#notebook-list ul').empty();
	for (var i = 0; i < notebooks.length; i++) {
		var notebook = notebooks[i];
		var li = $(notebookTemplate.replace('#[name]', notebook.name));
		li.data('notebookId', notebook.id).data('name', notebook.name);
		ul.append(li);
	}
}

/** 利用ajax获取笔记列表,将笔记列表信息显示出来 */
function loadNotes() {
	var li = $(this);
	showList($('#note-list'));
	$('#note-list ul').empty().off('click', '.note').on('click', '.note',
			loadNoteContents);
	showContent($('#edit_note'));
	// li.siblings('li').find('.btn_delete').hide();
	$('#note-list ul').empty();

	li.find('a').addClass('checked');
	li.siblings().find('a').removeClass('checked');
	$('#input_note_title').removeData().val('');
	$('#myEditor').empty();
	var url = 'note/list.do';
	var data = {
		'notebookId' : $(this).data('notebookId')
	};

	$.getJSON(url, data, function(result) {
		console.log(result);
		var state = result.state;
		if (state == 0) {
			var notes = result.data;
			if (notes.length == 0) {
				// li.find('.btn_delete').show();
				return;
			}
			showNotes(notes);
		} else if (state == 1) {
			alert(result.message);
		}
	});
}

/** 显示笔记列表 */
function showNotes(notes) {
	var ul = $('#note-list ul');
	for (var i = 0; i < notes.length; i++) {
		var note = notes[i];
		var li = $(noteTemplate.replace('#[title]', note.title));
		var noteIT = {
			noteId : note.id,
			noteTitle : note.title,
		};
		li.data(noteIT);
		ul.append(li);
	}
}

/** 点击笔记li中的下拉按钮后,显示三个操作选项 */
function noteButtonSlideDown() {
	var tar = $(this);
	// 由于hide()是异步的,所以此处不能使用ul下的所有li来隐藏,否则被点击的li也不能显示操作选项div,
	// 而应该让被点击的li的兄弟li来hide(),这样就可以控制被点击li的下拉div的显示
	tar.closest('li').siblings().find('.note_menu').hide();
	if (tar.closest('a').next('div').css('display') == 'none') {
		tar.closest('a').next('div').show();
	} else {
		$('.note_menu').hide();
	}
	// return false;
}

/** 点击body的任何地方,隐藏点击笔记li中的下拉选项 */
function bodyClick(e) {
	// 获取点击的事件源
	var tar = $(e.target);
	// 如果被点击的是笔记li的下拉按钮或者是下拉选项的div,则不能冒泡到此处隐藏div
	if (tar.closest('div').andSelf().hasClass('note_menu')
			|| tar.closest('button').andSelf().hasClass('btn_slide_down')) {
		return;
	}
	$('.note_menu').hide();
}

/** 利用ajax获取笔记的内容 */
function loadNoteContents() {
	var li = $(this);
	// 判断是否是批量删除状态
	var isDeleteNotes = li.siblings('li').eq(0).find('span').length == 0 ? false
			: true;
	if (!isDeleteNotes) {
		li.find('a').addClass('checked');
		li.siblings().find('a').removeClass('checked');
		$('#note-list .btn_slide_down').show();
	} else {
		$('#note-list .btn_slide_down').hide();
	}
	var noteId = li.data('noteId');
	var noteTitle = li.data('noteTitle');
	var url = 'note/body.do';
	var data = {
		'noteId' : noteId
	};
	$.getJSON(url, data, function(result) {
		console.log(result);
		var state = result.state;
		if (state == 0) {
			if (isDeleteNotes) {
				$('#noput_note_title').html(noteTitle);
				$('#previews_note').find('p').html(result.data);
			} else {
				showNoteContent(noteId, noteTitle, result.data);
			}
		} else if (state == 1) {
			alert(result.message);
		}
	});
}

/** 显示标题 #input_note_title, 显示内容 #myEditor */
function showNoteContent(noteId, noteTitle, noteBody) {
	$('#input_note_title').data('noteId', noteId).data('noteTitle', noteTitle)
			.data('noteBody', noteBody).val(noteTitle);
	um.setContent(noteBody);
}

/** 保存笔记的修改 */
function updateNote() {
	var jTitle = $('#input_note_title');
	var noteId = jTitle.data('noteId');
	var oldTitle = jTitle.data('noteTitle');
	var oldBody = jTitle.data('noteBody');
	var newTitle = jTitle.val();
	var newBody = um.getContent();
	var notebookId = $('#notebook-list .checked').parent().data('notebookId');
	var userId = getCookie('userId');
	if (!noteId) {
		alert('未选中笔记');
		return;
	}
	if (!newTitle) {
		alert('标题不能为空')
		return;
	}
	if (!notebookId) {
		alert('未选中笔记本');
	}
	if (!userId) {
		alert('未登录');
	}
	var url = 'note/updateNote.do';
	var data = {
		'id' : noteId,
		'notebookId' : notebookId,
		'userId' : userId
	};
	var hasChanged = false;
	if (newTitle.trim() != oldTitle.trim()) {
		data.title = newTitle;
		jTitle.data('noteTitle', newTitle);
		hasChanged = true;
	}
	if (newBody.trim() != oldBody.trim()) {
		data.body = newBody;
		jTitle.data('noteBody', newBody);
		hasChanged = true;
	}
	if (!hasChanged) {
		return;
	}
	$.post(url, data, function(result) {
		console.log(result);
		var state = result.state;
		if (state == 0) {
			// alert('保存成功!')
			flushNoteList(newTitle);
			alertSuccess('笔记保存成功')
		} else if (state == 1) {
			alert('保存失败: ' + result.message);
		}
	});

}

/** 保存笔记后,刷新笔记列表,就是将被修改的笔记移到列表的第一位,因为整个列表是按最后修改时间来排序的 */
function flushNoteList(title) {
	var li = $('#note-list .checked').parent();
	var noteId = li.data('noteId');
	var oldTitle = li.data('noteTitle');
	li.remove();
	var temp = li.html().replace(oldTitle, title);
	li.html(temp).data('noteId', noteId).data('noteTitle', title);
	$('#note-list ul').prepend(li);
}

/** 弹出 创建新笔记 对话框 */
function showAddNoteDialog() {
	$('#can').load('alert/alert_note.html', function() {
		$('.opacity_bg').show();
		$('#input_note').focus();
	});
}

/** 利用ajax 创建新笔记 */
function addNote() {
	var title = $('#input_note').val();
	if (!title) {
		alert('标题不能为空');
		return;
	}
	var notebookId = $('#notebook-list .checked').parent().data('notebookId');
	if (!notebookId) {
		alert('未选中笔记本');
		return;
	}
	var userId = getCookie('userId');
	if (!userId) {
		alert('未登录');
		return;
	}
	var url = 'note/addNote.do';
	var data = {
		title : title,
		notebookId : notebookId,
		userId : userId
	};
	$.getJSON(url, data, function(result) {
		console.log(result);
		var state = result.state;
		if (state == 0) {
			showNewNote(result.data);
		} else {
			alert(result.message);
		}
	});

}

/** 定时器Id,用于clearInterval */
var intervalId;
/** 保存笔记成功后,弹出保存成功对话框,3秒后自动消失 */
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

/** 在编辑区显示新创建的笔记内容 */
function showNewNote(note) {
	closeDialog();
	var id = note.id;
	var title = note.title;
	var body = note.body;
	var li = $(noteTemplate.replace('#[title]', title)).data('noteId', id)
			.data('noteTitle', title);
	$('#note-list ul').prepend(li).find('a').removeClass('checked');
	li.find('a').addClass('checked');
	showNoteContent(id, title, body);
}

/** 笔记的三个点击事件,移动,分享,删除 */
function noteMenuAction(e) {
	e.stopPropagation();
	var tar = $(e.target);
	// console.log(tar);
	if (tar.hasClass('btn_delete') || tar.parent().hasClass('btn_delete')) {
		$('#can').load('alert/alert_delete_note.html', function() {
			$('.opacity_bg').show();
			$('#can .sure').click(function() {
				moveNoteToTrash(tar)
			});
		});
	}

	if (tar.hasClass('btn_move') || tar.parent().hasClass('btn_move')) {
		$('#can').load('alert/alert_move.html', function() {
			$('.opacity_bg').show();
			showNotebookSelectList();
			$('#can .sure').click(function() {
				moveNoteToOtherNotebook(tar)
			});
		});
	}
}

/** 利用ajax将笔记status改为0,并在列表中删除此Note 的li */
/** 将笔记移动到回收站,即将其status_id改为0,正常为1 */
function moveNoteToTrash(tar) {
	var li = tar.closest('li');
	var noteId = li.data('noteId');
	var url = 'note/deleteNote.do';
	var data = {
		noteId : noteId
	};
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			li.remove();
			clearEdit();
		} else {
			alert(result.message);
		}
	});
}

/** 移动笔记到其他笔记本时,显示笔记本的下拉选项 */
function showNotebookSelectList() {
	var url = 'notebook/list.do';
	var data = {
		userId : getCookie('userId')
	};
	var slec = $('#moveSelect').focus();
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			var notebooks = result.data;
			for (var i = 0; i < notebooks.length; i++) {
				var notebook = notebooks[i];
				var op = $('<option></option>').html(notebook.name).val(
						notebook.id);
				slec.append(op);
			}
		} else {
			alert(result.message);
		}
	});
}

/** 利用ajax将笔记的notebookId改为指定指,并将列表中此Note 的li删除 */
/** 将笔记移动到其他的笔记本 */
function moveNoteToOtherNotebook(tar) {
	var notebookId = $('#moveSelect').val();
	if (notebookId == 'none') {
		return;
	}
	var li = tar.closest('li');
	var noteId = li.data('noteId');
	var url = 'note/moveNote.do';
	var data = {
		noteId : noteId,
		notebookId : notebookId
	};
	console.log(data);

	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			li.remove();
			clearEdit();
		} else {
			alert(result.message);
		}
	});

}

/** 回收站,获取删除的Note */
/** 利用ajax获取回收站列表 */
function loadTrashNotes() {
	var url = 'note/listDelNote.do';
	var data = {
		userId : getCookie('userId')
	};
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			showdelNote(result.data);
		} else {
			alert(result.message);
		}
	});
}
/** 回收站,显示删除的Note */
/** 显示回收站笔记列表 */
function showdelNote(notes) {
	showContent($('#previews_note'));
	var ul = showList($('#trash-list')).find('ul').empty();
	for (var i = 0; i < notes.length; i++) {
		var note = notes[i];
		var li = $(delNoteTemplate.replace('#[title]', note.title)).data(
				'noteId', note.id).data('notebookId', note.notebookId).data(
				'noteTitle', note.title);
		ul.append(li);
	}
}
/** 加载预览区 */
/** 加载预览笔记内容 */
function loadPreviewsNote() {
	console.log('显示预览内容')
	var div = showContent($('#previews_note'));
	$(this).siblings().find('a').removeClass('checked');
	$(this).find('a').addClass('checked');
	var url = 'note/body.do';
	var data = {
		noteId : $(this).data('noteId')
	};
	var title = $(this).data('noteTitle');
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			$('#noput_note_title').html(title);
			div.find('p').html(result.data);
		} else {
			alert(result.message);
		}
	});
}

/** 显示指定的列表,隐藏其他列表,因为笔记本左边只能显示一个列表,所以要切换显示 */
function showList(list) {
	$('#note-list').hide();
	$('#trash-list').hide();
	$('#collection-list').hide();
	$('#activity-list').hide();
	list.show();
	return list;
}

/** 显示指定的内容显示区域,笔记列表显示编辑区,其他列表显示预览区 */
function showContent(content) {
	$('#previews_note').hide();
	$('#edit_note').hide();
	content.show();
	return content;
}

/** 弹出 删除回收站中的笔记 的对话框 */
function alertDeleteTrashNote() {
	var li = $(this).closest('li');
	$('#can').load('alert/alert_delete_rollback.html', function() {
		$('.opacity_bg').show();
		$('#can .sure').click(function() {
			deleteTrashNote(li);
		});
	});
}

/** 删除回收站中的笔记 */
function deleteTrashNote(li) {
	var url = 'note/deleteDelNote.do';
	var data = {
		noteId : li.data('noteId')
	};
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			li.remove();
			clearPreviews();
		} else {
			alert(result.message);
		}
	});
}

/** 弹出 恢复回收站中的笔记 的对话框 */
function alertReplayTrashNote() {
	var li = $(this).closest('li');
	$('#can').load('alert/alert_replay.html', function() {
		$('.opacity_bg').show();
		showReplayNoteSelectList(li);
		$('#can .sure').click(function() {
			replayTrashNote(li);
		});
	});
}

/** 恢复笔记到其他笔记本时,显示笔记本的下拉选项 */
function showReplayNoteSelectList(li) {
	var url = 'notebook/list.do';
	var data = {
		userId : getCookie('userId')
	};
	var slec = $('#replaySelect').focus().empty();
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			var notebooks = result.data;
			for (var i = 0; i < notebooks.length; i++) {
				var notebook = notebooks[i];
				var op = $('<option></option>').html(notebook.name).val(
						notebook.id);
				if (li.data('notebookId') == notebook.id) {
					op.attr('selected', 'selected');
				}
				slec.append(op);
			}
		} else {
			alert(result.message);
		}
	});
}

/** 恢复笔记:利用ajax修改Note的notebookId,并将其从回收站删除,最后清空预览区 */
function replayTrashNote(li) {
	var url = 'note/replayNote.do';
	var data = {
		noteId : li.data('noteId'),
		notebookId : $('#replaySelect').val(),
		title : li.data('noteTitle')
	}
	console.log(data);
	$.getJSON(url, data, function(result) {
		console.log(result);
		if (result.state == SUCCESS) {
			li.remove();
			clearPreviews();
		} else {
			alert(result.message);
		}
	});
}

/** 清空预览区 */
function clearPreviews() {
	$('#noput_note_title').empty();
	$('#previews_note').find('p').empty();
}

/** 清空编辑区 */
function clearEdit() {
	$('#input_note_title').val('');
	um.setContent('');
}

/** 清空#can中显示的对话框 */
function closeDialog() {
	$('#can').empty();
	$('.opacity_bg').hide();
	if (intervalId) {
		clearInterval(intervalId);
	}
}

/** 开始心跳检测,每10分钟与服务器通信一次,不然30分钟后,服务器自动删除与浏览器相关的session */
function startHeartBeat() {
	var id = setInterval(function() {
		var url = 'user/heartBeat.do';
		$.getJSON(url, function(result) {
			console.log(result);
			if (result.state != SUCCESS) {
				clearInterval(id);
			}
		});
	}, 600000);
}
