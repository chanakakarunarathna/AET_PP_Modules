(function(module) {

    module.service('productService', ['productEndpoint', 'productTransformer', '$q', '$log', 'partnerDetails', 'userDetails', '$stateParams',
        function(productEndpoint, productTransformer, $q, $log, partnerDetails, userDetails, $stateParams) {

            this.searchProducts = function(params) {
                return productEndpoint.searchProducts(params).then(function(dto) {
                    return productTransformer.searchDTOToSearchResults(dto);
                }, function(err) {
                    console.error("Could not search booking", err);
                    return $q.reject(err);
                });

            };
        }
    ]);

})(angular.module('aet.domain.product'));
