(function() {
    'use strict';

    angular
        .module('codeBlogApp')
        .controller('ArticleDialogController', ArticleDialogController);

    ArticleDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', 'DataUtils', 'entity', 'Article'];

    function ArticleDialogController($timeout, $scope, $state, $stateParams, DataUtils, entity, Article) {
        var vm = this;

        vm.article = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function() {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {}

        function save() {
            if (vm.article.id !== null) {
                Article.update(vm.article, onSaveSuccess, onSaveError);
            } else {
                Article.save(vm.article, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('codeBlogApp:articleUpdate', result);
            // $uibModalInstance.close(result);
            vm.isSaving = false;
            $state.go('article-detail', {}, { reload: "article-detail" })
        }

        function onSaveError() {
            vm.isSaving = false;
            $state.go('article-detail', {}, { reload: "article-detail" })
        }

        vm.datePickerOpenStatus.createTime = false;

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
