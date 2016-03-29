(function () {
    'use strict';

    angular
        .module('searchApp.core')
        .controller('main', ['authenticationService', main]);

    function main(authenticationService) {
        var vm = this;
        vm.logout = logout;
        vm.username = undefined;
        vm.userGroup = undefined;

        activate();

        ///////

        function activate() {
            vm.username = authenticationService.getUserName();
            vm.userGroup = authenticationService.getUserGroup();
        }

        function logout() {
            authenticationService.logout();
        }
    }

})();