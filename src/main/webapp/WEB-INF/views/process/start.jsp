<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>Start</title>
	</head>
	<body>
		<h1>Start</h1>
		
		<c:out value="${process.name}"/> has been started<br/><br/>
		
		<a href="<c:url value="/"/>">Home</a>
	</body>
</html>
