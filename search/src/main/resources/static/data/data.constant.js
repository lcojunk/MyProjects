(function () {
    'use strict';
    angular.module('searchApp.data')
        .constant('DATA', {
            MESSAGES: {
                ERROR: {
                    GET_ERROR: 'Error for get-request on address: ',
                    POST_ERROR: 'Error for post-request on address: '
                }
            }
        });
})();