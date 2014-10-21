'use strict';

shopstuffsApp.factory('Attribute', function ($resource) {
    return $resource('app/rest/attributes/:id', {}, {
        'query': { method: 'GET', isArray: true},
        'get': { method: 'GET'},
        'labels': { url: 'app/rest/attribute/labels', method: 'GET', isArray: true },
        'options': { url: '/app/rest/attribute/options/:id', method: 'GET', isArray: true}
    });
});
