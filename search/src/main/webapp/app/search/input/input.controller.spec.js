describe('Controller: input', function () {

    beforeEach(module('searchApp.search'));

    var inputCtrl, mockModel;
    var mockSearchService,
        mockSuggestions,
        mockSearchModel,
        $scope,
        $controller,
        $timeout,
        $rootScope;

    beforeEach(inject(function (_$controller_, _$rootScope_, _$timeout_, UTIL, $q) {

        mockSuggestions = ['mock1', 'mock2'];
        var suggestionDeferred = $q.defer();
        suggestionDeferred.resolve(mockSuggestions);
        mockSearchService = {
            search: jasmine.createSpy().and.returnValue($q.defer().promise),
            getSuggestions: jasmine.createSpy().and.returnValue(suggestionDeferred.promise)
        };
        mockModel = {};
        mockSearchModel = {
            getModel: jasmine.createSpy().and.returnValue(mockModel),
            reset: jasmine.createSpy()
        };
        $controller = _$controller_;
        $rootScope = _$rootScope_;
        $timeout = _$timeout_;
        $scope = $rootScope.$new();
        inputCtrl = $controller('input', {
            $timeout: $timeout,
            $rootScope: $rootScope,
            searchService: mockSearchService,
            SearchModel: mockSearchModel,
            UTIL: UTIL
        });
    }));

    it('should be defined', function () {
        expect(inputCtrl).toBeDefined();
    });

    it('should retrieve search model and attach to controller', function () {
        expect(mockSearchModel.getModel).toHaveBeenCalled();
        expect(inputCtrl.model).toBe(mockModel);
    });
    it('should retrieve search model', function () {
        expect(mockSearchModel.getModel).toHaveBeenCalled();
    });

    it('should call search-service when searching', function () {
        inputCtrl.search();
        expect(mockSearchService.search).toHaveBeenCalledWith(1);
        // gets called during activation, so check for two calls
        expect(mockSearchService.search.calls.count()).toBe(2);
    });

    it('should call the search-model when resetting and search again', function () {
        inputCtrl.reset();
        expect(mockSearchModel.reset).toHaveBeenCalledWith();
        expect(mockSearchService.search).toHaveBeenCalledWith(1);
        // gets called during activation, so check for two calls
        expect(mockSearchService.search.calls.count()).toBe(2);
    });

    it('should return suggestions from search-service as promise', function () {
        var query = 'test';
        inputCtrl.getSuggestions(query).then(function (result) {
            expect(result).toBe(mockSuggestions);
        }, function () {
            fail();
        });
        $rootScope.$digest();
        expect(mockSearchService.getSuggestions).toHaveBeenCalledWith(query);
    });

});

