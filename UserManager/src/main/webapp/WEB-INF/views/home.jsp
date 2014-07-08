<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>
	<title>Home</title>
    <link href="/userManager/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- List users BEGIN -->
	<h1>
		Users
	</h1>
	
	<button class="btn btn-primary" onclick="openDialogForInsert()">
		<span class="glyphicon glyphicon-plus"></span> New User
	</button>
	<table class="table">
		<tr>
			<th>Name</th>
			<th>Lastname</th>
			<th>Phonenumber</th>
			<th>Update</th>
			<th>Delete</th>
		</tr>
		<c:forEach var="user" items="${userList}">
			<tr>
				<td>${user.name}</td>
				<td>${user.lastname}</td>
				<td>${user.phonenumber}</td>
				<td>
					<button class="btn btn-primary" onclick="openDialogForUpdate('${user.id}', '${user.name}', '${user.lastname}', '${user.phonenumber}')">
						<span class="glyphicon glyphicon-pencil"></span>
					</button>
				</td>
				<td>
					<button class="btn btn-primary" onclick="removeRecord('${user.id}')">
						<span class="glyphicon glyphicon-minus"></span>
					</button>
				</td>
			</tr>
		</c:forEach>
	</table>
<!-- List users END -->
		<div class="loading" style="text-align:center;width:100%;z-index:1151;display:none">
			<img src="/userManager/resources/images/ajax-loader.gif">
		</div>
<!-- New user dialog BEGIN -->
<!-- Button trigger modal -->
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="loading" style="text-align:center;width:100%;z-index:1051;display:none">
			<img src="/userManager/resources/images/ajax-loader.gif">
		</div>
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">Create New User</h4>
	      </div>
	      <div class="modal-body">
	      <form>
	      	<input type="hidden" id="userid" />
	        <table class="table">
	        	<tr style="display:none" id="validationRow">
	        		<td colspan="2" id="validationText" style="color:red"></td>
	        	</tr>
	        	<tr>
	        		<th>Name</th>
	        		<td><input type="text" id="name" /></td>
	        	</tr>
	        	<tr>
	        		<th>Lastname</th>
	        		<td><input type="text" id="lastname" /></td>
	        	</tr>
	        	<tr>
	        		<th>Phonenumber</th>
	        		<td><input type="text" id="phonenumber" /></td>
	        	</tr>
	        	<tr id="recaptchaRow">
	        		<td colspan="2">${recaptchaHtml}</td>
	        	</tr>
	        </table>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal" id="button_close">Close</button>
	        <button type="button" class="btn btn-primary" id="button_save_changes">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
<!-- New user dialog END -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="/userManager/resources/bootstrap/js/bootstrap.min.js"></script>
    <script src="/userManager/resources/bootstrap/js/bootbox.min.js"></script>
    <script src="/userManager/resources/js/jquery.mask.min.js"></script>
    <script>
    	$('#phonenumber').mask('(000) 000-00-00');
    	$('#button_save_changes').click(function()
		{
    		var nullCaptchaText = $('#userid').val() == 'new' && $('#recaptcha_response_field').val() == '';
    		if($('#name').val() == '' || $('#lastname').val() == '' || nullCaptchaText)
   			{
    			$('#validationRow').show();
    			$('#validationText').html("Name, lastname or reCaptcha fields can not be null");
   				return;
   			}
    		$('.loading').show();
    		$.post("saveUser", { id : $('#userid').val(), name : $('#name').val(), 
    								lastname : $('#lastname').val(), phonenumber : $('#phonenumber').val(),
    								recaptchaResponseField : $('#recaptcha_response_field').val(),
    								recaptchaChallengeField : $('#recaptcha_challenge_field').val(),
    								
    							},
    		function (response)
 			{
				$('.loading').hide();
				if(response == 'success')
	   				location.reload();
 			});
		});

    	var removeRecord = function(id)
    	{
    		bootbox.confirm("Are you sure that you want to remove this record?", function(result)
    		{
    			if(result)
    			{
    				$('.loading').show();
    				$.post("removeUser", { id : id }, function (response)
		 			{
    					$('.loading').hide();
		    			if(response == 'success')
		    				location.reload();
		 			});	
    			}
    		}); 
    		
    	};
    	
    	var openDialogForUpdate = function(id, name, lastname, phonenumber)
    	{
    		$('#userid').val(id);
    		$('#name').val(name);
    		$('#lastname').val(lastname);
    		$('#phonenumber').val(phonenumber);
    		$('#validationRow').hide();
    		$('#recaptchaRow').hide();
    		$('#myModal').modal('show');
//     		$.post("getUser", { id : id }, function (response)
//   	 		{
//   	    		console.log(response);
//   	 		});
//     		$('#myModal').modal('toggle');
//     		$('#myModal').modal('show');
//     		$('#myModal').modal('hide');
    	};
    	
    	var openDialogForInsert = function(id)
    	{
    		$('#userid').val('new');
    		$('#name').val('');
    		$('#lastname').val('');
    		$('#phonenumber').val('');
    		$('#recaptchaRow').show();
    		$('#validationRow').hide();
    		$('#myModal').modal('show');
    	};
    </script>
</body>
</html>
