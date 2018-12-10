(function() {
    'use strict';

    angular
        .module('sykkelAndKeysApp')
        .controller('CenterDeleteController',CenterDeleteController);

    CenterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Center'];

    function CenterDeleteController($uibModalInstance, entity, Center) {
        var vm = this;

        vm.center = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Center.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
