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

<style type="text/css">
	.row {
    	height: 100vh;
	}
</style>

<title>Inspeção</title>
</head>
<body ng-app="modalApp" ng-controller="modalController">
	<div class="row">
		<div class="col-1 bg-primary h-100">
				<nav class="navbar navbar-dark justify-content-center" >
					<h4 class="navbar-brand">Menu</h4>
				  		<div class="navbar-nav">
							<a class="nav-link" href="${linkTo[InspectController].inspect}">Inspecão</a>
						</div>
				</nav>
		</div>

		<div class="col-6">
			<h1>Inspeção de Telas</h1>
			
			<c:if test="${not empty errors}">
				<c:forEach items="${errors}" var="error">
					<div class="alert alert-danger" role="alert">
						<p>${error.message}</p>
					</div>
				</c:forEach>
			</c:if>

			<p>Informe URL do portal:</p>
			<form class="form-inline" action="<c:url value='/inspecao' />" method="post">
				<input class="form-control mr-sm-2" type="text" name="portal.url">
				<button class="btn btn-outline-primary my-2 my-sm-0" type="submit" >Inspecionar</button>
			</form>
			<br>

			<c:if test="${not empty elements}">
				<h3>Foram encontrados os seguintes elementos de navegação:</h3>

				<table class="table table-bordered table-hover table-sm">
					<thead class="thead-light">
						<tr>
							<th scope="col">Componente</th>
							<th scope="col">Atributos</th>
						</tr>
					</thead>

					<c:forEach items="${elements}" var="element">
						<tbody>
							<tr>
								<td>${element.tagName}</td>
								<td>
									<select>
											<c:forEach items="${element.attributes}" var="attribute">
												<option value="${attribute.key}">${attribute.key}=${attribute.value}</option>
											</c:forEach>
									</select>
								</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</c:if>
		</div>
	</div>
	
	<c:if test="${not empty redirectedUrl}">
		{{toggleModal()}}
	</c:if>
	
	<div class="container">
		<modal title="Redirecionamento" visible="showModal" data-backdrop="static" data-keyboard="false"> 
			<c:if test="${not empty redirectedUrl}">
				<p>${errors[0].message}</p>
			</c:if>
			<p>Deseja continuar?</p>

			<footer>
				<form action="<c:url value='/inspecao' />" method="post">
					<input type="hidden" name="portal.url" value="${redirectedUrl}">
					<button type="submit" class="btn btn-outline-primary">Sim</button>
				</form>
		
				<button type="button" class="btn btn-outline-secondary" onclick="location.href='<c:url value='/inspecao'/>'">Não</button>
			</footer> 
		</modal>
	</div>

</body>
</html>