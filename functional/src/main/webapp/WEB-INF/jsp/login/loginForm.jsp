<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aplicação para Testes de Funcionalidade</title>
</head>
<body>
	
	<form  action="<c:url value='/login/autentica' />" method="post">
		<input type="text" name="user.name">
		<input type="password" name="user.pass">
		<button type="submit">Entrar</button>
	</form>
	
</body>
</html>