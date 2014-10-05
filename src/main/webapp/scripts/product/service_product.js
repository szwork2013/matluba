'use strict';

shopstuffsApp
    .factory('Product', function ($resource) {
        return $resource('app/rest/products/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    })
    .factory('ProductTypes', function($resource) {
        return $resource('app/rest/products/types', null, {
            'query': { method: 'GET', isArray: true }
        })
    });
