(function(module) {
    
    module.service('FormStatus', [function() {
		
		return [
			{
				name: 'STARTED',
				label: 'Started'
			},
			{
				name: 'PENDING_PAYMENT',
				label: 'Pending Payment'
			},
			{
				name: 'SUBMITTED',
				label: 'Submitted'
			},
			{
				name: 'SUBMITTED_PENDING_FEES',
				label: 'Pending Additional Payment'
			},
			{
				name: 'SUBMITTED_PENDING_EDIT',
				label: 'Pending Edits'
			},
			{
				name: 'APPROVED',
				label: 'Approved'
			},
		]
		
    }]);

})(angular.module('aet.domain.formBuilder'));