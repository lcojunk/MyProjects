describe('Service: searchService', function () {

    beforeEach(module('searchApp.search'));

    var searchService, VALUES;

    var dataServiceMock = {},
        windowMock = {},
        searchModelMock = {},
        searchResultMock = {};
    var $rootScope;

    var searchResultObj, suggResultObj, mockEntity;

    beforeEach(function () {
        searchResultObj = {
            result: {
                lobStatistic: ['opt1', 'opt2'],
                branchStatistic: ['opt1', 'opt2'],
                technologyStatistic: ['opt1', 'opt2'],
                ptMin: 12,
                ptMax: 200,
                costMin: 4000,
                costMax: 10000,
                projectEndOptions: [0, 1, 2],
                serviceEndOptions: [0, 1],
                references: [
                    {id: 1},
                    {id: 2},
                    {id: 3}
                ]
            }
        };
        suggResultObj = {
            result: {
                suggestions: ['Tag1', 'Tag2']
            }
        };
        mockEntity = {};

        module(function ($provide) {
            $provide.value('dataService', dataServiceMock);
            $provide.value('$window', windowMock);
            $provide.value('SearchModel', searchModelMock);
            $provide.value('SearchResult', searchResultMock);
        });
        inject(function (_searchService_, _$rootScope_, $q, _VALUES_) {
            searchService = _searchService_;
            $rootScope = _$rootScope_;
            VALUES = _VALUES_;

            windowMock.open = jasmine.createSpy();
            searchModelMock.toEntity = jasmine.createSpy().and.returnValue(mockEntity);
            searchModelMock.setManDaysBounds = jasmine.createSpy();
            searchModelMock.setVolumeBounds = jasmine.createSpy();
            searchModelMock.setLobOptions = jasmine.createSpy();
            searchModelMock.setIndustryOptions = jasmine.createSpy();
            searchModelMock.setTechnologyOptions = jasmine.createSpy();
            searchModelMock.setProjectEndOptions = jasmine.createSpy();
            searchModelMock.setServiceEndOptions = jasmine.createSpy();

            searchResultMock.setResult = jasmine.createSpy();

            var searchResultDeferred = $q.defer();
            searchResultDeferred.resolve(searchResultObj);

            var suggResultDeffered = $q.defer();
            suggResultDeffered.resolve(suggResultObj);

            dataServiceMock.getFacetResult = jasmine.createSpy().and.returnValue(searchResultDeferred.promise);
            dataServiceMock.getSuggestions = jasmine.createSpy().and.returnValue(suggResultDeffered.promise);
        });
    });
    it('should exist', (function () {
        expect(searchService).toBeDefined();
    }));

    it('should open a new tab to download document', function () {
        var mockRef = {id: 5436};
        searchService.downloadDocument(mockRef);
        expect(windowMock.open).toHaveBeenCalled();
        var arguments = windowMock.open.calls.mostRecent().args;
        // should put reference id in url
        expect(arguments[0]).toContain(mockRef.id);
        // verify tab opening
        expect(arguments[1]).toEqual('_blank');
    });

    it('should return suggestions from data-service as promise', function () {
        var testQuery = 'test';
        var promise = searchService.getSuggestions(testQuery).then(function (suggs) {
           expect(suggs).toBe(suggResultObj.result.suggestions);
        }, function () {
            fail();
        });
        $rootScope.$digest();
        expect(dataServiceMock.getSuggestions).toHaveBeenCalledWith(testQuery);
    });

    describe('when searching', function () {
        it('should serialize search-model', function () {
            searchService.search(3);
            expect(searchModelMock.toEntity).toHaveBeenCalled();
        });

        it('should call data-service', function () {
            var pNumber = 5;
            searchService.search(pNumber);
            expect(dataServiceMock.getFacetResult)
                .toHaveBeenCalledWith(mockEntity, pNumber, VALUES.DEFAULT_PAGE_SIZE);
        });
        it('should assign search-result', function () {
            searchService.search(3);
            $rootScope.$digest();
            expect(searchResultMock.setResult).toHaveBeenCalled();
        });

        it('should update search-model', function () {
            var pNumber = 5;
            searchService.search(pNumber);
            $rootScope.$digest();
            expect(searchModelMock.setManDaysBounds)
                .toHaveBeenCalledWith(searchResultObj.result.ptMin, searchResultObj.result.ptMax);
            expect(searchModelMock.setVolumeBounds)
                .toHaveBeenCalledWith(searchResultObj.result.costMin, searchResultObj.result.costMax);
            expect(searchModelMock.setLobOptions)
                .toHaveBeenCalledWith(searchResultObj.result.lobStatistic);
            expect(searchModelMock.setIndustryOptions)
                .toHaveBeenCalledWith(searchResultObj.result.branchStatistic);
            expect(searchModelMock.setTechnologyOptions)
                .toHaveBeenCalledWith(searchResultObj.result.technologyStatistic);
            expect(searchModelMock.setProjectEndOptions).toHaveBeenCalled();
            expect(searchModelMock.setServiceEndOptions).toHaveBeenCalled();
        });
    });
})
;