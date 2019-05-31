package com.stackroute.keepnote.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.model.Note;

/*
 * Annotate the class with @Controller annotation.@Controller annotation is used to mark 
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 */
@Controller
public class NoteController {

	public NoteController(final NoteDAO noteDao) {
		this.noteDAO = noteDao;
	}
	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the persistence data. Each note
	 * should contain Note Id, title, content, status and created date. 2. Add a new
	 * note which should contain the note id, title, content and status. 3. Delete
	 * an existing note 4. Update an existing note
	 * 
	 */

	/*
	 * Autowiring should be implemented for the NoteDAO. Create a Note object.
	 * 
	 */
	@Autowired
	private NoteDAO noteDAO;

	private static final String NOTE_LIST = "noteList";
	private static final String NOTE_TITLE = "noteTitle";
	private static final String NOTE_CONTENT = "noteContent";
	private static final String NOTE_STATUS = "noteStatus";
	private static final String REDIRECT_HOME = "redirect:/";

	/*
	 * Define a handler method to read the existing notes from the database and add
	 * it to the ModelMap which is an implementation of Map, used when building
	 * model data for use with views. it should map to the default URL i.e. "/index"
	 */
	@GetMapping("/")
	public String getAllNotes(final ModelMap model) {
		model.addAttribute(NOTE_LIST, noteDAO.getAllNotes());
		return "index";
	}

	/*
	 * Define a handler method which will read the NoteTitle, NoteContent,
	 * NoteStatus from request parameters and save the note in note table in
	 * database. Please note that the CreatedAt should always be auto populated with
	 * system time and should not be accepted from the user. Also, after saving the
	 * note, it should show the same along with existing messages. Hence, reading
	 * note has to be done here again and the retrieved notes object should be sent
	 * back to the view using ModelMap This handler method should map to the URL
	 * "/add".
	 */
	@PostMapping("/add")
	public String addNote(final ModelMap model, final HttpServletRequest request) {
		if (request.getParameter(NOTE_TITLE).isEmpty() || request.getParameter(NOTE_CONTENT).isEmpty()
				|| request.getParameter(NOTE_STATUS).isEmpty()) {
			model.addAttribute("error", "Required fileds are missing");
			return "index";
		}
		final Note note = new Note();
		note.setNoteTitle(request.getParameter(NOTE_TITLE));
		note.setNoteContent(request.getParameter(NOTE_CONTENT));
		note.setNoteStatus(request.getParameter(NOTE_STATUS));
		note.setCreatedAt(LocalDateTime.now());
		this.noteDAO.saveNote(note);
		return REDIRECT_HOME;
	}

	/*
	 * Define a handler method which will read the NoteId from request parameters
	 * and remove an existing note by calling the deleteNote() method of the
	 * NoteRepository class.This handler method should map to the URL "/delete".
	 */
	@GetMapping("/delete")
	public String deleteNote(final ModelMap model, final @RequestParam int noteId) {
		this.noteDAO.deleteNote(noteId);
		model.addAttribute(NOTE_LIST, this.noteDAO.getAllNotes());
		return REDIRECT_HOME;
	}

	/*
	 * Define a handler method which will update the existing note. This handler
	 * method should map to the URL "/update".
	 */
	@PostMapping("/update")
	public String updateNote(final ModelMap model, final HttpServletRequest request) {
		final Note note = new Note();
		note.setNoteId(Integer.parseInt(request.getParameter("noteId")));
		note.setNoteTitle(request.getParameter(NOTE_TITLE));
		note.setNoteContent(request.getParameter(NOTE_CONTENT));
		note.setNoteStatus(request.getParameter(NOTE_STATUS));
		note.setCreatedAt(LocalDateTime.now());
		this.noteDAO.saveNote(note);
		model.addAttribute(NOTE_LIST, this.noteDAO.getAllNotes());
		return REDIRECT_HOME;
	}

}
