'use strict';

shopstuffsApp.factory('Attribute', function ($resource) {
        return $resource('app/rest/attributes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
