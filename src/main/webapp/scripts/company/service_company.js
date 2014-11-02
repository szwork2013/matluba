'use strict';

shopstuffsApp.factory('Company', function ($resource) {
        return $resource('app/rest/company', {}, {
            'get': { method: 'GET'},
            'save': { method: 'POST'}
        });
    });
