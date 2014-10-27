'use strict';

shopstuffsApp
    .factory('Product', function ($resource) {
        return $resource('app/rest/products/:id', null, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'},
            'addAttribute': {  method: 'POST', url: 'app/rest/product/attribute', params: {} },
            'deleteAttribute': {  method: 'DELETE', url: 'app/rest/product/attribute', params: {} },
            'search': {method: 'POST', isArray: true, url:'app/rest/product/search/:page', params:{}}
        });
    })
    .factory('ProductTypes', function($resource) {
        return $resource('app/rest/products/types', null, {
            'query': { method: 'GET', isArray: true }
        })
    });
