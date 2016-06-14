angular.module('physical').factory("physicalService",
    ['$http', '$q', 'SERVICE_STATIC_JSON', "REST_URI", "$rootScope", function ($http, $q, SERVICE_STATIC_JSON, REST_URI, $rootScope,ngDialog) {
        var service = {};

		/******************* Physical sizer*******************/
            service.getRooms= function(callback){
                 $http({
					url: REST_URI.projectUri  + $rootScope.projectId +"/physical/rooms",
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

            service.getProjectById = function (callback) {
               $http({
                url: REST_URI.projectUri + $rootScope.projectId,
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
			
			service.getRackTypes= function(callback){
                 $http({
					url: REST_URI.aciSizerUri + "/physical/racktypes",
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
			
			service.getRoomPolicy= function(callback){
                 $http({
					url: REST_URI.aciSizerUri + "/physical/policy",
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
        /************************add room service****************************************/
            service.addRoom= function(jsonObj,callback){

                $http({
                    url : REST_URI.projectUri+ $rootScope.projectId+"/physical/rooms",
                    method : "POST",
                    data:jsonObj,
                    dataType: "json",
                }).success(function(response) {
                    callback(response);
                }).error(function(response) {
                    console.log("error in api call");
                    callback(response.status);
                    $rootScope.notification.show({
                        title: response.error,
                        message: response.message
                    }, "error");
                });
            }
			
			service.addServer= function(jsonObj,roomId, rowId, rackId, callback){

                $http({
				url : REST_URI.projectUri+ $rootScope.projectId+"/physical/rooms/" +roomId + "/rows/" + rowId + "/racks/" +rackId +"/servers",
                    method : "POST",
                    data:jsonObj,
                    dataType: "json",
                }).success(function(response) {
                    callback(response);
                }).error(function(response) {
                    console.log("error in api call");
                    $rootScope.notification.show({
                        title: response.error,
                        message: response.message
                    }, "error");
                });
            }

             service.addSwitch= function(jsonObj,roomId, rowId, rackId, callback){

                $http({
                url : REST_URI.projectUri+ $rootScope.projectId+"/physical/rooms/" +roomId + "/rows/" + rowId + "/racks/" +rackId +"/switches",
                    method : "POST",
                    data:jsonObj,
                    dataType: "json",
                }).success(function(response) {
                    callback(response);
                }).error(function(response) {
                    console.log("error in api call");
                    $rootScope.notification.show({
                        title: response.error,
                        message: response.message
                    }, "error");
                });
            }

            /****************************add device service end*************************************/
            
            service.editRoom= function(jsonObj,roomId,callback){
                $http({
                    url : REST_URI.projectUri+$rootScope.projectId+"/physical/rooms/" + roomId,
                    method : "PUT",
                    data:jsonObj,
                    dataType: "json",
                }).success(function(response) {
                    callback(response);
                }).error(function (response) {
                    console.log("error in api call");
                    callback(response.status);
                    $rootScope.notification.show({
                        title: response.error,
                        message: response.message
                    }, "error");
                });
            }

            service.editRack= function(jsonObj,roomId,rowId, rackId, callback){
                $http({
                    url : REST_URI.projectUri+$rootScope.projectId+"/physical/rooms/" + roomId + "/rows/" + rowId + "/racks/" + rackId,
                    method : "PUT",
                    data:jsonObj,
                    dataType: "json",
                }).success(function(response) {
                    callback(response);
                }).error(function (response) {
                    console.log("error in api call");
                    $rootScope.notification.show({
                        title: response.error,
                        message: response.message
                    }, "error");
                });
            }

           service.deleteRoom = function (id,callback) {

            $http({
                url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + id,
                method: "DELETE",
            }).success(function (response) {
                callback(response);
            }).error(function (data) {
                console.log("error in api call");
                $rootScope.notification.show({
                    title: data.error,
                    message: data.message
                }, "error");
            });
        }
		
		service.cloneRoom = function (id,callback) {

            $http({
                url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + id +"/clone",
                method: "POST",
            }).success(function (response) {
                callback(response);
            }).error(function (data) {
                console.log("error in api call");
                $rootScope.notification.show({
                    title: data.error,
                    message: data.message
                }, "error");
            });
        }
		
		service.getAllRows= function(id, callback){
                 $http({
					url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + id +"/rows",
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
			
			service.getAllRacks= function(roomId, rowId, callback){
                 $http({
					url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + roomId +"/rows/" + rowId + "/racks",
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
		

			service.editRow= function(jsonObj,roomId, rowId, callback){
                $http({
                    url : REST_URI.projectUri+$rootScope.projectId+"/physical/rooms/" + roomId + "/rows/" +rowId,
                    method : "PUT",
                    data:jsonObj,
                    dataType: "json",
                }).success(function(response) {
                    callback(response);
                }).error(function(response) {
                    console.log("error in api call");
                    $rootScope.notification.show({
                        title: response.error,
                        message: response.message
                    }, "error");
                });
            }

/************************Delete Row *****************************************/
		service.deleteRow = function (roomId, rowId ,callback) {

            $http({
                url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + roomId +"/rows/" + rowId,
                method: "DELETE",
            }).success(function (response) {
                callback(response);
            }).error(function (data) {
                console.log("error in api call");
                $rootScope.notification.show({
                    title: data.error,
                    message: data.message
                }, "error");
            });
        }
		
		
		service.deleteRack = function (roomId, rowId ,rackId,callback) {

            $http({
                url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + roomId +"/rows/" + rowId+"/racks/" + rackId,
                method: "DELETE",
            }).success(function (response) {
                callback(response);
            }).error(function (data) {
                console.log("error in api call");
                $rootScope.notification.show({
                    title: data.error,
                    message: data.message
                }, "error");
            });
        }
		
		service.deleteServer = function (roomId, rowId ,rackId,serverId,callback) {

            $http({
                url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + roomId +"/rows/" + rowId+"/racks/" + rackId +"/servers/"+serverId,
                method: "DELETE",
            }).success(function (response) {
                callback(response);
            }).error(function (data) {
                console.log("error in api call");
                $rootScope.notification.show({
                    title: data.error,
                    message: data.message
                }, "error");
            });
        }		

        service.deleteSwitch = function (roomId, rowId , rackId, switchId, callback) {
                $http({
                    url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + roomId +"/rows/" + rowId + "/racks/" + rackId + "/switches/"+ switchId,
                    method: "DELETE",
                }).success(function (response) {
                    callback(response);
                }).error(function (data) {
                    console.log("error in api call");
                    $rootScope.notification.show({
                        title: data.error,
                        message: data.message
                    }, "error");
                });
            }

            service.editServer= function(jsonObj,roomId, rowId, rackId, serverId, callback){
                $http({
                    url : REST_URI.projectUri+$rootScope.projectId+"/physical/rooms/" + roomId + "/rows/" +rowId + "/racks/" + rackId + "/servers/" +serverId,
                    method : "PUT",
                    data:jsonObj,
                    dataType: "json",
                }).success(function(response) {
                    callback(response);
                }).error(function(response) {
                    callback(response);
                    console.log("error in api call");
                    $rootScope.notification.show({
                        title: response.error,
                        message: response.message
                    }, "error");
                });
            }

		service.getDevices = function (roomId, rowId ,callback) {

            $http({
                url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + roomId +"/rows/" + rowId +"/racks",
                method: "GET",
            }).success(function (response) {
                callback(response);
            }).error(function (data) {
                console.log("error in api call");
                $rootScope.notification.show({
                    title: data.error,
                    message: data.message
                }, "error");
            });
        }
		
		/********************* Get Template Types for Switch *************************/
        service.getSwitchLeafTemplateTypes = function(callback){
             $http({
                    url: REST_URI.aciSizerUri + "/physical/devices/leafs",
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

        service.getSwitchSpineTemplateTypes = function(callback){
             $http({
                    url: REST_URI.aciSizerUri + "/physical/devices/spines",
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
		
		 service.getServerTemplateTypes = function(callback){
             $http({
                    url: REST_URI.aciSizerUri + "/physical/devices/servers",
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

        service.getFabricInterconnect = function(callback){
             $http({
                    url: REST_URI.aciSizerUri + "/physical/devices/fabricInterconnects",
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

         service.getFireWallTemplateTypes = function(callback){
             $http({
                    url: REST_URI.aciSizerUri + "/physical/devices/firewalls",
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

        service.startRowTermination = function(jsonObj, roomId, rowId,addSever, callback){
             $http({
                    url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + roomId +"/rows/" + rowId + "/terminate?"+"isManual="+addSever,
                    method: "POST",
                    data:jsonObj,
                     dataType: "json",
                }).success(function (response) {
                    callback(response);
                }).error(function (data) {
                    console.log("error in api call");
                    callback(data.status);
                    // $rootScope.fadeOutBackground();
                    $rootScope.notification.show({
                        title: data.error,
                        message: data.message
                    }, "error");
                });
        }

        service.startRoomTermination = function(jsonObj, roomId, callback){
             $http({
                    url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + roomId + "/terminate",
                    method: "POST",
                    data:jsonObj,
                     dataType: "json",
                }).success(function (response) {
                    callback(response);
                }).error(function (data) {
                    console.log("error in api call");
                    callback(data.status);
                    $rootScope.notification.show({
                        title: data.error,
                        message: data.message
                    }, "error");
                });
        }

        service.revertRowTermination = function(roomId, rowId,callback){
             $http({
                    url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + roomId +"/rows/" + rowId + "/revert",
                    method: "POST",
                }).success(function (response) {
                    callback(response);
                }).error(function (data) {
                    callback(data.status);
                    console.log("error in api call");
                    // $rootScope.fadeOutBackground();
                    $rootScope.notification.show({
                        title: data.error,
                        message: data.message
                    }, "error");
                });
        }

        service.revertRoomTermination = function(jsonObj, roomId, callback){
             $http({
                    url: REST_URI.projectUri  + $rootScope.projectId + "/physical/rooms/" + roomId + "/revert",
                    method: "POST",
                     data:jsonObj,
                     dataType: "json",
                }).success(function (response) {
                    callback(response);
                }).error(function (data) {
                    callback(data.status);
                    console.log("error in api call");
                    // $rootScope.fadeOutBackground();
                    $rootScope.notification.show({
                        title: data.error,
                        message: data.message
                    }, "error");
                });
        }
        
		return service;
    }]);
