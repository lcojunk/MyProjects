(function () {
    angular.module('searchApp.core', [
        'angular-jwt',
        'ngCookies',
        'ui.router',
        'ngAnimate',
        'angular-loading-bar',
        'angularUtils.directives.dirPagination',
        'mgcrea.ngStrap',
        'angular-jwt']);

    angular.module('searchApp.core')
        .run(['$rootScope', 'STRINGS', 'VALUES',
            function ($rootScope, STRINGS, VALUES) {
                $rootScope.STRINGS = STRINGS;
                $rootScope.VALUES = VALUES;
            }]);
})();