(function() {
    'use strict';

    angular
        .module('codeBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('article', {
                parent: 'entity',
                url: '/article?page&sort&search',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '时间轴'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/article/articles.html',
                        controller: 'ArticleController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('article');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('article-detail', {
                parent: 'article',
                url: '/article/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '文章详情'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/article/article-detail.html',
                        controller: 'ArticleDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('article');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Article', function($stateParams, Article) {
                        return Article.get({ id: $stateParams.id }).$promise;
                    }],
                    previousState: ["$state", function($state) {
                        var currentStateData = {
                            name: $state.current.name || 'article',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('article-detail.edit', {
                parent: 'article-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/article/article-dialog.html',
                        controller: 'ArticleDialogController',
                        controllerAs: 'vm',
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Article', function($stateParams, Article) {
                        return Article.get({ id: $stateParams.id }).$promise;
                    }]
                }

            })
            .state('article.new', {
                parent: 'article',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '新建文章'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/article/article-add.html',
                        controller: 'ArticleAddController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: function() {
                        return {
                            userId: null,
                            labelName: null,
                            title: null,
                            abstracts: null,
                            createTime: null,
                            year: null,
                            month: null,
                            day: null,
                            headImgUrl: null,
                            renderEngine: null,
                            articleType: null,
                            readNum: null,
                            commentNum: null,
                            content: null,
                            id: null
                        };
                    }
                }
            })
            .state('article.edit', {
                parent: 'article',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/article/article-dialog.html',
                        controller: 'ArticleDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Article', function(Article) {
                                return Article.get({ id: $stateParams.id }).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('article', null, { reload: 'article' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('article.delete', {
                parent: 'article',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/article/article-delete-dialog.html',
                        controller: 'ArticleDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Article', function(Article) {
                                return Article.get({ id: $stateParams.id }).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('article', null, { reload: 'article' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            });
    }

})();
