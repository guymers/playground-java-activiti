<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>User Task 2</title>
	</head>
	<body>
		<h1>User Task 2</h1>
		
		<form action="<c:url value="/process/complex-process-jpa/${task.id}/complete"/>" method="POST">
			Information: <input type="text" name="information"/><br/>
			Amount: <input type="text" name="amount"/><br/>
			<input type="submit" value="Submit" />
		</form>
	</body>
</html>
