package cn.tedu.note.service;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.tedu.note.entity.Note;

public interface NoteService extends Serializable {

	List<Map<String, Object>> listNotes(String notebookId);

	List<Map<String, Object>> listDeleteNotes(String userId);

	String getNoteBodyByNoteId(String noteId);

	Note updateNote(Note note);

	Note createNote(Note note);

	int deleteNote(String noteId);

	int deleteDelNote(String noteId);

	Note moveNote(String noteId, String notebookId);

	Note replayNote(String noteId, String notebookId, String title);

	int deleteNotes(String... noteIds);

	int uploadNote(MultipartFile noteFile, String data) throws IOException;

	Object[] downloadNote(String userId, String noteId) throws UnsupportedEncodingException;

}
