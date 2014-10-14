shopstuffsApp.controller('AddAttributeController', ['$scope', 'Attribute', function ($scope, $modal, $log, Attribute) {
    $scope.attributes = [];

    $scope.openAttributeModal = function () {
        $log.info('Modal opend');
        var modalInstance = $modal.open({
            templateUrl: 'attributeModal.html',
            controller: 'AttributeModalCtrl',
            resolve: {
                attribute: function () {
                    return {};
                }
            }
        });

        modalInstance.result.then(function (attribute) {
            $log.info('Changes are applied: ' + new Date());
        }, function () {
            $log.info('Changes to Attribute are canceled: ' + new Date());
        });
    }
}]);
