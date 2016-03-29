(function () {
    'use strict';

    angular
        .module('searchApp.widgets')
        .controller('rangeInput', ['$scope', rangeInput]);

    function rangeInput($scope) {
        var vm = this;

        // variables to distinguish which knob got changed
        var startMin = undefined;
        var startMax = undefined;

        vm.maxInputChange = function () {
            vm.model.userMax = vm.model.max;
            triggerChange();
        };

        vm.minInputChange = function () {
            vm.model.userMin = vm.model.min;
            triggerChange();
        };

        vm.sliderOptions = {
            step: 1,
            draggableRange: true,
            onStart: function () {
                startMin = vm.model.min;
                startMax = vm.model.max;
            },
            onEnd: function () {
                if (startMin != vm.model.min) {
                    vm.model.userMin = vm.model.min;
                }
                if (startMax != vm.model.max) {
                    vm.model.userMax = vm.model.max;
                }
                triggerChange();
            }
        };

        activate();

        function activate() {
            $scope.$watch('vm.model.floor', function (newValue, oldValue) {
                vm.sliderOptions.floor = newValue;
            });
            $scope.$watch('vm.model.ceil', function (newValue, oldValue) {
                vm.sliderOptions.ceil = newValue;
            });

        }

        function triggerChange() {
            if (vm.change) {
                vm.change();
            }
        }
    }
})();