(function () {
    'use strict';

    angular.module('searchApp.search')
        .factory('SearchResult', [SearchResult]);

    function SearchResult() {
        var page = {
            content: [],
            totalCount: undefined,
            pageNumber: undefined
        };

        var service =  {
            getCurrentPage: getCurrentPage,
            setResult: setResult
        };

        return service;

        function getCurrentPage() {
            return page;
        }

        function setResult(result, totalCount, pageNumber) {
            page.content.length = 0;
            page.content.push.apply(page.content, result);
            page.totalCount = totalCount;
            page.pageNumber = pageNumber;
        }
    }
})();