<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>Task Claimed</title>
	</head>
	<body>
		<h1>Task Claimed</h1>
		
		<a href="<c:url value="/user/${userId}"/>">Back</a>
	</body>
</html>
