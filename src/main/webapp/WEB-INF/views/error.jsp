<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>Error</title>
	</head>
	<body>
		<h1>Error</h1>
		
		<c:url value="${error}"/>
	</body>
</html>
