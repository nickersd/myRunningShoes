var userModule = angular.module('MRS', ['userServiceModule', 'shoeServiceModule']);

userModule.controller('MRSController', ['$scope', 'userService', 'shoeService',
    function($scope, userService, shoeService)  {


        $scope.currUser;
        $scope.currUserShoes;
        $scope.userId;
        $scope.welcome;
        $scope.loginStatehide = false;
        $scope.ShoeListShow = false;
        $scope.ShoeButtonListShow = false;
        $scope.loginErrorShow = false;
        $scope.shoeList;

        $scope.getUser = function(userId) {


           userService.getUserService(userId).then(function(data) {
               $scope.currUser = data;
               $scope.currUserShoes = data.userShoes;
               $scope.loginStatehide = true;
               $scope.ShoeButtonListShow = true;
           }, function (error) {
               $scope.currUser = {};
               $scope.currUserShoes = {};
               $scope.loginStatehide = false;
               $scope.ShoeListShow = false;
               $scope.ShoeButtonListShow = false;
               $scope.loginErrorShow = true;

           });

            };

        $scope.login = function(email, password) {

         userService.login(email, password).then(function(data) {
             //$scope.currUser = data;
             //$scope.currUserShoes = data.userShoes;
             $scope.loginStatehide = true;
             $scope.ShoeButtonListShow = true;
             $scope.loginErrorShow = false;

         }, function (error) {

             $scope.loginStatehide = false;
             $scope.ShoeListShow = false;
             $scope.ShoeButtonListShow = false;
             $scope.loginErrorShow = true;

         });

        };

        $scope.saveMiles = function (userId, shoeId, inputMiles) {
            userService.setMiles(userId, shoeId, inputMiles);
            $scope.getUser(userId);
        };

    $scope.getAllShoes = function () {
        $scope.ShoeListShow = true;
        $scope.ShoeButtonListShow = false;
        shoeService.getAllShoes();
    };

    $scope.addUserShoe = function (UserId, ShoeId) {
        shoeService.addUserShoe(UserId, ShoeId);
        userService.getUserService(UserId);
        $scope.ShoeListShow = true;
        $scope.ShoeButtonListShow = true;
    }
    }]);
