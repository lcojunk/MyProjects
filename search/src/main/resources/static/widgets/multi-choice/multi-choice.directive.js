(function () {
    'use strict';
    angular.module('searchApp.widgets')
        .directive('multiChoice', ['UTIL', 'TEMPLATES', multiChoice]);

    function multiChoice(UTIL, TEMPLATES) {
        var directive = {
            controller: 'multiChoice',
            controllerAs: UTIL.CONTROLLER_AS_VM,
            bindToController: {
                model: '=ngModel',
                query: '@multiChoiceQuery',
                sortType: '@multiChoiceSortType',
                change: '=multiChoiceChange',
                displayProperty: '@multiChoiceDisplayProperty',
                id: '@'
            },
            templateUrl: TEMPLATES.MULTI_CHOICE,
            restrict: 'EA',
            scope: {},
            require: ['^ngModel']
        };
        return directive;
    }
})();