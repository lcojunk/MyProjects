describe('Service: dataService', function () {

    beforeEach(module('searchApp.data'));

    var dataService,
        $httpBackend;

    beforeEach(function () {
        module(function ($provide) {
        });
        inject(function (_dataService_, _$httpBackend_) {
            dataService = _dataService_;
            $httpBackend = _$httpBackend_;
        });
    });

    afterEach(function () {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });

    it('should exist', (function () {
        expect(dataService).toBeDefined();
    }));

    describe('when getting a reference by id', function () {
        it('should make a GET request with the given id', function () {
            var replyObject = {result: {id: 3}};
            $httpBackend.expectGET('api/references/3').respond(200, {data: replyObject});
            dataService.getReferenceById(3).then(function (replyObject) {
                expect(replyObject).toBe(replyObject);
            });
            $httpBackend.flush();
        });
    });
    describe('when calling for facet result', function () {
        it('should make a POST request', function () {
            var replyObject = {result: ['ref1', 'ref2']};
            var postEntity = {test: 'test'};
            $httpBackend.expectPOST('api/search', postEntity).respond(200, {data: replyObject});
            dataService.getFacetResult(postEntity).then(function (replyObject) {
                expect(replyObject).toBe(replyObject);
            });
            $httpBackend.flush();
        });
    });
});