
(function (module) {

  /**
   * @ngdoc object
   * @name aet.customfilters.missionchooseaction
   *
   * @description The filter is used to sort mission choose action drop down
   *
   *
   *
   */
   module.filter('checkCurveType', function() {
   return function(inputs,curveType) {
     var outputResults=[];
     angular.forEach(inputs,function(input,key){
       if (input.curveType === curveType || input.curveType === 'BOTH') {
         outputResults.push(input);
       }
     });
     return outputResults;
   };
 });

})(angular.module('aet.customfilters.missionchooseaction'));
