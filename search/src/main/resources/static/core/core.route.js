(function () {
    angular.module('searchApp.core')
        .config(['$stateProvider', 'TEMPLATES', 'UTIL', 'STATES',
            function ($stateProvider, TEMPLATES, UTIL, STATES) {
                $stateProvider
                    .state(STATES.ROOT.NAME, {
                        templateUrl: TEMPLATES.MAIN,
                        controller: 'main',
                        controllerAs: UTIL.CONTROLLER_AS_VM
                    });
            }]);
})();