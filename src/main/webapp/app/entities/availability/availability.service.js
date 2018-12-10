(function() {
    'use strict';
    angular
        .module('sykkelAndKeysApp')
        .factory('Availability', Availability);

    Availability.$inject = ['$resource'];

    function Availability ($resource) {
        var resourceUrl =  'api/availabilities/:id';

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
