describe('Controller: detail', function () {

    beforeEach(module('searchApp.search'));

    var detailCtrl,
        index,
        page;
    var $scope,
        mockSearchService,
        mockSearchResult,
        $controller,
        $rootScope;

    beforeEach(inject(function (_$controller_, _$rootScope_, $q) {
        $controller = _$controller_;
        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        index = 2;
        $scope.startIndex = index;
        page = {
            content: [
                {name: 'test1', id: 123},
                {name: 'test2', id: 124},
                {name: 'test3', id: 125},
                {name: 'test4', id: 125},
                {name: 'test5', id: 126}],
            totalCount: 5
        };

        var refDefer = $q.defer();
        refDefer.resolve({});

        mockSearchService = {
            getReferenceById: jasmine.createSpy().and.returnValue(refDefer.promise)
        };
        mockSearchResult = {
            getCurrentPage: jasmine.createSpy().and.returnValue(page)
        };
        detailCtrl = $controller('detail', {
            $scope: $scope,
            searchService: mockSearchService,
            SearchResult: mockSearchResult
        });
    }));

    it('should be defined', function () {
        expect(detailCtrl).toBeDefined();
    });
    it('should bind the page to the view model', function () {
        expect(detailCtrl.page).toBe(page);
    });

    describe('when instantiated with start-index bound to scope', function () {

        it('should bind the start-index', function () {
            expect(detailCtrl.referenceIndex).toBe(index);
        });


        it('should get the reference by its id', function () {
            expect(mockSearchService.getReferenceById).toHaveBeenCalledWith(page.content[index].id);
        });

        // functionality for displaying next reference

        //describe('calling for next reference', function () {
        //    describe('when index is not at the last position', function () {
        //        var index = page.content.length - 2;
        //
        //        it('should increment the current index by one', function () {
        //            detailCtrl.nextReference();
        //            expect(detailCtrl.referenceIndex).toBe(index + 1);
        //        });
        //
        //        it('should get the next reference by its id', function () {
        //            detailCtrl.nextReference();
        //            expect(mockSearchService.getReferenceById.toHaveBeenCalledWith(page.content[index + 1].id));
        //        });
        //    });
        //    describe('when the index is at the last position', function () {
        //        var index = page.content.length - 1;
        //
        //        it('should not change the index', function () {
        //            detailCtrl.nextReference();
        //            expect(detailCtrl.referenceIndex).toBe(index);
        //        });
        //    });
        //});
        //describe('calling onKeyUp with event with key-code 39', function () {
        //    var event = {keyCode: 39};
        //    describe('when index is not at the last position', function () {
        //        var index = page.content.length - 2;
        //
        //        it('should increment the current index by one', function () {
        //            detailCtrl.onKeyUp(event);
        //            expect(detailCtrl.referenceIndex).toBe(index + 1);
        //        });
        //
        //        it('should get the next reference by its id', function () {
        //            detailCtrl.onKeyUp(event);
        //            expect(mockSearchService.getReferenceById.toHaveBeenCalledWith(page.content[index + 1].id));
        //        });
        //    });
        //
        //    describe('when the index is at the last position', function () {
        //        var index = page.content.length - 1;
        //
        //        it('should not change the index', function () {
        //            detailCtrl.onKeyUp(event);
        //            expect(detailCtrl.referenceIndex).toBe(index);
        //        });
        //    });
        //});

        // functionality for displaying previous reference
        //
        //describe('calling for previous reference', function () {
        //    it('should decrement the current index by one', function () {
        //        detailCtrl.prevReference();
        //        expect(detailCtrl.refIndex).toBe(index - 1);
        //    });
        //    describe('when the index is at the first possible position', function () {
        //        var index = 0;
        //        beforeEach(function () {
        //            $scope.references = refs;
        //            $scope.refIndex = index;
        //            detailCtrl = $controller('detail', {
        //                $scope: $scope
        //            });
        //        });
        //        it('should not change the index', function () {
        //            detailCtrl.prevReference();
        //            expect(detailCtrl.refIndex).toBe(index);
        //        });
        //    });
        //});
        //describe('calling onKeyUp with event with key-code 37', function () {
        //    var event = {keyCode: 37};
        //    it('should decrement the current index by one', function () {
        //        detailCtrl.onKeyUp(event);
        //        expect(detailCtrl.refIndex).toBe(index - 1);
        //    });
        //    describe('when the index is at the first possible position', function () {
        //        var index = 0;
        //        beforeEach(function () {
        //            $scope.references = refs;
        //            $scope.refIndex = index;
        //            detailCtrl = $controller('detail', {
        //                $scope: $scope
        //            });
        //        });
        //        it('should not change the index', function () {
        //            detailCtrl.onKeyUp(event);
        //            expect(detailCtrl.refIndex).toBe(index);
        //        });
        //    });
        //});
    });
});