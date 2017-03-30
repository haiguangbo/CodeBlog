(function() {
    'use strict';

    angular
        .module('codeBlogApp')
        .controller('GalleryDetailController', GalleryDetailController);

    GalleryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gallery'];

    function GalleryDetailController($scope, $rootScope, $stateParams, previousState, entity, Gallery) {
        var vm = this;

        vm.gallery = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('codeBlogApp:galleryUpdate', function(event, result) {
            vm.gallery = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
