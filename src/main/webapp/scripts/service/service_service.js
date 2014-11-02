'use strict';

shopstuffsApp.factory('Service', function ($resource) {
        return $resource('app/rest/services/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
