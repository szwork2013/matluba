'use strict';

angular.module('shopstuffsApp')
    .directive('activeMenu', function ($translate, $locale, tmhDynamicLocale) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs, controller) {
                var language = attrs.activeMenu;

                scope.$watch(function () {
                    return $translate.use();
                }, function (selectedLanguage) {
                    if (language === selectedLanguage) {
                        tmhDynamicLocale.set(language);
                        element.addClass('active');
                    } else {
                        element.removeClass('active');
                    }
                });
            }
        };
    })
    .directive('activeLink', function (location) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs, controller) {
                var clazz = attrs.activeLink;
                var path = attrs.href;
                path = path.substring(1); //hack because path does bot return including hashbang
                scope.location = location;
                scope.$watch('location.path()', function (newPath) {
                    if (path === newPath) {
                        element.addClass(clazz);
                    } else {
                        element.removeClass(clazz);
                    }
                });
            }
        };
    })
    .directive('passwordStrengthBar', function () {
        return {
            replace: true,
            restrict: 'E',
            template: '<div id="strength">' +
                      '<small translate="global.messages.validate.newpassword.strength">Password strength:</small>' +
                      '<ul id="strengthBar">' +
                        '<li class="point"></li><li class="point"></li><li class="point"></li><li class="point"></li><li class="point"></li>' +
                      '</ul>' +
                    '</div>',
            link: function (scope, iElement, attr) {
                var strength = {
                    colors: ['#F00', '#F90', '#FF0', '#9F0', '#0F0'],
                    mesureStrength: function (p) {

                        var _force = 0;
                        var _regex = /[$-/:-?{-~!"^_`\[\]]/g; // "

                        var _lowerLetters = /[a-z]+/.test(p);
                        var _upperLetters = /[A-Z]+/.test(p);
                        var _numbers = /[0-9]+/.test(p);
                        var _symbols = _regex.test(p);

                        var _flags = [_lowerLetters, _upperLetters, _numbers, _symbols];
                        var _passedMatches = $.grep(_flags, function (el) { return el === true; }).length;

                        _force += 2 * p.length + ((p.length >= 10) ? 1 : 0);
                        _force += _passedMatches * 10;

                        // penality (short password)
                        _force = (p.length <= 6) ? Math.min(_force, 10) : _force;

                        // penality (poor variety of characters)
                        _force = (_passedMatches == 1) ? Math.min(_force, 10) : _force;
                        _force = (_passedMatches == 2) ? Math.min(_force, 20) : _force;
                        _force = (_passedMatches == 3) ? Math.min(_force, 40) : _force;

                        return _force;

                    },
                    getColor: function (s) {

                        var idx = 0;
                        if (s <= 10) { idx = 0; }
                        else if (s <= 20) { idx = 1; }
                        else if (s <= 30) { idx = 2; }
                        else if (s <= 40) { idx = 3; }
                        else { idx = 4; }

                        return { idx: idx + 1, col: this.colors[idx] };
                    }
                };
                scope.$watch(attr.passwordToCheck, function (password) {
                    if (password) {
                        var c = strength.getColor(strength.mesureStrength(password));
                        iElement.removeClass('ng-hide');
                        iElement.find('ul').children('li')
                            .css({ "background": "#DDD" })
                            .slice(0, c.idx)
                            .css({ "background": c.col });
                    }
                });
            }
        }
    })
    .directive('dateRange', function () {
        return {
            restrict: 'A',
            scope: {
                maxDate: "=",
                minDate: "=",
                format: "=",
                useMls:"="
            },
            controller: function ($scope) {

                var formatDate = function (date, format) {
                    if ($scope.useMls) {
                      return date.getTime();
                    }

                    var val = {
                        d: date.getDate(),
                        m: date.getMonth() + 1,
                        yy: date.getFullYear().toString().substring(2),
                        yyyy: date.getFullYear()
                    };
                    val.dd = (val.d < 10 ? '0' : '') + val.d;
                    val.mm = (val.m < 10 ? '0' : '') + val.m;

                    var date = [];
                    for (var i = 0, cnt = format.parts.length; i < cnt; i++) {
                        date.push(val[format.parts[i]]);
                    }

                    return date.join(format.separator);
                },
                parseFormat = function (format) {
                    var separator = format.match(/[.\/\-\s].*?/),
                    parts = format.split(/\W+/);
                    if (!separator || !parts || parts.length === 0) {
                        throw new Error("Invalid date format.");
                    }
                    return { separator: separator, parts: parts };
                };

                this.getRawFormat = function () {
                    return $scope.format;
                }

                this.addMax = function (maxScope) {
                    if ($scope.maxScope) {
                        throw new Error("Only one maximum datepicker can exist!");
                    }
                    $scope.maxScope = maxScope;
                    if ($scope.maxDate) {
                        $scope.maxScope.setDate($scope.maxDate);
                    }
                }

                this.addMin = function (minScope) {
                    if ($scope.minScope) {
                        throw new Error("Only one minimum datepicker can exist!");
                    }
                    $scope.minScope = minScope;
                    if ($scope.minDate) {
                        $scope.minScope.setDate($scope.minDate);
                    }
                }

                this.minChanged = function (date) {
                    $scope.minDate =  formatDate(date, parseFormat(this.getRawFormat()));

                    var oneDayAfter = new Date(date);
                    oneDayAfter.setDate(oneDayAfter.getDate() + 1);
                    $scope.maxScope.setMin(date);

                    if (date.valueOf() > $scope.maxScope.getDate().valueOf()) {
                        $scope.maxScope.setDate(oneDayAfter);
                    } else {
                        $scope.maxScope.setDate($scope.maxScope.getDate());
                    }

                    $scope.maxDate = formatDate(oneDayAfter, parseFormat(this.getRawFormat()));

                    $scope.minScope.open(false);
                    $scope.maxScope.open(true);

                    $scope.$digest();
                }

                this.maxChanged = function (date) {

                    $scope.maxDate = formatDate(date, parseFormat(this.getRawFormat()));
                    $scope.maxScope.open(false);
                }
            }
        };
    })
    .directive('rangeDatepicker', function () {
        return {
            require: '^dateRange',
            restrict: 'E',
            scope: {
                maxDate: '@'
            },
            template: ' <div class="input-group"><input type="text" class="form-control" value=""/><span class="input-group-btn"><button type="button" class="btn btn-default" ng-click="toggleOpen($event)"><i class="glyphicon glyphicon-calendar"></i></button></span></div>',

            link: function (scope, element, attr, controller) {
                var $picker, minDate;

                scope.setMin = function (date) {
                    minDate = date;
                }

                scope.setDate = function (date) {
                    scope.dateValue = date;
                    $picker.setValue(date);
                }

                scope.getDate = function () {
                    return $picker.date;
                }

                scope.open = function (isOpen) {
                    isOpen ? $picker.show() : $picker.hide();
                }

                scope.toggleOpen = function () {
                    $picker.picker.css("display") === 'block' ? $picker.hide() : $picker.show();
                }

                $picker = $(element[0]).find("input")
                       .datepicker({
                           format: controller.getRawFormat(),
                           onRender: function (date) {
                               return minDate ? (date.valueOf() <= minDate.valueOf() ? 'disabled' : '') : '';
                           }
                       })
                        .on("changeDate", function (e) {
                            scope.maxDate ? controller.maxChanged(e.date) : controller.minChanged(e.date);
                        })
                        .data("datepicker");

                scope.maxDate == "true" ? controller.addMax(scope)
                                : controller.addMin(scope);
            }
        }
    })
    .directive('gooseInput', function () {
        return {
            restrict: 'E',
            scope: {
                inputPlaceholder: '@',
                wingPlaceholder: '@',
                onAdd: "&"
            },
            template: '<div class="goose">' +
                          '<div class="goose-input-group">' +
                            '<input type="text" class="goose-input form-control" />' +
                            '<div class="popover fade bottom in" role="tooltip"><div class="arrow"></div><div class="popover-content"><input type="text" class="goose-wing form-control" /></div></div>' +
                          '</div>' +
                       '<div class="goose-messages" ng-show="error != null">{{error}}</div></div>',
            transclude: true,

            link: function (scope, element, attr) {
                var $element = $(element),
                    $input = $element.find(".goose-input"),
                    $inputSibling = $element.find(".goose-wing"),
                    parentPopover = ".popover",

                    delayedReset = function () {
                        window.setTimeout(function () {
                            if (!$inputSibling.is("input:focus") && !$input.is("input:focus")) {
                                scope.reset();
                            }
                        }, 5);
                    },

                    keyUpHandler = function (e) {
                        // esc cancel key
                        if (e.keyCode === 27) {
                            if ($input.is(":focus")) $input.blur(); else $inputSibling.blur();
                        } else if (e.keyCode === 13) {
                            scope.apply();
                        }
                    };

                scope.disable = function (disabled) {
                    $inputSibling.attr("disabled", disabled);
                    $input.attr("disabled", disabled);
                }

                scope.reset = function () {
                    $element.off("keyup");
                    $input.val(null);
                    $inputSibling.off("focusin").val(null).closest(parentPopover).hide();
                    scope.error = null;
                }

                scope.apply = function () {
                    var item = { value: $input.val(), alias: $inputSibling.val() };
                    var result = true; //validator.validate(item);
                    var proceed = function (isValid, error) {
                        if (!isValid) {
                            scope.error = error;
                            scope.disable(false);

                            $inputSibling.val(item.alias);
                            $input.val(item.value).focus();
                        } else {
                            scope.reset();
                            scope.onAdd({ "item": item });
                        }
                    };

                    if (angular.isFunction(result.then)) {
                        scope.disable(true);

                        $inputSibling.val("validating...");

                        result.then(function (response) {
                            proceed(response.isValid, response.error);
                        });
                    } else {
                        proceed(result);
                    }

                    $input.blur();
                }

                $input.attr('placeholder', scope.inputPlaceholder);
                $inputSibling.attr('placeholder', scope.wingPlaceholder).closest(parentPopover).hide();

                $input.focusin(function () {
                    $element.keyup(keyUpHandler);
                    $input.focusout(delayedReset);
                    $inputSibling.closest(parentPopover).show();
                    $inputSibling.focusout(delayedReset);
                });
            }
        }
    });
