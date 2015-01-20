angular.module('userServiceModule', [])
    .factory('userService', function($http, $q, $rootScope) {

        var currUser;
        var user = {};
        var errorUser = {firstName: 'Test1', lastName: 'Last1'};

        user.setIndexStateHide = function() {
            $rootScope.indexStateHide = true;
        };

        user.getUserService = function(userId) {

            var deferred = $q.defer();

            var responsePromise = $http.get('http://localhost:8080/myRunningShoes/user', { params: {userId: userId} });
            responsePromise.success(function(data, status, headers, config) {
                currUser = data;
                $rootScope.currUser = currUser;
                $rootScope.currUserShoes = currUser.userShoes;

               deferred.resolve(currUser);
            });
            responsePromise.error(function(response, status) {
                console.log("The request failed with response " + response + " and status code " + status);

            });
            return deferred.promise;

        };

        user.login = function(email, password) {

            var deferred = $q.defer();

            var responsePromise = $http.get('http://localhost:8080/myRunningShoes/auth', { params: {'email': email,
           'password': password} });
            
            responsePromise.success(function(data, status, headers, config) {
                currUser = data;
                $rootScope.currUser = currUser;
                $rootScope.currUserShoes = currUser.userShoes;
                console.log("user logged in");
                deferred.resolve(currUser);

            });
            responsePromise.error(function(response, status) {
                console.log("The request failed with response " + response + " and status code " + status);
                    $rootScope.currUser = {};
                    $rootScope.currUserShoes = {};
                    $rootScope.loginErrorShow = true;

            });
            return deferred.promise; 
        };

        user.setMiles = function(userId, shoeId, miles) {
        	
            var deferred = $q.defer();

            var responsePromise = $http.get('http://localhost:8080/myRunningShoes/userShoe',
                { params: {'userId': userId, 'shoeId': shoeId, 'miles': miles } });
            responsePromise.success(function(data, status, headers, config) {
                console.log("success saving shoe miles");
                user.getUserService(userId);
                deferred.resolve(data);
            });
            responsePromise.error(function(response, status) {
                console.log("The request failed with response " + response + " and status code " + status);
            });
            return deferred.promise;
        };

        user.addUser = function (firstName, lastName, email, password) {
            var deferred = $q.defer();
            console.log(password);

            var responsePromise = $http.get('http://localhost:8080/myRunningShoes/newUser',
                { params: {'firstName': firstName, 'lastName': lastName, 'email': email,
                'password': password} });
            responsePromise.success(function(data, status, headers, config) {
                console.log("success adding new user");
                deferred.resolve(data);
                $rootScope.currUser = data;
            });
            responsePromise.error(function(response, status) {
                console.log("Adding new user request failed with response " + response + " and status code " + status);
            });
            return deferred.promise;
        };


    return user;
});

