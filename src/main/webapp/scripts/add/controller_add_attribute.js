




shopstuffsApp.controller('AddAttributeCtrl',
    ['$scope', '$modal', '$log', 'Attribute', 'Product', function ($scope, $modal, $log, Attribute, Product) {

        $scope.labels = Attribute.labels();

        $scope.selectedLabel = null;

        $scope.selectedOption  = null;

        $scope.groupedAttributes = [];

        $scope.enableAddAttribute = false;

        $scope.onSelect = function () {
            if ($scope.selectedLabel && !$scope.selectedLabel.children){
                $scope.selectedLabel.children = Attribute.options( { id: $scope.selectedLabel.id });
            }
        };

        $scope.toggleAttributeEdit = function () {
            $scope.enableAddAttribute = !$scope.enableAddAttribute;
        };

        $scope.addAttribute = function () {
            if ($scope.selectedOption) {
                var exist = _.findWhere($scope.product.attributes, {'id': $scope.selectedOption.id});
                if (!exist) {
                    // todo
                    // ({ productId: 213, attributeId: 77 });
                    $scope.product.attributes.push($scope.selectedOption);
                    $scope.updateAttributeList();
                }
            }
        };

        $scope.onOptionChange = function () {
            console.log("change");
        }

        $scope.updateAttributeList = function () {
            var attributes = $scope.product.attributes,
                hash = {}, collection = [],
                len = attributes.length;

            while (len--) {
                var option = attributes[len];
                if (option.parent) {
                     if( !hash.hasOwnProperty(option.parent.id)) {
                        collection[collection.length]
                            = hash[option.parent.id]
                            = { label: option.parent, options: [] };
                    }
                    hash[option.parent.id].options.push(option);
                }
            }

            $scope.groupedAttributes = collection;
            console.log($scope.groupedAttributes);
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
