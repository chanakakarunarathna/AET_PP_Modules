(function(module) {
    
    module.service('MasterField', [function() {

        function MasterField() {

			this.id = undefined;
			this.name = undefined;
        	this.label = undefined;
        	this.tooltip = undefined;
        	this.maxLength = undefined;
        	this.valueType = undefined;
        	this.isMandatory = undefined;
        	this.options = [];
			this.hidden = undefined;

        }

        return MasterField;

    }]);

})(angular.module('aet.domain.masterfield'));

