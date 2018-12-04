(function(module) {
    
    module.service('fieldTransformer', ['genericTransformer', 'FieldDTO', 'Field', '$log',
        function(genericTransformer, FieldDTO, Field, $log) {

            this.fieldToFieldDTO = function(field) { //field = Field obj
            	return genericTransformer.objectToDTO(field, FieldDTO);
            };
			
			this.getDTOToField = function(dto, name) {
				
				var field = genericTransformer.DTOToObject(dto, Field);
				field.name = name;
				field.type = 'field';
				
				return field;
			};
           
        }
]);

})(angular.module('aet.domain.field'));

