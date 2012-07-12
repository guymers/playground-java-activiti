<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>Process</title>
	</head>
	<body>
		<h1><c:out value="${task.name}"/></h1>
		
		<form action="<c:url value="/process/complete_task/${task.id}"/>" method="POST">
			Information: <input type="text" name="information"/><br/>
			Amount: <input type="text" name="amount"/><br/>
			<input type="submit" value="Submit Again" />
		</form>
	</body>
</html>
