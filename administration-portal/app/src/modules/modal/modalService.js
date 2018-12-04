(function(module) {

    /**
     * @ngdoc object
     * @name aet.modals.modalService
     *
     * @description A service used to display modals in the application
     *
     */
    module.service('modalService', ['$modal', 'alertsService', '$log', '$q', '$rootScope', 'userDetails', '$timeout',
        function($modal, alertsService, $log, $q, $rootScope, userDetails, $timeout) {

            this.deleteModal = function(name, isAdminUser, partnerPlacesFound) {

                var scope = $rootScope.$new(true);
                scope.name = name;
                scope.isAdminUser = isAdminUser;
                scope.partnerPlacesFound = partnerPlacesFound;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/deleteModal.html',
                    controller: 'ModalInstanceController',
                    scope: scope

                });
            };

            this.unsavedChangesModal = function(type) {

                var scope = $rootScope.$new(true);
                scope.type = type;
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/saveModal.html',
                    controller: 'UnsavedController',
                    scope: scope,
                    backdrop: 'static',
                    keyboard: false

                });
            };

            this.customDeleteModal = function(name, message) {

                var scope = $rootScope.$new(true);
                scope.name = name;
                scope.message = message;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/customDeleteModal.html',
                    controller: 'ModalInstanceController',
                    scope: scope

                });

            };

            this.customNotificationModal = function(title, message, buttonLabel) {

                var scope = $rootScope.$new(true);
                scope.title = title;
                scope.message = message;
                scope.buttonLabel = buttonLabel;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/customNotificationModal.html',
                    controller: 'ModalInstanceController',
                    scope: scope

                });

            };

            this.partnerPlaceExpiredModal = function(title, message) {

                var scope = $rootScope.$new(true);
                scope.title = title;
                scope.message = message;

                return $modal.open({
                    templateUrl: 'src/modules/modal/templates/editPartnerPlaceModal.html',
                    controller: 'EditPartnerPlaceInstanceController',
                    scope: scope
                });

            };

            this.partnerPlaceAndListWarningModal = function(adminUser, partnerPartnerPlaces, partnerLists) {

                var scope = $rootScope.$new(true);
                scope.adminuser = adminUser;
                scope.partnerPartnerPlaces = partnerPartnerPlaces;
                scope.partnerLists = partnerLists;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/partnerPlaceAndListWarningModal.html',
                    controller: 'partnerPlaceAndListWarningController',
                    scope: scope,
                    backdrop: "static"

                });

            };

            this.customEditModal = function(name, message, editLabel, cancelLabel) {

                var scope = $rootScope.$new(true);
                scope.name = name;
                scope.message = message;
                scope.editLabel = editLabel;
                scope.cancelLabel = cancelLabel;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/customEditModal.html',
                    controller: 'ModalInstanceController',
                    scope: scope

                });
            };

            this.customCopyModal = function(name, message, copyLabel, cancelLabel) {
                var alert = document.getElementById('alert');
                if (alert !== null) {
                    $timeout(function() {
                        var modal = document.getElementsByClassName("modal-dialog");
                        modal[0].style.top = '60px';
                    }, 50);
                }

                var scope = $rootScope.$new(true);
                scope.name = name;
                scope.message = message;
                scope.copyLabel = copyLabel;
                scope.cancelLabel = cancelLabel;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/customCopyModal.html',
                    controller: 'ModalInstanceController',
                    scope: scope

                });

            };

            this.bookingCancel = function(booking, manual) {
                var scope = $rootScope.$new(true);
                scope.booking = booking;
                scope.manual = manual;
                return $modal.open({
                    templateUrl: 'src/modules/modal/templates/deleteBooking.html',
                    controller: 'deleteBookingController',
                    size: 'lg',
                    backdrop: "static",
                    scope: scope

                });

            };

            this.resendEmailModal = function(booking, emailType) {
                var scope = $rootScope.$new(true);
                scope.booking = booking;
                scope.emailType = emailType;
                return $modal.open({
                    templateUrl: 'src/modules/modal/templates/resendEmailModal.html',
                    controller: 'resendEmailModalController',
                    backdrop: "static",
                    scope: scope
                });
            };
            
            this.createMissionSend = function(warningType) {

                var scope = $rootScope.$new(true);
                scope.warningType = warningType;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/createMissionSendModel.html',
                    controller: 'CreateMissionSendController',
                    scope: scope

                });

            };

            this.createDiscussionSend = function(warningType) {

                var scope = $rootScope.$new(true);
                scope.warningType = warningType;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/createDiscussionSendModal.html',
                    controller: 'CreateDiscussionSendController',
                    scope: scope

                });

            };


            this.partnerPlaceNotification = function() {

                var scope = $rootScope.$new(true);
                scope.name = name;
                scope.title = 'Welcome ' + userDetails.getUser().firstName;
                scope.description = 'What Partner Place do you want to work on today?';
                scope.forceAccept = userDetails.getUser().isSuperAdmin;

                if (userDetails.getUser().isSuperAdmin) {
                    return $modal.open({

                        templateUrl: 'src/modules/modal/templates/partnerPlaceNotification.html',
                        controller: 'PartnerPlaceNotificationController',
                        scope: scope,
                        backdrop: 'static',
                        keyboard: false
                    });
                } else {
                    return $modal.open({

                        templateUrl: 'src/modules/modal/templates/partnerPlaceNotification.html',
                        controller: 'PartnerPlaceNotificationController',
                        scope: scope
                    });
                }

            };

            this.partnerPlacePickerNotification = function() {

                var scope = $rootScope.$new(true);
                scope.name = name;
                scope.title = 'Switch Partner Place';
                scope.description = 'What Partner Place do you want to work on next?';
                scope.forceAccept = true;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/partnerPlaceNotification.html',
                    controller: 'PartnerPlaceNotificationController',
                    scope: scope,
                    backdrop: 'static',
                    keyboard: false
                });

            };



            this.addPartnerPlaceTeamList = function() {

                var scope = $rootScope.$new(true);
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/addPartnerPlaceTeam.html',
                    controller: 'PartnerPlaceTeamController',
                    backdrop: "static",
                    scope: scope

                });

            };

            this.addCustomerList = function() {

                var scope = $rootScope.$new(true);
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/createCustomerList.html',
                    controller: 'createCustomerListController',
                    size: 'lg',
                    backdrop: "static",
                    scope: scope

                });

            };

            this.addEmployeeList = function() {

                var scope = $rootScope.$new(true);
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/createEmployeeList.html',
                    controller: 'createEmployeeListController',
                    size: 'lg',
                    backdrop: "static",
                    scope: scope

                });

            };

            this.externalListDuplicateValidation = function(dupilicateListsCount, invalidUsersCount) {

                var scope = $rootScope.$new(true);
                scope.dupilicateListsCount = dupilicateListsCount;
                scope.invalidUsersCount = invalidUsersCount;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/externalListDuplicateValidationModel.html',
                    controller: 'externalListDuplicateValidationController',
                    scope: scope

                });

            };

            this.viewParticipantList = function(participantList) {

                var scope = $rootScope.$new(true);
                scope.participantList = participantList;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/viewParticipantList.html',
                    controller: 'viewParticipantListController',
                    scope: scope

                });

            };

            this.shareMapModal = function(partnerPlaceTeamMembers, partnerPlaceSMEs, stages, dataType) {

                var scope = $rootScope.$new(true);
                scope.partnerPlaceTeamMembers = partnerPlaceTeamMembers;
                scope.partnerPlaceSMEs = partnerPlaceSMEs;
                scope.stages = stages;
                scope.dataType = dataType;

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/shareMap.html',
                    controller: 'ShareMapController',
                    scope: scope,
                    size: 'lg',
                    backdrop: "static"

                });

            };

            this.viewPartnerPlaceList = function(partnerPlaceList) {
                var scope = $rootScope.$new(true);
                scope.partnerPlaceList = partnerPlaceList;
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/partnerPlaceListSelector.html',
                    controller: 'viewPartnerPlaceListController',
                    scope: scope

                });
            };

            this.partnerPlaceDurationError = function(data, type) {
                var scope = $rootScope.$new(true);
                scope.data = data;
                scope.title = 'Date cannot be changed';
                scope.buttonLabel = 'Ok';

                scope.tableData = [];
                angular.forEach(scope.data.missions, function(mission, mKey) {
                    var rowItem = {};
                    rowItem.type = 'Performance Mission';
                    rowItem.title = mission.title;
                    rowItem.audience = mission.participantType;
                    scope.tableData.push(rowItem);
                });
                angular.forEach(scope.data.discoveryMissions, function(mission, mKey) {
                    var rowItem = {};
                    rowItem.type = 'Discovery Mission';
                    rowItem.title = mission.title;
                    rowItem.audience = mission.participantType;
                    scope.tableData.push(rowItem);
                });
                angular.forEach(scope.data.discussions, function(discussion, dKey) {
                    var rowItem = {};
                    rowItem.type = 'Discussion';
                    rowItem.title = discussion.title;
                    rowItem.audience = discussion.participantType;
                    scope.tableData.push(rowItem);
                });


                scope.description = '<p>Partner Place ' + type + ' cannot be selected because you have ';
                scope.description += ((data.missions.length > 1) ? 'Performance Missions' : ((data.missions.length === 1) ? 'a Performance Mission' : ''));
                scope.description += ((data.missions.length > 0) && (data.discoveryMissions.length > 0) ? ((data.discussions.length > 0) ? ', ' : ' and ') : '');
                scope.description += ((data.discoveryMissions.length > 1) ? 'Discovery Missions' : ((data.discoveryMissions.length === 1) ? 'a Discovery Mission' : ''));
                scope.description += ((data.discoveryMissions.length > 0) && (data.discussions.length > 0) ? ' and ' : '');
                scope.description += ((data.discussions.length > 1) ? 'Discussions' : ((data.discussions.length === 1) ? 'a Discussion' : ''));
                scope.description += ' that ' + (angular.equals(type, 'Start Date') ? 'precede' : 'exceed') + ' that ' + type + '.</p>';

                scope.description += '<p>Please update the ';
                scope.description += ((data.missions.length > 0) ? 'Performance Mission' : '');
                scope.description += ((data.missions.length > 0) && (data.discoveryMissions.length > 0) ? ((data.discussions.length > 0) ? ', ' : ' and ') : '');
                scope.description += ((data.discoveryMissions.length > 0) ? 'Discovery Mission' : '');
                scope.description += (((data.discussions.length > 0) && (data.discoveryMissions.length > 0)) ? ' and ' : '');
                scope.description += ((data.discussions.length > 0) ? 'Discussion' : '');
                scope.description += ' Date' + (((data.missions.length > 1) || (data.discussions.length > 1) || (data.discoveryMissions.length > 1)) ? 's' : '') + ', then come back and update the Partner Place ' + type + '.</p>';
                scope.description += '<p>These item(s) need your attention:</p>';

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/partnerPlaceDurationNotification.html',
                    controller: 'ModalInstanceController',
                    size: 'lg',
                    scope: scope

                });
            };

            this.copyPartnerPlace = function(partnerPlace, adminusers) {
                var scope = $rootScope.$new(true);
                scope.origpartnerplace = partnerPlace;
                scope.adminusers = adminusers;
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/copyPartnerPlace.html',
                    controller: 'copyPartnerPlaceController',
                    size: 'lg',
                    backdrop: "static",
                    scope: scope

                });

            };

            this.copyPTMission = function(mission, type) {

                var scope = $rootScope.$new(true);
                scope.origMission = mission;
                scope.type = type;
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/copyMission.html',
                    controller: 'copyPTMissionController',
                    size: 'lg',
                    backdrop: "static",
                    scope: scope

                });

            };

            this.copyEmployeeMission = function(mission, type) {

                var scope = $rootScope.$new(true);
                scope.origMission = mission;
                scope.type = type;
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/copyMission.html',
                    controller: 'copyEmployeeMissionController',
                    size: 'lg',
                    backdrop: "static",
                    scope: scope

                });

            };

            this.copyCustomerMission = function(mission, type) {

                var scope = $rootScope.$new(true);
                scope.origMission = mission;
                scope.type = type;
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/copyMission.html',
                    controller: 'copyCustomerMissionController',
                    size: 'lg',
                    backdrop: 'static',
                    scope: scope

                });

            };

            this.deliveryStatus = function(participantList, feedbackChannelType, feedbackChannelObj) {

                var scope = $rootScope.$new(true);
                scope.participantList = participantList;
                scope.feedbackChannelType = feedbackChannelType;
                scope.feedbackChannelObj = feedbackChannelObj;
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/viewDelieveryStatus.html',
                    controller: 'viewDelieveryStatusController',
                    size: 'lg',
                    scope: scope

                });
            };

            this.clearPartnerPlaceEmailConfFields = function() {
                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/clearPartnerPlaceEmailConfFields.html',
                    controller: 'ClearPartnerPlaceEmailConfFieldsController'

                });
            };

            this.addNote = function(point) {

                var scope = $rootScope.$new(true);
                scope.origPoint = angular.copy(point);
                scope.point = angular.copy(point);

                return $modal.open({

                    templateUrl: 'src/modules/modal/templates/addNote.html',
                    controller: 'NoteController',
                    scope: scope

                });
            };

            this.viewVoucher = function(voucherType, voucherUrls) {
                var scope = $rootScope.$new(true);
                scope.voucherType = voucherType;
                scope.voucherUrls = voucherUrls;
                return $modal.open({
                    templateUrl: 'src/modules/modal/templates/viewVoucher.html',
                    controller: 'viewVoucherController',
                    backdrop: "static",
                    scope: scope
                });

            };
        }
    ]);

})(angular.module('aet.modals'));
