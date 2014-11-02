'use strict';

shopstuffsApp.factory('Company', function ($resource) {
        return $resource('app/rest/companys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
