(function () {
    'use strict';

    angular.module('searchApp.search')
        .factory('searchService', ['dataService', 'SearchModel', 'SearchResult', '$window', 'VALUES', searchService]);

    function searchService(dataService, SearchModel, SearchResult, $window, VALUES) {
        var service = {
            search: search,
            getReferenceById: getReferenceById,
            getSuggestions: getSuggestions,
            downloadDocument: downloadDocument,
            getSortTypes: getSortTypes,
            sort: sort
        };

        return service;

        /////// public methods

        function getSortTypes() {
            return VALUES.SORT_TYPES;
        }

        function sort(type, sortDesc) {
            // TODO: validate
            SearchModel.setSorting(type, sortDesc);
            search(1);
        }

        function downloadDocument(reference) {
            var url = 'search/api/references/' + reference.id + '/document';
            $window.open(url, '_blank');
        }

        function getSuggestions(query) {
            return dataService.getSuggestions(query).then(function (resultObject) {
                var result = resultObject.result;
                return result.suggestions;
            });
        }

        function getReferenceById(id) {
            return dataService.getReferenceById(id).then(function (resultObj) {
                return resultObj.result;
            });
        }

        function search(pageNumber) {
            var ent = SearchModel.toEntity();
            return dataService.getFacetResult(ent, pageNumber, VALUES.DEFAULT_PAGE_SIZE).then(function (resultObj) {
                var result = resultObj.result;
                SearchResult.setResult(result.references, result.totalObjects, pageNumber);
                updateSearchModel(result);
            });
        }

        /////// private methods

        function updateSearchModel(result) {
            SearchModel.setManDaysBounds(result.ptMin, result.ptMax);
            SearchModel.setVolumeBounds(result.costMin, result.costMax);
            SearchModel.setLobOptions(result.lobStatistic);
            SearchModel.setIndustryOptions(result.branchStatistic);
            SearchModel.setTechnologyOptions(result.technologyStatistic);
            var projectEndOptions = [];
            result.projectEndOptions.forEach(function (optionValue) {
                projectEndOptions.push({value: optionValue, label: getYearsAgoString('Projekt', optionValue)});
            });
            SearchModel.setProjectEndOptions(projectEndOptions);
            var serviceEndOptions = [];
            result.serviceEndOptions.forEach(function (optionValue) {
                serviceEndOptions.push({value: optionValue, label: getYearsAgoString('Wartung', optionValue)});
            });
            SearchModel.setServiceEndOptions(serviceEndOptions);
        }


        function getYearsAgoString(property, yearsAgo) {
            var string = property;
            if (yearsAgo == 0) {
                string += ' dauert noch an';
            } else {
                string += 'ende vor maximal ' + yearsAgo + ((yearsAgo == 1) ? ' Jahr' : ' Jahren');
            }
            return string;
        }
    }
})();