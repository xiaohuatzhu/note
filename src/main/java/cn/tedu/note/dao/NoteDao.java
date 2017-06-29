package cn.tedu.note.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.tedu.note.entity.Note;

public interface NoteDao extends Serializable {

	List<Map<String, Object>> findNotesByNotebookId(String notebookId);

	List<Note> findAllByNotebookId(String notebookId);
	List<Map<String, Object>> findDeleteNotes(String userId);

	String findNoteBodyByNoteId(String noteId);

	int countNoteByTitleNotebookIdUserId(Note note);

	int addNote(Note note);

	Note findNoteByNoteId(String noteId);

	Note findDeleteNoteByNoteId(String noteId);

	int updateNote(Note note);

	int deleteDelNote(String noteId);

	int countNotesByNoteId(@Param("noteId") String noteId, @Param("status") String status);

	int countNoteByTitleNotebookId(@Param("notebookId") String notebookId, @Param("title") String title);

	int countNoteByNotebookId(String notebookId);

}
