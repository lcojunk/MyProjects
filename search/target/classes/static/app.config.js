(function () {
    angular.module('searchApp')
        .config(['$locationProvider', '$stateProvider', '$urlRouterProvider',
            'TEMPLATES', 'UTIL', 'STATES', 'cfpLoadingBarProvider',
            function ($locationProvider, $stateProvider, $urlRouterProvider,
                      TEMPLATES, UTIL, STATES, cfpLoadingBarProvider) {
                //cfpLoadingBarProvider.includeSpinner = false;
                cfpLoadingBarProvider.latencyThreshold = 10;
                $urlRouterProvider.otherwise(STATES.ROOT.SEARCH_VIEW.COMPONENTS.URL);
            }]);
})();