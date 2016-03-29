(function () {
    'use strict';

    angular
        .module('searchApp.search', [
            'searchApp.core',
            'searchApp.data',
            'searchApp.widgets',
            ////////////////
            'ngTagsInput',
            'rzModule',
            'angularUtils.directives.dirPagination'
        ]);
})();