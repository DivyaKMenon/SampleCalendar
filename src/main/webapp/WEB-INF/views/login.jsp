<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.11.3.js"></script>
<title>Home</title>

<script>
	function send(username, password, sprint, assignee, sprintType, project) {
		var object = {
			username : username.value,
			password : password.value,
			sprint : sprint.value,
			assignee : assignee.value,
			project : project.value,
			sprintType : sprintType.value
		};
		var count = 0;
		document.getElementById('timer').textContent = "Loading.";
		var interval = setInterval(function() {
			count++;
			if (count > 3) {
				count = 0;
				document.getElementById('timer').textContent = "Loading.";
			}
			document.getElementById('timer').textContent += '.';
		}, 400);
		$.ajax({
			type : "post",
			url : "list",
			contentType : "application/json",
			data : JSON.stringify(object)
		}).done(function(data) {
			clearInterval(interval);
			document.getElementById('timer').textContent = "";
			document.getElementById('div').innerHTML = data;
		});
	}
</script>
</head>
<body>
	<h1>
		<label id="timer"></label>
	</h1>
	<br>
	<label id="username" style="display: none;">${user.username}</label>
	<label id="password" style="display: none;">${user.password}</label>
	<select id="sprint">
		<c:forEach items="${allSprint}" var="sprint">

			<option value="${sprint}">${sprint}</option>
		</c:forEach>
	</select>
	<select id="project">
		<c:forEach items="${allProject}" var="project">

			<option value="${project}">${project}</option>
		</c:forEach>
	</select>
	<!-- <input type="text" id="sprint"> -->
	<input type="text" id="assignee" placeholder="Assignee">
	<select id="sprintType">
		<option value="any">Open/Closed sprints</option>
		<option value="closed">Closed sprints</option>
		<option value="open">Open sprints</option>
	</select>
	<input value="Submit" type="button"
		onclick="send(document.getElementById('username'), document.getElementById('password'),document.getElementById('sprint'),document.getElementById('assignee'),document.getElementById('sprintType'),document.getElementById('project'))">

	<div id="div"></div>

</body>
</html>
