var userModule = angular.module('MRS', [ 'userServiceModule',
		'shoeServiceModule', 'ngRoute' ]);

userModule.controller('MRSController', [
		'$scope',
		'$location',
		'userService',
		'shoeService',
		function($scope, $location, userService, shoeService) {

			$scope.currUser;
			$scope.currUserShoes;
			$scope.userId;
			$scope.welcome;
			$scope.loginStatehide = false;
			$scope.ShoeListShow = false;
			$scope.ShoeButtonListShow = false;
			$scope.loginErrorShow = false;
			$scope.indexStateHide = false;
			$scope.shoeList;
			$scope.shoeTableShow = false;
			$scope.newUser;



			$scope.getUser = function(userId) {

				userService.getUserService(userId).then(function(data) {
					$scope.currUser = data;
					$scope.currUserShoes = data.userShoes;
					$scope.loginStatehide = true;
					$scope.ShoeButtonListShow = true;
					
				}, function(error) {
					$scope.currUser = {};
					$scope.currUserShoes = {};
					$scope.loginStatehide = false;
					$scope.ShoeListShow = false;
					$scope.ShoeButtonListShow = false;
					$scope.loginErrorShow = true;

				});

			};

			$scope.login = function(email, password) {
				// AES 256 bit encrypt the password. It'll be stored that
				// way in the DB and get compared that way
				var key = CryptoJS.enc.Hex
						.parse('36ebe205bcdfc499a25e6923f4450fa8');
				var iv = CryptoJS.enc.Hex
						.parse('be410fea41df7162a679875ec131cf2c');
				var encrypted = CryptoJS.AES.encrypt(password, key, {
					iv : iv,
					mode : CryptoJS.mode.CBC,
					padding : CryptoJS.pad.Pkcs7
				});

				/* TODO: this should have been done by unit test
				 * console.log('encrypted: ' + encrypted.toString());
				 *
				 * var decrypted = CryptoJS.AES.decrypt( encrypted.toString(),
				 * key, { iv: iv, mode: CryptoJS.mode.CBC, padding:
				 * CryptoJS.pad.Pkcs7 } ); console.log('decrypted, by hand:
				 * '+decrypted.toString(CryptoJS.enc.Utf8));
				 */

				userService.login(email, encrypted.toString()).then(
						function(data) {
							$scope.welcome = "Welcome";
							$scope.currUser = data;
							$scope.currUserShoes = data.userShoes;
							$scope.loginStatehide = true;
							$scope.ShoeButtonListShow = true;
							$scope.loginErrorShow = false;
							$scope.shoeTableShow = true;
							$location.path('/userView');
						}, function(error) {

							$scope.loginStatehide = false;
							$scope.ShoeListShow = false;
							$scope.ShoeButtonListShow = false;
							$scope.loginErrorShow = true;

						});

			};

			$scope.saveMiles = function(userId, shoeId, inputMiles) {
				userService.setMiles(userId, shoeId, inputMiles).then(function(data) {
					//wait until promise is returned before resetting user data
					//TODO: return user data from the service so we don't have to
					//make a second call
					$scope.getUser(userId);
				}, function(error) {
					console.log("Error setting miles");
				});
			};

			$scope.getAllShoes = function() {
				$scope.ShoeListShow = true;
				$scope.ShoeButtonListShow = false;
				shoeService.getAllShoes().then(function(data) {
					$scope.shoeList = data;
				}, function(error) {
					console.log("Error getting shoe list");
				});
			};

			$scope.addUserShoe = function(UserId, ShoeId) {
				shoeService.addUserShoe(UserId, ShoeId).then(function(data) {
					$scope.ShoeListShow = true;
					$scope.ShoeButtonListShow = true;
					$scope.getUser(UserId);

				}, function(error) {
					console.log("Error adding shoe");
				});

			};

			$scope.addNewUser = function(firstName, lastName, email, password) {
				userService.addUser(firstName, lastName, email, password).then(function(data) {

					console.log("Adding user");
				}, function(error) {
					console.log("Error adding user");
				});

			};

		} ]);










userModule.config(['$routeProvider',
    function($routeProvider, $scope) {
        $routeProvider.
            when('/register', {
                templateUrl: 'Register.html',
                controller: 'MRSController'
            }).
            when('/userView', {
            	templateUrl: 'User.html',
            	controller: 'MRSController'
            }).
			when('/login', {
				templateUrl: 'Login.html',
				controller: 'MRSController'
            }).
			when('/loginChoice', {
				templateUrl: 'LoginChoice.html',
				controller: 'MRSController'
			}).
			otherwise( {
				redirectTo: '/loginChoice'
			})
    }]);

userModule.directive('ngEnter', function() {
	return function(scope, element, attrs) {
		element.bind("keydown keypress", function(event) {
			if (event.which === 13) {
				scope.$apply(function() {
					scope.$eval(attrs.ngEnter);
				});

				event.preventDefault();
			}
		});
	};
});
