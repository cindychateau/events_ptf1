<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Show Event</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<h1>${event.eventName}</h1>
				<p>
					<b>Planner:</b> ${event.planner.firstName}
				</p>
				<p>
					<b>Date:</b> ${event.eventDate}
				</p>
				<p>
					<b>Location:</b> ${event.eventLocation}
				</p>
				<p>
					<b>State:</b> ${event.eventState}
				</p>
				<p>
					<b># of People:</b> ${event.attendees.size()}
				</p>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Name</th>
							<th>Location</th>
						</tr>
					</thead>
					<tbody>
						<!-- Recorremos todos los usuarios que van a ir al evento -->
						<c:forEach items="${event.attendees}" var="user">
							<tr>
								<td>${user.firstName}</td>
								<td>${user.location}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col">
				<h2>Message Wall</h2>
				<div>
					<!-- Recorremos todos los mensajes que publicaron al evento -->
					<c:forEach items="${event.eventMessages}" var="message">
						<p>
							${message.author.firstName} says: ${message.content}
						</p>
					</c:forEach>
				</div>
				<form:form action="/message" method="post" modelAttribute="message">
					<div class="form-group">
						<form:label path="content">Add Comment:</form:label>
						<form:textarea path="content" class="form-control" />
						<form:errors path="content" class="text-danger" />
						<form:hidden path="author" value="${userInSession.id}" />
						<form:hidden path="event" value="${event.id}"/>
						<input type="submit" class="btn btn-primary" value="Send" />
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>