(function() {
    'use strict';

    angular
        .module('codeBlogApp')
        .controller('ArticleController', ArticleController);

    ArticleController.$inject = ['$state', 'DataUtils', 'Article', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function ArticleController($state, DataUtils, Article, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            console.log("读取所有文章数据");
            Article.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);

            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                console.log(data);
                vm.articles = data;
                for (var i = 0, alength = vm.articles.length; i < alength; i++) {
                    vm.articles[i].labelName = vm.articles[i].labelName === null ? "" : vm.articles[i].labelName.split(";");
                    console.log(vm.articles[i].labelName);
                }

                vm.page = pagingParams.page;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();