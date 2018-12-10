(function() {
    'use strict';

    angular
        .module('sykkelAndKeysApp')
        .controller('CenterDialogController', CenterDialogController);

    CenterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Center'];

    function CenterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Center) {
        var vm = this;

        vm.center = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.center.id !== null) {
                Center.update(vm.center, onSaveSuccess, onSaveError);
            } else {
                Center.save(vm.center, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('sykkelAndKeysApp:centerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
