(function(module) {
    
    module.service('FieldEntryDTO', [function() {

        function FieldEntryDTO() {

        	this.label = undefined;
        	this.tooltip = undefined;
        	this.maxLength = undefined;
        	this.valueType = undefined;
        	this.isMandatory = undefined;
        	this.order = undefined;
			this.value = undefined;
			this.options = undefined;
			this.selectedOptions = undefined;
			this.hidden = undefined;

        }

        return FieldEntryDTO;

    }]);

})(angular.module('aet.domain.fieldEntry'));

