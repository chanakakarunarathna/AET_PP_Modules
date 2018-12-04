(function(module) {

    module.service('bookingEndpoint', ['$q', '$http', 'api', 'partnerDetails',
        function($q, $http, api, partnerDetails) {

            this.updateBooking = function(booking) {

                var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/booking/' + booking.id;
                return $http.put(url, booking);
            };

            this.findBooking = function(id) {

                var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/booking/' + id;
                return $http.get(url);
            };

            this.listBooking = function(params) {
                var listBookingURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/booking';
                return $http.get(listBookingURL, {
                    params: params
                });
            };

            this.searchBookingByPartnerId = function(params) {
                var listBookingByPartnerIdURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/booking';
                return $http.get(listBookingByPartnerIdURL, {
                    params: params
                });
            };

            this.deleteBooking = function(id) {
                var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/booking/' + id;
                return $http.delete(url);
            };

            this.cancelBooking = function(id, booking) {
                var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/booking/' + id + '/cancel';
                return $http.put(url, booking)
            };

            this.publishEvent = function(id, params) {
            	var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/booking/' + id + '/publishevent';
            	return $http.get(url, {
                    params: params
                });
            };
            
            this.resendEmail = function(id, resendEmailRequest) {
            	var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/booking/' + id + '/resendEmail';
            	return $http.post(url, resendEmailRequest
            	);
            };
            
            this.getVendorList = function() {
                var getVendorListURL = api('admin') + '/admin/vendors';
                return $http.get(getVendorListURL);
            };

            this.getVoucherContent = function(id) {
            	var url = api('admin') + '/booking/voucher/' + id;
                return $http.get(url);
            };

            this.manualCancelBooking = function(id, booking) {
                var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/booking/' + id + '/manualcancel';
                return $http.put(url, booking)
            };
        }
    ]);

})(angular.module('aet.endpoints'));
