'use strict';

shopstuffsApp.factory('Image', function ($resource) {
        return $resource('app/rest/products/:productId/images/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
