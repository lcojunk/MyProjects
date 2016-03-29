(function () {
    'use strict';

    angular
        .module('searchApp.search')
        .controller('input',
            ['$timeout', '$rootScope', 'searchService', 'SearchModel', 'UTIL', input]);

    function input($timeout, $rootScope, searchService, SearchModel, UTIL) {
        var vm = this;
        vm.model = undefined;
        vm.search = search;
        vm.reset = reset;
        vm.getSuggestions = getSuggestions;

        activate();

        function activate() {
            vm.model = SearchModel.getModel();
            search();
        }

        function search() {
            searchService.search(1).then(function () {
                renderSlider();
            });
        }

        function reset() {
            SearchModel.reset();
            search();
        }

        function getSuggestions(query) {
            return searchService.getSuggestions(query);
        }

        //// private

        function renderSlider() {
            $timeout(function () {
                $rootScope.$broadcast(UTIL.SLIDER_RENDER_FORCE);
            });
        }
    }
})();