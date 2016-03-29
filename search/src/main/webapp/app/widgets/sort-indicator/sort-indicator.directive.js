(function () {
    'use strict';
    angular.module('searchApp.widgets')
        .directive('sortIndicator', ['TEMPLATES', 'VALUES', sortIndicator]);

    function sortIndicator(TEMPLATES, VALUES) {
        var directive = {
            restrict: 'A',
            templateUrl: TEMPLATES.SORT_INDICATOR,
            scope: {
                currentType: '=ngModel',
                instanceType: '=sortIndicator',
                sortDesc: '=sortIndicatorDesc'
            }
        };

        return directive;
    }
})();