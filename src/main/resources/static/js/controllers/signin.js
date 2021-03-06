'use strict';

/* Controllers */
  // signin controller
app.controller('SigninFormController', ['$scope', '$http','$localStorage','$state', function($scope, $http,$localStorage, $state) {
    $scope.currentuser = {};
    $scope.authError = null;
    $scope.login = function() {
      $scope.authError = null;
      // Try to login
      //$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
      $http.post('api/console/login', {account: $scope.user.account, password: $scope.user.password})
      .then(function(response) {
        if ( !response.data.code || response.data.code!=200) {
          $scope.authError = '帐号或密码输入错误！ ';
        }else{
          $scope.app.currentuser = response.data.data;
          $localStorage.currentuser = $scope.app.currentuser;
          console.log($localStorage.currentuser);
          $state.go('app.dashboard-v1');
        }
      }, function(x) {
        $scope.authError = '服务器数据请求错误！';
      });
    };
  }])
;