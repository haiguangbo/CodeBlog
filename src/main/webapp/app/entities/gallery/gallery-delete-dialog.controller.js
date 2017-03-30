(function() {
    'use strict';

    angular
        .module('codeBlogApp')
        .controller('GalleryDeleteController',GalleryDeleteController);

    GalleryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gallery'];

    function GalleryDeleteController($uibModalInstance, entity, Gallery) {
        var vm = this;

        vm.gallery = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gallery.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
