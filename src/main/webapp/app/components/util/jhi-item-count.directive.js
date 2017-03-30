(function() {
    'use strict';

    var jhiItemCount = {
        template: '<div class="info">' +
                    '第 {{(($ctrl.page - 1) * $ctrl.itemsPerPage) == 0 ? 1 : (($ctrl.page - 1) * $ctrl.itemsPerPage + 1)}} - ' +
                    '{{($ctrl.page * $ctrl.itemsPerPage) < $ctrl.queryCount ? ($ctrl.page * $ctrl.itemsPerPage) : $ctrl.queryCount}} ' +
                    '项, 共 {{$ctrl.queryCount}} 项.' +
                '</div>',
        bindings: {
            page: '<',
            queryCount: '<total',
            itemsPerPage: '<'
        }
    };

    angular
        .module('codeBlogApp')
        .component('jhiItemCount', jhiItemCount);
})();
