(function() {
    'use strict';

    angular
        .module('codeBlogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gallery', {
            parent: 'entity',
            url: '/gallery?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'codeBlogApp.gallery.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gallery/galleries.html',
                    controller: 'GalleryController',
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
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gallery');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('gallery-detail', {
            parent: 'gallery',
            url: '/gallery/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'codeBlogApp.gallery.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gallery/gallery-detail.html',
                    controller: 'GalleryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gallery');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Gallery', function($stateParams, Gallery) {
                    return Gallery.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gallery',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gallery-detail.edit', {
            parent: 'gallery-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gallery/gallery-dialog.html',
                    controller: 'GalleryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gallery', function(Gallery) {
                            return Gallery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gallery.new', {
            parent: 'gallery',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gallery/gallery-dialog.html',
                    controller: 'GalleryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                labelName: null,
                                category: null,
                                imgUrl: null,
                                shortMsg: null,
                                createTime: null,
                                resourceType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gallery', null, { reload: 'gallery' });
                }, function() {
                    $state.go('gallery');
                });
            }]
        })
        .state('gallery.edit', {
            parent: 'gallery',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gallery/gallery-dialog.html',
                    controller: 'GalleryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gallery', function(Gallery) {
                            return Gallery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gallery', null, { reload: 'gallery' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gallery.delete', {
            parent: 'gallery',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gallery/gallery-delete-dialog.html',
                    controller: 'GalleryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gallery', function(Gallery) {
                            return Gallery.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gallery', null, { reload: 'gallery' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
