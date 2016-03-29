(function () {
    'use strict';

    angular
        .module('searchApp.search')
        .controller('result', [
            '$scope',
            '$modal', 'searchService', 'SearchResult', 'UTIL', 'TEMPLATES', result]);

    function result($scope, $modal, searchService, SearchResult, UTIL, TEMPLATES) {
        var vm = this;
        vm.page = undefined;
        vm.showError = false;
        vm.itemsPerPage = 30;
        vm.sortTypes = undefined;
        vm.selectedSortType = undefined;
        vm.sortDesc = false;
        vm.getPage = getPage;
        vm.showDetail = showDetail;
        vm.sort = sort;

        activate();

        function activate() {
            vm.page = SearchResult.getCurrentPage();
            vm.sortTypes = searchService.getSortTypes();
            vm.selectedSortType = vm.sortTypes.relevance;
        }

        function getPage(number) {
            searchService.search(number);
        }

        function sort(type) {
            if(angular.isDefined(type)) {
                // if selected twice in a row, invert sort order
                if(vm.selectedSortType == type) {
                    vm.sortDesc = !vm.sortDesc;
                } else {
                    vm.selectedSortType = type;
                    vm.sortDesc = false;
                }
            }
            searchService.sort(vm.selectedSortType, vm.sortDesc);
        }

        function showDetail(referenceIndex) {
            var modalScope = $scope.$new();
            modalScope.startIndex = referenceIndex;
            var refModal = $modal({
                scope: modalScope,
                controller: 'detail',
                controllerAs: UTIL.CONTROLLER_AS_VM,
                templateUrl: TEMPLATES.DETAIL
            });
            refModal.$promise.then(refModal.show);
        }
    }
})();