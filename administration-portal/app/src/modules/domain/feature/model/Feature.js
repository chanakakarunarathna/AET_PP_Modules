(function(module) {
    
    module.service('Feature', [function() {

        function Feature() {

			this.id = undefined;
			this.title = undefined;
			this.description = undefined;
			this.type = undefined;
			this.isSuperAdminFeature = undefined;
			this.isActive = undefined;
			this.isPortalFeature = undefined;
			
			this.dependencies = []
        }

        return Feature;

    }]);

})(angular.module('aet.domain.field'));

