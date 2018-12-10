(function() {
    'use strict';

    angular
        .module('sykkelAndKeysApp')
        .controller('CenterDetailController', CenterDetailController);

    CenterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Center'];

    function CenterDetailController($scope, $rootScope, $stateParams, previousState, entity, Center) {
        var vm = this;

        vm.center = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('sykkelAndKeysApp:centerUpdate', function(event, result) {
            vm.center = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
