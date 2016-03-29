describe('Service: SearchModel', function () {

    beforeEach(module('searchApp.search'));

    var SearchModel;

    beforeEach(function () {
        inject(function (_SearchModel_) {
            SearchModel = _SearchModel_;
        });
    });
    it('should exist', (function () {
        expect(SearchModel).toBeDefined();
    }));

    it('should initialize model correctly', function () {
        var model = SearchModel.getModel();
        expect(model.tags).toBeArray();
        assertMultiChoiceFacet(model.lob);
        assertMultiChoiceFacet(model.industry);
        assertMultiChoiceFacet(model.technology);
        assertRangeFacet(model.manDays);
        assertRangeFacet(model.volume);
        assertSingleChoiceFacet(model.projectEnd);
        assertSingleChoiceFacet(model.serviceEnd);

        function assertSingleChoiceFacet(facet) {
            expect(facet).toBeDefined();
            expect(facet.options).toBeDefined();
            expect(facet.options).toBeArray();
            expect(facet.hasOwnProperty('selected')).toBeTrue();
        }

        function assertMultiChoiceFacet(facet) {
            expect(facet).toBeDefined();
            expect(facet.options).toBeDefined();
            expect(facet.options).toBeArray();
        }

        function assertRangeFacet(facet) {
            expect(facet).toBeDefined();
            expect(facet.min).toBe(0);
            expect(facet.max).toBe(0);
            expect(facet.floor).toBe(0);
            expect(facet.ceil).toBe(0);
            expect(facet.hasOwnProperty('userMin')).toBeTrue();
            expect(facet.hasOwnProperty('userMax')).toBeTrue();
        }
    });

    describe('when serializing', function () {
        it('should define a serilized entity', function () {
            var entity = SearchModel.toEntity();
            expect(entity).toBeDefined();
        });

        it('should serialize tags if there are any', function () {
            var tagOne = {text: 'tag1'};
            var tagTwo = {text: 'tag2'};
            SearchModel.getModel().tags.push(tagOne);
            SearchModel.getModel().tags.push(tagTwo);
            var entity = SearchModel.toEntity();
            expect(entity.freeText).toBeArray();
            expect(entity.freeText.length).toBe(2);
            expect(entity.freeText).toContain(tagOne.text);
            expect(entity.freeText).toContain(tagTwo.text);
        });

        it('should not serialize tags if there are none', function () {
            var entity = SearchModel.toEntity();
            expect(entity.freeText).toBeUndefined();
        });

        it('should serialize manDays if there has been user input', function () {
            var uMin = 12;
            var uMax = 300;
            SearchModel.getModel().manDays.userMin = uMin;
            SearchModel.getModel().manDays.userMax = uMax;
            var entity = SearchModel.toEntity();
            expect(entity.ptSpan).toBeArray();
            expect(entity.ptSpan[0]).toBe(uMin);
            expect(entity.ptSpan[1]).toBe(uMax);
        });
        it('should not serialize manDays if there has not been user input', function () {
            var entity = SearchModel.toEntity();
            expect(entity.ptSpan).toBeUndefined();
        });
        it('should serialize volume if there has been user input', function () {
            var uMin = 12;
            var uMax = 300;
            SearchModel.getModel().volume.userMin = uMin;
            SearchModel.getModel().volume.userMax = uMax;
            var entity = SearchModel.toEntity();
            expect(entity.costSpan).toBeArray();
            expect(entity.costSpan[0]).toBe(uMin);
            expect(entity.costSpan[1]).toBe(uMax);
        });
        it('should not serialize volume if there has not been user input', function () {
            var entity = SearchModel.toEntity();
            expect(entity.costSpan).toBeUndefined();
        });
        it('should serialize lobs if any are selected', function () {
            var testOptions = [
                {entity: {id: 1}, selected: false},
                {entity: {id: 2}, selected: true},
                {entity: {id: 3}, selected: true}
            ];
            SearchModel.getModel().lob.options.push.apply(SearchModel.getModel().lob.options, testOptions);
            var entity = SearchModel.toEntity();
            expect(entity.lobs).toBeArray();
            expect(entity.lobs).toContain(testOptions[1].entity.id);
            expect(entity.lobs).toContain(testOptions[2].entity.id);
        });
        it('should not serialize lobs if none are selected', function () {
            var testOptions = [
                {entity: {id: 1}, selected: false},
                {entity: {id: 2}, selected: false},
                {entity: {id: 3}, selected: false}
            ];
            SearchModel.getModel().lob.options.push.apply(SearchModel.getModel().lob.options, testOptions);
            var entity = SearchModel.toEntity();
            expect(entity.lobs).toBeUndefined();
        });
    });
});