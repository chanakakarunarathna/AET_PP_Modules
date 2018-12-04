(function(module) {

    module.controller('PollsController', ['$scope','adminService', 'searchManager', '$state', 'modalService',
        function($scope, adminService, searchManager, $state, modalService) {
			$scope.searchManager = searchManager;

			$scope.deletePoll = function(external) {
				modalService.deleteModal(external.id, $scope.searchManager, adminService.deleteAdmin);
			};

			$scope.editPoll = function(external) {
				$state.go('index.secured.polls.edit', {id:external.id});
			};

			
        }
    ]);

})(angular.module('aet.screens.polls'));
