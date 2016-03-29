describe('Controller: result', function () {

    beforeEach(module('searchApp.search'));

    var resultCtrl;
    var mockSearchService,
        mockPage,
        mockSearchResult,
        $scope,
        $controller,
        mockModal,
        UTIL,
        TEMPLATES,
        $rootScope;

    var mockSortTypes;

    beforeEach(inject(function (_$controller_, _$rootScope_, $q, _UTIL_, _TEMPLATES_) {
        UTIL = _UTIL_;
        TEMPLATES = _TEMPLATES_;
        mockModal = jasmine.createSpy().and.returnValue({$promise: $q.defer().promise});
        mockPage = {
            content: [{name: 'test1'}, {name: 'test2'}, {name: 'test3'}, {name: 'test4'}, {name: 'test5'}],
            totalCount: 5
        };
        mockSortTypes = [];
        mockSearchService = {
            search: jasmine.createSpy().and.returnValue($q.defer().promise),
            getSortTypes: jasmine.createSpy().and.returnValue(mockSortTypes),
            sort: jasmine.createSpy()
        };
        mockSearchResult = {
            getCurrentPage: jasmine.createSpy().and.returnValue(mockPage)
        };
        $controller = _$controller_;
        $rootScope = _$rootScope_;
        $scope = $rootScope.$new();
        resultCtrl = $controller('result', {
            $scope: $scope,
            $modal: mockModal,
            searchService: mockSearchService,
            SearchResult: mockSearchResult
        });
    }));

    it('should be defined', function () {
        expect(resultCtrl).toBeDefined();
    });

    it('should retrieve the result page', function () {
        expect(mockSearchResult.getCurrentPage).toHaveBeenCalled();
        expect(resultCtrl.page).toBe(mockPage);
    });

    it('should call search-service when getting page', function () {
        var pNumber = 3;
        resultCtrl.getPage(pNumber);
        expect(mockSearchService.search).toHaveBeenCalledWith(pNumber);
    });

    it('should initialize detail-modal when show reference detail', function () {
        var rIndex = 12;
        resultCtrl.showDetail(rIndex);
        var arguments = mockModal.calls.mostRecent().args;
        expect(arguments[0].scope.startIndex).toBe(rIndex);
        expect(arguments[0].controller).toEqual('detail');
        expect(arguments[0].controllerAs).toEqual(UTIL.CONTROLLER_AS_VM);
        expect(arguments[0].templateUrl).toEqual(TEMPLATES.DETAIL);
    });

    describe('when sorting', function () {
        var sortType = 'test';
        beforeEach(function () {
            resultCtrl.sort(sortType);
        });
        it('should set the sort type accordingly', function () {
            expect(resultCtrl.selectedSortType).toBe(sortType);
        });

        it('should call search-service', function () {
            expect(mockSearchService.sort).toHaveBeenCalledWith(sortType, resultCtrl.sortDesc);
        });

        it('should not set the type, if called with undefined type', function () {
            resultCtrl.sort(undefined);
            expect(resultCtrl.selectedSortType).toBe(sortType);
        });

        it('should sort ascending', function () {
            expect(resultCtrl.sortDesc).toBeFalse();
        });

        it('should reverse sort order when called with the same type', function () {
            resultCtrl.sort(sortType);
            expect(resultCtrl.sortDesc).toBeTrue();
            resultCtrl.sort(sortType);
            expect(resultCtrl.sortDesc).toBeFalse();
        });

    });
});

