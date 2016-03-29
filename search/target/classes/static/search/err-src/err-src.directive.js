(function () {
    'use strict';
    angular.module('searchApp.search')
        .directive('errSrc', [errSrc]);

    function errSrc() {
        var directive = {
            restrict: 'A',
            link: link
        };

        function  link(scope, element, attrs) {
            element.bind('error', function() {
                if (attrs.src != attrs.errSrc) {
                    attrs.$set('src', attrs.errSrc);
                }
            });
        }
    return directive;
    }
})();