(function(module) {

  module.service('Product', [function() {

    function Product() {

      this.productId = undefined;
      this.languageCode = undefined;
      this.productName = undefined;
      this.loyaltyId = undefined;
      this.vendor = undefined;
      this.itinerary = undefined;
      this.productText = undefined;
      this.rewardPoints = undefined;
      
    }
      return Product;

    }]);

})(angular.module('aet.domain.product'));
