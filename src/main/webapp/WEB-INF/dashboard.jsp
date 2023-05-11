<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<nav class="d-flex justify-content-between align-items-center">
			<h1>Welcome ${userInSession.firstName}!</h1>
			<a href="/logout" class="btn btn-danger">Logout</a>
			<!--img src="/images/gatito_small.jpg" alt="gatito"-->
		</nav>
		<div class="row">
			<h2>Events in your state</h2>
		</div>
		<div class="row">
			<h2>Events in other states</h2>
		</div>
		<div class="row">
			<h2>Create Event</h2>
			<form:form action="/create" method="post" modelAttribute="newEvent">
				<div>
					<form:label path="eventName">Name</form:label>
					<form:input path="eventName" class="form-control" />
					<form:errors path="eventName" class="text-danger" />
				</div>
				<div>
					<form:label path="eventDate">Date</form:label>
					<form:input type="date" path="eventDate" class="form-control" />
					<form:errors path="eventDate" class="text-danger" />
				</div>
				<div>
					<form:label path="eventLocation">Location</form:label>
					<form:input path="eventLocation" class="form-control" />
					<form:errors path="eventLocation" class="text-danger" />
				</div>
				<div>
					<form:label path="eventState">State</form:label>
					<form:select path="eventState" class="form-control">
						<c:forEach items="${states}" var="state">
							<option value="${state}">${state}</option>
						</c:forEach>
					</form:select>
				</div>
				<form:hidden path="planner" value="${userInSession.id}" />
				<input type="submit" value="Create Event" class="btn btn-sucess mt-3" >
			</form:form>
		</div>
	</div>
</body>
</html>