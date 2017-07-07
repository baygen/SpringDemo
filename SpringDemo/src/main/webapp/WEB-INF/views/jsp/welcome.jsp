<%@page session="false"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Spring MVC 4 + Ajax Hello World</title>

<c:url var="home" value="/" scope="request" />

<%--<spring:url value="/resources/core/css/hello.css" var="coreCss" />--%>
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />

<spring:url value="/resources/core/js/jquery.1.10.2.min.js"
	var="jqueryJs" />
<script src="${jqueryJs}"></script>
</head>

<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Spring 4 MVC Ajax Hello World</a>
		</div>
	</div>
</nav>

<div class="container" style="min-height: 500px">

    <div class="starter-template">
		<h1>Forms</h1>
		<br>
            <form class="form-horizontal" id="search-form">
			
		<label class="col-sm-2 control-label">Company name</label>
		<div class="col-sm-10">
		    <input type=text class="form-control" id="companyName" size="30">
                </div>        
                    <label class="col-sm-2 control-label">Earnings</label>
                <div class="col-sm-10">
                    <input type=text class="form-control" id="earnings" size="18">
                </div>
		
		<label class="col-sm-2 control-label">Parent Name</label>
		    <div class="col-sm-10">
                        <input type=text class="form-control" id="parentName" size="30">
		    </div>
                	
		<button type="submit" id="createbtn"
			class="btn btn-primary ">Create</button>
			
	    </form>
                
                <p><h3>Edit form </h3><br>    </p>
        <form class="form-horizontal" id="edit-form">
            Company name <input type="text" id="editCompanyName" >
            
            New name <input type="text" id="newName" >
            <br>
            New earnings <input type="text" id="newEarnings" size="15" >
            
            New parent name<input type="text" id="newParentName" >
                
            <button type="submit" class="btn btn-primary " id="edit">Edit</button>
               
        </form>
                
        <p></p>
        <p><h3>Delete form </h3><br></p>
            Company name <input type="text" id="delCompanyName" >
            <br>
        <form class="form-horizontal" id="del-one-form">
            <button type="submit" class="btn btn-primary " id="delete">Delete</button>
        </form>
            <br>
        <form class="form-horizontal" id="del-tree-form">

            <button type="submit" class="btn btn-primary " id="delTree">Delete with childs</button>
        </form>
        
        <br>
                
             <div id="errorview"></div>   
            <div id="feedback"></div>

	</div>

</div>


<script>
	jQuery(document).ready(function($) {

		$("#search-form").submit(function(event) {
			enableButtons(false);
			event.preventDefault();
			createCompany();
		});
                
                $("#edit-form").submit(function(event) {
			enableButtons(false);
			event.preventDefault();
			editCompany();
		});
                
                $("#del-one-form").submit(function() {
                        enableButtons(false);
			event.preventDefault();
			deleteOne();                    
                });
                
                $("#del-tree-form").submit(function() {
                        enableButtons(false);
			event.preventDefault();
			deleteTree();                    
                });

	});

    function createCompany() {

		var editData = {};
		editData["companyName"] = $("#companyName").val();
		editData["earnings"] = $("#earnings").val();
                editData["parentName"] = $("#parentName").val();

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "${home}create",
			data : JSON.stringify(editData),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				display(data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				alert(e);
			},
			done : function(e) {
				console.log("DONE");
				enableButtons(true);
			}
		});

	}

    function editCompany() {
        var editData = {};
	editData["companyName"] = $("#editCompanyName").val();
	editData["newName"] = $("#newName").val();
        editData["earnings"] = $("#newEarnings").val();
        editData["parentName"] = $("#newParentName").val();
        alert(editData.newName);
        
        $.ajax({
		type : "POST",
		contentType : "application/json",
		url : "${home}edit",
		data : JSON.stringify(editData),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			display(data);
		},
		error : function(e) {
		console.log("ERROR: ", e);
		alert(e);
		},
		done : function(e) {
			console.log("DONE");
			enableButtons(true);
		}
	    });

	}
        
    function deleteOne (){
    var editData = {};
	editData["companyName"] = $("#delCompanyName").val();
	
        $.ajax({
		type : "POST",
		contentType : "application/json",
		url : "${home}delete",
		data : JSON.stringify(editData),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			display(data);
		},
		error : function(e) {
		console.log("ERROR: ", e);
		alert(e);
		},
		done : function(e) {
			console.log("DONE");
			enableButtons(true);
		}
	    });
    }
    
    function deleteTree (){
    var editData = {};
	editData["companyName"] = $("#delCompanyName").val();
	
        $.ajax({
		type : "POST",
		contentType : "application/json",
		url : "${home}delete/tree",
		data : JSON.stringify(editData),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			display(data);
		},
		error : function(e) {
		console.log("ERROR: ", e);
		alert(e);
		},
		done : function(e) {
			console.log("DONE");
			enableButtons(true);
		}
	    });
    }
    
    	function display(data) {
		var json = "<h4>Enabled trees</h4><pre>"
                            +data.msg
//				+ JSON.stringify(data, null, 4) 
                                + "</pre>";
                        
                var errorcode = "<h3>"+data.code+"</h3>";
		$('#feedback').html(json);
                $("#errorview").html(errorcode);
	}
    
	function enableButtons(flag) {
		$("#createbtn").prop("disabled", flag);
                $("#edit").prop("disabled", flag);
                $("#delete").prop("disabled", flag);
                $("#delTree").prop("disabled", flag);
	}


</script>

</body>
</html>