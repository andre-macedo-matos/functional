var modalApp = angular.module('modalApp', []);

modalApp.directive('modal', function () {
    return {
      templateUrl: 'resources/template/modal.html',
      restrict: 'E',
      transclude: true,
      replace:true,
      scope:true,
      backdrop: 'static',
      keyboard: false,
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
    
 })
	 .directive('footer', function() {
		 return {
			 require: '^^modal',
			 templateUrl: 'resources/template/modal-footer.html',
			 restrict: 'E',
			 transclude: true
		 };
	});
;
	 
