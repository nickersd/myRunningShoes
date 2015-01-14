angular.module('shoeServiceModule', [])
    .factory('shoeService', function($http, $q, $rootScope) {

        var shoe = {};

        shoe.getAllShoes = function() {

            var deferred = $q.defer();
            var shoeList;

            var responsePromise = $http.get('http://localhost:8080/myRunningShoes/shoe');
            responsePromise.success(function(data, status, headers, config) {
                shoeList = data;
                $rootScope.shoeList = shoeList;
                deferred.resolve(shoeList);
            });
            responsePromise.error(function(response, status) {
                console.log("The request failed with response " + response + " and status code " + status);
            });
            return deferred.promise;

        };

        shoe.addUserShoe = function(userId, shoeId) {
            var deferred = $q.defer();
            console.log("userId: " + userId + "shoeID: " + shoeId)

            var responsePromise = $http.get('http://localhost:8080/myRunningShoes/userAddShoe',
                { params: {userId: userId, shoeId: shoeId} });
            responsePromise.success(function(data, status, headers, config) {
                deferred.resolve(data);
                console.log("Success saving shoe")
            });
            responsePromise.error(function(response, status) {
                console.log("The request failed with response " + response + " and status code " + status);
            });
            return deferred.promise;
        };

        return shoe;

    });



