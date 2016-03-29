describe('Controller: main', function () {

    beforeEach(module('searchApp.core'));

    var mainCtrl;
    var $controller,
        $location,
        mockAuthService;

    beforeEach(inject(function (_$controller_, _$location_) {
        $controller = _$controller_;
        $location = _$location_;

        mockAuthService = {
            getUserName: jasmine.createSpy().and.returnValue('TestUser'),
            getUserGroup: jasmine.createSpy().and.returnValue('TestGroup')
        };

        mainCtrl = $controller('main', {
            authenticationService: mockAuthService,
            $location: $location
        });
    }));

    it('should be defined', function () {
        expect(mainCtrl).toBeDefined();
    });
});