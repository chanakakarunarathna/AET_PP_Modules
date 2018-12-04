(function(module) {
    
    module.service('FieldDTO', [function() {

        function FieldDTO() {

        	this.label = undefined;
        	this.tooltip = undefined;
        	this.maxLength = undefined;
        	this.valueType = undefined;
        	this.isMandatory = undefined;
        	this.order = undefined;
			this.hidden = undefined;
        	this.options = undefined;
        }

        return FieldDTO;

    }]);

})(angular.module('aet.domain.field'));

