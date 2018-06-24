<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inspeção</title>
</head>
<body>
	<p>
		<a href="${linkTo[InspectController].inspect}">Inspecão</a>
	</p>
	
	<c:if test="${not empty errors}">
		<c:forEach items="${errors}" var="error">
			<p>${error.message}</p>
		</c:forEach>
	</c:if>

	<p>Informe URL do portal:</p>
	<form action="<c:url value='/inspecao' />" method="post">
		<input type="text" name="portal.url">
		<button type="submit">Inspecionar</button>
	</form>
	
	<c:if test="${not empty elements}">
		<p>Foram encontrados os seguintes elementos de navegação:</p>

		<c:forEach items="${elements}" var="element">
			<p> ${element.tagName } 
			<select>
					<c:forEach items="${element.attributes}" var="attribute">
						<option value="${attribute.key}">${attribute.key} = ${attribute.value}</option>
					</c:forEach>
			</select>	
			</p>
		</c:forEach>
	</c:if>

</body>
</html>