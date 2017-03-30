(function() {
    'use strict';
    angular
        .module('codeBlogApp')
        .factory('Comment', Comment);

    Comment.$inject = ['$resource', 'DateUtils'];

    function Comment ($resource, DateUtils) {
        var resourceUrl =  'api/comments/:id';

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
