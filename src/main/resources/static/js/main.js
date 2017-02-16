/**
 * Created by syed.ahmad on 2/6/2017.
 */
var PocAPP=angular.module('PocAPP',[]);

PocAPP.controller('Controller', function($scope,$http,$q,$filter) {



    $scope.init_result = function() {
        return getUserManagementData($q).then(
            function(data) {
                console.log(data);
                $scope.resultFromData=data.fileList;

            },
            function(error) {
                console.log('error found');
            }
        );
    };


    $scope.fire_ajax_submit = function() {
        // alert('Clicked');
        $('#fileListPrint').empty();
        var form = $('#fileUploadForm')[0];
       // var form=document.getElementById('fileUploadForm');
        var data = new FormData(form);
        console.log('Submit data'+data);

        //data.append("CustomField", "This is some extra data, testing");

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "/api/upload/multi/model",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log("SUCCESS : ", data);
                form.reset();
                // document.getElementById("fileListPrint").innerHTML='';

                $scope.init_result();
            },
            error: function (e) {
                console.log("ERROR : ", e);


            }
        });
    };

    function getUserManagementData($q) {
        var deferred = $q.defer();
        $.ajax({
            type: "GET",
            url: "/result",
            success: function(result) {
                deferred.resolve(result);
            },
            error: function(err) {
                deferred.reject(err);
            }
        });
        return deferred.promise;
    }

    $scope.editUser=[];
    $scope.fileList=[];
    $scope.editDetails=function (value,list) {
        var formData=document.getElementById('fileEditForm');
        formData.reset();
        console.log('Edit Value : '+value+','+'List :'+list);

        $scope.editUser=value;
        $scope.fileList=list;
        angular.element(document).ready(function() {
            $( "#dialog" ).dialog({
                autoOpen: true,
                width: 600
            });

            $( "#btnEdit" ).on( "click", function() {
                $( "#dialog" ).dialog( "open" );

            });
        });
    };


    $scope.deleteDetails=function (id) {
        $http.get('/api/delete?id=' + id)
            .success(function(data) {
                console.log(data + ' deleted');
                $scope.init_result();
            });


    };

    $scope.up=false;
    $scope.down=true;
    $scope.orderById = function () {
        if($scope.up){
            $scope.resultFromData= $filter('orderBy')($scope.resultFromData, 'id');
            $scope.up=false;
        }else if(!$scope.up){
            $scope.resultFromData= $filter('orderBy')($scope.resultFromData, '-id');
            $scope.up=true;
        }

    };

    $scope.orderByName = function () {

        if($scope.down){
            $scope.resultFromData= $filter('orderBy')($scope.resultFromData, 'name');
            $scope.down=false;
        }else if(!$scope.down){
            $scope.resultFromData= $filter('orderBy')($scope.resultFromData, '-name');
            $scope.down=true;
        }


    }







});