<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>Complete</title>
	</head>
	<body>
		<h1>Complete</h1>
		
		<c:out value="${task.name}"/> has been completed<br/><br/>
		
		<a href="<c:url value="/"/>">Home</a>
	</body>
</html>
