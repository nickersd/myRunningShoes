angular.module('userServiceModule', [])
    .factory('userService', function($http, $q, $rootScope) {

        var currUser;
        var user = {};
        var errorUser = {firstName: 'Test1', lastName: 'Last1'};

        user.getUserService = function(userId) {

            var deferred = $q.defer();

            var responsePromise = $http.get('http://localhost:8080/myRunningShoes/user', { params: {userId: userId} });
            responsePromise.success(function(data, status, headers, config) {
                currUser = data;
                $rootScope.currUser = currUser;
                $rootScope.currUserShoes = currUser.userShoes;
               // deferred.resolve(currUser);
                //return deferred.promise;
            });
            responsePromise.error(function(response, status) {
                console.log("The request failed with response " + response + " and status code " + status);
            });

        };

        user.setMiles = function(userId, shoeId, miles) {

            var responsePromise = $http.get('http://localhost:8080/myRunningShoes/userShoe',
                { params: {'userId': userId, 'shoeId': shoeId, 'miles': miles } });
            responsePromise.success(function(data, status, headers, config) {
                console.log("success saving shoe miles");
                user.getUserService(userId);
            });
            responsePromise.error(function(response, status) {
                console.log("The request failed with response " + response + " and status code " + status);
            });

        };


    return user;
});

