angular.module('dashboard').factory("dashboardService",
    ['$http', '$q', 'SERVICE_STATIC_JSON', "REST_URI", "$rootScope", function ($http, $q, SERVICE_STATIC_JSON, REST_URI, $rootScope,ngDialog) {
        var service = {};

       service.getProjects = function (callback) {

            $http({
                url: REST_URI.getProjectUri,
                method: "GET"
            }).success(function (response) {
                callback(response);
            }).error(function (data) {
                console.log("error in api call");
               // $rootScope.fadeOutBackground();
                $rootScope.notification.show({
                    title: data.error,
                    message: data.message
                }, "error");
            });
        }

        service.addProject = function (data, callback) {
            $http({
                url: REST_URI.projectUri,
                method: "POST",
                data: data
            }).success(function (response) {               
                callback(response);
            }).error(function (response) {
                console.log("error in api call");                
                $rootScope.fadeOutBackground();
                $rootScope.notification.show({
                    title: response.error,
                    message: response.message
                }, "error");
            });
        }

        service.getProject = function (callback) {
	
			var url = REST_URI.projectUri+$rootScope.projectId;
			//canceler.resolve();
			var res = $http.get(url);
	
            res.success(function (data, status, headers, config) {
				//	canceler.resolve();
                callback(data);
			
            });
            res.error(function (data, status, headers, config) {
				if (status != 0) {
					console.log("error in api call");
					$rootScope.fadeOutBackground();
					$rootScope.notification.show({
						title: data.error,
						message: data.message
					}, "error");
				}
            });
			
			
		
        }
		
		service.editProject = function (id,data,callback) {
            $http({
                url: REST_URI.projectUri+id,
                method: "PUT",
				data: data
            }).success(function (response) {
                callback(response);
            }).error(function (response) {
                console.log("error in api call");
                $rootScope.fadeOutBackground();
                $rootScope.notification.show({
                    title: response.error,
                    message: response.message
                }, "error");
            });
        }
	
		 service.getProjectbyId = function (id, callback) {
            $http({
                url: REST_URI.projectUri+id,
                method: "GET"
            }).success(function (response) {
                callback(response);
            }).error(function (response) {
                console.log("error in api call");
                $rootScope.fadeOutBackground();
                $rootScope.notification.show({
                    title: response.error,
                    message: response.message
                }, "error");
            });
        }


        service.deleteProject = function (id, callback) {
            $http({
                url: REST_URI.projectUri + id,
                method: "DELETE"
            }).success(function (response) {
                callback(response);
            }).error(function (data) {
                console.log("error in api call");
                $rootScope.fadeOutBackground();               
                $rootScope.notification.show({
                    title: data.error,
                    message: data.message
                }, "error");
            });
        }

        
        
        service.cloneProject = function (id,callback) {
            $http({
                url: REST_URI.projectUri+id+"/clone",
                method: "POST"
            }).success(function (response) {
                callback(response);
            }).error(function (response) {
                console.log("error in api call");
                $rootScope.fadeOutBackground();
                $rootScope.notification.show({
                    title: response.error,
                    message: response.message
                }, "error");
            });
        }

        service.getPhysicalSizerInventory = function (index, id,callback) {
            $http({
                url: REST_URI.projectUri+id+"/physical/inventory",
                method: "GET"
            }).success(function (response) {
                callback(index, response);
            }).error(function (response) {
                console.log("error in api call");
                $rootScope.fadeOutBackground();
                $rootScope.notification.show({
                    title: response.error,
                    message: response.message
                }, "error");
            });
        }
		
        return service;

    }]);