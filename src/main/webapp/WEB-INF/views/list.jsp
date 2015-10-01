<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
List


<h1>ALL ISSUES</h1>
	<table border="1">
		<tr>
			<td>ID</td>
			<td>PROJECT</td>
			<td>STATUS</td>
			<td>DESCRIPTION</td>
		</tr>
		<c:forEach items="${allIssues}" var="issue">
			<tr>
				<td>${issue.id}</td>
				<td>${issue.project.name}</td>
				<td>${issue.status.name}</td>
				<td>${issue.description}</td>
				<td>${issue.assignee.name}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>