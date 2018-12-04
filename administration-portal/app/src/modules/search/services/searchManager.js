(function (module) {

  /**
   * @ngdoc service
   * @name aet.search.service:SearchManager
   *
   * @description
   * A service for searching. Can be used with an {@link aet.search.directive:aetSearch aetSearch} to streamline creating
   * searchable table results.
   *
   * @example <caption>Set params and search</caption>
   * ```
   * var searchManager = new SearchManager(mySearchFunction);
   * searchManager.debounceDelay = 2000;
   * searchManager.count = 100;
   * searchManager.search();
   * searchManager.promise.then(searchReults)
   * ```
   *
   *
   */

  module.service('SearchManager', ['$log', '$q', 'aetDebounce', 'SearchResults',
    function ($log, $q, aetDebounce, SearchResults) {

      /**
       *
       * @ngdoc function
       * @name aet.search.service:SearchManager#SearchManager
       * @methodOf aet.search.service:SearchManager
       *
       * @description The SearchManager constructor.
       *
       * @param {function} searchFn This function should return a promise that that yields a {@link aet.search.object:SearchResults SearchResults} object.
       *
       */
      function SearchManager(searchFn, searchCount) {

        var self = this;

        this.searchFn = searchFn;

        /**
         *
         * @ngdoc property
         * @name aet.search.service:SearchManager#searchResults
         * @propertyOf aet.search.service:SearchManager
         *
         * @description A {@link aet.search.object:SearchResults SearchResults} object. Populated when a successful response is received.
         *
         */
        this.searchResults;

        this.query = '';

        /**
         *
         * @ngdoc property
         * @name aet.search.service:SearchManager#debounceDelay
         * @propertyOf aet.search.service:SearchManager
         *
         * @description The delay between typing and when a call to the backend is actually made
         *
         * @default 1000
         *
         */
        this.debounceDelay = 3000;

        /**
         *
         * @ngdoc property
         * @name aet.search.service:SearchManager#maxWait
         * @propertyOf aet.search.service:SearchManager
         *
         * @description The maximum debounce delay.
         *
         * @default 3000
         *
         */
        this.maxWait = 3000;

        this.page = 1;

        /**
         *
         * @ngdoc property
         * @name aet.search.service:SearchManager#count
         * @propertyOf aet.search.service:SearchManager
         *
         * @description The number of results to return
         *
         * @default 10
         *
         */
        this.count = searchCount || 15;

        this.maxPages = 10;

        /**
         *
         * @ngdoc property
         * @name aet.search.service:SearchManager#showLoading
         * @propertyOf aet.search.service:SearchManager
         *
         * @description This equals true whenever the debounce is active or we are waiting for a response from the server
         *
         */
        this.showLoading = false;

        this.fieldToSearch = {
          name: 'q',
          urlParam: 'q',
          label: 'All'
        };

        /**
         *
         * @ngdoc property
         * @name aet.search.service:SearchManager#promise
         * @propertyOf aet.search.service:SearchManager
         *
         * @description A promise that will is resolved whenever search results are retrieved
         *
         */
        this.promise = undefined;

        var debouncedFn = aetDebounce(this.searchFn, this.debounceDelay);

        this.filters = [];

        this.sortableFields = [];

        /**
         *
         * @ngdoc method
         * @name aet.search.service:SearchManager#search
         * @methodOf aet.search.service:SearchManager
         *
         * @description Performs a search immediately without any debounce delay.
         *
         * @returns {promise} A promise that returns the SearchManager instance
         *
         */
        this.search = function () {

          debouncedFn.cancel();

          this.showLoading = true;

          var searchObject = this.createSearchObject(Math.max(this.page - 1, 0));

          return this.searchFn(searchObject).then(function (searchResults) {
            self.searchResults = searchResults;
            return self;
          }, function (err) {
            console.error("Search unsuccessful", err);
            return self;
          }).finally(function () {
            self.showLoading = false;
          });

        };

        /**
         *
         * @ngdoc method
         * @name aet.search.service:SearchManager#debouncedSearch
         * @methodOf aet.search.service:SearchManager
         *
         * @description Performs a debounced search.
         *
         * @returns {promise} A promise that returns the SearchManager instance
         *
         */
        this.debouncedSearch = function () {
          this.showLoading = true;

          var searchObject = this.createSearchObject(0);

          if (this.query !== '') {
            if (this.promise) {
              debouncedFn(searchObject);
            }
            else {
              this.promise = debouncedFn(searchObject).then(function (result) {
                self.page = 0;
                self.searchResults = result;
              }, function (err) {
                self.searchResults = new SearchResults();
              }).finally(function () {
                self.promise = undefined;
                self.showLoading = false;
              });
            }
          }
          else {
            if (angular.isDefined(debouncedFn))
              debouncedFn.cancel();

            if (this.promise) {
              debouncedFn(searchObject);
            }
            else {
              this.promise = this.searchFn(searchObject).then(function (result) {
                self.page = 1;
                self.searchResults = result;
              }, function (err) {
                self.searchResults = new SearchResults();
              }).finally(function () {
                self.promise = undefined;
                self.showLoading = false;
              });
            }
          }
        };

        this.delete = function (id) {
          _.remove(this.searchResults.results, function (result) {
            return result.id === id;
          });
          --this.searchResults.pagination.total;
        };

        this.createSearchObject = function (page) {
          var searchObject = {
            page: page,
            count: this.count
          };

          var sortableField = _.find(this.sortableFields, function (field) {
            return field.sortState !== undefined;
          });
          if (sortableField) {
            searchObject.sortby = sortableField.name;
            searchObject.sortdirection = sortableField.sortState;
          }

          searchObject[this.fieldToSearch.urlParam] = this.query;

          _.forEach(this.filters, function (filter) {
            if (angular.isDefined(filter.value))
              searchObject[filter.name] = filter.value;
          });

          return searchObject;
        };

        this.addFilter = function (filter) {
          this.filters.push(filter);
        };

        this.removeFilter = function (filter) {
          _.remove(this.filters, function (aFilter) {
            return aFilter.name === filter.name && aFilter.label === filter.label;
          });
        };

      }

      return SearchManager;
    }
  ]);

})(angular.module('aet.search'));
