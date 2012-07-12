<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>History</title>
	</head>
	<body>
		<h1>Unfinished Processes</h1>
		<c:forEach var="process" items="${unfinishedProcesses}">
			<a href="<c:url value="/history/process/instance/${process.id}"/>"><c:out value="${process.processDefinitionId}"/></a><br/>
		</c:forEach>
		
		<h1>Finished Processes</h1>
		<c:forEach var="process" items="${finishedProcesses}">
			<a href="<c:url value="/history/process/instance/${process.id}"/>"><c:out value="${process.processDefinitionId}"/></a><br/>
		</c:forEach>
	</body>
</html>
