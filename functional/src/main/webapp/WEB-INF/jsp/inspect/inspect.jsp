<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inspe��o</title>
</head>
<body>
	<p><a href="<c:url value='/inspecao'/>">Inspec�o</a></p>
	
	<p>Informe URL do portal: </p>
	<form action="<c:url value='/inspecao' />" method="post">
		<input type="text" name="portal.url">
		<button type="submit">Inspecionar</button>
	</form>
</body>
</html>