(function(module) {

  module.service('userDetails', ['localStorageService', 'partnerDetails',
    function(localStorageService, partnerDetails) {

      var _user = undefined;
      var userStorageKey = 'userId';

      this.setUser = function(user) {
        if (angular.isDefined(user)) {
          _user = user;
          localStorageService.set(userStorageKey, _user.id);
        }

      };

      this.getUser = function() {
        return _user;
      };

      this.getUserId = function() {

        if (angular.isDefined(_user)) {
          return _user.id;
        } else if (angular.isDefined(localStorageService.get(userStorageKey))) {
          return localStorageService.get(userStorageKey);
        }

      };

      this.clear = function() {
        _user = undefined;
        localStorageService.remove(userStorageKey);
      };

    }
  ]);

})(angular.module('aet.security'));
