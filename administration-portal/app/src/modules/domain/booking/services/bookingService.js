(function(module) {

    module.service('bookingService', ['bookingEndpoint', 'superAdminEndpoint', 'bookingTransformer', '$q', '$log', 'partnerDetails', 'PartnerRole', 'userDetails', 'partnerPlaceService', '$stateParams', 'partnerPlaceDetails',
        function(bookingEndpoint, superAdminEndpoint, bookingTransformer, $q, $log, partnerDetails, PartnerRole, userDetails, partnerPlaceService, $stateParams, partnerPlaceDetails) {

            this.deleteBooking = function(id) {
                return bookingEndpoint.deleteBooking(id).then(function(response) {
                    return response.data;
                }, function(err) {
                    $log.debug("Could not delete booking", err);
                    return $q.reject(err);
                });
            };

            this.updateBooking = function(booking) {
                if (booking.isSuperAdmin) {
                    booking.partnerRoles = [{}];
                }

                var dto = bookingTransformer.bookingToUpdateDTO(booking);

                return bookingEndpoint.updateBooking(dto).then(function(response) {
                    return response.data;
                }, function(err) {
                    $log.debug("Could not update business user", err);
                    return $q.reject(err);
                });

            };

            this.findBooking = function(id) {

                return bookingEndpoint.findBooking(id).then(function(response) {
                    return bookingTransformer.DTOToBooking(response.data);
                }, function(err) {
                    console.error("Could not load business user");
                    return $q.reject(err);
                });

            };

            this.searchBooking = function(params) {
                return bookingEndpoint.listBooking(params).then(function(dto) {
                    return bookingTransformer.searchDTOToSearchResults(dto);
                }, function(err) {
                    console.error("Could not search booking", err);
                    return $q.reject(err);
                });

            };

            this.searchBookingByPartnerId = function(params) {

                return bookingEndpoint.searchBookingByPartnerId(params).then(function(dto) {
                    return bookingTransformer.searchDTOToSearchResults(dto);
                }, function(err) {
                    console.error("Could not search bookings by partner id", err);
                    return $q.reject(err);
                });

            };

            this.cancelBooking = function(params) {

                var booking = {};
                booking.cancellationReasonCode = params.cancellationReason.cancelCode;

                return bookingEndpoint.cancelBooking(params.bookingId, booking).then(function(response) {
                    return response.data;
                }, function(err) {
                    console.error("Could not cancel Booking");
                    return $q.reject(err);
                });
            };

            this.publishEvent = function(booking) {
            	var params = {};
            	return bookingEndpoint.publishEvent(booking.bookingId, params).then(function(response) {
                    return response.data;
                }, function(err) {
                    console.error("Could not resend email Booking");
                    return $q.reject(err);
                });
            };
            
            this.resendEmail = function(booking, emailType, email) {
            	var resendEmailRequest = bookingTransformer.toResendEmailRequestDTO(emailType, email);
            	return bookingEndpoint.resendEmail(booking.bookingId, resendEmailRequest).then(function(response) {
                    return response.data;
                }, function(err) {
                    console.error("Could not resend email Booking");
                    return $q.reject(err);
                });
            };

            this.getVendorList = function() {
                return bookingEndpoint.getVendorList().then(function(dto) {
                    return bookingTransformer.toVendorList(dto);
                }, function(err) {
                    console.error("Could not get vendor list", err);
                    return $q.reject(err);
                });
            };

            this.getVoucherContent = function(id) {
            	return bookingEndpoint.getVoucherContent(id).then(function(dto) {
            		return bookingTransformer.toVoucherHTML(dto);
                }, function(err) {
                    console.error("Could not get Voucher content", err);
                    return $q.reject(err);
                });
            };

            this.manualCancelBooking = function(params) {

                var booking = {};
                booking.refundType = params.refundType.key;
                if(params.cancellationAmount != undefined && params.cancellationAmount != null){
                	booking.cancellationAmount = params.cancellationAmount;
                }
                if(params.cancellationPercentage != undefined && params.cancellationPercentage != null){
                	booking.cancellationAmount = params.total.finalTotal*(params.cancellationPercentage/100);
                }
                booking.cancellationReasonCode = params.cancellationReason.cancelCode;

                return bookingEndpoint.manualCancelBooking(params.bookingId, booking).then(function(response) {
                    return response.data;
                }, function(err) {
                    console.error("Could not manual cancel Booking");
                    return $q.reject(err);
                });
            };

            this.searchBookingByPartnerIdEditPartnerPlace = function(params, partnerPlaceId) {

                return bookingEndpoint.searchBookingByPartnerId(params).then(function(dto) {
                    partnerPlaceService.findPartnerPlace(partnerPlaceId).then(function(booking) {

                        var bookings = dto.data.bookings;
                        var selectedBookings = booking;
                        var users = [];

                        angular.forEach(selectedBookings.partnerPlaceAdmins, function(partnerPlaceAdmin, partnerPlaceAdminsKey) {
                            users.push(partnerPlaceAdmin);
                        });
                        angular.forEach(selectedBookings.partnerPlaceLeaders, function(partnerPlaceLeader, partnerPlaceAdminsKey) {
                            users.push(partnerPlaceLeader);
                        });
                        angular.forEach(selectedBookings.partnerPlaceSupportMembers, function(partnerPlaceSupportMember, partnerPlaceAdminsKey) {
                            users.push(partnerPlaceSupportMember);
                        });
                        angular.forEach(selectedBookings.partnerPlaceTeamMembers, function(partnerPlaceTeamMember, partnerPlaceAdminsKey) {
                            users.push(partnerPlaceTeamMember);
                        });

                        angular.forEach(users, function(user, userKey) {
                            for (var i = 0; bookings.length > i; i++) {
                                if (bookings[i].id === user.id) {
                                    bookings.splice(i, 1);
                                }
                            }
                        });

                        return bookings;
                    });


                    return bookingTransformer.searchDTOToSearchResults(dto);


                }, function(err) {
                    console.error("Could not search bookings by partnerId edit partner place", err);
                    return $q.reject(err);
                });

            };


            this.getAuthorizedPartners = function(booking) {
                return _.map(booking.partnerRoles, function(partnerRole) {
                    return partnerRole.partner;
                });
            };

        }
    ]);

})(angular.module('aet.domain.booking'));
