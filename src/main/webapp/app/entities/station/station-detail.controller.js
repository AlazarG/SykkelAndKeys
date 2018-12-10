(function() {
    'use strict';

    angular
        .module('sykkelAndKeysApp')
        .controller('StationDetailController', StationDetailController);

    StationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Station', 'Availability', 'Center'];

    function StationDetailController($scope, $rootScope, $stateParams, previousState, entity, Station, Availability, Center) {
        var vm = this;

        vm.station = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sykkelAndKeysApp:stationUpdate', function(event, result) {
            vm.station = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
