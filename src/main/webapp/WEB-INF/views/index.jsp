<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KeepNote</title>
</head>
<body>
	<!-- Create a form which will have text boxes for Note ID, title, content and status along with a Send 
		 button. Handle errors like empty fields -->
	<h3>Note Info</h3>
	<form name="fmSaveNote" action="add" method="post">
		Title: <input type="text" id="noteTitle" name="noteTitle">
		<br>
		Content: <textarea id="noteContent"	name="noteContent"></textarea> 
		
		Status:  
		<select id="noteStatus"	name="noteStatus">
			<option value="active">Active</option>
			<option value="complete">Complete</option>
		</select> 
		<br> <br> 
		<input type="submit" value="Submit">
	</form>
	<!-- display all existing notes in a tabular structure with Id, Title,Content,Status, Created Date and Action -->
	<c:if test="${not empty error}">
		<p style="color: red">
			<strong>Error</strong>: ${error}
		</p>
	</c:if>
	<hr>
	<h3>List of Notes</h3>
	<table>
		<tr>
			<td>Note ID</td>
			<td>Title</td>
			<td>Content</td>
			<td>Status</td>
			<td>Created At</td>
			<td>Delete Action</td>
		</tr>
		<c:forEach items="${noteList}" var="note">
			<tr>
				<td>${note.noteId}</td>
				<td>${note.noteTitle}</td>
				<td>${note.noteContent}</td>
				<td>${note.noteStatus}</td>
				<td>${note.createdAt}</td>
				<td>
					<form name="fmDelNote" action="delete">
						<input type="hidden" name="noteId" value="${note.noteId}">
						<input type="submit" value="Delete Note">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>