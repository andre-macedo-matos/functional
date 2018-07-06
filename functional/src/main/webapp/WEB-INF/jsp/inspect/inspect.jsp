<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

<title>Inspeção</title>
</head>
<body ng-app="modalApp" ng-controller="MainCtrl" >
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
		<button type="submit">Inspecionar</button>
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
	    
	  <modal title="Redirecionamento" visible="showModal">
	  	<c:if test="${not empty redirected}">
			<p>${errorMessage}</p>
		</c:if>
	  	<p>Deseja continuar?</p>
	    <form action="<c:url value='/inspecao' />" method="post">
			<input type="hidden" name="portal.url" value="${redirected}">
		  	<button type="submit" class="btn btn-default">Sim</button>
		</form>
	    <button type="button" class="btn btn-default" onclick="location.href='<c:url value='/inspecao'/>'">Não</button>
	  </modal>
	</div>

<script>
var modalApp = angular.module('modalApp', []);

modalApp.controller('MainCtrl', function ($scope) {
    $scope.toggleModal = function(){
        $scope.showModal = true;
    };
  });

modalApp.directive('modal', function () {
    return {
      template: '<div class="modal fade">' + 
          '<div class="modal-dialog">' + 
            '<div class="modal-content">' + 
              '<div class="modal-header">' + 
                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' + 
                '<h4 class="modal-title">{{ title }}</h4>' + 
              '</div>' + 
              '<div class="modal-body" ng-transclude></div>' + 
            '</div>' + 
          '</div>' + 
        '</div>',
      restrict: 'E',
      transclude: true,
      replace:true,
      scope:true,
      link: function postLink(scope, element, attrs) {
        scope.title = attrs.title;

        scope.$watch(attrs.visible, function(value){
          if(value == true)
            $(element).modal('show');
          else
            $(element).modal('hide');
        });

        $(element).on('shown.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = true;
          });
        });

        $(element).on('hidden.bs.modal', function(){
          scope.$apply(function(){
            scope.$parent[attrs.visible] = false;
          });
        });
      }
    };
  });
</script>

</body>
</html>