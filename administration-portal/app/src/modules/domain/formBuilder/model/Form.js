(function(module) {
    
    module.service('Form', [function() {

        function Form() {

            this.id = undefined;
            this.name = undefined;
            //this.feeExpression = undefined;
            this.feeExpressions = [];

            this.screens = [];
			this.notifications = {};
			this.isActive =undefined;
			this.isPublished =undefined;
        }

        return Form;

    }]);

})(angular.module('aet.domain.formBuilder'));

