package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus 
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */
	@Autowired
	private final SessionFactory sessionFactory;

	public NoteDAOImpl(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Save the note in the database(note) table.
	 */

	public boolean saveNote(final Note note) {
		final Session session = this.sessionFactory.getCurrentSession();
		session.save(note);
		session.flush();
		return Boolean.TRUE;

	}

	/*
	 * Remove the note from the database(note) table.
	 */

	public boolean deleteNote(final int noteId) {
		if(null == this.getNoteById(noteId)) {
			return Boolean.FALSE;
		}
		final Session session = this.sessionFactory.getCurrentSession();
		session.delete(this.getNoteById(noteId));
		session.flush();
		return Boolean.TRUE;

	}

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	public List<Note> getAllNotes() {
		final String hql = "FROM Note note ORDER BY note.createdAt DESC";
		return (List<Note>) this.sessionFactory.getCurrentSession().createQuery(hql).getResultList();
		
	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	public Note getNoteById(final int noteId) {
		final Session session = this.sessionFactory.getCurrentSession();
		final Note note = session.get(Note.class, noteId);
		session.flush();
		return note;

	}

	/* Update existing note */

	public boolean UpdateNote(final Note note) {
		if(null == this.getNoteById(note.getNoteId())) {
			return Boolean.FALSE;
		}
		final Session session = this.sessionFactory.getCurrentSession();
		session.clear();
		session.update(note);
		session.flush();
		return Boolean.TRUE;

	}

}
