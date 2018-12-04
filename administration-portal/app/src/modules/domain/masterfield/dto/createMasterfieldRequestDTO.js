(function(module) {
    
    module.service('CreateMasterfieldRequestDTO', [function() {

        function CreateMasterfieldRequestDTO() {
            
        	this.name = undefined;
        	this.label = undefined;
        	this.tooltip = undefined;
        	this.maxLength = undefined;
        	this.valueType = undefined;
        	this.isMandatory = undefined;
			this.hidden = undefined;
        	this.options = undefined;
        }

        return CreateMasterfieldRequestDTO;

    }]);

})(angular.module('aet.domain.masterfield'));