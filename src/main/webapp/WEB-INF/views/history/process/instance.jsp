<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>Process Instance</title>
	</head>
	<body>
		<h1>Process Instance</h1>
		<c:url value="${historicProcessInstance}"/>
		
		<h1>tasks</h1>
		<c:url value="${tasks}"/>
		
		<h1>activities</h1>
		<c:url value="${activities}"/>
		
		<h1>variables</h1>
		<c:url value="${variables}"/>
	</body>
</html>
