<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>Complex Process</title>
	</head>
	<body>
		<h1>Complex Process</h1>
		
		<form action="<c:url value="/process/complex-process-jpa/start"/>" method="POST">
			Information: <input type="text" name="information"/><br/>
			<input type="submit" value="Start Process" />
		</form>
	</body>
</html>
