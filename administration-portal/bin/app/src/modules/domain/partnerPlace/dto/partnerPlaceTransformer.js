(function(module) {

  module.service('partnerPlaceTransformer', ['PartnerPlace', 'genericTransformer', 'partnerRoleTransformer', '$log', 'CreatePartnerPlaceDTO', 'UpdatePartnerPlaceDTO', 'ChangeChannelDTO', 'SendTestEmailDTO',
    function(PartnerPlace, genericTransformer, partnerRoleTransformer, $log, CreatePartnerPlaceDTO, UpdatePartnerPlaceDTO, ChangeChannelDTO, SendTestEmailDTO) {

      this.DTOToPartnerPlace = function(dto) {

        var partnerPlace = genericTransformer.DTOToObject(dto, PartnerPlace);
        partnerPlace.partnerPlaceAdminIds = dto.partnerPlaceLeaderIds;
        partnerPlace.partnerPlaceSupportMembers = dto.partnerPlaceSupportMembers;
        partnerPlace.partnerPlaceTeamMembers = dto.partnerPlaceTeamMembers;
        partnerPlace.partnerCollection = dto.partnerCollection;
        partnerPlace.topTips = dto.topTips;
        partnerPlace.partner = dto.partner;
        partnerPlace.isTopDestination = dto.isTopDestination.toString();
        partnerPlace.hasCityGuide = dto.hasCityGuide.toString();
        partnerPlace.vendorActivityCount = dto.vendorActivityCount;
        return partnerPlace;
      };

      this.DTOToPartnerPlaces = function(dto) {
        return _.map(dto, this.DTOToPartnerPlace);
      };

      this.searchDTOToSearchResults = function(dto) {
        var searchResults = genericTransformer.DTOToSearchResults(dto.data);

        angular.forEach(dto.data.partnerPlaces, function(partnerPlace, partnerPlaceKey) {
        	partnerPlace.creatorName = partnerPlace.location;
        });

        searchResults.results = dto.data.partnerPlaces;
        return searchResults;
      };

      this.loginDTOToPartnerPlace = function(dto) {
        var partnerPlace = genericTransformer.DTOToObject(dto, PartnerPlace);
        partnerPlace.id = dto.userId;
        partnerPlace.email = dto.email;
        return partnerPlace;
      };

      this.partnerPlaceToCreateDTO = function(partnerPlace) {
        var dto = genericTransformer.objectToDTO(partnerPlace, CreatePartnerPlaceDTO);
        dto.partnerPlaceAdminIds = partnerPlace.partnerPlaceLeaderIds;
        dto.partnerPlaceTeamMemberIds = partnerPlace.partnerPlaceTeamMemberIds;
        dto.partnerPlaceSupportMemberIds = partnerPlace.partnerPlaceSupportMemberIds;
        dto.partnerCollection = partnerPlace.partnerCollection;
        dto.topTips = partnerPlace.topTips;
        dto.isTopDestination = partnerPlace.isTopDestination.key;
        dto.hasCityGuide = partnerPlace.hasCityGuide.key;
        dto.vendorActivityCount = partnerPlace.vendorActivityCount;

        return dto;
      };

      this.partnerPlaceToUpdateDTO = function(partnerPlace) {
        var dto = genericTransformer.objectToDTO(partnerPlace, UpdatePartnerPlaceDTO);
        dto.partnerPlaceAdminIds = partnerPlace.partnerPlaceLeaderIds;
        dto.partnerPlaceTeamMemberIds = partnerPlace.partnerPlaceTeamMemberIds;
        dto.partnerPlaceSupportMemberIds = partnerPlace.partnerPlaceSupportMemberIds;
        dto.partnerCollection = partnerPlace.partnerCollection;
        dto.topTips = partnerPlace.topTips;
        dto.isTopDestination = partnerPlace.isTopDestination.key;
        dto.hasCityGuide = partnerPlace.hasCityGuide.key;
        dto.vendorActivityCount = partnerPlace.vendorActivityCount;
        return dto;
      }

      this.channelToChangeChannelDTO = function(channel) {
        var dto = genericTransformer.objectToDTO(channel, ChangeChannelDTO);
        return dto;
      };

      this.testEmailToSendTestEmailDTO = function(testEmail) {
        var dto = genericTransformer.objectToDTO(testEmail, SendTestEmailDTO);
        return dto;
      };

      this.DTOToValidateDurationResponse = function(reponse) {
        reponse.isValid = (angular.isUndefined(reponse.missions) || reponse.missions.length === 0) && (angular.isUndefined(reponse.discussions) || reponse.discussions.length === 0) && (angular.isUndefined(reponse.discoveryMissions) || reponse.discoveryMissions.length === 0);
        angular.forEach(reponse.missions, function(mission, mKey) {
          mission.creatorName = mission.creator.firstName + " " + mission.creator.lastName;
          var startDate = new Date(mission.startDate);
          var endDate = new Date(mission.endDate);
          mission.startDate = startDate.getMonth() + 1 + "/" + startDate.getDate() + "/" + startDate.getFullYear();
          mission.endDate = endDate.getMonth() + 1 + "/" + endDate.getDate() + "/" + endDate.getFullYear();
        });

        angular.forEach(reponse.discoveryMissions, function(dMission, mKey) {
          dMission.creatorName = dMission.creator.firstName + " " + dMission.creator.lastName;
          var startDate = new Date(dMission.startDate);
          var endDate = new Date(dMission.endDate);
          dMission.startDate = startDate.getMonth() + 1 + "/" + startDate.getDate() + "/" + startDate.getFullYear();
          dMission.endDate = endDate.getMonth() + 1 + "/" + endDate.getDate() + "/" + endDate.getFullYear();
        });

        angular.forEach(reponse.discussions, function(discussion, dKey) {
          discussion.creatorName = discussion.creator.firstName + " " + discussion.creator.lastName;
          var startDate = new Date(discussion.startDate);
          var endDate = new Date(discussion.endDate);
          discussion.startDate = startDate.getMonth() + 1 + "/" + startDate.getDate() + "/" + startDate.getFullYear();
          discussion.endDate = endDate.getMonth() + 1 + "/" + endDate.getDate() + "/" + endDate.getFullYear();
        });
        return reponse;
      };

      this.partnerPlaceParamConvert = function(params) {
        if(params != undefined  && params.sortby != undefined){
          if(params.sortby == 'parentId'){
            params.sortby = 'id'
          }else if(params.sortby == 'parentWebId'){
            params.sortby = 'webid'
          }else if(params.sortby == 'location'){
            params.sortby = 'name'
          }else if(params.sortby == 'activityCount'){
            params.sortby = 'count'
          }else if(params.sortby == 'isTopDestination'){
            params.sortby = 'topdestination'
          }else if(params.sortby == 'hasCityGuide'){
            params.sortby = 'cityguide'
          }
        }
        return params;
      };

    }
  ]);

})(angular.module('aet.domain.partnerPlace'));
