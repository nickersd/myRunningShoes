var userModule = angular.module('user', ['userServiceModule']);

userModule.controller('userController', ['$scope', 'userService', function($scope, userService)  {
        $scope.testUsers = [
            {firstName: 'Test1', lastName: 'Last1'},
            {firstName: 'Test2', lastName: 'Last2'},
            {firstName: 'Test3', lastName: 'Last3'}];


        $scope.currUser;
        $scope.currUserShoes;


        $scope.userId;
        $scope.welcome;

        $scope.getUser = function(userId) {

            userService.getUserService(userId);
            //$scope.currUser = userService.currUser;

            }

    }]);
