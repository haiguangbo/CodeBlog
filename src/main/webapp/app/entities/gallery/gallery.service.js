(function() {
    'use strict';
    angular
        .module('codeBlogApp')
        .factory('Gallery', Gallery);

    Gallery.$inject = ['$resource', 'DateUtils'];

    function Gallery ($resource, DateUtils) {
        var resourceUrl =  'api/galleries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createTime = DateUtils.convertDateTimeFromServer(data.createTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
