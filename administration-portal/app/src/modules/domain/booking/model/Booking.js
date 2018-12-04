(function(module) {

  module.service('Booking', [function() {

    function Booking() {

      this.bookingId = undefined;
      this.partnerId = undefined;
      this.customerId = undefined;
      this.loyaltyId = undefined;
      this.bookingReference = undefined;
      this.bookingOptions = undefined;
      this.extendedAttributes = undefined;
      this.createdTime = undefined;
      this.loyaltyAccount = {};
      this.bookerDetails = {};
      this.payment = {};
      this.total = {};
      this.bookingSummary = {};
    }
      return Booking;

    }]);

})(angular.module('aet.domain.booking'));
