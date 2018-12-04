(function(module) {
    
    module.service('Role', [function() {

        function Role() {

            this.id = undefined;
            this.title = undefined;
            this.description = undefined;
            this.isActive = undefined;
			
			this.features = [];

        }

        return Role;

    }]);

})(angular.module('aet.domain.role'));