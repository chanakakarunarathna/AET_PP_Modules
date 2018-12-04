(function(module) {
    
    module.service('CreateRoleRequestDTO', [function() {

        function CreateRoleRequestDTO() {
            
        	this.title = undefined;
			this.description = undefined;
			
			this.featureIds = [];
        }

        return CreateRoleRequestDTO;

    }]);

})(angular.module('aet.domain.role'));