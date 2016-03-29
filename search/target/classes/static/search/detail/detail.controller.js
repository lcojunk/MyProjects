(function () {
    'use strict';

    angular
        .module('searchApp.search')
        .controller('detail', ['$scope', 'searchService', 'SearchResult', detail]);

    function detail($scope, searchService, SearchResult) {
        var vm = this;
        vm.referenceIndex = $scope.startIndex;
        vm.reference = undefined;
        vm.page = SearchResult.getCurrentPage();
        vm.onKeyUp = onKeyUp;

        vm.nextReference = nextReference;
        vm.prevReference = prevReference;

        vm.downloadDocument = downloadDocument;

        activate();

        function downloadDocument() {
            searchService.downloadDocument(vm.reference);
        }

        function nextReference() {
            if (vm.referenceIndex < (vm.page.content.length - 1)) {
                vm.referenceIndex++;
            }
            activate();
        }

        function onKeyUp($event) {
            var kCode = $event.keyCode;
            switch (kCode) {
                case 37:
                    prevReference();
                    break;
                case 39:
                    nextReference();
                    break;
            }
        }

        function prevReference() {
            if (vm.referenceIndex > 0) {
                vm.referenceIndex--;
            }
            activate();
        }

        function activate() {
            searchService.getReferenceById(vm.page.content[vm.referenceIndex].id).then(function (reference) {
                vm.reference = reference;
            });
        }
    }

})();