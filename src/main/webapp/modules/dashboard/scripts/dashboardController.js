(function(){
	'use strict';

angular.module('dashboard')
.controller('HomeController', function ($scope, $rootScope, $location, dashboardService, DIALOG_CONSTANTS) {
		
		
		//Window height set to div tag height dynamically		
		 $scope.listContainerHeight = window.innerHeight-90;
		  $rootScope.projectDropDown = false;
    	// Project Window - function - starts				
                    $rootScope.isProjectVisible=false;
	                $scope.loadProjectview = function(proj,location) {
						$rootScope.projectId =proj.id; 
                        $rootScope.isProjectVisible=true;
                        $rootScope.currProject = proj;
		                localStorage.setItem("projectid",$rootScope.projectId );
		                if (location === 'logical') {
		                	$location.path('/projects');
		                	$rootScope.project['selectedProject'] = "Logical";
		            	} else {
		            	    $location.path('/physical');
		            	    $rootScope.project['selectedProject'] = "Physical";
		            	}
	                }
					
					$rootScope.isProjectVisible=false;
	                $scope.loadnewProjectview = function(resp) {
                        $rootScope.isProjectVisible=true;
		                $rootScope.projectId =  resp.id; 
		                $rootScope.currProject=resp;
		                localStorage.setItem("projectid",$rootScope.projectId );
		                if ($rootScope.currProject.type == "Nexus 9000") {
		                	$location.path('/physical');
		                	$rootScope.project['selectedProject'] = "Physical";
		                } else {
		                	$location.path('/projects');
		            	}
	                }
					$scope.editProject=false;


	                $scope.openAddProjectWindow = function() {
		                $scope.appTypeShow = false;
                        $scope.projectNameShow = false;
                        $scope.projectAccountShow = false;
                        $scope.projectDescShow = false;
                        
		                var window = $("#projectwindow");
		                var onClose = function() {
		                }
		                $('#lightBox').show();
		                if($scope.editProject == false){
		                	$scope.addProject={};
		            	}
		                if (!window.data("aciWindow")) {
			                window.aciWindow({
			                    width : "350px",
			                    title : "ADD PROJECT",
			                    actions : [ "Close" ]
			                });
		                }

		                 var projects = [ {
		                    "text" : "ACI",
		                    "value" : "ACI"
		                }, {
		                    "text" : "Nexus 9000",
		                    "value" : "Nexus 9000"
		                } ];

		                if($scope.editProject ==true){
		               		window.data("aciWindow").title("EDIT PROJECT");
		               	} else {
		               		window.data("aciWindow").title("ADD PROJECT");           
		                	 $("#apptype").empty();
		                	$("#apptype").aciComboBox({
		                   		dataTextField : "text",
		                   		dataValueField : "value",
								dataSource: projects,
		                    	filter : "contains",
		                    	suggest : true
		                	});
		           		 }	
		                window.data("aciWindow").open();
		                window.data("aciWindow").center();
					
						$('.a-window-action').click(function(){$('#lightBox').hide(); $scope.editProject=false;});

	                }
	                
	                $scope.onCanceladdProject = function() {
	                	if ($scope.editProject == false){
						var model = $("#apptype").aciComboBox()
						model[0].value = "";
					}
		                $("#projectwindow").data("aciWindow").close();
						$scope.editProject=false;
						$('#lightBox').hide();
	                }

	                $scope.validateAddProject = function(event) {
		                event.preventDefault();
                        $scope.appTypeShow = false;
                        $scope.projectNameShow = false;
                        $scope.projectAccountShow = false;
                        $scope.projectDescShow = false;
                        if ($scope.editProject == false) {
                        	var model = $("#apptype").aciComboBox();                       
                    	}
                        if($scope.addProject.name == null || $scope.addProject.name == ""){
                            $scope.projectNameShow = true;
                        }
                        else if(($scope.editProject == false) && (model[0].value == "")){
                        	$scope.appTypeShow = true;
                        }
                        
                        else if($scope.addProject.account == null || $scope.addProject.account == ""){
                             $scope.projectAccountShow = true;
                        }
                        else if($scope.addProject.description == null || $scope.addProject.description == ""){
                             $scope.projectDescShow = true;
                        }
                        else{
		                // window.alert("Add Project - Testing");
		                $scope.appTypeShow = false;
                        $scope.projectNameShow = false;
                        $scope.projectAccountShow = false;
                        $scope.projectDescShow = false;
                        var addProjectJson = {};

		                if ($scope.editProject == false) {
		                	addProjectJson = {
		                    	"name" : $scope.addProject.name,
		                    	"account" : $scope.addProject.account,
		                    	"description" : $scope.addProject.description,
		                    	"type": model[0].value
		                	};
		            	} else {
		            		addProjectJson = {
		                    	"name" : $scope.addProject.name,
		                    	"account" : $scope.addProject.account,
		                    	"description" : $scope.addProject.description,
		                    	"type": $scope.addProject['type']
		                	};

		            	}

						if ($scope.editProject == true) {
							//var project=$scope.projectGridData[$scope.rowIndex];
							//var project=$rootScope.currProject;
							dashboardService.editProject($scope.projectlistResponce[$scope.editProjectIndex].id, addProjectJson, function(resp) {
			                	$scope.getProjectList();
							});
							$scope.editProject=false;
						} else {
							dashboardService.addProject(addProjectJson, function(resp) { 
								//$scope.loadnewProjectview(resp);
								$scope.getProjectList();
							});
						}
		                $("#projectwindow").data("aciWindow").close();
		                $('#lightBox').hide();
                    }
	                }

	                $scope.openEditProjectWindow = function(index){
	                	$scope.editProject = true;
	                	$scope.addProject={};
	                	$scope.editProjectIndex = index;
	                	$scope.addProject['name']= $scope.projectlistResponce[index].name;
	                	$scope.addProject['description']= $scope.projectlistResponce[index].description;
	                	$scope.addProject['account']= $scope.projectlistResponce[index].account;
	                	$scope.addProject['type'] = $scope.projectlistResponce[index].type;
	                	$scope.openAddProjectWindow();
	                }



                    $rootScope.fadeOutBackground = function(){
                        $("body").prepend("<div id='PopupMaskProject' style='position:fixed;width:100%;height:100%;z-index:11;background-color:gray;'></div>");
                        $("#PopupMaskProject").css('opacity', 0.2); 
                    }

                    $scope.physicalTabCss = function(){
                        $("#physicalTab").css("border-top","1px solid #bcbcbc");
                        $("#physicalTab").css("border-right","1px solid #bcbcbc");
                        $("li#physicalTab.ui-state-default.ui-corner-top").css("border-left","1px solid #bcbcbc");
                        $("#logicalTab").css("border-top","none");
                        $("#logicalTab").css("border-left","none");
                       
                    }

                    $scope.logicalTabCss = function(){
                        $("#physicalTab").css("border-top","none");
                        $("#physicalTab").css("border-right","none");
                        $("#physicalTab").css("border-left","none");
                        $("#logicalTab").css("border-top","1px solid #bcbcbc");
                        $("#logicalTab").css("border-left","1px solid #bcbcbc");
                    }

	                $rootScope.projectList = {};
	                $rootScope.selectedProjecttIndex = 0;                   

	                $scope.months = ["Jan","Feb","Mar","Apr","May","June","July",
	                				 "Aug", "Sep", "Oct", "Nov", "Dec"];
	                				 
	                $scope.getProjectList = function() {
						/*Start Add Field*/
                        // $rootScope.notification = $("#notificationProject").aciNotification({
                        //     position: {
                        //         pinned: true,
                        //         top: "35%",
                        //          right: "40%",
                        //         left: "35%",
                        //         bottom: "40%"
                        //     },
                        //     autoHideAfter: 0,
                        //     stacking: "down",
                        //     hideOnClick:0,
                        //     templates: [{
                        //         type: "error",
                        //         template: $("#errorTemplateProj").html()
                        //     }]                    
                        // }).data("aciNotification");  
						//$scope.projectGridDataSource.data([]); 
						/*End Add Field*/
						dashboardService.getProjects(function(resp) {
							$rootScope.currProject=resp[0];
							$scope.projectlistResponce = resp; 
							$scope.physicalInventory = [];
							$scope.date=[];
							$scope.year=[];
							$scope.month=[];
							for(var i=0;i<$scope.projectlistResponce.length;++i) {
								var d = new Date($scope.projectlistResponce[i].lastUpdatedTime);
                				$scope.date[i] = d.getDate();
                				$scope.year[i] = d.getFullYear();
                				$scope.month[i] = $scope.months[d.getMonth()];
	                			dashboardService.getPhysicalSizerInventory(i, $scope.projectlistResponce[i].id, function(index, resp) {
                					$scope.physicalInventory[index] = resp;
                					
            					})
							}
            			})
            			$rootScope.projectSelect = "";
	                }



	                
					
					$scope.deleteProj = function (id) {
			//alert("Delete testing");
            	dashboardService.deleteProject(id, function (resp) {
				$scope.getProjectList();
                console.log("got the response");

            })
        };
		
		
				
		$scope.delProjectWindowVisible = false;		
		$scope.openDelProjectWindow = function (id,name) { 
			$scope.projectDelId=id;
			$('#lightBox').show();
            var window = $("#delProjectWindow");
            var onClose = function () {
            }

            if (!window.data("aciWindow")) {
                window.aciWindow({
                    width: "350px",
                    title: "DELETE PROJECT",
                    actions: [
                        "Close"
                    ],
                    close: onClose
                });
            }
            $('.a-window-action').click(function(){$('#lightBox').hide();});
			//$scope.delTenantLabel = DIALOG_CONSTANTS.deleteTenant.label + $scope.projectGridData[$scope.rowIndex].name + "?";
			$scope.delTenantLabel = DIALOG_CONSTANTS.deleteTenant.label+name+'?';
			
			window.data("aciWindow").open();
            window.data("aciWindow").center();
		}
		
		 
		
		
		$scope.deleteProjectconfirm = function(){ 
				//var projectDelId = $scope.projectListResponse[$scope.selected].id;
                $scope.deleteProj($scope.projectDelId); 
				$("#delProjectWindow").data("aciWindow").close();
            	$('#lightBox').hide();
		}
		
		$scope.onCanceldelProject = function(){
			$("#delProjectWindow").data("aciWindow").close();
			$('#lightBox').hide();
		}
		
		$scope.cloneProjectWindowVisible = false;		
		$scope.opencloneProjectWindow = function (id,name) {
			$scope.projectCloneId=id;
            var window = $("#cloneProjectWindow");
            var onClose = function () {
            }

            if (!window.data("aciWindow")) {
                window.aciWindow({
                    width: "350px",
                    title: "CLONE PROJECT",
                    actions: [
                        "Close"
                    ],
                    close: onClose
                });
            }
            $('.a-window-action').click(function(){$('#lightBox').hide();});
			 //$scope.cloneprojLabel = DIALOG_CONSTANTS.cloneProject.label + $scope.projectGridData[$scope.rowIndex].name + "?";
			
			$scope.cloneprojLabel = DIALOG_CONSTANTS.cloneProject.label+name +'?';
			$('#lightBox').show();
			window.data("aciWindow").open();
            window.data("aciWindow").center();
		}
		
		$scope.cloneProjectconfirm = function(){
            
				//var projectCloneId = $scope.projectGridData[$scope.rowIndex].id;  
				var projectCloneId	=	$scope.projectCloneId;
				 
              	dashboardService.cloneProject(projectCloneId, function (resp) {
				$scope.getProjectList();
                console.log("got the response");

            });
				$("#cloneProjectWindow").data("aciWindow").close(); 
				$('#lightBox').hide();
		}
		
		$scope.onCancelcloneProject = function(){
			$("#cloneProjectWindow").data("aciWindow").close();
			$('#lightBox').hide();
		}

		
	    //Project Window - function - Ends
        $rootScope.projectName="";
	    $scope.getProjectList();
		$('[data-toggle="tooltip"]').tooltip(); 
			
			//Remove the edit/delete popup after redirection to project list page
			var editNode = document.getElementsByClassName("a-toolbar a-widget");
			 var len = editNode.length;
			 for(var i=0; i<len; ++i){
				editNode[0].remove();
			}  
			
			// Fix for destroying all the aci-windows each time redirection happens
			 var aciWindow = document.getElementsByClassName("ng-scope a-window-content a-content");
			 var len = aciWindow.length;
			 for(var i=0; i<len; ++i){
				aciWindow[0].remove();
			}  
			
			// Reset active tabs and make logical view tab as active
			$("#navbar-collapse li").removeClass("active");
			$("#navbar-collapse li").first().addClass("active");
			
			//clear the sizer utilization pop-up
			 var popup = document.getElementsByClassName("popover");
			 var len = popup.length;
			 for(var i=0; i<len; ++i){
				popup[0].remove();
			}  
			  
			$scope.logicalSizer = function(event){ 
				// console.log($(event.currentTarget.parentElement))
				 $(event.currentTarget.parentElement).next().addClass('show');
				 $(event.currentTarget.parentElement).next().removeClass('hide');
				 $(event.currentTarget.parentElement).next().next().addClass('hide');
				 $(event.currentTarget.parentElement).next().next().removeClass('show');
				 $(event.currentTarget).addClass('dashboardTabActive');
				 $(event.currentTarget).prev().removeClass('dashboardTabActive'); 
			}
			$scope.physicalSizer = function(event){  
				$(event.currentTarget.parentElement).next().next().addClass('show');
				$(event.currentTarget.parentElement).next().next().removeClass('hide');
				$(event.currentTarget.parentElement).next().addClass('hide');
				$(event.currentTarget.parentElement).next().removeClass('show'); 
				$(event.currentTarget).addClass('dashboardTabActive');
				$(event.currentTarget).next().removeClass('dashboardTabActive');
			}

			$scope.setActivePhysicalTab = function(type,index){
				/* For Nexus 9000 set physical tab as active */
			 	if(type == "Nexus 9000") {
				//	$("li.dashboardPhyTab:eq("+index+")").addClass("dashboardTabActive");
					$("li.dashboardPhyTab:eq("+index+")").css("width","100%");
				}
			} 
 			 
      })
	  
})();
