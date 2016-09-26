/**
 * @ngdoc function
 * @name webappApp.controller:physicalController
 * @description
 * # physicalController
 * Controller of the physical sizer
 */
angular.module('physical')
.controller('physicalController', function ($scope, $rootScope,$timeout, $location, physicalService, PHY_SIZER_CONSTANTS, DIALOG_CONSTANTS) {

	$scope.listContainerHeight = window.innerHeight-100;
	$scope.rightContainerHeight = window.innerHeight-120;


	$scope.room ={};
	$scope.room['enterRoomDetails']=false;
	$scope.id = 1;
	$scope.roomDetails = [];
	$scope.useJsonFlag = false;
	$scope.editMode = false;
	$scope.roomDetailsView = true;

	$rootScope.projectId = localStorage.getItem("projectid");
	$scope.myFunc=function(){
		$scope.room.policyDetail=!$scope.room.policyDetail;$('.left_Scrollcontainer').slimScroll({distance: '5px',opacity: 1});
		
		
	}

	$scope.loadRoomDetails = function(){
		if($location.path() == "/physical") {
			if ($rootScope.currProject) {
				$rootScope.projectName= ($rootScope.currProject.name.length < 32) ? $rootScope.currProject.name : $rootScope.currProject.name.slice(0,29)+"...";
			}  else {
				physicalService.getProjectById(function (resp) {
					$rootScope.currProject = resp;
					$rootScope.projectName= ($rootScope.currProject.name.length < 32) ? $rootScope.currProject.name : $rootScope.currProject.name.slice(0,29)+"...";
				});
				
			}
			$rootScope.projectDropDown = true;
			$rootScope.project['selectedProject'] = "Physical";
			$rootScope.isProjectVisible=true;
			$("#projectstab").removeClass("active");
			$("#physicaltab").addClass("active");

		}

		if ($scope.useJsonFlag == true) {
			$scope.roomDetailsArr = $scope.roomDetails;
		} else {

			physicalService.getRooms(function(resp){
				$scope.roomDetailsArr = resp;
			});
		}
		$scope.room['roomDetailsScreen'] = false;
		$('.room_Scrollcontainer').slimScroll({distance: '5px',opacity: 1});
		
	}

	$scope.delRoom = function(index, id){
		if($scope.editMode == true || $scope.room['enterRoomDetails']==true){
			return;
		}
		var window = $("#deleteRoomWindow");
		$scope.roomId = id;
		$('#lightBox').show();
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "300px",
				title: "Delete Room",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		$('.a-window-action').click(function(){$('#lightBox').hide();});
		var label = $('#delRoomLabel');
		label.text(DIALOG_CONSTANTS.deleteLabel.label + $scope.roomDetailsArr[index].name +"?");

		window.data("aciWindow").open();
		window.data("aciWindow").center();
	}
	
	
	$scope.validateDeleteRoom = function(event){    
			physicalService.deleteRoom($scope.roomId, function(resp){
				$scope.loadRoomDetails();
			});	
			$("#deleteRoomWindow").data("aciWindow").close();
			$('#lightBox').hide();
	}
	
	$scope.onCancelDeleteRoom = function () {
		$("#deleteRoomWindow").data("aciWindow").close();
		$('#lightBox').hide();
	}

	$scope.closeAddRoom = function(){
		$scope.enableAllRooms();
		if ($scope.editMode == true) {
			$scope.editMode = false;
			$scope.editRoomIndex = -1;
		}
		$scope.room = {};
		$scope.room['enterRoomDetails']=false;
		$('#addRoomWin').fadeOut('slow');
		$('.roomDetailsCard').removeClass('backDrop')
	}

	$scope.roomPolicyArr =[];
	$scope.rackTypesArr = [];
	$scope.enableAddRoom = function(){

		if($scope.editMode == false) {
			$scope.room['enterRoomDetails']=true;
			$('#addRoomWin').fadeIn();
			$("li.roomDetailsCard").addClass('backDrop');
			$('.dropdown-toggle').prop('disabled', true);
		}
		physicalService.getRackTypes(function(resp){
			$scope.rackTypesArr = resp;
			for(var i=0;i<$scope.rackTypesArr.length; ++i){
				if($scope.rackTypesArr[i].type == "24RU") {
					$scope.room['rackType'] = $scope.rackTypesArr[i].id;
					break;
				}
			}
		});
		physicalService.getRoomPolicy(function(resp){
			$scope.roomPolicyArr = resp;
		});
 			
			if($('#addRoom').offset().left>900){
				$('.policyDetails').css('margin-left','-74%');
			}else{
				$('.policyDetails').css('margin-left','0px');
			}
	}


	$scope.room['policyDetail']=false;

	$scope.selectRoomPolicy = function(id, name){ 
		$scope.room['roomPolicy'] = id;
		$scope.room['roomPolicyName'] = name;
		$scope.room['policyDetail']=false; 
		$scope.editRoom['value3'] = name;
		$scope.editRoom['policyId'] = id;
	}
	$scope.terminateRowLeafDetails = function(id, name){  
		$scope.terminate['templateId'] = id;
		$scope.terminate['templateName'] = name;
		$scope.room['roompolicyDetail']=false; 
	}
	$scope.selectRoomPolicyDetails = function(id, name){ 
		$scope.room['policyDetails']=false; 
		$scope.terminateRoom['spineTemplate'] = name;
		$scope.terminateRoom['spineTemplateId'] = id;
	}

	$scope.selectRoomLeafPolicyDetails = function(id, name){ 
		$scope.room['policyleafDetail']=false; 
		$scope.terminateRoom['leafTemplate'] = name;
		$scope.terminateRoom['leafTemplateId'] = id;
	}




	$scope.selectRackPolicy = function(id, name){ 
		$scope.editRack['policyId'] = id;
		$scope.editRack['policy'] = name;
		$scope.room['policyDetail']=false; 
	}
	

	$scope.saveBtnClicked = false;
	$scope.addRoom = function(){
		var jsonObj={
				"noOfRows":$scope.room['noOfRows'],
				"noOfRacks":$scope.room['noOfRacks'],
				"policyId":$scope.room['roomPolicy'],
				"rackTypeId":$scope.room['rackType']
		}
		$('.roomDetailsCard').removeClass('backDrop');
		if ($scope.useJsonFlag == true) {
			//edit Room
			if ($scope.editMode == true) {
				$scope.roomDetailsArr[$scope.editRoomIndex].noOfRows = $scope.room['noOfRows'];
				$scope.roomDetailsArr[$scope.editRoomIndex].noOfRacks = $scope.room['noOfRacks']
				$scope.roomDetailsArr[$scope.editRoomIndex].policy.name = $scope.room['roomPolicy'];
				$scope.roomDetailsArr[$scope.editRoomIndex].rackType.id = $scope.room['rackType'];
				$scope.editMode = false;
				//	$scope.roomDetailsView = true;
				$scope.editRoomIndex = -1;
				$scope.room = {};
				$scope.room['enterRoomDetails']=false;
			} else {	
				var json = {
						"id":$scope.id,
						"noOfRows":$scope.room['noOfRows'],
						"noOfRacks":$scope.room['noOfRacks']
				};
				json.policy = {
						name:$scope.room['roomPolicy']
				}
				json.rackType = {
						id:$scope.room['rackType']
				}
				json.name = "room" + $scope.id; 
				$scope.roomDetails.push(json);
				$scope.room = {};
				$scope.room['enterRoomDetails']=false;
				$scope.loadRoomDetails();
				$scope.id += 1;
			}
		} else {
			$scope.saveBtnClicked = true;
			if ($scope.editMode == true) {
				physicalService.editRoom(jsonObj, $scope.roomDetailsArr[$scope.editRoomIndex].id, function(resp){
					if (isNaN(resp)){
						$scope.editMode = false;
						$scope.editRoomIndex = -1;
						$scope.room = {};
						$scope.room['enterRoomDetails']=false;
						$scope.loadRoomDetails();
						$scope.enableAllRooms();
					}
					$scope.saveBtnClicked = false;
				});
			} else{
				physicalService.addRoom(jsonObj,function(resp){
					// in case no error
					if (isNaN(resp)){
						$scope.room = {};
						$scope.room['enterRoomDetails']=false;
						$scope.roomDetailsArr.push(resp);
 					}
					$scope.saveBtnClicked = false;
					$scope.enableAllRooms();
				});
			}
		}
	}

	$scope.opencloneroomWindow = function (id,name) {
			if($scope.editMode == true || $scope.room['enterRoomDetails']==true){
				return;
			}
			$rootScope.roomCloneName=name;
			$rootScope.roomCloneId=id;
            var window = $("#cloneRoomWindow");
            var onClose = function () {
            }

            if (!window.data("aciWindow")) {
                window.aciWindow({
                    width: "350px",
                    title: "CLONE ROOM",
                    actions: [
                        "Close"
                    ],
                    close: onClose
                });
            }
            $('.a-window-action').click(function(){$('#lightBox').hide();});
			
			$scope.cloneprojLabel = DIALOG_CONSTANTS.cloneProject.label+$rootScope.roomCloneName +'?';
			$('#lightBox').show();
			window.data("aciWindow").open();
            window.data("aciWindow").center();
		}


	$scope.duplicateRoom = function() { 
		var id = $rootScope.roomCloneId;
		if ($scope.useJsonFlag == true) {
			var index = -1;
			for (var i=0;i<$scope.roomDetailsArr.length; ++i) {
				if( $scope.roomDetails[i].id == id) {
					index = i;
					break;
				}
			}
			var dup = $scope.roomDetailsArr.slice(index,1);
			dup[0].id = id;
			$rootScope.id += 1;
			var json = angular.copy(dup[0]);
			$scope.roomDetailsArr.push(json);
		} else {
			physicalService.cloneRoom(id, function(resp){
				$scope.loadRoomDetails();
			});
		}
		$("#cloneRoomWindow").data("aciWindow").close();
		$('#lightBox').hide();
	}	
	$scope.onCancelCloneRoom = function(){
		$("#cloneRoomWindow").data("aciWindow").close();
		$('#lightBox').hide();
	}

	$scope.enableAllRooms = function(){
		var roomListElem = $("li.roomDetailsCard");		
		roomListElem.siblings().removeClass('backDrop');
		$("#addRoom").removeClass('backDrop');
		$('.dropdown-toggle').prop('disabled', false);
	}
	$scope.roomContainerHeight = window.innerHeight-40;
	$scope.rowContainerHeight = window.innerHeight-82;

	console.log($scope.roomContainerHeight)
	$scope.EditRoom = function(index,event){
		if (($scope.editMode == false) && ($scope.room['enterRoomDetails']== false)) {
			var roomListElem = $("li.roomDetailsCard:eq("+index+")");
			roomListElem.addClass('disableOtherRooms');
			roomListElem.siblings().addClass('backDrop');
			var addRoomElem = $("#addRoom").addClass('backDrop');
			$('.dropdown-toggle').prop('disabled', true);
			$scope.room['noOfRows']=$scope.roomDetailsArr[index].noOfRows; 
			$scope.room['noOfRacks']=$scope.roomDetailsArr[index].noOfRacks;
			$scope.room['rackType']=$scope.roomDetailsArr[index].rackType.id;
			$scope.room['roomPolicy']=$scope.roomDetailsArr[index].policy.id;
			$scope.room['roomPolicyName']=$scope.roomDetailsArr[index].policy.name;
			$scope.editRoomIndex = index;
			$scope.editMode = true;
			$scope.editRoomName = $scope.roomDetailsArr[index].name; 
			physicalService.getRackTypes(function(resp){
				$scope.rackTypesArr = resp;
			});
			physicalService.getRoomPolicy(function(resp){
				$scope.roomPolicyArr = resp;
			});
		}
		 
		if($(event.currentTarget.parentElement.parentElement.parentElement).offset().left>900){
			$(event.currentTarget.parentElement.parentElement.parentElement).find('.policyDetails').css('margin-left','-45%'); 
		} else{
			$(event.currentTarget.parentElement.parentElement.parentElement).find('.policyDetails').css('margin-left','0px'); 
		}
	}

	$scope.selectedRoomIndex = -1;
	$scope.rowsArray = [];

	$scope.showRowDetails = false;

	$scope.showRoomDetails = function(index) {
		if($scope.editMode == true || $scope.room['enterRoomDetails']==true){
			return;
		}
		$scope.room['roomDetailsScreen'] = true;
		$scope.selectedRoomIndex = index;
		$scope.selectedRowIndex = -1;
		$scope.rowsArray=[];
		if ($scope.rackDetails == true) {
			prevActiveTab1 = "deviceTab";
			prevActiveContent1 = "deviceContent";
		}
		$scope.rackDetails = false;
		$scope.editDelSwitchShow=false;
		$scope.deviceShow=false;
		$scope.serverAdded=false;
		$scope.rackAdded = false;
		$scope.showSwitch=false; 
		$scope.showServer = false;
		$scope.racksArray = [];

		$('.row_Scrollcontainer').slimScroll({distance: '5px',opacity: 1});


		var rowHeight = $('#rowScrollBar').height();
		$('#rowScrollBar').find('.slimScrollDiv').css('height',$scope.rowContainerHeight-19);
		/* reset Breadcrumb display*/
		$scope.getRoomName();

		/*reset horizontal rack navigation scroll */
		$scope.displayedRackIndex = 0;
		//$scope.rackNavigationDisplay();
		physicalService.getAllRows($scope.roomDetailsArr[index].id,function(resp){
			$scope.rowsArray = resp;
		});
		physicalService.getRoomPolicy(function(resp){
			$scope.roomPolicyArr = resp;
		});		
		physicalService.getRackTypes(function(resp){
			$scope.rackTypesArr = resp;
		});
		$scope.editRoom['value1'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].noOfRows;
		$scope.editRoom['value2'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].noOfRacks;
		$scope.editRoom['value3'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].policy.name;
		$scope.editRoom['rackType'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].rackType.id;
		$scope.editRoomView2 = true; // Flag for disabling the Edit Mode of Room - After clicking the room Details link on Room Card
		$scope.getRoomInventory();

	}

	$scope.getRoomInventory = function(){
		var key;
		$scope.roomInventoryKeys = [], values=[];
		$scope.devicePortUtil =[];
		$scope.leafPortUtil =[];
		$scope.spinePortUtil =[];
		var obj = $scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.deviceCounts;

		for (key in obj) {
  			if (obj.hasOwnProperty(key))
   			 $scope.roomInventoryKeys.push(key);
   		}
		var ruUtil = $scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.rusConsumed / $scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.totalNumOfRus;
		$scope.ruUtilization = Math.round(ruUtil * 100) +"%";
		if($scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.totalNumOfRus == 0) {
   			$scope.ruUtilization = "0%";
		}

		$scope.powerConsumed = Math.round($scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.powerConsumed*10)/10;
		$scope.totalPower = Math.round($scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.totalPower*10)/10;

		var powerUtil = $scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.powerConsumed / $scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.totalPower;
		$scope.powerUtilization = Math.round(powerUtil * 100) +"%";
		if($scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.totalPower == 0) {
   			$scope.powerUtilization = "0%";
		}

		var coolingUtil = $scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.coolingConsumed / $scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.totalCooling;
		$scope.coolingUtilization = Math.round(coolingUtil * 100) +"%";
		if($scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.totalCooling == 0) {
   			$scope.coolingUtilization = "0%";
		}
		


   		//device port utilization
   		for(var i=0; i<$scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.portInventoryDevice.length; ++i){
   			$scope.devicePortUtil.push( Math.round((($scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.portInventoryDevice[i].terminated/
   										$scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.portInventoryDevice[i].totalPorts) * 100)) + "%");

   		}
   		for(var i=0; i<$scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.portInventoryLeaf.length; ++i){
   			$scope.leafPortUtil.push( Math.round((($scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.portInventoryLeaf[i].terminated/
   										$scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.portInventoryLeaf[i].totalPorts) * 100)) + "%");

   		}
   		for(var i=0; i<$scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.portInventorySpines.length; ++i){
   			$scope.spinePortUtil.push( Math.round((($scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.portInventorySpines[i].terminated/
   										$scope.roomDetailsArr[$scope.selectedRoomIndex].inventoryInfo.portInventorySpines[i].totalPorts) * 100)) + "%");

   		}
	}

	$scope.getRowInventory = function(){
		var key;
		$scope.rowInventoryKeys = [], values=[];
		$scope.rowsDevicePortUtil =[];
		$scope.rowsLeafPortUtil =[];
		$scope.rowsSpinePortUtil =[];
		var obj = $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.deviceCounts;

		for (key in obj) {
  			if (obj.hasOwnProperty(key))
   			 $scope.rowInventoryKeys.push(key);
   		}
		var ruUtil = $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.rusConsumed / $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalNumOfRus;
		$scope.rowRuUtilization = Math.round(ruUtil * 100) +"%";
		if($scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalNumOfRus == 0) {
   			$scope.rowRuUtilization = "0%";
   		}

   		$scope.rowPowerConsumed = Math.round($scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.powerConsumed*10)/10;
		$scope.rowTotalPower = Math.round($scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalPower*10)/10;
		
		var powerUtil = $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.powerConsumed / $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalPower;
		$scope.rowPowerUtilization = Math.round(powerUtil * 100) +"%";
		if($scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalPower == 0) {
   			$scope.rowPowerUtilization = "0%";
   		}

		var coolingUtil = $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.coolingConsumed / $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalCooling;
		$scope.rowCoolingUtilization = Math.round(coolingUtil * 100) +"%";
		if($scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalCooling == 0) {
   			$scope.rowCoolingUtilization = "0%";
   		}

   		//device port utilization
   		for(var i=0; i<$scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.portInventoryDevice.length; ++i){
   			$scope.rowsDevicePortUtil.push( Math.round((($scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.portInventoryDevice[i].terminated/
   										$scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.portInventoryDevice[i].totalPorts) * 100)) + "%");

   		}
   		for(var i=0; i<$scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.portInventoryLeaf.length; ++i){
   			$scope.rowsLeafPortUtil.push( Math.round((($scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.portInventoryLeaf[i].terminated/
   										$scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.portInventoryLeaf[i].totalPorts) * 100)) + "%");

   		}
   		for(var i=0; i<$scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.portInventorySpines.length; ++i){
   			$scope.rowsSpinePortUtil.push( Math.round((($scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.portInventorySpines[i].terminated/
   										$scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.portInventorySpines[i].totalPorts) * 100)) + "%");

   		}
	}

$scope.getRackInventory = function(){
		var key;
		$scope.rackInventoryKeys = [], values=[];
		$scope.rackDevicePortUtil =[];
		$scope.rackLeafPortUtil =[];
		$scope.rackSpinePortUtil =[];
		var obj = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.deviceCounts;

		for (key in obj) {
  			if (obj.hasOwnProperty(key))
   			 $scope.rackInventoryKeys.push(key);
   		}
   		
		var ruUtil = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.rusConsumed / $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.totalNumOfRus;
		$scope.rackRuUtilization = Math.round(ruUtil * 100) +"%";
		if($scope.racksArray[$scope.selectedRackIndex].inventoryInfo.totalNumOfRus==0) {
   			$scope.rackRuUtilization = "0%";
   		}

   		$scope.rackPowerConsumed = Math.round($scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.powerConsumed*10)/10;
		$scope.rackTotalPower = Math.round($scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.totalPower*10)/10;
		 
		var powerUtil = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.powerConsumed / $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.totalPower;
		$scope.rackPowerUtilization = Math.round(powerUtil * 100) +"%";
		if($scope.racksArray[$scope.selectedRackIndex].inventoryInfo.totalPower==0) {
   			$scope.rackPowerUtilization = "0%";
   		}
		

		var coolingUtil = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.coolingConsumed / $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.totalCooling;
		$scope.rackCoolingUtilization = Math.round(coolingUtil * 100) +"%";
		if($scope.racksArray[$scope.selectedRackIndex].inventoryInfo.totalCooling==0) {
   			$scope.rackCoolingUtilization = "0%";
        }
		
   		//device port utilization
   		for(var i=0; i<$scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.portInventoryDevice.length; ++i){
   			$scope.rackDevicePortUtil.push( Math.round((($scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.portInventoryDevice[i].terminated/
   										$scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.portInventoryDevice[i].totalPorts) * 100)) + "%");

   		}
   		for(var i=0; i<$scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.portInventoryLeaf.length; ++i){
   			$scope.rackLeafPortUtil.push( Math.round((($scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.portInventoryLeaf[i].terminated/
   										$scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.portInventoryLeaf[i].totalPorts) * 100)) + "%");

   		}
   		for(var i=0; i<$scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.portInventorySpines.length; ++i){
   			$scope.rackSpinePortUtil.push( Math.round((($scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.portInventorySpines[i].terminated/
   										$scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].inventoryInfo.portInventorySpines[i].totalPorts) * 100)) + "%");

   		}
	}
	$scope.tooltipAdd = function(event){
		 if($(event.currentTarget).hasClass('greyCircle')){
		 		$(event.currentTarget.parentElement).attr('title','There are changes on the Rack. Please run termination')
		 }
		 else if($(event.currentTarget).hasClass('greenCircle')){
		 		$(event.currentTarget.parentElement).attr('title','All ports terminated')
		 }
		 else if($(event.currentTarget).hasClass('redCircle')){
		 		$(event.currentTarget.parentElement).attr('title','Some of the ports are not terminated')
		 }
	}

	$scope.showUnterminatedRacks = false;
	$scope.toggleRacksDiv=function(index, event){ 
		$scope.showUnterminatedRacks = !$scope.showUnterminatedRacks;
		$scope.unTerminatedRack = true;
		$scope.rackNavigationSelected = false;
		$scope.selectedRowIndex = index;
	}
 
 	$scope.hideUnterminatedRacks=function(){
 		if($scope.unTerminatedRack == true){
 			$scope.unTerminatedRack = false;
 		} else{
 			$scope.showUnterminatedRacks = false;
 			$scope.unTerminatedRack = false;
 		}
 	}

	/* updating inventory after add/delete server/switch */
	$scope.getRoomsAndRows = function(){
		physicalService.getRooms(function(resp){
				$scope.roomDetailsArr = resp;
				$scope.getRoomInventory();
			});
			physicalService.getAllRows($scope.roomDetailsArr[$scope.selectedRoomIndex].id,function(resp){
				for(var i=0; i<$scope.rowsArray.length; ++i) {
					$scope.rowsArray[i].inventoryInfo = resp[i].inventoryInfo;
					$scope.rowsArray[i].unterminatedRacks = resp[i].unterminatedRacks;
					$scope.rowsArray[i].state = resp[i].state;
					if ($scope.selectedRowIndex != -1){
						$scope.getRowInventory();
					}	
				}
				$('.statuscircle').removeClass('redCircle');
				$('.statuscircle').removeClass('greyCircle');
				$('.statuscircle').removeClass('greenCircle');
				var elem=$('.statuscircle');
				for(var i=0;i<resp.length;i++){
					var setColor = $scope.getRowStateCircleClass(resp[i].state);
					elem[i].className += " " + setColor;
				}
			});
	}


	$scope.rackNavigationDisplay = function() {
		var displayRackNum, totalRacks;
		if($scope.racksArray.length == 0)
		{
			$scope.rackCount = false;
		} else {
			$scope.rackCount = true;
		}
		if ($scope.displayedRackIndex+$scope.selectedRackIndex+1 < 10) {
			displayRackNum = "0" + ($scope.displayedRackIndex+$scope.selectedRackIndex+1);
		} else{
			displayRackNum = $scope.displayedRackIndex+$scope.selectedRackIndex+1;
		}
		if ($scope.racksArray.length+1 < 10) {
			totalRacks = "0" + $scope.racksArray.length;
		} else{
			totalRacks = $scope.racksArray.length;
		}
		$scope.rackIndex = displayRackNum + " of " + totalRacks;
	}

	$scope.displayedRackIndex = 0;
	
	$scope.rackDeleted = false;
	$scope.editDevice = false;

	$scope.rightHeight = window.innerHeight-110;

	$scope.rowAction = function(index,event){
		// Disable click operation when edit Room selected
		if(event){
		 $('#projectstab').parent().parent().parent().parent().parent().find('.full-height').css('height','auto');
		 $('#projectstab').parent().parent().parent().parent().parent().parent().find('.ng-scope').css('height','auto');
		 }
		
		//$('#rowScrollBar').toggleClass('row_Scrollcontainer');
		if(($scope.editMode ==true) || ($scope.deleteRowSelected) || ($scope.rowTermination) || ($scope.unTerminatedRack)){
			return false;
		} 
		
		$('#roomScroll,#roomScroll .slimScrollDiv,#roomScroll .room_Scrollcontainer').css('height','auto')
		$('.row_titleBox').find('.removeRow').removeClass('show');
		$('.row_titleBox').find('.removeRow').addClass('hide');
		$('.row_rack_container').find('.removeRow').eq(index).addClass('show');
		$('.row_rack_container').find('.removeRow').eq(index).removeClass('hide');
		$('.row_rack_container').find('li').addClass('hide');
		$('.row_rack_container').find('li').eq(index).addClass('show');
		$('.row_rack_container').find('li').eq(index).removeClass('hide');
	  
		$scope.rackHeight = window.innerHeight-230;
		$scope.showRowDetails= false;

		$scope.selected = index;
		if(!$scope.editDevice) {
			$scope.selectedRackIndex = -1;
		}
		$scope.showServer=false;
		$scope.showSwitch=false;
		$scope.deviceShow=false;
		$scope.rackAdded = false;
		$scope.serverAdded =false;
		$scope.editDelSwitchShow=false;
		$scope.editRoomView2 = true;
		$scope.editRowView2 = true;
		$scope.addDevice1=false;$scope.addDevice2=false;$scope.addDevice3=false;

		if ($scope.rackDetails == false) {
			$scope.selectedRowIndex = -1;
		}
		if(index != $scope.selectedRowIndex){
			$scope.rackDetails = true;
			$scope.rackCount = true;
			$scope.displayedRackIndex = 0;	
			$scope.selectedRowIndex = index;
			$scope.getRowInventory();
			$('.row_rack_container').find('li').removeClass('show');

		}
		else {
			if ((!$scope.rackNavigationSelected) && (!$scope.rackDeleted) && (!$scope.deleteRowSelected) 
				&& (!rowEdited) && (!$scope.editDevice) && (!$scope.rowTermination)) {
				$scope.rackDetails =! $scope.rackDetails;
				$scope.displayedRackIndex = 0;
				$scope.rackCount = !$scope.rackCount;
				if ($scope.rackDetails == false) {
					$scope.selectedRowIndex = -1;
				}
			}
			if($scope.rackDetails == false) {  
				$('.row_rack_container').find('li').removeClass('hide');
			}
			
		}

		if (!$scope.rackNavigationSelected) {
			$scope.getRoomName();
			if ($scope.showRoomDetails && $scope.selectedRowIndex != -1){
				$scope.getRowName();
			}
			/* Adjust scroll position to the selected row */
			if (!$scope.editDevice && !$scope.rackDeleted) {
				var rowWidth = 41;
				var div = $("#wid60scrollbar").get(0);
				var scrollVal = -Math.abs(($scope.selectedRowIndex)*rowWidth);
    			$("#wid60scrollbar div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
    		}
    		$scope.rackDeleted = false;
			if ($scope.rackDetails == true) {
				prevActiveTab1 = "rowDeviceTab";
				prevActiveContent1 = "rowDeviceContent";
				physicalService.getAllRacks($scope.roomDetailsArr[$scope.selectedRoomIndex].id,$scope.rowsArray[index].id,function(resp){
					$scope.racksArray = resp;
					$scope.editRoom['value1'] = $scope.racksArray.length;
					$scope.createRacks();
				//    $scope.scrollDownRacks();
					$scope.hideRackScrollBars();
					deviceClicked = false;

					if(!$scope.editDevice) {
						if(rowEdited==false){
							$scope.showRackDetails(0);
						} else {
							$scope.showRowDetails = true;
							rowEdited = false;
						}
						$scope.scrollDownRacks();
					} else{
						$scope.showRackDetails($scope.selectedRackIndex);
						if ($scope.rackScrollPos == PHY_SIZER_CONSTANTS.DOWN) {
							$scope.scrollDownRacks();
						} else{
							$scope.scrollUpRacks();
						}
						$scope.editDevice = false;
					}
					
					$scope.rackNavigationDisplay();
				});
				physicalService.getRackTypes(function(resp){
					$scope.rackTypesArr = resp;
				});
			} else {
				prevActiveTab1 = "deviceTab";
				prevActiveContent1 = "deviceContent";
			}

			if ($scope.showRowDetails == true) {		
				$scope.editRoom['value1'] =  $scope.racksArray.length;	
				if ($scope.rowsArray[$scope.selectedRowIndex].rackType)	{
					$scope.editRoom['value2'] = $scope.rowsArray[$scope.selectedRowIndex].rackType.id;
				}
				$scope.editRoom['value3'] = $scope.rowsArray[$scope.selectedRowIndex].policy.name;
				$scope.editRoom['policyFlag'] =  $scope.rowsArray[$scope.selectedRowIndex].policyInherited;
				$scope.editRowView2 = true;

			} else {
				$scope.editRoom['value1'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].noOfRows;
				$scope.editRoom['value2'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].noOfRacks;
				$scope.editRoom['value3'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].policy.name;
				$scope.editRoom['rackType'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].rackType.id;
			}

		} else {
			$scope.rackNavigationSelected = false;
		}
	}	 

	$scope.moreText = "More";//Initialize this value to "More" for the first toggle
	$scope.moreDevice = false;
	$scope.moredeviceDetails = function(){
		$scope.moreDevice = ! $scope.moreDevice;				
		if($scope.moreDevice == true)
			$scope.moreText = "Less";
		else
			$scope.moreText = "More";
	}

	$scope.getRowStateCircleClass=function(state){
		var returnClass; 
		if (state==0){ 
			returnClass="greyCircle";  
		} else if (state == 1){ 
			returnClass="redCircle"; 
		} else if(state == 2){ 
			returnClass="greenCircle"; 
		}
		return returnClass;
		
	}


	$scope.rowTermination = false;

	$scope.terminatedEvent = function(rowIndex){
		$scope.selectedRowIndex = rowIndex;
		$scope.terminate={};
		$scope.editDelSwitchShow=false;
		$scope.deviceShow=false;
		$("li label").removeClass("activeServer");
		physicalService.getSwitchLeafTemplateTypes(function(resp){
				$scope.switchTemplateTypes = resp;			
			});
		
		var window = $("#terminateRowWindow");		
		 $('#lightBox').show(); 
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "450px",
				height: "270px",
				title: "Terminate All Devices",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		$scope.rowTermination = true;
		$('.a-window-action').click(function(){$('#lightBox').hide();$scope.rowTermination = false; $scope.room.roompolicyDetail=false;});
		window.data("aciWindow").open();
		window.data("aciWindow").center();
	}
	$scope.onCancelRowTerminate = function () {
		$scope.rowTermination = false;
		$("#terminateRowWindow").data("aciWindow").close(); 
		$('#lightBox').hide();
	}

	$scope.terminate ={};
	$scope.startTerminateRow = function(event){
		var jsonObj = {
			"templateTypeId": $scope.terminate['templateId'],
			"placementPattern": $scope.terminate['algorithm']
		}
		$("#terminateRowWindow").data("aciWindow").close(); 
		$('#lightBox').hide();

		// Show progress dialog
		var window = $("#terminateWindow");		
		 $('#lightBox').show();
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "450px",
				title: "Terminating Ports",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		window.data("aciWindow").title("Terminating Ports");
		$('.a-window-action').click(function(){$('#lightBox').hide();});
		window.data("aciWindow").open();
		window.data("aciWindow").center();
		console.log("add sever :"+$scope.addServer);
		physicalService.startRowTermination(jsonObj, $scope.roomDetailsArr[$scope.selectedRoomIndex].id, $scope.rowsArray[$scope.selectedRowIndex].id,$scope.addServer,function(resp){
				// After row termination successful, display success message
					$("#terminateWindow").data("aciWindow").close(); 
					var window = $("#terminateStatusWindow");		
					if (!window.data("aciWindow")) {
						window.aciWindow({
							width: "450px",
							title: "Terminating Ports",
							actions: [
				      		    "Close"
				       	  	],
				          	close: onClose
						});
					}
					window.data("aciWindow").title("Terminating Ports");
					if (resp.length == 0) {
						var label = $('#terminationMessage');
						label.text(PHY_SIZER_CONSTANTS.rowTerminationSuccessful);
					} else {
						$scope.terminationError = true;
						var label = $('#terminationErrorMessage');
						label.text(PHY_SIZER_CONSTANTS.rowTerminationFailure);
						$scope.errorRacks = resp;
					}
					$('.a-window-action').click(function(){$('#lightBox').hide();});
					window.data("aciWindow").open();
					window.data("aciWindow").center();
		});
		
	}

	$scope.onCompleteRowTermination = function()
	{
		if ($scope.rowTermination == true) {
			// Update inventory
			physicalService.getAllRacks($scope.roomDetailsArr[$scope.selectedRoomIndex].id,$scope.rowsArray[$scope.selectedRowIndex].id,function(resp){
				$scope.racksArray = resp;
				if ($scope.rackDetails == true) {
					$scope.createRacks();
					$scope.hideUnusedRacks();
					//$scope.hideRackScrollBars();
					$scope.showRacks();
					$scope.rackNavigationDisplay();
				}

				$scope.rackNavigationSelected = false;
			    $scope.rowTermination = false;
				$scope.terminationError = false;
				$scope.getRoomsAndRows();
				//$("ul#rack2.device_box li:first-child").css("border-top","1px solid black");
				$("#terminateStatusWindow").data("aciWindow").close(); 
				$("#lightBox").hide();
		});	
		} else { 

			if ($scope.rackDetails == true) {
				physicalService.getAllRacks($scope.roomDetailsArr[$scope.selectedRoomIndex].id,$scope.rowsArray[$scope.selectedRowIndex].id,function(resp){
				$scope.racksArray = resp;
				$scope.createRacks();
				$scope.hideUnusedRacks();
				//$scope.hideRackScrollBars();
				$scope.showRacks();
				$scope.rackNavigationDisplay();
				});

            }
			$scope.getRoomsAndRows();
			$("#terminateStatusWindow").data("aciWindow").close(); 
			$("#lightBox").hide();
			$scope.terminationError = false;
		}
		$scope.scrollUpRacks();
		//$scope.selectedRackSettings();
	}

	
	$scope.revertTermination = function(index){
		$scope.selectedRowIndex = index;
		//only for Row Termination
		if (index != -1) {
			$scope.rowTermination = true;
		}
		var window = $("#revertTerminationWindow");		
		 $('#lightBox').show();
		if($scope.rowTermination == true){
			if (!window.data("aciWindow")) {
				window.aciWindow({
					width: "300px",
					title: "Revert Automatic Placed Leaf",
					actions: [
				          "Close"
				          ],
				          close: onClose
				});
			}
			window.data("aciWindow").title("Revert Automatic Placed Leaf");
		} else {
			if (!window.data("aciWindow")) {
				window.aciWindow({
					width: "300px",
					title: "Terminating Rows",
					actions: [
				          "Close"
				          ],
				          close: onClose
				});
			}
			window.data("aciWindow").title("Terminating Rows");

		}
		var label = $('#revertLabel');
	    label.text(PHY_SIZER_CONSTANTS.rowTerminationLabel);
		$('.a-window-action').click(function(){$('#lightBox').hide();$scope.rowTermination=false;});
		window.data("aciWindow").open();
		window.data("aciWindow").center();
	}

	$scope.validateRevertTermination = function(){
		if ($scope.rowTermination == true) {
			physicalService.revertRowTermination($scope.roomDetailsArr[$scope.selectedRoomIndex].id,$scope.rowsArray[$scope.selectedRowIndex].id,function(resp){
				// if there is no error
				if(resp == null){
					// After row termination successful, display success message
					$("#revertTerminationWindow").data("aciWindow").close(); 
					var window = $("#terminateStatusWindow");		
					if (!window.data("aciWindow")) {
						window.aciWindow({
							width: "450px",
							title: "Revert Automatic Placed Leaf",
							actions: [
				      		    "Close"
				       	  	],
				          	close: onClose
						});
					}
					window.data("aciWindow").title("Revert Automatic Placed Leaf");
					var label = $('#terminationMessage');
					label.text(PHY_SIZER_CONSTANTS.rowRevertSuccess);
					$('.a-window-action').click(function(){$('#lightBox').hide();});
					window.data("aciWindow").open();
					window.data("aciWindow").center();
				}
				else {
					$scope.rowTermination = false;
				}
			});
		}
	}

	$scope.onCancelrevertTermination = function(){
		$("#revertTerminationWindow").data("aciWindow").close();
		$("#lightBox").hide();	
		$scope.rowTermination = false;
	}

	$scope.terminateRoom ={};
	$scope.terminateRoomEvent = function(){
		$scope.terminateRoom ={};
		$scope.terminateRoom['allRows'] = 0;
		$scope.terminateRoom['rowList'] =[];
		$scope.listbox_moveacross("rightBox","leftBox");
		//$scope.listbox_clearSelection();

		physicalService.getSwitchLeafTemplateTypes(function(resp){
				$scope.switchLeafTemplateTypes = resp;			
			});
		physicalService.getSwitchSpineTemplateTypes(function(resp){
				$scope.switchSpineTemplateTypes = resp;			
			});
		
		var window = $("#terminateRoomWindow");		
		 $('#lightBox').show();
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "450px",
				title: "Terminating Room",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		window.data("aciWindow").title("Terminating Room");
		$('.a-window-action').click(function(){$('#lightBox').hide();  $scope.room.policyDetails=false; $scope.room.policyleafDetail=false;});
		window.data("aciWindow").open();
		window.data("aciWindow").center();
	}

	$scope.onCancelRoomTerminate = function(){
		$("#terminateRoomWindow").data("aciWindow").close();
		$("#lightBox").hide();	
	}


	$scope.startTerminateRoom = function(){
		if($scope.terminateRoom['allRows'] == 1){
			for(var i =0; i<$scope.rowsArray.length; ++i) {
				$scope.terminateRoom['rowList'].push($scope.rowsArray[i].id);
			}
		}

		if ($scope.termServer == false) {
			$scope.ntRack = false;
		}

		if ($('#terminateRoomWindow .rowTabs li:eq(1)').attr('class')=='rowtabActive'){
			$scope.termServer = true;
			$scope.terminateRow = false;
			$scope.addLeaf = false;
		} else {
			$scope.termServer = false;
			$scope.terminateRow = true;
			if ($scope.leafDetails==false){
				$scope.addLeaf = true;
			} else {
				$scope.addLeaf = false;
			}
		}


		var jsonObj = {
			"switchUi": {
				"templateTypeId": $scope.terminateRoom['leafTemplateId'],
				"placementPattern":$scope.terminateRoom['algorithm'],
			},	
			"preferredSpineId": $scope.terminateRoom['spineTemplateId'],
			"rowIds": $scope.terminateRoom['rowList'],
			"spineRack":{
                        "rowId":$scope.terminateRoom['spineRow'],
                        "rackId":$scope.terminateRoom['spineRack']
            },
            "terminateRow": $scope.terminateRow,
            "addLeaf": $scope.addLeaf,
            "terminateSpine":$scope.termServer,
            "useNetworkRack":$scope.ntRack
		};
		$("#terminateRoomWindow").data("aciWindow").close(); 
		$('#lightBox').hide();
		// Show progress dialog
		var window = $("#terminateWindow");		
		 $('#lightBox').show();
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "450px",
				title: "Terminating Rows",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		window.data("aciWindow").title("Terminating Rows");
		$('.a-window-action').click(function(){$('#lightBox').hide();});
		window.data("aciWindow").open();
		window.data("aciWindow").center();
		physicalService.startRoomTermination(jsonObj, $scope.roomDetailsArr[$scope.selectedRoomIndex].id, function(resp){
					$("#terminateWindow").data("aciWindow").close(); 
					var window = $("#terminateStatusWindow");		
					if (!window.data("aciWindow")) {
						window.aciWindow({
							width: "450px",
							title: "Terminating Rows",
							actions: [
				      		    "Close"
				       	  	],
				          	close: onClose
						});
					}
					window.data("aciWindow").title("Terminating Rows");
					if (resp == true) {
						var label = $('#terminationMessage');
						if ($scope.termServer == true) {
							label.text(PHY_SIZER_CONSTANTS.spineTerminationSuccessful);
						} else {
							label.text(PHY_SIZER_CONSTANTS.roomTerminationSuccessful);
						}
						$('.a-window-action').click(function(){$('#lightBox').hide();});
						window.data("aciWindow").open();
						window.data("aciWindow").center();

					} else if (resp == false){
						$scope.terminationError = true;
						var label = $('#terminationErrorMessage');
						label.text("There are some rows NOT terminated. Please check the details");
						$('.a-window-action').click(function(){$('#lightBox').hide();});
						window.data("aciWindow").open();
						window.data("aciWindow").center();
				//		$scope.errorRacks = resp;
					} else {
						//$("#terminateStatusWindow").data("aciWindow").close(); 
						$("#lightBox").hide();
						$scope.terminationError = false;
						$scope.rackNavigationSelected = false;
					}
					
		});
	}


	$scope.revertRoomTermination = function(){
		$scope.revertRoom ={};
		$scope.revertRoom['allRows'] = 0;
		$scope.revertRoom['rowList'] =[];
		$scope.revertRoomSelected = true;
		$scope.listbox_moveacross("rightBoxrevert","leftBoxrevert");
		
		var window = $("#revertRoomWindow");		
		 $('#lightBox').show();
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "450px",
				title: "Revert Automatic Placed Switches",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		window.data("aciWindow").title("Revert Automatic Placed Switches");
		$('.a-window-action').click(function(){$('#lightBox').hide();});
		window.data("aciWindow").open();
		window.data("aciWindow").center();		
	}
$scope.startRevertRoom = function(){
		if($scope.revertRoom['allRows'] == 1){
			for(var i =0; i<$scope.rowsArray.length; ++i) {
				$scope.revertRoom['rowList'].push($scope.rowsArray[i].id);
			}
		}
		var jsonObj = {
			"rowIds": $scope.revertRoom['rowList']
		};
		physicalService.revertRoomTermination(jsonObj, $scope.roomDetailsArr[$scope.selectedRoomIndex].id,function(resp){
					$("#revertRoomWindow").data("aciWindow").close(); 
					if(resp == null) {
					var window = $("#terminateStatusWindow");	
					if (!window.data("aciWindow")) {
						window.aciWindow({
							width: "450px",
							title: "Revert Automatic Placed Switches",
							actions: [
				      		    "Close"
				       	  	],
				          	close: onClose
						});
					}
					window.data("aciWindow").title("Revert Automatic Placed Switches");
					var label = $('#terminationMessage');
					label.text(PHY_SIZER_CONSTANTS.roomRevertSuccess);
					$('.a-window-action').click(function(){$('#lightBox').hide();});
					window.data("aciWindow").open();
					window.data("aciWindow").center();
				} else {
					$("#lightBox").hide();	
				}
			});
	}

	$scope.onCancelRoomTerminate = function(){
		$("#terminateRoomWindow").data("aciWindow").close(); 
		$("#lightBox").hide();	
	}

	$scope.onCancelRevertRoomTerminate = function(){
		$("#revertRoomWindow").data("aciWindow").close(); 
		$("#lightBox").hide();	
	}

	$scope.getSpineRackArray = function()
	{
		physicalService.getAllRacks($scope.roomDetailsArr[$scope.selectedRoomIndex].id, $scope.terminateRoom['spineRow'],function(resp){
				$scope.SpineRackArray = resp;			
			});
	}

	$scope.deleteRowRoomClick = function(){
		// If edit row is selected, dont allow delete row
		if($scope.editMode ==true){
			return false;
		}
		$('#lightBox').show();
		if($scope.rackDetails == true){
			$scope.deleteSelectedRow();

		} else {
			$scope.delRoom($scope.selectedRoomIndex, $scope.roomDetailsArr[$scope.selectedRoomIndex].id);
		}
	}

	$scope.deleteRowSelected=false;

	$scope.deleteSelectedRow = function(){
		var window = $("#deleteRowWindow");		
		var selectedRoomId = $scope.roomDetailsArr[$scope.selectedRoomIndex].id;
		var selectedRowId = $scope.rowsArray[$scope.selectedRowIndex].id;
		$scope.deleteRowVariables = {
				roomId : selectedRoomId,
				rowId : selectedRowId
				
		}
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "300px",
				title: "Delete Row",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		$('.a-window-action').click(function(){$('#lightBox').hide();$scope.deleteRowSelected = false});
		var label = $('#delRowLabel');
		label.text(DIALOG_CONSTANTS.deleteLabel.label + $scope.rowsArray[$scope.selectedRowIndex].name + "?");
		$scope.deleteRowSelected = true;
		window.data("aciWindow").open();
		window.data("aciWindow").center();
	}
	
	//$('.row_rack_container').find('li').addClass('hide');
	$scope.deleteRackClicked = function(event){
		$('#lightBox').show();  

		if($scope.rackDetails == true){
			$scope.deleteSelectedRack();   
		} 
		console.log($(event.target.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement))
		$(event.target.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement).addClass('show');
		$(event.target.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement).removeClass('hide');
		
	}


 
$scope.editDeviceFields = function(index, event){ 
	//$(event.currentTarget.parentElement.parentElement).find('.txtFields').addClass('show');
	$(event.currentTarget.parentElement.parentElement).find('.txtFields').removeAttr('disabled');
	$(event.currentTarget.parentElement.parentElement).find('.txtFields').addClass('textFieldAction');
	$scope.editablePortGroup[index] = true;
}

/*Selectaddthe add server abd switch popup*/

$scope.addDevice = 
[
	{server:'Server',switch:'Switch',firewall:'Firewall',loadbalancer:'Load Balancer',fabricinterconnect:'Fabric Interconnect',firewall:'Firewall',firewall:'Firewall'}
]
	$scope.addDeviceWindow = function(){

		var elem = document.getElementById("addDeviceWindow");
		var window = $(elem);
		 $('#lightBox').show();
		 $scope.deviceTab=true;
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "400px",
				title: "Add Device",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		$('.a-window-action').click(function(){$('#lightBox').hide();});
		$('#deviceId dd').click(function(){
			 $("#addDeviceWindow").data("aciWindow").close();

		}); 
		window.data("aciWindow").open();
		window.data("aciWindow").center();
	}
 
	
	$scope.deleteSelectedRack = function(){
		var window = $("#deleteRackWindow");

		var selectedRoomId = $scope.roomDetailsArr[$scope.selectedRoomIndex].id;
		var selectedRowId = $scope.rowsArray[$scope.selectedRowIndex].id;
		var selectedRackId = $scope.racksArray[$scope.displayedRackIndex + $scope.selectedRackIndex].id;
		$scope.deleteRackVariables = {
				roomId : selectedRoomId,
				rowId : selectedRowId,
				rackId: selectedRackId
				
		}
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "300px",
				title: "Delete Rack",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		$('.a-window-action').click(function(){$('#lightBox').hide();});
		var label = $('#delRackLabel');
		label.text(DIALOG_CONSTANTS.deleteLabel.label + $scope.racksArray[$scope.displayedRackIndex + $scope.selectedRackIndex].name + "?");

		window.data("aciWindow").open();
		window.data("aciWindow").center();
	}
	
	
	
	
	$scope.tooltipPolicy=true;
	$scope.chartView=true;

	$scope.editRoom = [];
	$scope.editRow = [];
	$scope.editRoomSelected = function(event){ 
		 $scope.tooltipPolicy=false;
		 $('#num').focus();
		 $(event.currentTarget.parentElement).find('.group').children().addClass('actColor');
		 $('#editFormBox,#headerBox').show();

		$scope.editMode = true;	
		$('.dropdown-toggle').prop('disabled', true);

		physicalService.getRackTypes(function(resp){
			$scope.rackTypesArr = resp;
		});
		$('#deviceContent,#rowDeviceContent').find('.select-style').removeAttr('style');
		physicalService.getRoomPolicy(function(resp){
			$scope.roomPolicyArr = resp;
		});
		if ($scope.showRowDetails == false) {
			$scope.editRoomView2 = false;
			$scope.editRoom['value1'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].noOfRows;
			$scope.editRoom['value2'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].noOfRacks;
			$scope.editRoom['value3'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].policy.name;
			$scope.editRoom['policyId'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].policy.id;
			$scope.editRoom['rackType'] = $scope.roomDetailsArr[$scope.selectedRoomIndex].rackType.id;
		} else {
			$scope.editRowView2 = false;
			$scope.editRoom['value1'] =  $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalNumOfRacks;
			$scope.editRoom['value2'] = $scope.rowsArray[$scope.selectedRowIndex].rackType.id;
			$scope.editRoom['value3'] = $scope.rowsArray[$scope.selectedRowIndex].policy.name;
			$scope.getRowName();
		}
		$scope.getRoomName();
		
	}

	$scope.editRackSelected = function(event){ 
	 $scope.tooltipPolicy=false;
		 $('#num').focus();
		 $(event.currentTarget.parentElement).find('.group').children().addClass('actColor');
		 $('#editFormBox,#headerBox').show();

		$scope.editMode = true;	
		$scope.editRowView2 = false;
		$('.dropdown-toggle').prop('disabled', true);

		physicalService.getRackTypes(function(resp){
			$scope.rackTypesArr = resp;
		});
		$('#rackDeviceContent,#rackDeviceContent').find('.select-style').removeAttr('style');
		physicalService.getRoomPolicy(function(resp){
			$scope.roomPolicyArr = resp;
		});
		$scope.editRack['rackType']= $scope.racksArray[$scope.selectedRackIndex+$scope.displayedRackIndex].rackType.id;
		$scope.editRack['policyId'] = $scope.racksArray[$scope.selectedRackIndex+$scope.displayedRackIndex].policy.id;
		$scope.editRack['policy'] = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].policy.name;
		$scope.editRack['policyFlag'] = $scope.racksArray[$scope.selectedRackIndex+$scope.displayedRackIndex].policyInherited;
		$scope.editRack['isNetworkRack'] = $scope.racksArray[$scope.selectedRackIndex+$scope.displayedRackIndex].isNetworkTypeRack;
		$scope.getRoomName();
	}



	var rowEdited = false;

	$scope.editDetails = function(){
			
		$scope.tooltipPolicy=true;	
		var listRow = $('.list-container');
		listRow.prop('disabled', false);
		listRow.removeClass('backDrop');
		$('#deviceContent,#rowDeviceContent').find('.group').children().removeClass('actColor');
		$('#deviceContent, #rowDeviceContent').find('.select-style').css("background", "none");
		$scope.editMode = false;
		$('#editFormBox,#headerBox').hide();
		$('.dropdown-toggle').prop('disabled', false);
		if ($scope.showRowDetails == false) {
			var jsonObj = {
					noOfRows:$scope.editRoom['value1'],
					noOfRacks:$scope.editRoom['value2'],
					policyId: $scope.editRoom['policyId'],
				    rackTypeId: $scope.editRoom['rackType']
			}
			physicalService.editRoom(jsonObj,$scope.roomDetailsArr[$scope.selectedRoomIndex].id,function(resp){
				if (isNaN(resp)){
					$scope.chartView=true;
					$scope.roomDetailsArr[$scope.selectedRoomIndex].noOfRows = resp.noOfRows;
					$scope.roomDetailsArr[$scope.selectedRoomIndex].noOfRacks = resp.noOfRacks;
					$scope.roomDetailsArr[$scope.selectedRoomIndex].policy.id = resp.policy.id;
					$scope.roomDetailsArr[$scope.selectedRoomIndex].policy.name = resp.policy.name;
					$scope.roomDetailsArr[$scope.selectedRoomIndex].policy = resp.policy;
					$scope.roomDetailsArr[$scope.selectedRoomIndex].rackType.id = resp.rackType.id;
					$scope.editRoomButtonText ="Edit Room";
					$scope.deleteRoomButtonText = "Delete Room"
					$scope.showRoomDetails($scope.selectedRoomIndex);
			   	} else {
			   		$scope.showRoomDetails($scope.selectedRoomIndex);
			   	}
			});
		}
		else {
			var jsonObj = {
					noOfRacks:$scope.editRoom['value1'],
					rackTypeId:$scope.editRoom['value2'],
					policyId: $scope.editRoom['policyId'],
					policyInherited: $scope.editRoom['policyFlag']
			}
			physicalService.editRow(jsonObj,$scope.roomDetailsArr[$scope.selectedRoomIndex].id, $scope.rowsArray[$scope.selectedRowIndex].id, function(resp){
				$scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalNumOfRacks = resp.inventoryInfo.totalNumOfRacks;
				$scope.rowsArray[$scope.selectedRowIndex].rackType.id = resp.rackType.id;
				$scope.rowsArray[$scope.selectedRowIndex].policy = resp.policy;
				$scope.rackNavigationSelected = false;
				if($scope.rackDetails == true){
					rowEdited = true;
					$scope.rowAction($scope.selectedRowIndex);
				} else {
					$scope.changeRowDisplay($scope.selectedRowIndex);
				} 
			});
		}
	}

    $scope.editRackDetails = function(){
		var listRow = $('.list-container');
		$scope.tooltipPolicy= true;
		listRow.prop('disabled', false);
		listRow.removeClass('backDrop');
		$('#rackDeviceContent').find('.group').children().removeClass('actColor');
		$('#rackDeviceContent').find('.select-style').css("background", "none");
		$scope.editMode = false;
		$scope.editRowView2 = true;
		$('#editFormBox,#headerBox').hide();
		$('.dropdown-toggle').prop('disabled', false);
		var jsonObj = {
			policyId: $scope.editRack['policyId'],
		    policyInherited: $scope.editRack['policyFlag'],
		    rackTypeId: $scope.editRack['rackType'],
		    isNetworkTypeRack: $scope.editRack['isNetworkRack']==1?true:false
		}
		physicalService.editRack(jsonObj,$scope.roomDetailsArr[$scope.selectedRoomIndex].id, $scope.rowsArray[$scope.selectedRowIndex].id, $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].id,function(resp){
			$scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex] = resp;
			var list = document.getElementsByClassName("racksListClass");
			var len = list.length;
			for(var i=0; i<len; ++i){
				list[0].remove();
			} 
			$scope.createRacks();
			$scope.hideUnusedRacks();
			$scope.editRack['rackType']= $scope.racksArray[$scope.selectedRackIndex+ $scope.displayedRackIndex].rackType.id;
			$scope.editRack['policy'] = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].policy.name;
			$scope.editRack['policyId'] = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].policy.id;
			$scope.editRack['policyFlag'] = $scope.racksArray[$scope.selectedRackIndex+ $scope.displayedRackIndex].policyInherited;
			$scope.editRack['isNetworkRack'] = $scope.racksArray[$scope.selectedRackIndex+ $scope.displayedRackIndex].isNetworkTypeRack;
			});
	
	}

	$scope.cancelEditRackDetails = function() {		
		/*$scope.chartView=true;*/ 
		$scope.tooltipPolicy= true;
		$('#rackDeviceContent').find('.group').children().removeClass('actColor');
		$('#rackDeviceContent').find('.select-style').css("background", "none");
		$('#editFormBox,#headerBox').hide();
		$scope.editMode = false;
		$scope.editRowView2 = true;	
		$('.dropdown-toggle').prop('disabled', false);
		//$scope.editRoom=[];
		$scope.getRoomName();
		$scope.getRowName();
		$scope.editRack['rackType']= $scope.racksArray[$scope.selectedRackIndex+ $scope.displayedRackIndex].rackType.id;
		$scope.editRack['policy'] = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].policy.name;
		$scope.editRack['policyId'] = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].policy.id;
		$scope.editRack['policyFlag'] = $scope.racksArray[$scope.selectedRackIndex+ $scope.displayedRackIndex].policyInherited;
		$scope.editRack['isNetworkRack'] = $scope.racksArray[$scope.selectedRackIndex+ $scope.displayedRackIndex].isNetworkTypeRack;

	}

	$scope.validateDeleteRow = function(event){    
		$scope.postDeleteRow($scope.deleteRowVariables['roomId'], $scope.deleteRowVariables['rowId']);
		$("#deleteRowWindow").data("aciWindow").close();
		$('#lightBox').hide(); 
	}

    $scope.cancelEditDetails = function() {		
		/*$scope.chartView=true;*/ 
		$scope.tooltipPolicy=true;
		$('#deviceContent,#rowDeviceContent').find('.group').children().removeClass('actColor');
		$('#deviceContent, #rowDeviceContent').find('.select-style').css("background", "none");
		$('#editFormBox,#headerBox').hide();
		$scope.editMode = false;
		$('.dropdown-toggle').prop('disabled', false);
		if ($scope.showRowDetails == true) {
			$scope.editRoomButtonText ="Edit Row";	
			$scope.deleteRoomButtonText = "Delete Row"	
			$scope.editRowView2 = true;
		} else {
			$scope.editRoomButtonText ="Edit Room";	
			$scope.deleteRoomButtonText = "Delete Room"	
			$scope.editRoomView2 = true;
			$scope.editRoom['value3']= $scope.roomDetailsArr[$scope.selectedRoomIndex].policy.name;
		}
		//$scope.editRoom=[];
		$scope.getRoomName();
		$scope.getRowName();
		
	}

	$scope.showExpandedPolicy = function(){

		if($scope.rackDetails == false){
			if ($scope.editRoomView2==false) {
				$scope.room['policyDetail'] = !$scope.room['policyDetail'];
			}
		} else{
			if ($scope.selectedRackIndex ==  -1) {
				if ($scope.editRowView2==false && $scope.editRoom['policyFlag']==false) {
					$scope.room['policyDetail'] = !$scope.room['policyDetail'];
				}
			}
			else{
				if ($scope.editRowView2==false && $scope.editRack['policyFlag']==false) {
					$scope.room['policyDetail'] = !$scope.room['policyDetail'];
				}
			}
		} 
		$('.left_Scrollcontainer').slimScroll({distance: '5px',opacity: 1});

		  
	}
	 $scope.hideTerminationroomPolicy = function() {  
	    $scope.room['policyleafDetail'] = false;
	  };
	 $scope.hideTerminationPolicy = function() {  
	    $scope.room['roompolicyDetail'] = false;
	  };
	   $scope.hideteminationroomPolicy = function() {  
	    $scope.room['policyDetails'] = false;
	  };
 	  $scope.hidePolicy = function() {  
	    $scope.room['policyDetail'] = false;
	  };
	$scope.togglePolicyDisplay = function(){ 
		$scope.room['policyDetail'] = !$scope.room['policyDetail']; 
		$('.left_Scrollcontainer').slimScroll({distance: '5px',opacity: 1});
	}
    //  $scope.myClickHandler=function() {  
	   //    	$scope.room['policyDetail'] = !$scope.room['policyDetail']; 
    // }
   $scope.addServer=true;
   // $scope.checkBox = function(){  
   // 		if($('#addswitch').is(":checked")){ 
   // 			$('#already_terminate').hide();
   // 			 $('#p_id').removeAttr("disabled");
   // 			 $('.physicalLabels').removeAttr("disabled"); 
   // 			 $('#p_id').removeClass('disableItm');
   // 			 $scope.addServer=true;

   // 		}else{  
	  //  			 $('#p_id').prop('disabled', 'disabled');
	  //  			 $('#p_id').addClass('disableItm');
	  //  			 $('.physicalLabels').prop('disabled', 'disabled');
	  //  			 $scope.addServer=false;
	  //  			 $('#already_terminate').show();
   // 		}
   		
   // }
   $scope.radio1 = function(){  
   		 $('#already_terminate').hide();
   		 $('#p_id').removeClass('disableItm');
   		 $scope.addServer=true;
         $('.physicalLabels').removeAttr('disabled');
         $('#termFooter').find('input[type="submit"]').val('Place leaf & terminate');
   }
   $scope.radio2 = function(){  
   	$('#already_terminate').show();
   	$('#p_id').addClass('disableItm');
   	$('#termFooter').find('input[type="submit"]').val('Terminate with existing leaf');
   	 $scope.addServer=false;
     $('.physicalLabels').attr('disabled', 'disabled');
   }

	$scope.rowTerminationPolicyDetails = function(){  
 		 if(!$('#p_id').hasClass('disableItm')){ 
 		 	$scope.room.roompolicyDetail=false;
 		 }else{
 		 	$scope.room.roompolicyDetail=true;
 		 }
		$scope.room.roompolicyDetail=!$scope.room.roompolicyDetail;
		$('.left_Scrollcontainer').slimScroll({distance: '5px',opacity: 1});
	}
	$scope.rowTerminationPolicy = function(){
		 if(!$('#leafId').hasClass('disabledCls')){ 
 		 	$scope.room.policyleafDetail=false;
 		 }else{
 		 	$scope.room.policyleafDetail=true;
 		 }

		$scope.room.policyleafDetail=!$scope.room.policyleafDetail;
		$('.left_Scrollcontainer').slimScroll({distance: '5px',opacity: 1});
	}
	$scope.termServer=true;
	$scope.terminateSpine = function(){
		if($('#terminatespine').is(':checked')){
			$('#preSpine').removeClass('disableSpine'); $('.termDisable').hide();
			$scope.termServer=true;
		}else{
			$('#preSpine').addClass('disableSpine');
			$('.termDisable').show();
			$scope.termServer=false;
		}
	}

	$scope.roomTerminationPolicy = function(){
		if(!$('#preSpine').hasClass('disableSpine')){ 
 		 	$scope.room.policyDetails=false;
 		 }else{
 		 	$scope.room.policyDetails=true;
 		 }

		$scope.room.policyDetails=!$scope.room.policyDetails;
		$('.left_Scrollcontainer').slimScroll({distance: '5px',opacity: 1});
	}
	$scope.validateDeleteRow = function(event){    
		$scope.postDeleteRow($scope.deleteRowVariables['roomId'], $scope.deleteRowVariables['rowId']);
		$("#deleteRowWindow").data("aciWindow").close();
		$('#lightBox').hide();
	}
	$scope.ntRack=true;
	$scope.networkRack = function(){ 
		if($('#networkRacks').is(':checked')){
			$('.nRack').find('select').prop('disabled','disabled');
			 $('.netRack').show();
			$scope.ntRack=true;
		}else{
			$('.nRack').find('select').removeAttr('disabled');
			 $('.netRack').hide();
			$scope.ntRack=false;
		}
	}

	$scope.onCancelDeleteRow = function () {
		$("#deleteRowWindow").data("aciWindow").close();
		$scope.deleteRowSelected=false;
		$('#lightBox').hide();
	}

	$scope.postDeleteRow= function (selectedRoomId, selectedRowId) {
		physicalService.deleteRow(selectedRoomId,selectedRowId, function(resp){
			physicalService.getAllRows(selectedRoomId,function(resp){
				$scope.rowsArray = resp;
			});
			physicalService.getRooms(function(resp){
				$scope.roomDetailsArr = resp;
			});
			prevActiveTab1 = "deviceTab";
			prevActiveContent1 = "deviceContent";
			$scope.deleteRowSelected = false;
			//$scope.showRowDetails = false;
			$scope.rackDetails = true;
			$scope.rowAction($scope.selectedRowIndex);
			
		});
		$scope.rackDetails=false;
		$scope.rackCount = false;
		$scope.editRoomButtonText ="Edit Room";	
		$scope.deleteRoomButtonText = "Delete Room"
	}
	
	$scope.validateDeleteRack = function(event){ 
		$("#deleteRackWindow").data("aciWindow").close();
		$scope.postDeleteRack($scope.deleteRackVariables['roomId'], $scope.deleteRackVariables['rowId'], $scope.deleteRackVariables['rackId']);
		$('#lightBox').hide();
	}
	
	$scope.onCancelDeleteRack = function () {
		$("#deleteRackWindow").data("aciWindow").close();
		$('#lightBox').hide();
	}

	$scope.postDeleteRack= function (selectedRoomId, selectedRowId, selectedRackId) {
			//get the index of the rack to be deleted
			var index = -1;
			for(var i=0;i<$scope.racksArray.length;++i){
				if (selectedRackId == $scope.racksArray[i].id) {
					index = i;
				}
			}
		physicalService.deleteRack(selectedRoomId,selectedRowId, selectedRackId, function(resp){
			physicalService.getAllRacks(selectedRoomId,selectedRowId,function(resp){
					$scope.racksArray = resp;
					$scope.rackDeleted = true;
					$scope.rowAction($scope.selectedRowIndex);

					if (index == $scope.racksArray.length){
						//$scope.selectedRackIndex = -1;
						$scope.moveRacksLeft();
					}
					$scope.rackNavigationSelected=false;
					$scope.hideUnusedRacks();
					$scope.getRoomsAndRows();
				});
			});
	}

	$scope.leafDetails=false;
	
	$scope.leafRadio1 = function(){
		$scope.leafDetails=false;
		$('#leaf_terminate').hide();
		$('#termAction').val('Place & Terminate Leafs'); 
	}
	$scope.leafRadio2 = function(){
		$scope.leafDetails=true;
		$('#leaf_terminate').show();
		$('#termAction').val('Terminate Exsiting Leafs');
	}
	$scope.spineRadio1 = function(){
		$scope.spineDetails=false;
		$('#spains_terminate').hide();
		$('#termAction').val('Place & Terminate Spines'); 
	}
	$scope.spineRadio2 = function(){
		$scope.spineDetails=true;
		$('#spains_terminate').show();
		$('#termAction').val('Terminate Exsiting Spines');
	}


	$scope.moveRacksRight = function() {
		$scope.rackNavigationSelected = true;
		if($scope.selectedRackIndex + $scope.displayedRackIndex != $scope.racksArray.length-1) {
		//	$scope.selectedRackIndex +=1;
			$scope.showRackDetails($scope.selectedRackIndex+1);
			if ($scope.rackScrollPos == PHY_SIZER_CONSTANTS.DOWN) {
				$scope.scrollDownRacks();
			} else{
				$scope.scrollUpRacks();
			}
	    }
		if (($scope.selectedRackIndex == 3) && ($scope.racksArray.length > $scope.selectedRackIndex + $scope.displayedRackIndex)){
			$scope.displayedRackIndex +=1 ;
		//	$scope.selectedRackIndex = 0;
			var list = document.getElementsByClassName("racksListClass");
			var len = list.length;
			for(var i=0; i<len; ++i){
				list[0].remove();
			} 
			$scope.createRacks();
			$scope.hideUnusedRacks();
			$scope.hideRackScrollBars();
			$scope.showRackDetails(2);
			var list = document.getElementsByClassName("serverSvgSpan");
			var len = list.length;
			for(var i=0; i<len; ++i){
				list[0].remove();
			} 
            var switchlist = document.getElementsByClassName("switchSvgSpan");
			var len = switchlist.length;
			for(var i=0; i<len; ++i){
				switchlist[0].remove();
			} 
			$("li label").removeClass("activeServer");
			$('.serverDelBg').css("visibility", "hidden");
			$('.switchDelBg').css("visibility", "hidden");

			var serverText = document.getElementsByClassName("svgText");
			var len = serverText.length;
			for(var i=0; i<len; ++i){
				serverText[0].remove();
			}
			var switchText = document.getElementsByClassName("switchSvgText");
			var len = switchText.length;
			for(var i=0; i<len; ++i){
				switchText[0].remove();
			}
			$(".rackServerGroup").contents().unwrap();
			$(".rackSwitchGroup").contents().unwrap();
			$scope.removeSwitchClasses();
			$scope.getRackServers();
			$scope.getRackSwitches();
			if ($scope.rackScrollPos == PHY_SIZER_CONSTANTS.DOWN) {
				$scope.scrollDownRacks();
			} else{
				$scope.scrollUpRacks();
			}
		}
		$scope.rackNavigationSelected = false;
	}	

	$scope.moveRacksLeft = function() {
		$scope.rackNavigationSelected = true;
		if ($scope.selectedRackIndex + $scope.displayedRackIndex != 0) {
			$scope.selectedRackIndex -= 1;
	    }
		if($scope.selectedRackIndex >= 0) {
			$scope.showRackDetails($scope.selectedRackIndex);
			if ($scope.rackScrollPos == PHY_SIZER_CONSTANTS.DOWN) {
				$scope.scrollDownRacks();
			} else{
				$scope.scrollUpRacks();
			}
		}
		if (($scope.selectedRackIndex == -1) && ($scope.displayedRackIndex>0)){
			$scope.displayedRackIndex -= 1;
			var list = document.getElementsByClassName("racksListClass");
			var len = list.length;
			for(var i=0; i<len; ++i){
				list[0].remove();
			} 
			$scope.createRacks();
			$scope.hideUnusedRacks();
			$scope.hideRackScrollBars();
			$scope.showRacks();
			$scope.showRackDetails(0);
			var list = document.getElementsByClassName("serverSvgSpan");
			var len = list.length;
			for(var i=0; i<len; ++i){
				list[0].remove();
			}
			var switchlist = document.getElementsByClassName("switchSvgSpan");
			var len = switchlist.length;
			for(var i=0; i<len; ++i){
				switchlist[0].remove();
			} 
			//$("dd").removeClass("activeRack");
			//$scope.changeRowDisplay();
			$("li label").removeClass("activeServer");
			$('.serverDelBg').css("visibility", "hidden");
			$('.switchDelBg').css("visibility", "hidden");
			var serverText = document.getElementsByClassName("svgText");
			var len = serverText.length;
			for(var i=0; i<len; ++i){
				serverText[0].remove();
			}
			var switchText = document.getElementsByClassName("switchSvgText");
			var len = switchText.length;
			for(var i=0; i<len; ++i){
				switchText[0].remove();
			}
			$(".rackServerGroup").contents().unwrap();
			$(".rackSwitchGroup").contents().unwrap();
			$scope.removeSwitchClasses();
			$scope.getRackServers();
			$scope.getRackSwitches();
			if ($scope.rackScrollPos == PHY_SIZER_CONSTANTS.DOWN) {
				$scope.scrollDownRacks();
			} else{
				$scope.scrollUpRacks();
			} 
		}
		$scope.rackNavigationSelected = false;	
	}

	$scope.removeSwitchClasses = function(){
		var switchlist = document.getElementsByClassName("switchBorderRight");
			var len = switchlist.length;
			for(var i=0; i<len; ++i){
				switchlist[0].remove();
			} 
			var switchlist = document.getElementsByClassName("switchBorderLeft");
			var len = switchlist.length;
			for(var i=0; i<len; ++i){
				switchlist[0].remove();
			}
			var switchlist = document.getElementsByClassName("switchBorderTop");
			var len = switchlist.length;
			for(var i=0; i<len; ++i){
				switchlist[0].remove();
			}
			var switchlist = document.getElementsByClassName("switchBorderBottom");
			var len = switchlist.length;
			for(var i=0; i<len; ++i){
				switchlist[0].remove();
			}
			var switchlist = document.getElementsByClassName("switchImageBackground");
			var len = switchlist.length;
			for(var i=0; i<len; ++i){
				switchlist[0].remove();
			}
	}

	$scope.createRacks = function() {
		var list = document.getElementsByClassName("racksListClass");
		var len = list.length;
		for(var i=0; i<len; ++i){
			list[0].remove();
		}
		var list = document.getElementsByClassName("rackSwitchGroup");
		var len = list.length;
		for(var i=0; i<len; ++i){
			list[0].remove();
		} 
		var list = document.getElementsByClassName("rackServerGroup");
		var len = list.length;
		for(var i=0; i<len; ++i){
			list[0].remove();
		} 
		$scope.hideUnusedRacks();
		if ($scope.racksArray.length > 0) {
			var rackList1= document.getElementById("rack1");
			for (var i=0; i< $scope.racksArray[$scope.displayedRackIndex].rackType.physicalStats.noOfRus; ++i)
			//Create the new dropdown menu				
			{
				var effectiveId=$scope.racksArray[$scope.displayedRackIndex].rackType.physicalStats.noOfRus-i;
				var idTemp='rack1ru'+effectiveId;
				var rackList = document.createElement("li");
				rackList.setAttribute('data-ruid',idTemp);
				rackList.className ="racksListClass";
				var ruCount = document.createElement('span'); 
				ruCount.className="countNumber";
				var serverShow = document.createElement('label');
				rackList.appendChild(ruCount)
				rackList.appendChild(serverShow)
				serverShow.className = "rackClass";
				ruCount.value = i;
				ruCount.innerHTML =($scope.racksArray[$scope.displayedRackIndex].rackType.physicalStats.noOfRus-i); 
				rackList1.appendChild(rackList);
			}
		}
		if ($scope.racksArray.length > 1) {
			var rackList2= document.getElementById("rack2");
			if ($scope.racksArray[$scope.displayedRackIndex+1]) {
				var totalRus=$scope.racksArray[$scope.displayedRackIndex+1].rackType.physicalStats.noOfRus;
				for (var i=0; i< totalRus; ++i)
				//Create the new dropdown menu				
				{
					var effectiveId=totalRus-i;
					var idTemp='rack2ru'+effectiveId;
					var rackList = document.createElement("li");
					rackList.setAttribute('data-ruid',idTemp)
					rackList.className ="racksListClass";
					var ruCount = document.createElement('span'); 
					ruCount.className="countNumber";
					var serverShow = document.createElement('label');
					rackList.appendChild(ruCount)
					rackList.appendChild(serverShow)
					serverShow.className = "rackClass";
					ruCount.value = i;
					ruCount.innerHTML = ($scope.racksArray[$scope.displayedRackIndex+1].rackType.physicalStats.noOfRus-i); 
					rackList2.appendChild(rackList);
				}
			}
		}
		if ($scope.racksArray.length > 2) {
			var rackList3= document.getElementById("rack3");
			if ($scope.racksArray[$scope.displayedRackIndex+2]) {
				for (var i=0; i< $scope.racksArray[$scope.displayedRackIndex+2].rackType.physicalStats.noOfRus; ++i)
					//Create the new dropdown menu				
				{
					var effectiveId=$scope.racksArray[$scope.displayedRackIndex].rackType.physicalStats.noOfRus-i;
					var idTemp='rack3ru'+effectiveId;
					var rackList = document.createElement("li");
					rackList.setAttribute('data-ruid',idTemp);
					rackList.className ="racksListClass";

					var ruCount = document.createElement('span'); 
					ruCount.className="countNumber";
					var serverShow = document.createElement('label');
					rackList.appendChild(ruCount)
					rackList.appendChild(serverShow)
					serverShow.className = "rackClass";
					ruCount.value = i;
					ruCount.innerHTML =($scope.racksArray[$scope.displayedRackIndex+2].rackType.physicalStats.noOfRus-i); 
					rackList3.appendChild(rackList);
				}
			}
		}

			
		if($scope.rackServerList) {
			delete $scope.rackServerList;
		}
		$scope.rackServerList = new Array();
		for(var i=0; i< $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalNumOfRacks; ++i)
		{
			$scope.rackServerList[i] = new Array();
		}

		if($scope.rackSwitchList) {
			delete $scope.rackSwitchList;
		}
		$scope.rackSwitchList = new Array();
		for(var i=0; i< $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalNumOfRacks; ++i)
		{
			$scope.rackSwitchList[i] = new Array();
		}
		
		// Create the rackServer list from devices array
		for(var i=0; i< $scope.racksArray.length; ++i) {
			for(var k=0; k<$scope.racksArray[i].servers.length; ++k) {
				$scope.rackServerList[i].push($scope.racksArray[i].servers[k]);
			}
		}
		// Create rackSwitch list from devices array
		for(var i=0; i< $scope.racksArray.length; ++i) {
			for(var k=0; k<$scope.racksArray[i].switches.length; ++k) {
				$scope.rackSwitchList[i].push($scope.racksArray[i].switches[k]);
			}
		}

		$scope.getRackServers();
		$scope.getRackSwitches();
	}

	$scope.getRackServers = function() {

		$scope.rackServerIndex = [0,0,0];
		$scope.server =[];

		// Display the servers for Rack1
		if ($scope.rackServerList[$scope.displayedRackIndex]) {
			for(var i=0; i <$scope.rackServerList[$scope.displayedRackIndex].length; ++i) {
				for (var j =0; j<$scope.rackServerList[$scope.displayedRackIndex][i].numOfInstances * $scope.rackServerList[$scope.displayedRackIndex][i].deviceType.physicalStats.noOfRus; j++) {
					$scope.server['color'] = $scope.rackServerList[$scope.displayedRackIndex][i].deviceType.color;
					$scope.addServerImages(0);
				
				}
			$scope.addServerGroup(0,$scope.rackServerList[$scope.displayedRackIndex][i].numOfInstances * $scope.rackServerList[$scope.displayedRackIndex][i].deviceType.physicalStats.noOfRus, $scope.rackServerList[$scope.displayedRackIndex][i].id);
			
			}
		}
		// Display the servers for Rack2
		if ($scope.rackServerList[$scope.displayedRackIndex+1]) {
			for(var i=0; i <$scope.rackServerList[$scope.displayedRackIndex + 1].length; ++i) {
				for (var j =0; j<$scope.rackServerList[$scope.displayedRackIndex + 1][i].numOfInstances*$scope.rackServerList[$scope.displayedRackIndex +1][i].deviceType.physicalStats.noOfRus; j++) {
					$scope.server['color'] = $scope.rackServerList[$scope.displayedRackIndex+1][i].deviceType.color;
					$scope.addServerImages(1);
				}
				$scope.addServerGroup(1,$scope.rackServerList[$scope.displayedRackIndex+1][i].numOfInstances * $scope.rackServerList[$scope.displayedRackIndex+1][i].deviceType.physicalStats.noOfRus, $scope.rackServerList[$scope.displayedRackIndex+1][i].id);
			
			}
		}
		// Display the servers for 3
		if ($scope.rackServerList[$scope.displayedRackIndex+2]) {
			for(var i=0; i <$scope.rackServerList[$scope.displayedRackIndex + 2].length; ++i) {
				for (var j =0; j<$scope.rackServerList[$scope.displayedRackIndex + 2][i].numOfInstances*$scope.rackServerList[$scope.displayedRackIndex+2][i].deviceType.physicalStats.noOfRus; j++) {
					$scope.server['color'] = $scope.rackServerList[$scope.displayedRackIndex+2][i].deviceType.color;
					$scope.addServerImages(2);
				}
				$scope.addServerGroup(2,$scope.rackServerList[$scope.displayedRackIndex+2][i].numOfInstances * $scope.rackServerList[$scope.displayedRackIndex+2][i].deviceType.physicalStats.noOfRus, $scope.rackServerList[$scope.displayedRackIndex+2][i].id);
			}
		}
	}

	$scope.getRackSwitches = function() {
		//call backend code for getting switches for each rack
		$scope.rackSwitchIndex = [0,0,0];
		$scope.switchDetails=[];

		// Display the switches for Rack1
		if ($scope.rackSwitchList[$scope.displayedRackIndex]) {
			for(var i=0; i <$scope.rackSwitchList[$scope.displayedRackIndex].length; ++i) {
				for (var j =0; j<$scope.rackSwitchList[$scope.displayedRackIndex][i].deviceType.physicalStats.noOfRus; j++) {
					$scope.addSwitchImages(0,$scope.rackSwitchList[$scope.displayedRackIndex][i].id);
				}
				$scope.switchDetails['color']=$scope.rackSwitchList[$scope.displayedRackIndex][i].deviceType.color;
				$scope.addSwitchGroup(0,$scope.rackSwitchList[$scope.displayedRackIndex][i].deviceType.physicalStats.noOfRus,
					$scope.rackSwitchList[$scope.displayedRackIndex][i].id);
			}
		}
		// Display the switches for Rack2
		if ($scope.rackSwitchList[$scope.displayedRackIndex +1]) {
			for(var i=0; i <$scope.rackSwitchList[$scope.displayedRackIndex + 1].length; ++i) {
				for (var j =0; j<$scope.rackSwitchList[$scope.displayedRackIndex+1][i].deviceType.physicalStats.noOfRus; j++) {
					$scope.addSwitchImages(1,$scope.rackSwitchList[$scope.displayedRackIndex + 1][i].id);
				}
				$scope.switchDetails['color']=$scope.rackSwitchList[$scope.displayedRackIndex+1][i].deviceType.color;
				$scope.addSwitchGroup(1,$scope.rackSwitchList[$scope.displayedRackIndex+1][i].deviceType.physicalStats.noOfRus,
					$scope.rackSwitchList[$scope.displayedRackIndex + 1][i].id);
			}
		}
		// Display the switches for Rack3
		if ($scope.rackSwitchList[$scope.displayedRackIndex+2]) {
			for(var i=0; i <$scope.rackSwitchList[$scope.displayedRackIndex + 2].length; ++i) {
				for (var j =0; j<$scope.rackSwitchList[$scope.displayedRackIndex+2][i].deviceType.physicalStats.noOfRus; j++) {
					$scope.addSwitchImages(2,$scope.rackSwitchList[$scope.displayedRackIndex + 2][i].id);
				}
				$scope.switchDetails['color']=$scope.rackSwitchList[$scope.displayedRackIndex+2][i].deviceType.color;
				$scope.addSwitchGroup(2,$scope.rackSwitchList[$scope.displayedRackIndex+2][i].deviceType.physicalStats.noOfRus,
					$scope.rackSwitchList[$scope.displayedRackIndex+2][i].id);
			}
		}
	}

	$scope.hideUnusedRacks = function() {			
		if (!($scope.racksArray[$scope.displayedRackIndex]) || !($scope.racksArray[$scope.displayedRackIndex].rackType.physicalStats.noOfRus > 0))
		{
			$("#rackdd1").hide();
		}
		if (!($scope.racksArray[$scope.displayedRackIndex+1]) || !($scope.racksArray[$scope.displayedRackIndex+1].rackType.physicalStats.noOfRus > 0))
		{
			$("#rackdd2").hide();
		}
		if ((!$scope.racksArray[$scope.displayedRackIndex+2]) || !($scope.racksArray[$scope.displayedRackIndex+2].rackType.physicalStats.noOfRus > 0))
		{
			$("#rackdd3").hide();
		}
	}

	$scope.showRacks = function() {			
		if (($scope.racksArray[$scope.displayedRackIndex]) && ($scope.racksArray[$scope.displayedRackIndex].rackType.physicalStats.noOfRus > 0))
		{
			$("#rackdd1").show();
		}
		if (($scope.racksArray[$scope.displayedRackIndex+1]) && ($scope.racksArray[$scope.displayedRackIndex+1].rackType.physicalStats.noOfRus > 0))
		{
			$("#rackdd2").show();
		}
		if (($scope.racksArray[$scope.displayedRackIndex+2]) && ($scope.racksArray[$scope.displayedRackIndex+2].rackType.physicalStats.noOfRus > 0))
		{
			$("#rackdd3").show();
		}
	}

	$scope.getRoomName = function() {

		if ($scope.roomDetailsArr) {
			if ($scope.chartView==false && $scope.rackDetails==false) {
				$scope.displayRoomName = "Edit " + $scope.roomDetailsArr[$scope.selectedRoomIndex].name;
			} else{
				$scope.displayRoomName = $scope.roomDetailsArr[$scope.selectedRoomIndex].name;
			}
			if (($scope.rackDetails==false) && ($scope.showRowDetails == false)){
				$scope.displayRoomName += " Details";
			}
		}
	}


	$scope.changeRoomDisplay = function() { 
		if($scope.editMode==true){
			return false;
		}
		$scope.rackDetails = false;
		$scope.showRowDetails = false;
		$scope.editDelSwitchShow=false;
		$scope.deviceShow=false;
		$scope.serverAdded=false;
		$scope.rackAdded = false;
		$scope.showSwitch=false;
		$scope.showServer = false; 
		prevActiveTab1 = "deviceTab";
		prevActiveContent1 = "deviceContent";		
		$scope.selectedRowIndex = -1;
		if ($scope.chartView == false) {
			$scope.editRoomLabel1 = "Rows";
			$scope.editRoomLabel2 = "Racks";
			$scope.editRoomLabel3 = "Room Policy";
		}
		$scope.showRoomDetails($scope.selectedRoomIndex);
		$scope.getRoomName();
	}

	$scope.getRowName = function() {
		if ($scope.rowsArray.length > 0) {
			if ($scope.chartView==false && $scope.showRowDetails==true) {
				$scope.displayRowName = "Edit " + $scope.rowsArray[$scope.selectedRowIndex].name;
			} else {
				$scope.displayRowName = $scope.rowsArray[$scope.selectedRowIndex].name;
			}
			if ($scope.selectedRackIndex == -1){
				$scope.displayRowName += " Details";
			}
		}
	}

	$scope.changeRowDisplay = function(index) { 
		$('#editFormBox,#headerBox').hide();
		if ($scope.rowTermination || $scope.deleteRowSelected){
			return false;
		}
	    $scope.showRowDetails = true;
		$scope.editDelSwitchShow=false;
		$scope.deviceShow=false;
		$scope.rackAdded = false;
		$scope.serverAdded =false;
		$scope.showSwitch = false;
		$scope.showServer = false; 
		$scope.selectedRackIndex = -1;
		$scope.selectedRowIndex = index;
		$scope.editRowView2 = true;
		prevActiveTab1 = "rowDeviceTab";
		prevActiveContent1 = "rowDeviceContent";
		$scope.getRoomName();
	    $scope.getRowName();
	    $scope.getRowInventory();
	    $scope.editRoom['value1'] =  $scope.rowsArray[$scope.selectedRowIndex].inventoryInfo.totalNumOfRacks;
		if ($scope.rowsArray[$scope.selectedRowIndex].rackType)	{
			$scope.editRoom['value2'] = $scope.rowsArray[$scope.selectedRowIndex].rackType.id;
		}
		$scope.editRoom['value3'] = $scope.rowsArray[$scope.selectedRowIndex].policy.name;
		$scope.editRoom['policyFlag'] =  $scope.rowsArray[$scope.selectedRowIndex].policyInherited;
		$scope.hideRackScrollBars();

	
	}

  // Initialize the Row Details Show to be true
	$scope.editRack = [];
	var deviceClicked = false;

    $scope.hideRackScrollBars = function(){
    	var myDiv = $("#rack1scroll").get(0);
		$("#rack1scroll div.ngscrollbar-container-y").css('visibility', 'hidden');
		var myDiv = $("#rack2scroll").get(0);
		$("#rack2scroll div.ngscrollbar-container-y").css('visibility', 'hidden');
		var myDiv = $("#rack3scroll").get(0);
		$("#rack3scroll div.ngscrollbar-container-y").css('visibility', 'hidden');
    }

    $scope.rackScrollPos = PHY_SIZER_CONSTANTS.DOWN;

    $scope.scrollUpRacks = function(){
    	$("#rack1scroll div.ngscroll-content-container").css('margin-top', '0px');
		$("#rack2scroll div.ngscroll-content-container").css('margin-top', '0px');
		$("#rack3scroll div.ngscroll-content-container").css('margin-top', '0px');	
		$("#rack1scroll .ngscrollbar-y").css('top', '0px');
		$("#rack2scroll .ngscrollbar-y").css('top', '0px');
		$("#rack3scroll .ngscrollbar-y").css('top', '0px');
		$scope.rackScrollPos =PHY_SIZER_CONSTANTS.UP;
    }


    $scope.scrollDownRacks = function()
    {		
    	//Rack 1
    	var myDiv = $("#rack1scroll");
		if(myDiv.get(0).scrollHeight > myDiv.height()) {
    		var heightOfRackVisible = $("#rackdd1").height() - 28;
			var totalHeight = $("#rack1").height();
			var scrollVal =  -Math.abs(totalHeight - heightOfRackVisible);
			$("#rack1scroll div.ngscroll-content-container").css('margin-top', scrollVal -10 + 'px');
			var heightScrollContainer = $("#rack1scroll div.ngscrollbar-container-y").height();
			var heightScrollBar = $("#rack1scroll div.ngscrollbar-y").height();
			$("#rack1scroll .ngscrollbar-y").css('top', heightScrollContainer - heightScrollBar + 'px');
	   }

		//Rack2
		var myDiv = $("#rack2scroll");
		if(myDiv.get(0).scrollHeight > myDiv.height()) {
			var heightOfRackVisible = $("#rackdd2").height() - 28;
			var totalHeight = $("#rack2").height();
			var scrollVal =  -Math.abs(totalHeight - heightOfRackVisible);
			$("#rack2scroll div.ngscroll-content-container").css('margin-top', scrollVal -10 + 'px');
			var heightScrollContainer = $("#rack2scroll div.ngscrollbar-container-y").height();
			var heightScrollBar = $("#rack2scroll div.ngscrollbar-y").height();
			$("#rack2scroll .ngscrollbar-y").css('top', heightScrollContainer - heightScrollBar + 'px');
		}
		// Rack3 
		var myDiv = $("#rack3scroll");
		if(myDiv.get(0).scrollHeight > myDiv.height()) {
    		var heightOfRackVisible = $("#rackdd3").height() - 28;
			var totalHeight = $("#rack3").height();
			var scrollVal =  -Math.abs(totalHeight - heightOfRackVisible);
			$("#rack3scroll div.ngscroll-content-container").css('margin-top', scrollVal -10 + 'px');
			var heightScrollContainer = $("#rack3scroll div.ngscrollbar-container-y").height();
			var heightScrollBar = $("#rack3scroll div.ngscrollbar-y").height();
			$("#rack3scroll .ngscrollbar-y").css('top', heightScrollContainer - heightScrollBar + 'px');
		}
		$scope.rackScrollPos = PHY_SIZER_CONSTANTS.DOWN;
	}

    $scope.selectedRackSettings = function(){
		if($scope.selectedRackIndex == 0){
			$scope.addDevice1=true;
			$scope.addDevice2=false;
			$scope.addDevice3=false;
			var myDiv = $("#rack1scroll").get(0);
			$("#rack1scroll div.ngscrollbar-container-y").css('visibility', 'visible');
		}
		if($scope.selectedRackIndex == 1){
			$scope.addDevice1=false;
			$scope.addDevice2=true;
			$scope.addDevice3=false;
			var myDiv = $("#rack2scroll").get(0);
			$("#rack2scroll div.ngscrollbar-container-y").css('visibility', 'visible');
		}
		if($scope.selectedRackIndex == 2){
			$scope.addDevice1=false;
			$scope.addDevice2=false;
			$scope.addDevice3=true;
			var myDiv = $("#rack3scroll").get(0);
			$("#rack3scroll div.ngscrollbar-container-y").css('visibility', 'visible');
			
		}
    }

   
	$scope.showRackDetails = function(selectedRackIndex){

		if($scope.editMode==true){
			return false;
		}
		if (deviceClicked == true)
		{
			deviceClicked = false;
			$scope.selectedRackIndex = selectedRackIndex;
			$scope.hideRackScrollBars();
			$scope.selectedRackSettings();
			return false;
		}
		 

		// $("#rackdl div.ngscroll-content-container").css('margin-top', '-256px');
		// $("#rackd2 div.ngscroll-content-container").css('margin-top', '-256px');
		// $("#rackd3 div.ngscroll-content-container").css('margin-top', '-256px');


		$("li label").removeClass("activeServer");
		$('.serverDelBg').css("visibility", "hidden");
		$('.switchDelBg').css("visibility", "hidden");
		$scope.showRowDetails = false; // Change the row details show to false on clicking a rack
		$scope.showServer=false;
		$scope.rackAdded=false;
		$scope.serverAdded=false;
		$scope.deviceShow=false;
		$scope.showSwitch=false;
		$scope.editDelSwitchShow = false; 
		if($scope.selectedRackIndex == -1){
			prevActiveTab1 = "rackDeviceTab";
			prevActiveContent1 = "rackDeviceContent";
		}
		$scope.selectedRackIndex = selectedRackIndex;
		document.getElementById("ucsmanage").style.visibility="hidden";
		//changing the breadcrumb display 
		$scope.getRowName();

		$scope.getRackInventory();
		$scope.hideRackScrollBars();
		$scope.editRack['rackType']= $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].rackType.id;
		$scope.editRack['policy'] = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].policy.name;
		$scope.editRack['policyId'] = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].policy.id;
		$scope.editRack['policyFlag'] = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].policyInherited;
		$scope.editRack['isNetworkRack'] = $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].isNetworkTypeRack;
		$scope.selectedRackSettings();
		$scope.rackNavigationDisplay();
		
	}

	

	/**Server hide and shiw */
	$scope.showServer=false;
	$scope.showSwitch=false; 
	$scope.rackAdded=false;
	$scope.serverAdded=false;
	$scope.editDelSwitchShow = false;
	$scope.editablePortGroup=[];
	$scope.saveServer = function(deviceType){

		$scope.deviceType = deviceType;
		$scope.errorMsg = false;
		var elem = document.getElementById("addServerWindow");
		var window = $(elem);
		 $('.addserverScrollcontainer').slimScroll({distance: '5px',opacity: 1}); 	
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "680px",
				title: "Add Device",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		$('.a-window-action').click(function(){$('#lightBox').hide(); });
		window.data("aciWindow").open();
		window.data("aciWindow").center();

		var title=document.getElementById("addServerWindow_wnd_title");
		if (deviceType == PHY_SIZER_CONSTANTS.deviceType.server) {
			 title.innerHTML ="Adding Server";
			physicalService.getServerTemplateTypes(function(resp){		
			for(var i=0; i<resp.length; i++){
				$scope.serverTemplateTypes[i]={
					"id": resp[i].id,
					"name": resp[i].name,
					"rus": resp[i].physicalStats.noOfRus,
					"power": resp[i].powerCooling.power,
					"cooling": resp[i].powerCooling.coolingInBTU,
					"portGroups": resp[i].defaultPortGroup,
					"color": resp[i].color
				};		
			}
			});
		} else if (deviceType == PHY_SIZER_CONSTANTS.deviceType.fabricInterconnect) {
			title.innerHTML ="Adding Fabric Interconnect";
			physicalService.getFabricInterconnect(function(resp){		
			for(var i=0; i<resp.length; i++){
				$scope.serverTemplateTypes[i]={
					"id": resp[i].id,
					"name": resp[i].name,
					"rus": resp[i].physicalStats.noOfRus,
					"power": resp[i].powerCooling.power,
					"cooling": resp[i].powerCooling.coolingInBTU,
					"portGroups": resp[i].defaultPortGroup,
					"color": resp[i].color
				};		
			}
			//document.getElementById("ucsmanage").style.visibility="hidden";
			});
		} else if (deviceType == PHY_SIZER_CONSTANTS.deviceType.firewall) {
			title.innerHTML ="Adding Firewall";
			physicalService.getFireWallTemplateTypes(function(resp){		
			for(var i=0; i<resp.length; i++){
				$scope.serverTemplateTypes[i]={
					"id": resp[i].id,
					"name": resp[i].name,
					"rus": resp[i].physicalStats.noOfRus,
					"power": resp[i].powerCooling.power,
					"cooling": resp[i].powerCooling.coolingInBTU,
					"portGroups": resp[i].defaultPortGroup,
					"color": resp[i].color
				};		
			}
			});
		}

		$scope.showServer=true; 
		$scope.deviceTab=false;
		$scope.deviceShow=false;
		document.getElementById("serverrus").classList.remove("text_readonly");
		document.getElementById("serverpower").classList.remove("text_readonly");
		document.getElementById("servercooling").classList.remove("text_readonly");
		//$scope.deviceShow=true;
		$scope.showSwitch=false; 
		$scope.editDelSwitchShow = false;
		$scope.dropdownItem=false;
		$scope.server=[];
		$scope.serverList=[];
		$scope.serverForm=[];
		$scope.serverTemplateTypes=[];
		$scope.server['serverrus']='';
		$scope.server['instances']=1;
		var myDiv = $("#addServerScroll").get(0);
		var scrollVal = 0;
		$("#addServerScroll div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
	}
	

	$scope.updateServerInstanceDetails = function(){
		$scope.serverList=[];
		for(var i=0; i< $scope.serverTemplateTypes.length; ++i) {
			if ($scope.server['templateType']== $scope.serverTemplateTypes[i].id) {
				$scope.server['serverrus'] = $scope.serverTemplateTypes[i].rus;
				$scope.server['serverpower'] = $scope.serverTemplateTypes[i].power;
				$scope.server['servercooling'] = $scope.serverTemplateTypes[i].cooling;
				$scope.server['color'] = $scope.serverTemplateTypes[i].color;
				for(var j=0; j< $scope.serverTemplateTypes[i].portGroups.length; ++j) {
					$scope.serverList.push($scope.serverTemplateTypes[i].portGroups[j]);
					$scope.editablePortGroup[j]=false;
				}
			}
		}
		document.getElementById("serverrus").classList.add("text_readonly");
		document.getElementById("serverpower").classList.add("text_readonly");
		document.getElementById("servercooling").classList.add("text_readonly");
	}

	$scope.server =[];
	$scope.rackServerIndex = [0,0,0];
	$scope.rackSwitchIndex = [0,0,0];
	$scope.saveAddServer = function(){ 
		var data ={};
		data.name = $scope.serverForm['name'];
		data.speed = $scope.serverForm['speed'];
		data.numOfPorts = $scope.serverForm['numOfPorts'];
		data.type = $scope.serverForm['type'];
		data.redundancyModel = $scope.serverForm['redundancyModel'];
		

		if (!(data.name) && !(data.speed) && !(data.numOfPorts) && !(data.type) && !(data.redundancyModel)){
			// If all fields are empty do nothing 

		} else {
			if (!(data.name) || !(data.speed) || !(data.numOfPorts) || !(data.type) || !(data.redundancyModel)){
				// If few fields are not filled, display error message
				$scope.errorMsg = true;
				return false;
			} else{
				// If all fields are filled, use teh port group
				if ((data.redundancyModel == "A-A") || (data.redundancyModel == "A-S")) {
					data.placement = "2-TOR";
				} else {
					data.placement = "1-TOR";
				}
				$scope.serverList.push(data);  
				$scope.errorMsg=false;
			}
		}

		
		if($scope.serverList.length == 0) {
			$scope.errorServMsg = true;
			return false; 
		} else {
			$scope.errorServMsg = false;
		}
		$('#lightBox').hide();$('.a-window-action').click(function(){$('#lightBox').hide();});
		var jsonObj = {
				templateTypeId: $scope.server['templateType'],
				numOfInstances: $scope.server['instances'],
				ucsManaged: $scope.server['ucs'],
				portGroups: $scope.serverList
		}

		physicalService.addServer(jsonObj, $scope.roomDetailsArr[$scope.selectedRoomIndex].id, $scope.rowsArray[$scope.selectedRowIndex].id, $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].id,function (resp) {	
			$scope.rackServerList[$scope.displayedRackIndex + $scope.selectedRackIndex].push(resp);

			var numOfRus = resp.deviceType.physicalStats.noOfRus; 
			$scope.getRoomsAndRows();
			physicalService.getAllRacks($scope.roomDetailsArr[$scope.selectedRoomIndex].id,$scope.rowsArray[$scope.selectedRowIndex].id,function(resp){
				$scope.racksArray = resp;
				$scope.getRackInventory();
			});
		 
			for (var i =0; i<resp.numOfInstances*numOfRus; i++)
				$scope.addServerImages($scope.selectedRackIndex);
			$scope.addServerGroup($scope.selectedRackIndex, resp.numOfInstances*numOfRus, resp.id);

			$scope.showServer=false;
			$scope.deviceShow=true;

			/* Adjust left pane scroll position to the selected row */
			/*var rowWidth = 41;
			var div = $("#wid60scrollbar").get(0);
			var scrollVal = -Math.abs(($scope.selectedRowIndex)*rowWidth);

			var marginTop = $("#wid60scrollbar div.ngscroll-content-container").css('margin-top');
			if (marginTop != (scrollVal +'px'))
    		$("#wid60scrollbar div.ngscroll-content-container").css('margin-top', scrollVal + 'px');

			/* Adjust rack scroll position to th newly added server */
			//width of each added server Image - might have to change if images change */
		    var heightOfli = $("li .racksListClass").height();

			$scope.scrollDownRacks();

            if ($scope.selectedRackIndex == 0){
				var myDiv = $("#rack1scroll").get(0);
		     	var totalHeight = $("#rack1").height();
		     	var heightOfRackVisible = $("#rackdd1").height() - 28;
				var scrollVal =  -Math.abs(totalHeight - heightOfRackVisible);
				$("#rack1scroll div.ngscroll-content-container").css('margin-top', scrollVal -10 + 'px');
				var heightScrollContainer = $("#rack1scroll div.ngscrollbar-container-y").height();
				var heightScrollBar = $("#rack1scroll div.ngscrollbar-y").height();
				$("#rack1scroll .ngscrollbar-y").css('top', heightScrollContainer - heightScrollBar + 'px');

				var heightOfRackVisible = $("#rackdd1").height() - 28;
				if(heightOfli * ($scope.rackServerIndex[0]) > heightOfRackVisible) {
					var scrollVal =  -Math.abs(($scope.racksArray[$scope.displayedRackIndex+$scope.selectedRackIndex].rackType.physicalStats.noOfRus - ($scope.rackServerIndex[0])) * heightOfli);
					$("#rack1scroll div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
				}
		    } else if($scope.selectedRackIndex == 1){
		     	var myDiv = $("#rack2scroll").get(0);
		     	var heightOfRackVisible = $("#rackdd2").height() - 28;
		     	//First scroll down
		     	var totalHeight = $("#rack2").height();
				var scrollVal =  -Math.abs(totalHeight - heightOfRackVisible);
				$("#rack2scroll div.ngscroll-content-container").css('margin-top', scrollVal -10 + 'px');
				var heightScrollContainer = $("#rack2scroll div.ngscrollbar-container-y").height();
				var heightScrollBar = $("#rack2scroll div.ngscrollbar-y").height();
				$("#rack2scroll .ngscrollbar-y").css('top', heightScrollContainer - heightScrollBar + 'px');

				if(heightOfli * ($scope.rackServerIndex[1]) > heightOfRackVisible) {
					var scrollVal =  -Math.abs(($scope.racksArray[$scope.displayedRackIndex+$scope.selectedRackIndex].rackType.physicalStats.noOfRus - ($scope.rackServerIndex[1])) * heightOfli);
					$("#rack2scroll div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
				}
		    }else if($scope.selectedRackIndex == 2){
		    	var myDiv = $("#rack3scroll").get(0);
		    	var heightOfRackVisible = $("#rackdd3").height() - 28;
		    	//First scroll down
				var totalHeight = $("#rack3").height();
				var scrollVal =  -Math.abs(totalHeight - heightOfRackVisible);
				$("#rack3scroll div.ngscroll-content-container").css('margin-top', scrollVal -10  + 'px');
				var heightScrollContainer = $("#rack3scroll div.ngscrollbar-container-y").height();
				var heightScrollBar = $("#rack3scroll div.ngscrollbar-y").height();
				$("#rack3scroll .ngscrollbar-y").css('top', heightScrollContainer - heightScrollBar + 'px');

				if(heightOfli * ($scope.rackServerIndex[2]) > heightOfRackVisible) {
					var scrollVal =  -Math.abs(($scope.racksArray[$scope.displayedRackIndex+$scope.selectedRackIndex].rackType.physicalStats.noOfRus - ($scope.rackServerIndex[2])) * heightOfli);
					$("#rack3scroll div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
				}
		    }

		    var rack = $scope.getSelectedRackId($scope.selectedRackIndex);
		    $('.rackServerGroup li').find('label').removeClass('activeServer');
		    $('.rackSwitchGroup li').find('label').removeClass('activeServer');
		    $('.serverDelBg').css("visibility","hidden");
		    $('.switchDelBg').css("visibility", "hidden");
		    var ele = $('#'+rack+' .rackServerGroup label').slice(0, resp.numOfInstances*numOfRus);
		    ele.addClass("activeServer");

		    $('#'+rack+' .rackServerGroup .serverDelBg').first().css("visibility","visible");
		   if(ele.length==1){ 
	    		ele.find('svg').css('height','26px')
	    	}

		    $scope.serverGroupClicked(null,$scope.selectedRackIndex,resp.id, 0);
    	})	
		$("#addServerWindow").data("aciWindow").close();
	}

	$scope.addServerGroup = function(rackNum, number, id) {
  	        var index = $scope.racksArray[$scope.displayedRackIndex +rackNum].rackType.physicalStats.noOfRus - $scope.rackServerIndex[rackNum] -1;
            /* The below code is commented as there was issue with server grouping if RU's are increased in edit Rack
		    var effectiveRackUnit=$scope.rackServerIndex[rackNum]-number;
			var rack = $scope.getSelectedRackId(rackNum);
			var dataExp="[data-ruid^="+rack+"ru]";
			
			$(dataExp).filter(function(){ 
				return this.getAttribute('data-ruid').split('ru')[1]>effectiveRackUnit && this.getAttribute('data-ruid').split('ru')[1]<=effectiveRackUnit+number;

			}).wrapAll('<div class="rackServerGroup" />');*/
			var rack = $scope.getSelectedRackId(rackNum);
 			$('#'+rack+' li').slice(index+1,index+number+1).wrapAll('<div class="rackServerGroup position_relative"/>');
			if(number==1){
			 var dataExp1="[data-ruid^="+rack+"ru"+$scope.rackServerIndex[rackNum]+"]";
			 $(dataExp1).find('svg').css('height','26px');
			}

			 for(var i=0;i<$scope.rackServerList[$scope.displayedRackIndex + rackNum].length;++i) {
				if($scope.rackServerList[$scope.displayedRackIndex + rackNum][i].id == id){
					var template = $scope.rackServerList[$scope.displayedRackIndex + rackNum][i].deviceType.name;
					var instances = $scope.rackServerList[$scope.displayedRackIndex + rackNum][i].numOfInstances;
				}
			}

			var middle = number/2;
			if (parseInt(middle) !== middle) {
				// if it is not a whole number
				middle = Math.floor(middle);
			} 
			$('#'+rack+' .rackServerGroup li .rackClass').slice(middle,middle+1).each(function(){
				var label = document.createElement("div");
				label.className = "svgText";
				label.innerHTML = template;
				label.style.color="#3bb7d9";
				$(this).append(label);
				label.style.marginTop =  -40 + "px";
				if ((parseInt(number/2)) !== number/2) {
					if(number == 1) {
						label.style.marginTop =  10 + "px";
					} else {
						label.style.marginTop =  -20 + "px";
					}
				}
				if (number == 2){
					label.style.marginTop = -8 + "px";
				}
				if(number>2){
					var label1 = document.createElement("div");
					label1.className = "serverSvgCircle";
					label1.innerHTML = instances;
					$(this).append(label1);
					label1.style.marginTop =  -15 + "px";
					if ((parseInt(number/2)) !== number/2) {
						if(number == 1) {
							label1.style.marginTop =  10 + "px";
						} else {
							label1.style.marginTop =  7 + "px";
						}
					}
				}

			});

		    /*Adding click event for a server group */  
			var elem = $('#'+rack+' .rackServerGroup li').slice(0,number);
		//	elem.append(textNode);
			elem.id = id;
			for(var i=0; i < elem.length; ++i) {
				elem[i].addEventListener("click", function(event){
					$scope.serverGroupClicked(event,rackNum,id,1);
				});
			}
			$('#'+rack+' .rackServerGroup li').slice(0,1).append("<div class='serverDelBg'><i class='fa fa-trash cursor serverDelIcon' style:'visibility:hidden;'></i></div>");
			$(".serverDelIcon").click(function (event) {
   					$scope.deleteServer();
				});		
	}
	$scope.deviceShow = false;
	$scope.serverGroup=[];
 

	$scope.serverGroupClicked = function(event,rackNum, id, flag) { 
		$('.left_Scrollcontainer').slimScroll({distance: '0px',opacity: 1});
		if(flag == 1){
			$('.rackServerGroup li').find('label').removeClass('activeServer');
			$('.rackSwitchGroup li').find('label').removeClass('activeServer');
			$('.serverDelBg').css("visibility", "hidden");
			$('.switchDelBg').css("visibility", "hidden");
			$(event.currentTarget.parentElement.children).find("label").addClass("activeServer");
			$(event.currentTarget.parentElement.children).find(".serverDelBg").css("visibility","visible");
	    	deviceClicked = true;

	    	if($(event.currentTarget.parentElement.children).find("label").length==1){ 
	    		$(event.currentTarget.parentElement.children).find('svg').css('height','26px');
	    		//$(event.currentTarget.parentElement.children).find('.serverSvgSpan').css('top','0px')
	    	}
	    }

		$scope.deviceShow = true;
		$scope.editDelSwitchShow = false;
		$scope.serverAdded=false;
		$scope.serverList=[];
		$scope.serverGroup=[];
		var deviceType;
		for(var i=0;i<$scope.rackServerList[$scope.displayedRackIndex + rackNum].length;++i) {
			if($scope.rackServerList[$scope.displayedRackIndex + rackNum][i].id == id){
				$scope.serverGroupId = id;
				$scope.serverGroupRackNum = rackNum;
				$scope.serverGroup['templateType'] = $scope.rackServerList[$scope.displayedRackIndex + rackNum][i].deviceType.name;
				$scope.serverGroup['templateId'] = $scope.rackServerList[$scope.displayedRackIndex + rackNum][i].deviceType.id;
				$scope.serverGroup['instances'] = $scope.rackServerList[$scope.displayedRackIndex + rackNum][i].numOfInstances;
				$scope.serverGroup['ucs'] = $scope.rackServerList[$scope.displayedRackIndex + rackNum][i].isUcsManaged;
				$scope.serverGroup['list'] = $scope.rackServerList[$scope.displayedRackIndex + rackNum][i].portGroups;
				deviceType = $scope.rackServerList[$scope.displayedRackIndex + rackNum][i].deviceType.type;
				if (deviceType == "Server")
				{
					$scope.serverGroup['showUcsManaged'] = true;
				} else {
					$scope.serverGroup['showUcsManaged'] = false;
				}
				for (var j=0; j<$scope.rackServerList[$scope.displayedRackIndex + rackNum][i].portGroups.length; ++j) {
					$scope.serverList.push($scope.rackServerList[$scope.displayedRackIndex + rackNum][i].portGroups[j]);
					$scope.editablePortGroup[j]=false;
				}	
				break;
			}
		}
		if (deviceType == "Server") {
			physicalService.getServerTemplateTypes(function(resp){		
				for(var i=0; i<resp.length; i++){
					if (resp[i].id == $scope.serverGroup['templateId']){
						$scope.serverGroup['rus']= resp[i].physicalStats.noOfRus,
						$scope.serverGroup['power']= resp[i].powerCooling.power,
						$scope.serverGroup['cooling']=resp[i].powerCooling.coolingInBTU
					}
				}
			});
		} else if(deviceType == "Firewall") {
			physicalService.getFireWallTemplateTypes(function(resp){		
				for(var i=0; i<resp.length; i++){
					if (resp[i].id == $scope.serverGroup['templateId']){
						$scope.serverGroup['rus']= resp[i].physicalStats.noOfRus,
						$scope.serverGroup['power']= resp[i].powerCooling.power,
						$scope.serverGroup['cooling']=resp[i].powerCooling.coolingInBTU
					}
				}
			});
		} else if(deviceType == "Fabric Interconnect") {
			physicalService.getFabricInterconnect(function(resp){		
				for(var i=0; i<resp.length; i++){
					if (resp[i].id == $scope.serverGroup['templateId']){
						$scope.serverGroup['rus']= resp[i].physicalStats.noOfRus,
						$scope.serverGroup['power']= resp[i].powerCooling.power,
						$scope.serverGroup['cooling']=resp[i].powerCooling.coolingInBTU
					}
				}
			});
		}
			
	}
	
	$scope.validateDeleteServer = function(event){  

			physicalService.deleteServer($scope.roomDetailsArr[$scope.selectedRoomIndex].id, $scope.rowsArray[$scope.selectedRowIndex].id, $scope.racksArray[$scope.serverGroupRackNum + $scope.displayedRackIndex].id, $scope.serverGroupId, function(resp){
				$scope.editDevice = true;
				$scope.getRoomsAndRows();
				$scope.rackNavigationSelected = false;
				$scope.rowAction($scope.selectedRowIndex);
			});
			$("#deleteServerWindow").data("aciWindow").close();
			$('#lightBox').hide();
	}
	
	$scope.onCancelDeleteServer = function () {
		$("#deleteServerWindow").data("aciWindow").close();
		$('#lightBox').hide();$('.a-window-action').click(function(){$('#lightBox').hide();});
	}

	$scope.deleteServer = function(){ 
		var window = $("#deleteServerWindow");		
		$('#lightBox').show();
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "300px",
				title: "Delete Server",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});

		}
		$('.a-window-action').click(function(){$('#lightBox').hide();});
		var label = $('#delServerLabel');
		label.text(DIALOG_CONSTANTS.deleteLabel.label + "?");

		window.data("aciWindow").open();
		window.data("aciWindow").center();
	}


	$scope.getSelectedRackId = function(rackIndex){
		var id;
		if(rackIndex == 0) {
			id = "rack1";
		} else if(rackIndex == 1) {
			id = "rack2";
		} else {
			id = "rack3";
		}
		return id;
	}

	$scope.addServerImages = function(rackId){
		var spanEle = document.createElement("span");
		spanEle.className = "serverSvgSpan";
		var svgEle = document.createElementNS("http://www.w3.org/2000/svg",'svg');
		var svgimg = document.createElementNS('http://www.w3.org/2000/svg','path'); 
		svgEle.setAttributeNS(null,'viewBox','-5 6 165 4'); 
		svgEle.setAttributeNS(null,'width',100+'%');
		svgimg.setAttributeNS(null,'cursor','pointer');
		svgEle.setAttributeNS(null,'height','40');
		//svgimg.setAttributeNS(null,'d', PHY_SIZER_CONSTANTS.serverImage.path1);
		svgimg.style.fill = $scope.server['color'];
		svgimg.setAttributeNS(null, 'visibility', 'visible');
		var svgimg2 = document.createElementNS('http://www.w3.org/2000/svg','path');
		svgimg2.setAttributeNS(null,'d', PHY_SIZER_CONSTANTS.serverImage.path2);
		svgimg2.style.fill = "rgb(163, 155, 155)";
		svgimg2.setAttributeNS(null, 'visibility', 'visible');

		svgEle.appendChild(svgimg);
		svgEle.appendChild(svgimg2);
		spanEle.appendChild(svgEle);
		var id = $scope.getSelectedRackId(rackId);
		
		var nextRackIndex = $scope.racksArray[$scope.displayedRackIndex+rackId].rackType.physicalStats.noOfRus- ($scope.rackServerIndex[rackId] + 1);
		var listEle = document.getElementById(id).getElementsByTagName("label")[nextRackIndex];
		listEle.appendChild(spanEle);
		$scope.rackServerIndex[rackId] += 1;
	}

$scope.addSwitchImages = function(rackId, switchId){
		var spanEle = document.createElement("span");
		spanEle.className = "switchSvgSpan";
		var svgEle = document.createElementNS("http://www.w3.org/2000/svg",'svg');
		var svgimg = document.createElementNS('http://www.w3.org/2000/svg','path');
		svgEle.setAttributeNS(null,'viewBox','-8 7 175 5'); 
		svgEle.setAttributeNS(null,'width',100+'%');
		svgEle.style.fill = "red";
		svgEle.setAttributeNS(null,'height','35');
		svgimg.setAttributeNS(null,'d', PHY_SIZER_CONSTANTS.switchImage.path1);
		svgimg.style.fill = "#7F7F7F";
		svgimg.setAttributeNS(null, 'visibility', 'visible');
		svgEle.appendChild(svgimg);
		spanEle.appendChild(svgEle);
		var id;
		if(rackId == 0) {
			id = "rack1";
		} else if(rackId == 1) {
			id = "rack2";
		} else {
			id = "rack3";
		}
		var nextRackIndex =$scope.rackSwitchIndex[rackId];			
		document.getElementById(id).getElementsByTagName("label")[nextRackIndex].appendChild(spanEle);
		document.getElementById(id).getElementsByTagName("label")[nextRackIndex].id = switchId;		
		$scope.rackSwitchIndex[rackId] += 1;		
	}

	$scope.showSwitchDetails = [];

	$scope.editDeleteSwitch = function(event, rackNum, id, flag){
		//only for click event
		if(flag == false) {
			deviceClicked = true;
			$('.rackSwitchGroup li').find('label').removeClass('activeServer');
			$('.rackServerGroup li').find('label').removeClass('activeServer');
			$('.serverDelBg').css("visibility", "hidden");
			$('.switchDelBg').css("visibility", "hidden");
   			$(event.currentTarget.parentElement.children).find("label").addClass("activeServer");
   			$(event.currentTarget.parentElement.children).find(".switchDelBg").css("visibility","visible");
		}
		$scope.editDelSwitchShow = true;		 		
		$scope.editDeleteDetails = {
			"switchId": id
			}	
	    $scope.deviceShow= false;
		$scope.rackAdded= false;
		$scope.serverAdded= false;
		

		for(var i=0;i<$scope.rackSwitchList[$scope.displayedRackIndex + rackNum].length;++i) {
			if($scope.rackSwitchList[$scope.displayedRackIndex + rackNum][i].id == id){
				$scope.switchGroupId = id;
				$scope.switchGroupRackNum = rackNum;	
				//Uncomment after getting details from backend			
			    $scope.showSwitchDetails['deviceType'] = $scope.rackSwitchList[$scope.displayedRackIndex + rackNum][i].deviceType.type; 
				$scope.showSwitchDetails['templateType'] = $scope.rackSwitchList[$scope.displayedRackIndex +rackNum][i].deviceType.name;
				$scope.showSwitchDetails['templateId'] = $scope.rackSwitchList[$scope.displayedRackIndex +rackNum][i].deviceType.id;				
				$scope.showSwitchDetails['rus'] = $scope.rackSwitchList[$scope.displayedRackIndex +rackNum][i].deviceType.physicalStats.noOfRus;
				$scope.showSwitchDetails['power'] = $scope.rackSwitchList[$scope.displayedRackIndex +rackNum][i].deviceType.powerCooling.power;
				$scope.showSwitchDetails['cooling'] = $scope.rackSwitchList[$scope.displayedRackIndex +rackNum][i].deviceType.powerCooling.coolingInBTU;
				$scope.showSwitchDetails['list'] = $scope.rackSwitchList[$scope.displayedRackIndex +rackNum][i].portGroups;
			}
		
		}
		if(!flag)
			$scope.$apply();
	}

	$scope.cancelAddServer = function(){
		$scope.showServer=false;
		$scope.deviceTab=true;
		$scope.server=[];
		$scope.serverList=[];
		$scope.serverForm=[];
		$("#addServerWindow").data("aciWindow").close();
		$('#lightBox').hide();
	}


	$scope.onCancelDeleteSwitch = function () {
		$("#deleteSwitchWindow").data("aciWindow").close();
		$('#lightBox').hide();
	}

	$scope.deleteSwitch = function(){
		var window = $("#deleteSwitchWindow");
		$('#lightBox').show();		
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "300px",
				title: "Delete Switch",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		$('.a-window-action').click(function(){$('#lightBox').hide();}); 
		var label = $('#delSwitchLabel');
		label.text(DIALOG_CONSTANTS.deleteLabel.label + "?");

		window.data("aciWindow").open();
		window.data("aciWindow").center();
	}

	$scope.validateDeleteSwitch = function(){
		var switchId= $scope.editDeleteDetails['switchId'];		
		var actualRackNum;
		var actualRUNum; 
		for(var i=0; i<$scope.rackSwitchList.length; i++){
			for(j=0; j<$scope.rackSwitchList[i].length; j++){
				if($scope.rackSwitchList[i][j].id == switchId){
					actualRackNum = i;
					actualRUNum = j;
					break;					
				}
			}
		}
		physicalService.deleteSwitch($scope.roomDetailsArr[$scope.selectedRoomIndex].id, $scope.rowsArray[$scope.selectedRowIndex].id, $scope.racksArray[actualRackNum].id, switchId, function (resp) {	
				physicalService.getAllRacks($scope.roomDetailsArr[$scope.selectedRoomIndex].id,$scope.rowsArray[$scope.selectedRowIndex].id,function(resp){
				$scope.editDevice = true;
				$scope.getRoomsAndRows();
				$scope.rackNavigationSelected = false;
				$scope.rowAction($scope.selectedRowIndex);
				});
		});	
		$("#deleteSwitchWindow").data("aciWindow").close();
		$('#lightBox').hide();
		$scope.editDelSwitchShow = false;
	}

	$scope.saveAddSwitch = function(){

		$scope.showSwitch=false; 
		$scope.rackAdded=false;   
		$('#lightBox').hide();
		

		var jsonObj = {
			templateTypeId : $scope.switchDetails['templateType'],				
			portGroups : $scope.switchDetails['list'],
			deviceType : $scope.switchDetails['deviceType'],
			isSpine: ($scope.switchDetails['deviceType'] == "Leaf")?"false":"true"
		}
		
		physicalService.addSwitch(JSON.stringify(jsonObj), $scope.roomDetailsArr[$scope.selectedRoomIndex].id, $scope.rowsArray[$scope.selectedRowIndex].id, $scope.racksArray[$scope.selectedRackIndex + $scope.displayedRackIndex].id,function (resp) {	
			$scope.rackSwitchList[$scope.displayedRackIndex + $scope.selectedRackIndex].push(resp);		
			for(var i=0; i<resp.deviceType.physicalStats.noOfRus; i++){
					$scope.addSwitchImages($scope.selectedRackIndex , resp.id);
			}			
			$scope.addSwitchGroup($scope.selectedRackIndex ,resp.deviceType.physicalStats.noOfRus, resp.id);

			/* Adjust left pane scroll position to the selected row*/
			/*var rowWidth = 41;
			var div = $("#wid60scrollbar").get(0);
			var scrollVal = -Math.abs(($scope.selectedRowIndex)*rowWidth);

			var marginTop = $("#wid60scrollbar div.ngscroll-content-container").css('margin-top');
			if (marginTop != (scrollVal +'px'))
    		$("#wid60scrollbar div.ngscroll-content-container").css('margin-top', scrollVal + 'px');*/
    		$scope.scrollUpRacks();
			
			var heightOfli = $("li .racksListClass").height();  		

			var widthOfSwitch = 22;
          	if ($scope.selectedRackIndex == 0){
          		var heightOfRackVisible = $("#rackdd1").height() - 28; //28 is the height of the top portion
		    	var totalHeight = $("#rack1").height();
				var myDiv = $("#rack1scroll").get(0);
				var scrollVal = 0;
				$("#rack1scroll div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
				if($scope.racksArray[$scope.displayedRackIndex+$scope.selectedRackIndex].rackType.physicalStats.noOfRus - (heightOfli * $scope.rackSwitchIndex[0]) > heightOfRackVisible) {
					var scrollVal =  -Math.abs(totalHeight - heightOfRackVisible + ($scope.rackSwitchIndex[0] * heightOfli));
				   $("#rack1scroll div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
				}
		    } else if($scope.selectedRackIndex == 1){
		    	var heightOfRackVisible = $("#rackdd2").height() - 28; //28 is the height of the top portion
		    	var totalHeight = $("#rack2").height();
		    	var myDiv = $("#rack2scroll").get(0);
				var scrollVal = 0;
				$("#rack2scroll div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
				if($scope.racksArray[$scope.displayedRackIndex+$scope.selectedRackIndex].rackType.physicalStats.noOfRus - (heightOfli * $scope.rackSwitchIndex[1]) > heightOfRackVisible) {
					var scrollVal =  -Math.abs(totalHeight - heightOfRackVisible + ($scope.rackSwitchIndex[1] * heightOfli));
				   $("#rack2scroll div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
				}
		    }else if($scope.selectedRackIndex == 2){
		    	var heightOfRackVisible = $("#rackdd3").height() - 28; //28 is the height of the top portion
		    	var totalHeight = $("#rack3").height();
		    	var myDiv = $("#rack3scroll").get(0);
				var scrollVal = 0;
				$("#rack3scroll div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
				if($scope.racksArray[$scope.displayedRackIndex+$scope.selectedRackIndex].rackType.physicalStats.noOfRus - (heightOfli * $scope.rackSwitchIndex[2]) > heightOfRackVisible) {
					var scrollVal =  -Math.abs(totalHeight - heightOfRackVisible + ($scope.rackSwitchIndex[2] * heightOfli));
				   $("#rack3scroll div.ngscroll-content-container").css('margin-top', scrollVal + 'px');
				}	
		    }
		    var index = $scope.rackSwitchIndex[$scope.selectedRackIndex];

		    var rack = $scope.getSelectedRackId($scope.selectedRackIndex);
		    $('.rackSwitchGroup li').find('label').removeClass('activeServer');
		    $('.rackServerGroup li').find('label').removeClass('activeServer');
		    $('.serverDelBg').css("visibility", "hidden");
		    $('.switchDelBg').css("visibility", "hidden");
 		    var ele = $('#'+rack+' .rackSwitchGroup label').slice(index-resp.deviceType.physicalStats.noOfRus, index);
		    ele.addClass("activeServer"); 
		    $('#'+rack+' .rackSwitchGroup .switchDelBg').last().css("visibility","visible");
			$scope.editDeleteSwitch(null,$scope.selectedRackIndex , resp.id, true);	
			$scope.getRoomsAndRows();
			physicalService.getAllRacks($scope.roomDetailsArr[$scope.selectedRoomIndex].id,$scope.rowsArray[$scope.selectedRowIndex].id,function(resp){
				$scope.racksArray = resp;
				$scope.getRackInventory();
			});
		});		
		$("#addSwitchWindow").data("aciWindow").close();
	}

	$scope.addSwitchGroup= function(rackNum ,rus, id){
		var index = $scope.rackSwitchIndex[rackNum];
		var rack = $scope.getSelectedRackId(rackNum);

		//$('#'+rack+' li:lt(' + index +')').wrapAll('<div class="rackSwitchGroup"/>');
		
		$('#'+rack+' li').slice(index-rus,index).wrapAll('<div class="rackSwitchGroup position_relative"/>');

		$/*(".rackSwitchGroup").hover(function(){ 
			  		$(this).append("<div id='delId' style='position: absolute; background: #C3C3C3; display:block; z-index: 102; top: 6px; right: 37px; padding: 2px 7px;color: #fff;'><i class='fa fa-trash cursor'></i></div>");
			 },function(){
			 	 	$(this).find('#delId').remove();
			});*/

		var elem = $('#'+rack+' .rackSwitchGroup li').slice(index-rus,index);
		elem.id = id;
		for(var i=0; i < elem.length; ++i) {
				elem[i].addEventListener("click", function(event){	
					$scope.editDeleteSwitch(event, rackNum , id, false);		
				});
			}	
		$('#'+rack+' .rackSwitchGroup li').slice(index-rus,index-rus+1).append("<div class='switchDelBg'><i class='fa fa-trash cursor switchDelIcon' style:'visibility:hidden;'></i></div>");
			$(".switchDelIcon").click(function (event) {
   					$scope.deleteSwitch();
				});

	   for(var i=0;i<$scope.rackSwitchList[$scope.displayedRackIndex + rackNum].length;++i) {
				if($scope.rackSwitchList[$scope.displayedRackIndex + rackNum][i].id == id){
					var template = $scope.rackSwitchList[$scope.displayedRackIndex + rackNum][i].deviceType.name; 
				} 
			}

        var middle = (index-rus + index)/2;
			if (parseInt(middle) !== middle) {
				// if it is not a whole number
				middle = Math.floor(middle);
			} 
			$('#'+rack+' .rackSwitchGroup li').slice(middle,middle+1).each(function(){
				var label = document.createElement("div");
				label.className = "switchSvgText";
				label.innerHTML = template;
				label.style.color="#b6ba4b";
				$(this).append(label);
				var num = (index-rus + index)/2;
				// for odd numbers
				if (parseInt(num) !== num) {
					label.style.marginTop =  -28 + "px";
				}
				else{
					label.style.marginTop =  -45 + "px";
				} 
				
				// if the template name has less than or eq 8 char, increase left margin to align in center
				if(template.length <= 8){
					label.style.marginLeft = 102 + "px";
				}

			});

	   // bottom border for the last element
	    $('#'+rack+' .rackSwitchGroup li').slice(index-1,index).each(function(){
	    	$(this).append("<div class='switchBorderBottom'></div>");
			});
	    // top border for the first element
	    $('#'+rack+' .rackSwitchGroup li').slice(index-rus,(index-rus)+1).each(function(){ 
	    	$(this).prepend("<div class='switchBorderTop'></div>");
		});
	    // left and right borders for all the remaining elements
	    $('#'+rack+' .rackSwitchGroup li').slice(index-rus,index).each(function(i){ 
	      	$(this).prepend("<div class='switchBorderLeft'></div>");
	      	$(this).prepend("<div class='switchBorderRight'></div>");
	     	$(this).prepend("<div class='switchImageBackground'></div>");
	     	$(this).find(".switchImageBackground").css('background-color',$scope.switchDetails['color']);
	     	//for first element adjust the margins
	     	if (i==0){

	     		$(this).find(".switchBorderLeft").css('height','78%');
	     		$(this).find(".switchBorderLeft").css('top','7px');
	     		$(this).find(".switchBorderRight").css('top','7px');
	     		$(this).find(".switchBorderRight").css('height','78%');
	     		$(this).find(".switchImageBackground").css('top','5px');
	     		$(this).find(".switchImageBackground").css('height','75%'); 
	     	}

		});
	}

	 

	$scope.rackAddedSwitch = function(){
		$scope.rackAdded=false;  
	}
	$scope.addedDevice = function(){
		$scope.serverAdded=false;
		$scope.showServer=true;  
	}
	$scope.cancelAddSwitch = function(){
		$scope.showSwitch=false;
		$scope.deviceTab=true;  
		$("#addSwitchWindow").data("aciWindow").close();
		$('#lightBox').hide();
	}

	$scope.getSwitchTemplate = function(){
		if($scope.switchDetails['deviceType'] == "Leaf") {
			$scope.switchTemplateTypes = [];
			physicalService.getSwitchLeafTemplateTypes(function(resp){
				$scope.switchListResp = resp;			
				for(var i=0; i<resp.length; i++){
					$scope.switchTemplateTypes[i]={
						"id": $scope.switchListResp[i].id,
						"name": $scope.switchListResp[i].name,
						"rus": $scope.switchListResp[i].physicalStats.noOfRus,
						"power": $scope.switchListResp[i].powerCooling.power,
						"cooling": $scope.switchListResp[i].powerCooling.coolingInBTU,
						"defaultPortGroup": $scope.switchListResp[i].defaultPortGroup,
						"color": $scope.switchListResp[i].color
					};		
				}
			});
		} else {
			$scope.switchTemplateTypes = [];
			physicalService.getSwitchSpineTemplateTypes(function(resp){
				$scope.switchListResp = resp;			
				for(var i=0; i<resp.length; i++){
					$scope.switchTemplateTypes[i]={
						"id": $scope.switchListResp[i].id,
						"name": $scope.switchListResp[i].name,
						"rus": $scope.switchListResp[i].physicalStats.noOfRus,
						"power": $scope.switchListResp[i].powerCooling.power,
						"cooling": $scope.switchListResp[i].powerCooling.coolingInBTU,
						"defaultPortGroup": $scope.switchListResp[i].defaultPortGroup,
						"color": $scope.switchListResp[i].color
					};		
				}
			});
		}
	}
	$scope.terminateRow=true;
	$scope.terminationRow = function(){
		if($('#terminaterow').is(':checked')){
				$('.addTenantTable').find('input').removeAttr('disabled');$('.maskDisable').hide();
				$('.selectBoxMove').find('select').removeAttr('disabled'); $('.selectBoxMove').find('input').removeAttr('disabled'); 
				$scope.terminateRow=true;
		}else{
				$('.addTenantTable').find('input').prop('disabled','disabled');
				$('.selectBoxMove').find('select').prop('disabled','disabled');
				$('.selectBoxMove').find('input').prop('disabled','disabled');
				$('.maskDisable').show();
				$scope.terminateRow=false;
		}
	}
	$scope.addLeaf=true;
	$scope.addSwitche = function(){
		if($('#terminateswitch').is(':checked')){
			 $('#switchLeaf').find('input').removeAttr('disabled');
			 $('#switchLeaf').find('select').removeAttr('disabled');
			 $('#leafId').removeClass('disabledCls');$('.leafTerm').hide();
			 $scope.addLeaf=true;
		}else{
			 $('#switchLeaf').find('input').prop('disabled','disabled');
			 $('#switchLeaf').find('select').prop('disabled','disabled');
			 $('#leafId').addClass('disabledCls');$('.leafTerm').show();
			 $scope.addLeaf=false;
		}
	}
 
	$scope.savefabric = function(){
		$('#lightBox').hide();
	}
	$scope.savefirewall = function(){
		$('#lightBox').hide();
	}

	
	$scope.saveSwitch = function(){

		var elem = document.getElementById("addSwitchWindow");
		var window = $(elem);
		
		if (!window.data("aciWindow")) {
			window.aciWindow({
				width: "500px",
				title: "Add Switch",
				actions: [
				          "Close"
				          ],
				          close: onClose
			});
		}
		$('.a-window-action').click(function(){$('#lightBox').hide();});
		window.data("aciWindow").open();
		window.data("aciWindow").center();

		$scope.showSwitch=true; 
		$scope.dropdownItem=false;  
		$scope.deviceTab=false;
		$scope.switchDetails= [];	
		$scope.switchTemplateTypes=[];
		$scope.switchListResp;
		$scope.editDelSwitchShow = false;
		$scope.showRacksRightPanel(); // To show the smaller racks on the right side panel

		document.getElementById("switchrus").classList.remove("text_readonly");
		document.getElementById("switchpower").classList.remove("text_readonly");
		document.getElementById("switchcooling").classList.remove("text_readonly");
	}	

	$scope.updateSwitchInstanceDetails = function(){
		for(var i=0; i<$scope.switchTemplateTypes.length; i++){
			if($scope.switchDetails['templateType'] == $scope.switchTemplateTypes[i].id ){
				$scope.switchDetails['rus'] = $scope.switchTemplateTypes[i].rus;
				$scope.switchDetails['power'] = $scope.switchTemplateTypes[i].power;
				$scope.switchDetails['cooling'] = $scope.switchTemplateTypes[i].cooling;
				$scope.switchDetails['list'] = $scope.switchTemplateTypes[i].defaultPortGroup;
				$scope.switchDetails['color'] = $scope.switchTemplateTypes[i].color;
			}
		}

		document.getElementById("switchrus").classList.add("text_readonly");
		document.getElementById("switchpower").classList.add("text_readonly");
		document.getElementById("switchcooling").classList.add("text_readonly");
	}

	$scope.showRacksRightPanel = function(){
		var rackId = $scope.selectedRackIndex + $scope.displayedRackIndex ; // get the actual rackId
		var totalRacks = $scope.racksArray.length ;
		$scope.smallRacksToDisplay =[];
		if(rackId == 0){ //if selected rack is first rack
			if(totalRacks -1 < rackId+2 ){
				for(var i=0; i<totalRacks; i++){
					$scope.smallRacksToDisplay[i]= i;
				}
			}
			else{
				for(var i=0; i<rackId+3; i++){
					$scope.smallRacksToDisplay[i]= i;
				}
			}
		}	
		else if(totalRacks ==1){ // Only one rack is present in the row
			$scope.smallRacksToDisplay[0]= 0;
		}
		else if(rackId == totalRacks-1){ //if selected rack is last rack
			if(rackId -2 < 0){
				for(var i=0; i<totalRacks; i++){
					$scope.smallRacksToDisplay[i]= rackId-1 + i;
				}
			}
				else{
					for(var i=0; i<3; i++){
					$scope.smallRacksToDisplay[i]= rackId-2 + i;
				}
				}
			}
		

		else if(rackId -2 < 0){ // if only one rack is present on the left of selected rack
			if( rackId+2 > totalRacks-1 ){
				for(var i=0; i<totalRacks; i++){
					$scope.smallRacksToDisplay[i]= rackId-1 + i;
				}
			}
			else{
				for(var i=0; i<rackId +3; i++){
					$scope.smallRacksToDisplay[i]= rackId-1 + i;
				}
				}
			}
		

		else if(rackId+2 > totalRacks-1){ // if only one rack is present on the right of selected rack
			for(var i=0; i<4; i++){
					$scope.smallRacksToDisplay[i] = rackId-2 + i;
				}
		}
		else{ //if there are 2 racks each on left & right side of the selected rack
			for(var i=0; i<5; i++){
				$scope.smallRacksToDisplay[i]= rackId-2 + i;
			}
		}


	}

	/*Add Server Row*/	
	$scope.serverForm = [];
	//$scope.serverList = {};

	$scope.addServRow = function(){
		var data ={};
		$scope.errorServMsg = false;
		data.name = $scope.serverForm['name'];
		data.speed = $scope.serverForm['speed'];
		data.numOfPorts = $scope.serverForm['numOfPorts'];
		data.type = $scope.serverForm['type'];
		data.redundancyModel = $scope.serverForm['redundancyModel'];
		if ((data.redundancyModel == "A-A") || (data.redundancyModel == "A-S")) {
			data.placement = "2-TOR";
		} else {
			data.placement = "1-TOR";
		}

		if (!(data.name) || !(data.speed) || !(data.numOfPorts) || !(data.type) || !(data.redundancyModel) || !(data.placement)){
			$scope.errorMsg=true;
			return false;
		}else{
			$scope.serverList.push(data);  
			$scope.serverForm =[];
			$scope.errorMsg=false;
			for(var i=0;i<$scope.serverList.length; ++i) {
				$scope.editablePortGroup[i] = false;
			}
			return true;
		}
	}

	$scope.editServRow = function(index){
		var data ={};
		$scope.errorServMsg = false;
		data.name = $scope.serverList[index]['name'];
		data.speed = $scope.serverList[index]['speed'];
		data.numOfPorts = $scope.serverList[index]['numOfPorts'];
		data.type = $scope.serverList[index]['type'];
		data.redundancyModel = $scope.serverList[index]['redundancyModel'];
		if ((data.redundancyModel == "A-A") || (data.redundancyModel == "A-S")) {
			data.placement = "2-TOR";
		} else {
			data.placement = "1-TOR";
		}

		if (!(data.name) || !(data.speed) || !(data.numOfPorts) || !(data.type) || !(data.redundancyModel) || !(data.placement)){
			$scope.errorMsg=true;
			return false;
		}else{
			$scope.editablePortGroup[index] = false;
			/*$scope.removeServRow(index)
			$scope.serverList.push(data);  
			$scope.serverForm =[];
			$scope.errorMsg=false;
			for(var i=0;i<$scope.serverList.length; ++i) {
				
			}*/
			return true;
		}
	}


	/*Remove  server Row */	 
	$scope.removeServRow = function(del){
		if(del!=-1){
			$scope.serverList.splice(del,1);
		}
	}

	 
	$scope.showCurrentRack = function(index){ 
		$scope.showCurrent=true;
		$scope.selected=index;
	}

	$scope.editServerSelected = function(event){ 
		$('#instance').focus();
		$scope.editDevice=true;
		$(event.currentTarget).next().find('.colorBlue').addClass('actColor');
		$('.addserver').find('.select-style').removeAttr('style');
		$('#editFormBox,#headerBox').show();

		
	}

	$scope.cancelEditServer= function(){
		$scope.editDevice=false;
		$('.addServer').find('.actColor').removeClass('actColor');
		$('#serverAddDevice').find('.txtFields').attr('disabled');
		$('#serverAddDevice').find('.txtFields').removeClass('textFieldAction');
		$('#editFormBox,#headerBox').hide();
	}


	var onClose = function () {
	}

	$scope.editServer = function(){
		var jsonObj = {
			numOfInstances:$scope.serverGroup['instances'],
			portGroups:$scope.serverList,
			ucsManaged: $scope.serverGroup['ucs'],
			templateTypeId: $scope.serverGroup['templateId']
		}
		$('#serverAddDevice').find('.txtFields').attr('disabled');
		$('#serverAddDevice').find('.txtFields').removeClass('textFieldAction');
		$('.addServer').find('.actColor').removeClass('actColor');
		$('#editFormBox,#headerBox').hide();
		physicalService.editServer(jsonObj,$scope.roomDetailsArr[$scope.selectedRoomIndex].id,$scope.rowsArray[$scope.selectedRowIndex].id,
								 $scope.racksArray[$scope.serverGroupRackNum+$scope.displayedRackIndex].id,  $scope.serverGroupId,function(resp){
			if (resp.status) {
				$scope.editDevice=false;
			} else {
			$scope.getRoomsAndRows();
			$scope.rowAction($scope.selectedRowIndex);
			setTimeout(function () {
				var rack = $scope.getSelectedRackId($scope.serverGroupRackNum);
		        $('.rackServerGroup li').find('label').removeClass('activeServer');
		        $('.serverDelBg').css("visibility","hidden");
		        var count = -1;
		        var excludeRu = 0;
		        // for(var i=$scope.rackServerList[$scope.displayedRackIndex + $scope.serverGroupRackNum].length-1;i=>0;i--){
		        for(var i=$scope.rackServerList[$scope.displayedRackIndex + $scope.serverGroupRackNum].length-1;!i<0;i--){
		        	if($scope.rackServerList[$scope.displayedRackIndex + $scope.serverGroupRackNum][i].id == $scope.serverGroupId){
		        		count = i;
		        		break;
		        	}
		        	excludeRu += $scope.rackServerList[$scope.displayedRackIndex + $scope.serverGroupRackNum][i].numOfInstances*
		        				$scope.rackServerList[$scope.displayedRackIndex + $scope.serverGroupRackNum][i].deviceType.physicalStats.noOfRus;
		        }
		        if (count == $scope.rackServerList[$scope.displayedRackIndex + $scope.serverGroupRackNum].length-1) {
		        	var ele = $('#'+rack+' .rackServerGroup label').slice(0 , resp.numOfInstances*resp.deviceType.physicalStats.noOfRus);
		    	} else {
		    		var ele = $('#'+rack+' .rackServerGroup label').slice(excludeRu , excludeRu + resp.numOfInstances*resp.deviceType.physicalStats.noOfRus);
		  		}
		  		 ele.addClass("activeServer");

		    	$('#'+rack+' .rackServerGroup .serverDelBg').first().css("visibility","visible");
				$scope.serverGroupClicked(null,$scope.serverGroupRackNum, $scope.serverGroupId,0);
			 }, 500);
		  }
		});
		
	}


	var prevActiveTab="downlinkTab";
	var prevActiveContent="downlinkContent";
	$scope.tabShow = function(theTab,theContent){ 
		document.getElementById(theTab).className="tab_active";
		document.getElementById(theContent).style.display="";
		if(prevActiveTab!=""&&prevActiveContent!=""&&prevActiveTab!=theTab&&prevActiveContent!=theContent){ 
			document.getElementById(prevActiveTab).className="";
			document.getElementById(prevActiveContent).style.display="none";
			prevActiveTab=theTab;
			prevActiveContent=theContent;
		} 
	}
	
	var prevActiveTab1="deviceTab";
	var prevActiveContent1="deviceContent"; 
	$scope.rowTabs = function(theTab,theContent){
		if($scope.editMode == true){
			return false;
		}  
		$('#rackDeviceContent').hide();
		$('.rowTabs li').removeClass('rowtabActive'); 
		$('#'+theTab).addClass("rowtabActive");
		$('#'+theContent).show();


		if(prevActiveTab1!=""&&prevActiveContent1!=""&&prevActiveTab1!=theTab&&prevActiveContent1!=theContent){ 
			$('#'+prevActiveTab1).removeClass("rowtabActive")
			$('#'+prevActiveContent1).hide();
			prevActiveTab1=theTab;
			prevActiveContent1=theContent;
		} 
	}


	var prevActiveTab2="leafTab";
	var prevActiveContent2="leafContent"; 
	$scope.terminateTabs = function(termTab,termContent){ 

		$('#'+termTab).addClass("rowtabActive");
		$('#'+termContent).show(); 
 		
 		// console.log($('#terminateRoomWindow .rowTabs li').index())

		if(prevActiveTab2!=""&&prevActiveContent2!=""&&prevActiveTab2!=termTab&&prevActiveContent2!=termContent){ 
			$('#'+prevActiveTab2).removeClass("rowtabActive");
			$('#'+prevActiveContent2).hide();
			prevActiveTab2=termTab;
			prevActiveContent2=termContent; 
		}  
		if($('#terminateRoomWindow .rowTabs li:eq(0)').attr('class')=='rowtabActive'){
 				$('#termAction').val('Place & Terminate Leafs');
 		}	
 		if($('#terminateRoomWindow .rowTabs li:eq(1)').attr('class')=='rowtabActive'){
 				$('#termAction').val('Place & Terminate Spines');
 			
 		}
	}

$scope.listbox_moveacross = function(sourceID, destID) {
    var src = document.getElementById(sourceID);
    var dest = document.getElementById(destID);
 
    for(var count=0; count < src.options.length; count++) {
 
        if(src.options[count].selected == true) {
                var option = src.options[count];
 
                var newOption = document.createElement("option");
                newOption.value = option.value;
                newOption.text = option.text;
                newOption.selected = true;
                try {
                         dest.add(newOption, null); //Standard
                         src.remove(count, null);
                 }catch(error) {
                         dest.add(newOption); // IE only
                         src.remove(count);
                 }
                count--;
                if(destID == "rightBoxrevert"){
                	$scope.revertRoom['rowList'].push(option.value);
                } else if (destID =="rightBox"){
                	$scope.terminateRoom['rowList'].push(option.value);
                } else if(destID =="leftBoxrevert"){
                	var index = $scope.revertRoom['rowList'].indexOf(option.value);
                	$scope.revertRoom['rowList'].splice(index, 1);
                } else {
                	var index = $scope.terminateRoom['rowList'].indexOf(option.value);
                	$scope.terminateRoom['rowList'].splice(index, 1);
                }
        }
    }
}


 

$scope.goToRack = function(index,id){
	if($scope.rackDetails==false){
		$scope.selectedRowIndex = -1;
		$scope.rowAction(index);
	}

	setTimeout(function () {
		var index = -1;
		// Find the rack index in the racks array
		for(var i=0; i<$scope.racksArray.length;++i){
			if (id == $scope.racksArray[i].id){
				index = i;
				break;
			}
		}
		if (index > ($scope.displayedRackIndex + 2)) {
			var count = index - $scope.selectedRackIndex;
			for(var i=0; i<count;i++){
				$scope.moveRacksRight();
			}
			$scope.showRackDetails(2);
		}else if (index < ($scope.displayedRackIndex)) {
			var count = $scope.displayedRackIndex +1;
			for(var i=0; i<count;i++){
				$scope.moveRacksLeft();
			}
			$scope.showRackDetails(0);
		} else {
			var count = Math.abs(index-$scope.displayedRackIndex);
			$scope.showRackDetails(count);
		}
		$scope.rackNavigationSelected=false;
		$scope.showUnterminatedRacks = false;
		$scope.$apply();
	 }, 500);
    

}
   
$(document).unbind('keydown').bind('keydown',function(evt) {
		if(($scope.rackDetails == true) && (!$scope.editMode)) {
			// In case add device/server/switch window opened, block key movements
			if ($('#lightBox').is(':visible')) {
				evt.stopPropagation();
				return;
			}

		$scope.$apply(function () {
        switch(evt.keyCode) {
        //left key press
        case 37:
        	{
        		$scope.moveRacksLeft();         
             	break;
            }
        //right key press
        case 39:
        	{
				$scope.moveRacksRight();  
             	break;
            }
         // up arrow 
         case 38:
         	{
         		$scope.scrollUpRacks();
         		break;
         	}
         case 40:
         	{
         		$scope.scrollDownRacks();
         		break;
         	}
         case 46:
         	{
         		if($scope.editDelSwitchShow == true){
         			$scope.deleteSwitch();
         		} else if($scope.deviceShow ==true){
         			$scope.deleteServer();
         		}
         		break;
         	}
         default:
        	return false;
        }
    });
	}

});
})

function numberOnly(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}



