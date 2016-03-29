(function () {
    'use strict';
    angular.module('searchApp.widgets')
        .directive('rangeInput', ['UTIL', 'TEMPLATES', rangeInput]);

    function rangeInput(UTIL, TEMPLATES) {
        var directive = {
            controller: 'rangeInput',
            controllerAs: UTIL.CONTROLLER_AS_VM,
            bindToController: {
                model: '=ngModel',
                step: '@rangeInputStep',
                id: '@',
                change: '=rangeInputChange'
            },
            templateUrl: TEMPLATES.RANGE_INPUT,
            restrict: 'EA',
            scope: true
        };

        return directive;
    }
})();