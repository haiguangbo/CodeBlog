(function() {
    'use strict';

    angular
        .module('codeBlogApp')
        .controller('ArticleDetailController', ArticleDetailController);

    ArticleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Article'];

    function ArticleDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Article) {
        var vm = this;

        vm.article = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('codeBlogApp:articleUpdate', function(event, result) {
            vm.article = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
