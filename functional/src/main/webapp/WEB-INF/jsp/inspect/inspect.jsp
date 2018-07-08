<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">

<script src="jquery/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="angularjs/angular.min.js"></script>

<script src="resources/js/module/modalApp.js"></script>
<script src="resources/js/controller/modalController.js"></script>

<title>Inspeção</title>
</head>
<body ng-app="modalApp" ng-controller="modalController" >
	<p>
		<a href="${linkTo[InspectController].inspect}">Inspecão</a>
	</p>
	
	<c:if test="${not empty errors}">
		<c:forEach items="${errors}" var="error">
			<p>${error.message}</p>
		</c:forEach>
	</c:if>
	
	<c:if test="${not empty redirected}">
		{{toggleModal()}}
	</c:if>

	<p>Informe URL do portal:</p>
	<form action="<c:url value='/inspecao' />" method="post">
		<input type="text" name="portal.url">
		<button type="submit" class="btn btn-default">Inspecionar</button>
	</form><br>
	
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

	
	<div class="container">
		<modal title="Redirecionamento" visible="showModal" data-backdrop="static" data-keyboard="false">
		  	<c:if test="${not empty redirected}">
				<p>${errorMessage}</p>
			</c:if>
	  	<p>Deseja continuar?</p>

	    <footer>
			    <form action="<c:url value='/inspecao' />" method="post">
					<input type="hidden" name="portal.url" value="${redirected}">
				  	<button type="submit" class="btn btn-default">Sim</button>
				</form>
				
			  	<button type="button" class="btn btn-default" onclick="location.href='<c:url value='/inspecao'/>'">Não</button>
	    </footer>

	  </modal>
	</div>

</body>
</html>