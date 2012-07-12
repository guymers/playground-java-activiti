<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>Process</title>
	</head>
	<body>
		<h1><c:out value="${process.name}"/></h1>
		
		<form action="<c:url value="/process/start/${processKey}"/>" method="POST">
			Information: <input type="text" name="information"/><br/>
			<input type="submit" value="Start Process" />
		</form>
	</body>
</html>
