(function(module) {
    
	module.factory('aetDebounce', ['$timeout', '$q', function($timeout, $q) {
		
		return function debounce(func, wait, immediate) {
			var timeout;
			var deferred = $q.defer();
			
			 function debounced() {
				
				var context = this;
				var args = arguments;

				var later = function() {
					timeout = null;
					if(!immediate) {
						deferred.resolve(func.apply(context, args));
						deferred = $q.defer();
					}
				};

				var callNow = immediate && !timeout;

				if(timeout) {
					$timeout.cancel(timeout);
				}

				timeout = $timeout(later, wait);

				if(callNow) {
					deferred.resolve(func.apply(context, args));
					deferred = $q.defer();
				}

				return deferred.promise;
				 
			}
			
			function cancel() {
				if(timeout)
					$timeout.cancel(timeout);
			}
			
			debounced.cancel = cancel;
			
			return debounced;
		}
		
	}]);
    
})(angular.module('aet.debounce'));