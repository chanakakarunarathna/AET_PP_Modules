(function(module) {
    
    module.service('UpdateMasterfieldRequestDTO', [function() {

        function UpdateMasterfieldRequestDTO() {
            
			this.id = undefined;
        	this.name = undefined;
        	this.label = undefined;
        	this.tooltip = undefined;
        	this.maxLength = undefined;
        	this.valueType = undefined;
        	this.isMandatory = undefined;
        	this.options = undefined;
			this.hidden = undefined;
            
        }

        return UpdateMasterfieldRequestDTO;

    }]);

})(angular.module('aet.domain.masterfield'));