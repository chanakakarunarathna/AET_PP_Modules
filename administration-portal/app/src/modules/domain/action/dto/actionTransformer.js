(function(module) {

    module.service('actionTransformer', ['Action', 'genericTransformer',
        function(PartnerRole, genericTransformer) {

            // this.DTOToActions = function(dto) {
            //     return _.map(dto, this.DTOToPartnerRole);
            // }

            this.DTOToActions = function(dto) {
                var action = new Action();
                action.id=dto.id;
                action.title=dto.title;
                action.positionIndex=dto.positionIndex;
                action.channel=dto.channel;
                return action;
            };

            // this.actionToCreateDTO = function(action) {
            //   if(partnerRole.partner && partnerRole.role) {
            //     return {
            //       partnerId: partnerRole.partner.id,
            //       roleId: partnerRole.role.id
            //     };
            //   }
            //   else {
            //     return {};
            //   }
            //
            // };

        }
    ]);

})(angular.module('aet.domain.action'));
