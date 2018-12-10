(function () {
    'use strict';

    angular
        .module('sykkelAndKeysApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
