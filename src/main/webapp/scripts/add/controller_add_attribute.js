




shopstuffsApp.controller('AddAttributeCtrl',
    ['$scope', '$modal', '$log', 'Attribute', function ($scope, $modal, $log, Attribute) {

        $scope.labels = Attribute.labels();

        $scope.selectedLabel = null;
        $scope.selectedOption  = null;

        $scope.groupedAttributes = [];


        $scope.onSelect = function () {
            if ($scope.selectedLabel && !$scope.selectedLabel.children){
                $scope.selectedLabel.children = Attribute.options( { id: $scope.selectedLabel.id });
            }
        };

        $scope.addAttribute = function () {
            if ($scope.selectedOption) {

                var exist = $scope.product.attributes.find(function(options){
                    return options.id === $scope.selectedOption.id;
                });
                if (!exist) {
                    $scope.product.attributes.push($scope.selectedOption);
                    $scope.updateAttributeList();
                }
            }
        };

        $scope.updateAttributeList = function () {
            var attributes = $scope.product.attributes,
                hash = {}, collection = [], len = attributes.length,
                getLabel = function (id) {
                    return $scope.labels.find(function (label) {
                       return  label.id === id;
                    });
                }

            while (len--) {
                var option = attributes[len];
                if (!hash.hasOwnProperty(option.parentId)) {
                    collection[collection.length]
                        = hash[option.parentId]
                            = { label: getLabel(option.parentId), options: [] };
                }
                hash[option.parentId].options.push(option);
            }

            $scope.groupedAttributes = collection;
        }

        // TODO set selected attributes for the product.
        $scope.openAttributeModal = function () {
            var modalInstance = $modal.open({
                templateUrl: 'attributeModal.html',
                controller: 'AttributeModalCtrl',
                resolve: {
                    label: function () {
                        return $scope.selectedLabel;
                    }
                }
            });
            modalInstance.result.then(function (attribute) {
                $log.info('Changes are applied: ' + new Date());
            }, function () {
                $log.info('Changes to Attribute are canceled: ' + new Date());
            });
        };

    }]);
