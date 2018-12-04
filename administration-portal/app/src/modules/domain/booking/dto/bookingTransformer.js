(function(module) {

  module.service('bookingTransformer', ['Booking', 'genericTransformer', 'partnerRoleTransformer','partnerPlaceRoleTransformer', '$log', 'UpdateBookingDTO','SearchResults', 'ResendEmailRequestDTO',
    function(Booking, genericTransformer, partnerRoleTransformer, partnerPlaceRoleTransformer, $log, UpdateBookingDTO, SearchResults, ResendEmailRequestDTO) {

      this.DTOToBooking = function(dto) {
        var booking = genericTransformer.DTOToObject(dto, Booking);

        booking.partnerRoles = partnerRoleTransformer.DTOToPartnerRoles(dto.partnerRoles);
        booking.partnerPlaceRoles = partnerPlaceRoleTransformer.DTOToPartnerPlaceRoles(dto.partnerPlaceRoles);
        booking.bookingEvents = dto.bookingEvents;

//        convert createdDate to Date object
//        var createdDate = new Date(dto.createdTime);
//        booking.createdDate = createdDate.getMonth() + 1 + "/" + createdDate.getDate() + "/" + createdDate.getFullYear();
        return booking;
      };

      this.DTOToBookings = function(dto) {
        return _.map(dto, this.DTOToBooking);
      };

      this.searchDTOToSearchResults = function(dto) {  
        var searchResults = genericTransformer.DTOToSearchResults(dto.data);
        searchResults.results = dto.data.bookings;
        return searchResults;	

      };

      this.bookingToUpdateDTO = function(booking) {
        var dto = genericTransformer.objectToDTO(booking, UpdateBookingDTO);
        dto.partnerRoles = _.map(booking.partnerRoles, partnerRoleTransformer.partnerRoleToCreateDTO);
        return dto;
      };

      this.toResendEmailRequestDTO = function(emailType, email) {
    	  var dto = new ResendEmailRequestDTO();
    	  if(emailType=='Voucher'){
    		  emailType = "VOUCHER";
    	  }else{
    		  emailType = "BOOKING_EMAIL";
    	  }
    	  dto.emailType = emailType;
    	  dto.receiverEmail = email;
          return dto;
      };

      this.toVendorList = function(dto) {
    	  var filters = [{
    		  'key': "",
    		  "value": "&nbsp;"
		  }];
    	  for (var a = 0; a < dto.data.vendorList.length; a++) {
    		var filter = {
    			'key': dto.data.vendorList[a].vendorKey,
    			'value' : dto.data.vendorList[a].name
    	    };
    		filters.push(filter);
          }
    	  return filters;
      };

      this.toVoucherHTML = function(dto) {
    	  var htmlContent = null;
    	  if(dto.data.htmlContent!=null){
    		  htmlContent = dto.data.htmlContent;
    	  }
          return htmlContent;
      };

    }
  ]);

})(angular.module('aet.domain.booking'));
