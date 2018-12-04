(function(module) {
    
    module.service('FieldEntry', [function() {

        function FieldEntry() {

			this.name = undefined;
        	this.label = undefined;
        	this.tooltip = undefined;
        	this.maxLength = undefined;
        	this.valueType = undefined;
        	this.isMandatory = undefined;
        	this.order = undefined;
			this.value = undefined;
			this.hidden = false;
			this.options = undefined;
			this.selectedOptions = undefined;

        }

        return FieldEntry;

    }]);

})(angular.module('aet.domain.fieldEntry'));

