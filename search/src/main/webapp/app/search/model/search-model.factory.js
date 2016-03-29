(function () {
    'use strict';

    angular.module('searchApp.search')
        .factory('SearchModel', [SearchModel]);

    function SearchModel() {
        var model = {
            tags: [],
            lob: createMultipleChoiceFacet(),
            industry: createMultipleChoiceFacet(),
            technology: createMultipleChoiceFacet(),
            manDays: createRangeFacet(),
            volume: createRangeFacet(),
            projectEnd: createSingleChoiceFacet(),
            serviceEnd: createSingleChoiceFacet(),
            sortType: undefined,
            sortDesc: undefined
        };

        var service = {
            getModel: getModel,
            toEntity: toEntity,
            setLobOptions: setLobOptions,
            setIndustryOptions: setIndustryOptions,
            setTechnologyOptions: setTechnologyOptions,
            setManDaysBounds: setManDaysBounds,
            setVolumeBounds: setVolumeBounds,
            setProjectEndOptions: setProjectEndOptions,
            setServiceEndOptions: setServiceEndOptions,
            setSorting: setSorting,
            resetIndustry: resetIndustry,
            resetLob: resetLob,
            resetTechnology: resetTechnology,
            resetManDays: resetManDays,
            resetVolume: resetVolume,
            resetProjectEnd: resetProjectEnd,
            resetServiceEnd: resetServiceEnd,
            reset: reset
        };

        return service;

        /**
         * Public methods
         */
        function getModel() {
            return model;
        }

        function reset() {
            resetLob();
            resetIndustry();
            resetTechnology();
            resetManDays();
            resetVolume();
            resetProjectEnd();
            resetServiceEnd();
            model.tags.length = 0;
        }

        function setLobOptions(options) {
            setMultiOptions(model.lob, options);
        }

        function setIndustryOptions(options) {
            setMultiOptions(model.industry, options);
        }

        function setTechnologyOptions(options) {
            setMultiOptions(model.technology, options);
        }

        function setManDaysBounds(floor, ceil) {
            setBounds(model.manDays, floor, ceil);
        }

        function setVolumeBounds(floor, ceil) {
            setBounds(model.volume, floor, ceil);
        }

        function setProjectEndOptions(options) {
            setSingleOptions(model.projectEnd, options);
        }

        function setServiceEndOptions(options) {
            setSingleOptions(model.serviceEnd, options);
        }

        function setSorting(sortType, sortDesc) {
            model.sortType = sortType;
            model.sortDesc = sortDesc;
        }

        function resetIndustry() {
            resetMultipleChoiceFacet(model.industry);
        }

        function resetLob() {
            resetMultipleChoiceFacet(model.lob);
        }

        function resetTechnology() {
            resetMultipleChoiceFacet(model.technology);
        }

        function resetManDays() {
            resetRangeFacet(model.manDays);
        }

        function resetVolume() {
            resetRangeFacet(model.volume);
        }

        function resetProjectEnd() {
            resetSingleChoiceFacet(model.projectEnd);
        }

        function resetServiceEnd() {
            resetSingleChoiceFacet(model.serviceEnd);
        }

        function toEntity() {
            var entity = {};
            if (model.tags.length > 0) {
                entity.freeText = [];
                model.tags.forEach(function (tag) {
                    entity.freeText.push(tag.text);
                });
            }

            if (angular.isDefined(model.manDays.userMin) || angular.isDefined(model.manDays.userMax)) {
                entity.ptSpan = [];
                if (angular.isDefined(model.manDays.userMin)) {
                    entity.ptSpan[0] = model.manDays.userMin;
                } else {
                    entity.ptSpan[0] = model.manDays.floor;
                }
                if (angular.isDefined(model.manDays.userMax)) {
                    entity.ptSpan[1] = model.manDays.userMax;
                } else {
                    entity.ptSpan[1] = model.manDays.ceil;
                }
            }
            if (angular.isDefined(model.volume.userMin) || angular.isDefined(model.volume.userMax)) {
                entity.costSpan = [];
                if (angular.isDefined(model.volume.userMin)) {
                    entity.costSpan[0] = model.volume.userMin;
                } else {
                    entity.costSpan[0] = model.volume.floor;
                }
                if (angular.isDefined(model.volume.userMax)) {
                    entity.costSpan[1] = model.volume.userMax;
                } else {
                    entity.costSpan[1] = model.volume.ceil;
                }
            }

            var selectedLobs = [];
            model.lob.options.forEach(function (opt) {
                if (opt.selected) {
                    selectedLobs.push(opt.entity.id);
                }
            });
            if (selectedLobs.length > 0) {
                entity.lobs = selectedLobs;
            }

            var selectedIndustries = [];
            model.industry.options.forEach(function (opt) {
                if (opt.selected) {
                    selectedIndustries.push(opt.entity.id);
                }
            });
            if (selectedIndustries.length > 0) {
                entity.branches = selectedIndustries;
            }
            var selectedTechs = [];
            model.technology.options.forEach(function (opt) {
                if (opt.selected) {
                    selectedTechs.push(opt.entity.id);
                }
            });
            if (selectedTechs.length > 0) {
                entity.technologies = selectedTechs;
            }

            if (model.projectEnd.selected) {
                entity.projectEndOption = model.projectEnd.selected.value;
            }
            if (model.serviceEnd.selected) {
                entity.serviceEndOption = model.serviceEnd.selected.value;
            }

            //sorting
            if (angular.isDefined(model.sortType)) {
                entity.sort = {
                    type: model.sortType.value,
                    descending: model.sortDesc
                };
            }

            return entity;
        }

        /**
         * Private methods
         */

        function resetMultipleChoiceFacet(facet) {
            angular.forEach(facet.options, function (o) {
                o.selected = false;
            });
        }

        function resetSingleChoiceFacet(facet) {
            facet.selected = undefined;
        }

        function resetRangeFacet(facet) {
            facet.userMin = undefined;
            facet.userMax = undefined;
            facet.min = facet.floor;
            facet.max = facet.ceil;
        }

        function createMultipleChoiceFacet() {
            return {
                options: []
            }
        }

        function createRangeFacet() {
            return {
                min: 0,
                max: 0,
                floor: 0,
                ceil: 0,
                userMin: undefined,
                userMax: undefined
            }
        }

        function createSingleChoiceFacet() {
            return {
                options: [],
                selected: undefined
            }
        }

        function setBounds(rangeFacet, floor, ceil) {
            rangeFacet.floor = Math.floor(floor);
            rangeFacet.ceil = Math.ceil(ceil);
            if (rangeFacet.userMin != undefined) {
                if (rangeFacet.userMin > rangeFacet.floor) {
                    rangeFacet.min = rangeFacet.userMin;
                } else {
                    rangeFacet.min = rangeFacet.floor;
                }
            } else {
                rangeFacet.min = rangeFacet.floor;
            }
            if (rangeFacet.userMax != undefined) {
                if (rangeFacet.userMax < rangeFacet.ceil) {
                    rangeFacet.max = rangeFacet.userMax;
                } else {
                    rangeFacet.max = rangeFacet.ceil;
                }
            } else {
                rangeFacet.max = rangeFacet.ceil;
            }
        }

        function setMultiOptions(choiceFacet, options) {
            var selectedOptions = [];
            choiceFacet.options.forEach(function (option) {
                if (option.selected) {
                    selectedOptions.push(option);
                }
            });
            choiceFacet.options.length = 0;
            choiceFacet.options.push.apply(choiceFacet.options, selectedOptions);
            angular.forEach(options, function (newOption) {
                var wasSelected = selectedOptions.some(function (sOpt) {
                    return sOpt.entity.id == newOption.entity.id;
                });
                if (!wasSelected) {
                    choiceFacet.options.push(newOption);
                }
            });
        }

        function setSingleOptions(choiceFacet, options) {
            if (choiceFacet.selected) {
                var selectedValue = choiceFacet.selected.value;
            }
            choiceFacet.options.length = 0;
            choiceFacet.options.push.apply(choiceFacet.options, options);
            if (selectedValue != undefined) {
                choiceFacet.options.forEach(function (option) {
                    if (option.value == selectedValue) {
                        choiceFacet.selected = option;
                    }
                });
            }
        }
    }
})();