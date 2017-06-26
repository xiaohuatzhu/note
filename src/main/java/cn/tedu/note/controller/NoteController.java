package cn.tedu.note.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.note.entity.Note;
import cn.tedu.note.service.NoteService;
import cn.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/note")
public class NoteController extends AbstractController {

	@Resource
	private NoteService noteService;

	@RequestMapping("/list.do")
	@ResponseBody
	public Object list(String notebookId) {
		List<Map<String, Object>> list = noteService.listNotes(notebookId);
		return new JsonResult(list);
	}

	@RequestMapping("/listDelNote.do")
	@ResponseBody
	public Object listDelete(String userId) {
		List<Map<String, Object>> list = noteService.listDeleteNotes(userId);
		return new JsonResult(list);
	}

	@RequestMapping("/body.do")
	@ResponseBody
	public Object body(String noteId) {
		String body = noteService.getNoteBodyByNoteId(noteId);
		return new JsonResult(body);
	}

	@RequestMapping("/updateNote.do")
	@ResponseBody
	public Object updateNote(Note note) {
		System.out.println(note);
		Note note1 = noteService.updateNote(note);
		System.out.println(note1);
		return new JsonResult("OK");
	}

	@RequestMapping("/addNote.do")
	@ResponseBody
	public Object addNote(Note note) {
		Note newNote = noteService.createNote(note);
		return new JsonResult(newNote);
	}

	@RequestMapping("/deleteNote.do")
	@ResponseBody
	public Object deleteNote(String noteId) {
		int n = noteService.deleteNote(noteId);
		return new JsonResult(n);
	}

	@RequestMapping("/deleteDelNote.do")
	@ResponseBody
	public Object deleteDelNote(String noteId) {
		int n = noteService.deleteDelNote(noteId);
		return new JsonResult(n);
	}

	@RequestMapping("/moveNote.do")
	@ResponseBody
	public Object moveNote(String noteId, String notebookId) {
		Note note = noteService.moveNote(noteId, notebookId);
		return new JsonResult(note.getNotebookId());
	}

	@RequestMapping("/replayNote.do")
	@ResponseBody
	public Object replayNote(String noteId, String notebookId, String title) {
		Note note = noteService.replayNote(noteId, notebookId, title);
		return new JsonResult(note.getNotebookId());
	}

	@RequestMapping("/deleteNotes.do")
	@ResponseBody
	public Object deleteNotes(final String[] noteIds) {
		// System.out.println(Arrays.toString(noteIds));
		int n = noteService.deleteNotes(noteIds);
		return new JsonResult(n);
	}

	@RequestMapping("/uploadNote.do")
	@ResponseBody
	public Object uploadNote(MultipartFile noteFile, String data) throws IOException {
		System.out.println("NoteController.uploadNote()->fileName:" + noteFile.getOriginalFilename());
		int n = noteService.uploadNote(noteFile, data);
		return new JsonResult(n);
	}

	@RequestMapping(value = "/downloadNote.do", produces = "application/octet-stream")
	@ResponseBody
	public byte[] downloadNote(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String userId = req.getParameter("userId");
		String noteId = req.getParameter("noteId");
		System.out.println("NoteController.downloadNote()->" + userId + "," + noteId);
		Object[] objs = noteService.downloadNote(userId, noteId);
		String title = (String) objs[0];
		System.out.println(title);
		byte[] bs = (byte[]) objs[1];
		res.setCharacterEncoding("utf-8");
		res.setHeader("Content-Disposition",
				"attachment; filename=\"" + new String(title.getBytes("utf-8"), "ISO-8859-1") + ".doc\"");
		return bs;
	}

}
