(function () {
    'use strict';

    angular
        .module('searchApp.widgets')
        .controller('multiChoice', ['$scope', multiChoiceController]);

    function multiChoiceController($scope) {
        var vm = this;
        vm.callback = function () {
            if (vm.change) {
                vm.change();
            }
        }
    }
})();