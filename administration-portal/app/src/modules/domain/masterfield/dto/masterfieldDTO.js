(function(module) {
    
    module.service('MasterfieldDTO', [function() {

        function MasterfieldDTO() {
            // define DTO properties, e.g.
            // this.emailaddress = undefined;
        	this.id = undefined;
        	this.name = undefined;
        	this.label = undefined;
        	this.tooltip = undefined;
        	this.maxLength = undefined;
        	this.valueType = undefined;
        	this.isMandatory = undefined;
			this.hidden = undefined;
        	this.options = undefined;
            
        }

        return MasterfieldDTO;

    }]);

})(angular.module('aet.domain.masterfield'));

