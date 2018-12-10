(function() {
    'use strict';
    angular
        .module('sykkelAndKeysApp')
        .factory('Center', Center);

    Center.$inject = ['$resource'];

    function Center ($resource) {
        var resourceUrl =  'api/centers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
