(function(module) {
    
    module.service('fieldEntryTransformer', ['genericTransformer', 'FieldEntryDTO', 'FieldEntry', '$log', '$filter',
        function(genericTransformer, FieldEntryDTO, FieldEntry, $log, $filter) {

            this.fieldEntryToFieldEntryDTO = function(field) {
				
            	var fieldEntryDTO = genericTransformer.objectToDTO(field, FieldEntryDTO);
				
				if(fieldEntryDTO.valueType === 'DATE') {
					fieldEntryDTO.value = $filter('date')(fieldEntryDTO.value, 'MM/dd/yyyy');
				}
				
				return fieldEntryDTO;
            };
			
			this.getDTOToFieldEntry = function(dto, name) {
				var fieldEntry = genericTransformer.DTOToObject(dto, FieldEntry);
				fieldEntry.name = name;
				fieldEntry.value = this.convertFieldEntryValue(fieldEntry);
				
				return fieldEntry;
			};
			
			this.convertFieldEntryValue = function(fieldEntry) {
				var value = fieldEntry.value;
				
				switch(fieldEntry.valueType) {
					case "NUMBER":
						value = parseInt(value);
						break;
						
					case "YESNO":
						value = value === 'true';
						break;
				}
				
				return value;
			};
           
        }
]);

})(angular.module('aet.domain.fieldEntry'));