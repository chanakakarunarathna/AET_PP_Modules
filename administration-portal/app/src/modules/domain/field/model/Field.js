(function(module) {
    
    module.service('Field', [function() {

        function Field() {

			this.name = undefined;
        	this.label = undefined;
        	this.tooltip = undefined;
        	this.maxLength = undefined;
        	this.valueType = undefined;
        	this.isMandatory = undefined;
        	this.order = undefined;
			this.hidden = undefined;
        	this.options = [];
			
			// partner side only. This is the unique numerical id assigned
			// to the end of a name for the create permit form.
			this.index = undefined; 

        }

        return Field;

    }]);

})(angular.module('aet.domain.field'));

