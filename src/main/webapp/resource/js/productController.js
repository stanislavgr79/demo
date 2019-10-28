// var app = angular.module("demo", []).controller(
//     "myController",
//     function($scope, $http) {
//
//         var BASE_PATH = "http://localhost:8081";
//
//         $scope.getProductList = function() {
//             $http.get(BASE_PATH + "/getProductsList")
//                 .success(function(data) {
//                     $scope.products = data;
//                 });
//         }
//
//         $scope.addToOrder = function(productId) {
//             $http.put(BASE_PATH + "/order/add/" + productId)
//                 .success(function() {
//                     alert("Successfully");
//                 })
//         }
//
//         $scope.refreshOrder = function() {
//             $http.get(BASE_PATH + "/order/getOrder/"
//                 + $scope.orderId).success(function(data) {
//
//                 $scope.orders = data;
//             })
//         }
//
//         $scope.getOrder = function(orderId) {
//             $scope.orderId = orderId;
//             $scope.refreshOrder(orderId);
//         }
//
//         $scope.removeFromOrder = function(orderDetailId) {
//             $http.put(BASE_PATH +"/order/removeProductOrder/"
//                 + orderDetailId).success(function() {
//                 $scope.refreshOrder();
//             });
//         }
//
//         $scope.clearOrder = function() {
//             $http.put(BASE_PATH + "/order/removeAllProduct/"
//                 + $scope.orderId).success(function() {
//                 $scope.refreshOrder();
//             });
//         }
//
//         $scope.calculateGrandTotal = function() {
//             var grandTotal = 0.0;
//             for (var i = 0; i < $scope.carts.cartItem.length; i++)
//                 grandTotal = grandTotal + $scope.carts.cartItem[i].price;
//             return grandTotal;
//
//         }
//
//     });