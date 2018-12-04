(function (module) {
  module.service('MissionPreviewNavigationService', ['MissionPreviewNavigationObject', function (MissionPreviewNavigationObject) {


    this.tab = function(){
      var navigationObject =  new MissionPreviewNavigationObject(), self = this;

      navigationObject.isValidFunc = function(actions){
        var isValid = true;
        angular.forEach(actions.questions, function(question, key){
          if(question.inputType === 'SLIDER' && isValid){
            isValid = question.answer !== "0";
          }
        });
        return isValid;
      };

      navigationObject.isVisitedTab = false;

      navigationObject.isVisitedFunc = function(){
        self.isVisitedTab = true;
      };

      return navigationObject;
    }


  }])

})(angular.module('aet.navigation'));
