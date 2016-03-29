(function () {
    'use strict';

    angular.module('searchApp.core')
        .factory('authenticationService',
            ['jwtHelper', '$cookies', '$window', '$document', 'UTIL', 'VALUES', '$location', authenticationService]);

    function authenticationService(jwtHelper, $cookies, $window, $document, UTIL, VALUES, $location) {

        var token;

        return {
            getUserName: getUserName,
            getUserGroup: getUserGroup,
            getUserId: getUserId,
            getGroupValue: getGroupValue,
            logout: logout
        };

        ///////////////

        // public

        function getUserName() {
            var username;
            try {
                username = getToken().sub;
            } catch (err) {
                if (angular.equals($location.host(), 'localhost')) {
                    username = 'development';
                } else {
                    throw err;
                }
            }
            return username;
        }

        function getUserGroup() {
            var group;
            try {
                group = getToken().aud;
            } catch (err) {
                if (angular.equals($location.host(), 'localhost')) {
                    group = VALUES.USER_GROUP.BASIC;
                } else {
                    throw err;
                }
            }
            return group;
        }

        function getUserId() {
            return getToken().jti;
        }

        function logout() {
            deleteCookie(UTIL.TOKEN_COOKIE_NAME, '/');
            $window.location.assign('/authentication/');
        }

        function getGroupValue(group) {
            var groupValue;
            switch (group) {
                case VALUES.USER_GROUP.EDITORIAL:
                    groupValue = 1;
                    break;
                case VALUES.USER_GROUP.ADMIN:
                    groupValue = 2;
                    break;
                default:
                    groupValue = 0;
            }
            return groupValue;
        }

        // private

        function getToken() {
            if (!token) {
                token = jwtHelper.decodeToken(getJWT());
            }

            return token;
        }

        function getJWT() {
            var jwt = $cookies.get(UTIL.TOKEN_COOKIE_NAME);
            if (jwt) {
                return jwt;
            } else {
                throw new Error('JWT not found');
            }
        }

        function deleteCookie(name, path) {
            var expires = new Date(0).toUTCString();
            $document[0].cookie = name + '=; path=' + path + '; expires=' + expires;
        }

    }
})();