describe('Controller: multiChoice', function () {

    beforeEach(module('searchApp.widgets'));

    var multiChoiceCtrl, $controller, $scope;

    var options, selected, callback;

    beforeEach(inject(function (_$controller_, _$rootScope_) {
        $controller = _$controller_;
        multiChoiceCtrl = $controller('multiChoice', {$scope: _$rootScope_.$new()});

        // mimic directive binding
        options = ['optionOne', 'optionTwo', 'optionThree'];
        multiChoiceCtrl.originOptions = options;
        selected = [];
        multiChoiceCtrl.model = selected;
        multiChoiceCtrl.selected = selected;
        callback = jasmine.createSpy();
        multiChoiceCtrl.change = callback;
        id = 'test_id';
        multiChoiceCtrl.id = id;
    }));

    it('should be defined', function () {
        expect(multiChoiceCtrl).toBeDefined();
    });
});