var userModule = angular.module('user', ['userServiceModule', 'shoeServiceModule']);

userModule.controller('userController', ['$scope', 'userService', 'shoeService',
    function($scope, userService, shoeService)  {
        $scope.testUsers = [
            {firstName: 'Test1', lastName: 'Last1'},
            {firstName: 'Test2', lastName: 'Last2'},
            {firstName: 'Test3', lastName: 'Last3'}];



        $scope.currUser;
        $scope.currUserShoes;
        $scope.userId;
        $scope.welcome;
        $scope.loginStatehide = false;
        $scope.ShoeListShow = false;
        $scope.ShoeButtonListShow = false;
        $scope.shoeList;

        $scope.getUser = function(userId) {

            $scope.loginStatehide = true;
            $scope.ShoeButtonListShow = true;

            userService.getUserService(userId);

            };

        $scope.saveMiles = function (userId, shoeId, miles) {
            userService.setMiles(userId, shoeId, miles);
        };

    $scope.getAllShoes = function () {
        $scope.ShoeListShow = true;
        $scope.ShoeButtonListShow = false;
        shoeService.getAllShoes();
    };

    $scope.addUserShoe = function (UserId, ShoeId) {
        shoeService.addUserShoe(UserId, ShoeId);
        userService.getUserService(UserId);
        $scope.ShoeListShow = false;
        $scope.ShoeButtonListShow = true;
    }
    }]);
