(function () {
    angular.module('searchApp.search')
        .config(['$locationProvider', '$stateProvider', 'STATES', 'UTIL', 'TEMPLATES',
            function ($locationProvider, $stateProvider, STATES, UTIL, TEMPLATES) {
                $stateProvider
                    .state(STATES.ROOT.SEARCH_VIEW.NAME, {
                        templateUrl: TEMPLATES.SEARCH_VIEW,
                    })
                    .state(STATES.ROOT.SEARCH_VIEW.COMPONENTS.NAME, {
                        url: STATES.ROOT.SEARCH_VIEW.COMPONENTS.URL,
                        views: {
                            'input': {
                                templateUrl: TEMPLATES.SEARCH_INPUT,
                                controller: 'input',
                                controllerAs: UTIL.CONTROLLER_AS_VM
                            },
                            'result': {
                                templateUrl: TEMPLATES.SEARCH_RESULT,
                                controller: 'result',
                                controllerAs: UTIL.CONTROLLER_AS_VM
                            }
                        }
                    })
            }]);
})();