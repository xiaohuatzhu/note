package cn.tedu.note.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.entity.Notebook;
import cn.tedu.note.service.NotebookService;
import cn.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/notebook")
public class NotebookController extends AbstractController {

	@Resource
	private NotebookService notebookService;

	@RequestMapping("/list.do")
	@ResponseBody
	public Object list(String userId) {
		List<Map<String, Object>> list = notebookService.listNotebooks(userId);
		return new JsonResult(list);
	}
	
	@RequestMapping("/page.do")
	@ResponseBody
	public Object page(String userId, Integer page) {
		List<Map<String, Object>> list = notebookService.listNotebooks(userId, page);
		return new JsonResult(list);
	}

	@RequestMapping("/addNotebook.do")
	@ResponseBody
	public Object addNotebook(String userId, String name) {
		Notebook newNotebook = notebookService.createNotebook(userId, name);
		return new JsonResult(newNotebook);
	}

	@RequestMapping("/rename.do")
	@ResponseBody
	public Object rename(String userId, String notebookId, String name) {
		int n = notebookService.renameNotebook(userId, notebookId, name);
		return new JsonResult(n);
	}

	@RequestMapping("/deleteNotebook.do")
	@ResponseBody
	public Object deleteNotebook(String userId, String notebookId) {
		int n = notebookService.deleteNotebook(userId, notebookId);
		return new JsonResult(n);
	}
	@RequestMapping(value="/downloadNotebooks.do",produces="application/octet-stream")
	@ResponseBody
	public byte[] downloadNotebooks(String userId, HttpServletResponse res) throws IOException {
		Object[] objs = notebookService.downloadNotebook(userId);
		String name = (String) objs[0];
		byte[] bs = (byte[]) objs[1];
		res.setHeader("Content-Disposition",
				"attachment; filename=\"" + new String(name.getBytes("utf-8"), "ISO-8859-1") + ".xlsx\"");
		return bs;
	}

}
