(function () {
    'use strict';

    angular.module('searchApp.data')
        .factory('dataService', ['$http', 'DATA', dataService]);

    function dataService($http, DATA) {
        var service = {
            getFacetResult: getFacetResult,
            getReferenceById: getReference,
            getSuggestions: getSuggestions
        };

        return service;

        ///////////////

        function getSuggestions(query) {
            return makePost('api/suggest', {suggestion: query});
        }

        function getReference(id) {
            return makeGet('api/references/' + id);
        }

        function getFacetResult(postEntity, page, pageSize) {
            // ui start pagination at 1...
            postEntity.page = {
                page: page - 1,
                size: pageSize
            };
            return makePost('api/search', postEntity);
        }

        function makeGet(address) {
            var promise = $http.get(address)
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    throw new Error(DATA.MESSAGES.ERROR.GET_ERROR + address);
                    return response.data;
                });
            return promise;
        }

        function makePost(address, entity) {
            var promise = $http.post(address, entity)
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    throw new Error(DATA.MESSAGES.ERROR.POST_ERROR + address);
                    return response.data;
                });
            return promise;
        }
    }
})();