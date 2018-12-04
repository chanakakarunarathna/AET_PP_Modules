(function(module) {
    
    module.service('CreateFormDTO', [function() {

        function CreateFormDTO() {

            this.name = undefined;
            //this.feeExpression = undefined;
            this.feeExpressions = [];
			
            this.screens = {};
			this.notifications = {};
			this.isPublished=undefined;
			this.isActive=undefined;

        }

        return CreateFormDTO;

    }]);

})(angular.module('aet.domain.formBuilder'));

