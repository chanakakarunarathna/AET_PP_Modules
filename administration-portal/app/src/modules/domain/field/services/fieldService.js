(function(module) {
    
    module.service('fieldService', ['genericTransformer', 'Field',
        function(genericTransformer, Field) {

            this.createFieldFromMasterField = function(masterField) {
				var field = genericTransformer.objectToDTO(masterField, Field);
				field.type = 'field'; // for dnd
				field.options = masterField.options;
				
				return field;
			};

        }
    ]);

})(angular.module('aet.domain.field'));

