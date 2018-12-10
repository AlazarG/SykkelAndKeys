(function() {
    'use strict';

    angular
        .module('sykkelAndKeysApp')
        .controller('StationDialogController', StationDialogController);

    StationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Station', 'Availability', 'Center'];

    function StationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Station, Availability, Center) {
        var vm = this;

        vm.station = entity;
        vm.clear = clear;
        vm.save = save;
        vm.availabilities = Availability.query({filter: 'station-is-null'});
        $q.all([vm.station.$promise, vm.availabilities.$promise]).then(function() {
            if (!vm.station.availability || !vm.station.availability.id) {
                return $q.reject();
            }
            return Availability.get({id : vm.station.availability.id}).$promise;
        }).then(function(availability) {
            vm.availabilities.push(availability);
        });
        vm.centers = Center.query({filter: 'station-is-null'});
        $q.all([vm.station.$promise, vm.centers.$promise]).then(function() {
            if (!vm.station.center || !vm.station.center.id) {
                return $q.reject();
            }
            return Center.get({id : vm.station.center.id}).$promise;
        }).then(function(center) {
            vm.centers.push(center);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.station.id !== null) {
                Station.update(vm.station, onSaveSuccess, onSaveError);
            } else {
                Station.save(vm.station, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sykkelAndKeysApp:stationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
