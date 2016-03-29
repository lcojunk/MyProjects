(function () {
    angular.module('searchApp.core')
        .config(['paginationTemplateProvider', 'TEMPLATES', function (paginationTemplateProvider, TEMPLATES) {
            paginationTemplateProvider.setPath(TEMPLATES.PAGINATION);
        }]);
})();