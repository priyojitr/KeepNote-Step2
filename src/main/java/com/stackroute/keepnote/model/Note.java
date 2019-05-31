package com.stackroute.keepnote.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * The class "Note" will be acting as the data model for the note Table in the database. Please
 * note that this class is annotated with @Entity annotation. Hibernate will scan all package for 
 * any Java objects annotated with the @Entity annotation. If it finds any, then it will begin the 
 * process of looking through that particular Java object to recreate it as a table in your database.
 */
@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int noteId;
	private String noteTitle;
	private String noteContent;
	@Column(nullable = false)
	private String noteStatus;
	@SuppressWarnings("unused")
	private LocalDateTime createdAt;

	public Note() {
		// empty constructor (default)
	}

	public Note(final int noteId, final String noteTitle, final String noteContent, final String noteStatus,
			final LocalDateTime createdAt) {
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteStatus = noteStatus;
		this.createdAt = createdAt;
	}

	public int getNoteId() {

		return this.noteId;
	}

	public String getNoteTitle() {

		return this.noteTitle;
	}

	public String getNoteContent() {

		return this.noteContent;
	}

	public String getNoteStatus() {

		return this.noteStatus;
	}

	public void setNoteId(final int noteId) {
		this.noteId = noteId;
	}

	public void setNoteTitle(final String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public void setNoteContent(final String noteContent) {
		this.noteContent = noteContent;
	}

	public void setNoteStatus(final String noteStatus) {
		this.noteStatus = noteStatus;
	}

	public void setCreatedAt(final LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

}
