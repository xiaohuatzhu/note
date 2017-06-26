package cn.tedu.note.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.service.ShareService;
import cn.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/share")
public class ShareController extends AbstractController {
	
	@Resource
	private ShareService shareService;
	
	@RequestMapping("/search.do")
	@ResponseBody
	public Object search(String words){
		
		return new JsonResult();
	}
}
