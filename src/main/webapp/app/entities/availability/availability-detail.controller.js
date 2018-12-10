(function() {
    'use strict';

    angular
        .module('sykkelAndKeysApp')
        .controller('AvailabilityDetailController', AvailabilityDetailController);

    AvailabilityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Availability'];

    function AvailabilityDetailController($scope, $rootScope, $stateParams, previousState, entity, Availability) {
        var vm = this;

        vm.availability = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sykkelAndKeysApp:availabilityUpdate', function(event, result) {
            vm.availability = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
