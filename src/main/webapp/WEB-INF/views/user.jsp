<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>User</title>
	</head>
	<body>
		<h1>Available Tasks</h1>
		<c:forEach var="task" items="${availableTasks}">
			<a href="<c:url value="/user/${userId}/claim/${task.id}"/>"><c:out value="${task.name}"/> (<c:out value="${task.id}"/>)</a> - <a href="<c:url value="/history/process/instance/${task.processInstanceId}"/>">History</a><br/>
		</c:forEach>
		
		<h1>Assigned Tasks</h1>
		<c:forEach var="task" items="${assignedTasks}">
			<a href="<c:url value="/process/complete_task/${task.id}"/>"><c:out value="${task.name}"/> (<c:out value="${task.id}"/>)</a> - <a href="<c:url value="/history/process/instance/${task.processInstanceId}"/>">History</a><br/>
		</c:forEach>
		
		<h1>Owned Tasks</h1>
		<c:forEach var="task" items="${ownedTasks}">
			<c:out value="${task.name}"/><br/>
		</c:forEach>
	</body>
</html>
