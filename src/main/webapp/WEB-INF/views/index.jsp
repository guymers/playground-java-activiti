<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>Index</title>
	</head>
	<body>
		<h1>Processes</h1>
		<c:forEach var="process" items="${processes}">
			<a href="<c:url value="/process/start/${process.key}"/>"><c:out value="${process.name}"/></a><br/>
		</c:forEach>
		
		<h1>Users</h1>
		<c:forEach var="user" items="${users}">
			<a href="<c:url value="/user/${user.id}"/>"><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></a><br/>
		</c:forEach>
		
		<br/><br/>
		<a href="<c:url value="/history"/>">History</a>
	</body>
</html>
