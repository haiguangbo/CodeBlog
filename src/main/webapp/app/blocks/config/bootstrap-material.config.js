(function() {
    'use strict';

    angular
        .module('codeBlogApp')
        .config(bootstrapMaterialDesignConfig);

    //compileServiceConfig.$inject = [];
    bootstrapMaterialDesignConfig.$inject = []

    function bootstrapMaterialDesignConfig() {
        $.material.init();

    }
})();