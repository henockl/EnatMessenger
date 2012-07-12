<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Health Post Entries Management</title>
	<link rel="stylesheet" href='<s:url value="/resources/css/jquery/start/jquery-ui-1.8.16.custom.css" />' />
	<link rel="stylesheet" href='<s:url value="/resources/css/jqgrid/ui.jqgrid.css" />' />

    <style type="text/css">
        label.error {
            display: block;
        }

        .ui-jqgrid {
            font-size: 13px;
        }
    </style>

	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-1.6.2.min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery.form.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery.validate.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-ui-1.8.16.custom.min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jqgrid/grid.locale-en.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jqgrid/jquery.jqGrid.min.js" />'></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#accordion').accordion({
				collapsible: true,
				autoHeight: false,
				active: false
			});
			
			$('#hewprefix').attr("disabled", true);
			$('#transporterprefix').attr("disabled", true);
			
			$('#gottForm').validate({
				rules: {
					gottName: {
						required: true	
					}
				},
				messages: {
					gottName: {
						required: "Please enter a name."
					}
				}
			});
			
			$('#hewForm').validate({
				rules: {
					fullName: {
						required: true	
					},
					phoneNumber: {
						required: true,
						minlength: 9,
						maxlength: 9,
						number: true
					}
				},
				messages: {
					fullName: {
						required: "Please enter a name."
					},
					phoneNumber: {
						required: "Please enter a phone number.",
						minlength: "You must enter a 9 digit number.",
						maxlength: "You must enter a 9 digit number.",
						number: "You must enter only numbers." 
					}
				}
			});
			
			$('#transporterForm').validate({
				rules: {
					fullName: {
						required: true
					},
					phoneNumber: {
						required: true,
						minlength: 9,
						maxlength: 9,
						number: true
					}
				},
				messages: {
					fullName: {
						required: "Please enter a name."
					},
					phoneNumber: {
						required: "Please enter a phone number.",
						minlength: "You must enter a 9 digit number.",
						maxlength: "You must enter a 9 digit number.",
						number: "You must enter only numbers."
					}
				}
				
			});
			
			$('#gottForm').ajaxForm(
				{
					beforeSubmit: function() {
						return $('#gottForm').validate().form();
					},
					success: function(data) {
						$('#gottgridtable').jqGrid().trigger('reloadGrid');
						$('#gottForm').clearForm();
						var gott = $('#gott');
						$('#gott option').each(function(index, option) {
						    $(option).remove();
						});
						$.each(data, function() {
							gott.append($('<option/>').val(this.gottId).text(this.gottName));
						});
						$('#gottInputForm').dialog('close');
					}
				}
			);
			
			$('#hewForm').ajaxForm(
					{
						beforeSubmit: function() {
							return $('#hewForm').validate().form();
						},
						success: function() {
							$('#hewgridtable').jqGrid().trigger('reloadGrid');
							$('#hewForm').clearForm();	
							$('#sex').val('F');
							$('#hewprefix').val('+251');
                            $('#hewInputArea').dialog('close');
						}
					}
			);
			
			$('#transporterForm').ajaxForm(
					{
						beforeSubmit: function() {
							return $('#transporterForm').validate().form();
						},
						success: function() {
							$('#transportergridtable').jqGrid().trigger('reloadGrid');
							$('#transporterForm').clearForm();
							$('#transporterprefix').val('+251');
                            $('#transporterInputArea').dialog('close');
						}
					}
			);
			
			var postId = $('#gottPostId').val();
			$('#gottgridtable').jqGrid({
				url: '/ancmessenger/admin/gott/getgotts?postId=' + postId,
				datatype: 'json',
				mtype: 'GET',
				colNames: ['Gott Id', 'Gott Name'],
				colModel: [
				           	{name: 'gottId', index: 'gottId', editable: true, hidden: true},
				          	{name: 'gottName', index: 'gottName', width: 400, editable: true, required: true}
				],
				height: "auto",
				rowNum: 5,
				rowList: [5, 10, 15],
				rownumbers: true,
				pager: '#gottgridpager',
				caption: 'Existing Gotts',
				emptyrecords: 'No data available',
                ondblClickRow: editGottRow,
				loadonce: false,
				jsonReader: {
					root: 'rows',
				    page: 'page',
				    total: 'total',
				    records: 'records',
					repeatitems: false,
					cell: 'cell',
					id: 'id'
				}
			});
				
			$('#gottgridtable').jqGrid('navGrid', '#gottgridpager', 
				{edit: false, add: false, del: false, search: false}
			);
			
			$('#gottgridtable').jqGrid('navButtonAdd', '#gottgridpager',
					{
						caption: 'Add',
						buttonicon: 'ui-icon-pencil',
						onClickButton: function() {
							addGottRow();
						},
						position: 'last',
						title: 'Add Gott',
						cursor: 'pointer'
					}		
				);	
				
			$('#gottgridtable').jqGrid('navButtonAdd', '#gottgridpager',
				{
					caption: 'Edit',
					buttonicon: 'ui-icon-pencil',
					onClickButton: function() {
						editGottRow();
					},
					position: 'last',
					title: 'Edit Row',
					cursor: 'pointer'
				}		
			);	
			
			
			var hewPostId = $('#hewPostId').val();
			$('#hewgridtable').jqGrid({
				url: '/ancmessenger/admin/healthworker/gethealthworkers?postId=' + hewPostId,
				datatype: 'json',
				mtype: 'GET',
				colNames: ['HEW Id', 'Full Name', 'Phone Number', 'Sex'],
				colModel: [
				           	{name: 'workerId', index: 'workerId', editable: true, hidden: true},
				           	{name: 'fullName', index: 'fullName', width: 300, editable: true, required: true},
				          	{name: 'phoneNumber', index: 'phoneNumber', width: 200, editable: true, required: true},
				          	{name: 'sex', index: 'sex', width: 50, editable: true, edittype: 'select', editoptions: {value: 'F:F;M:M'}, required: true}
				],
				height: "auto",
				rowNum: 5,
				rowList: [5, 10, 15],
				rownumbers: true,
				pager: '#hewgridpager',
				caption: 'Existing HEW',
				emptyrecords: 'No data available',
                ondblClickRow: editHewRow,
				loadonce: false,
				jsonReader: {
					root: 'rows',
				    page: 'page',
				    total: 'total',
				    records: 'records',
					repeatitems: false,
					cell: 'cell',
					id: 'id'
				}
			});
			
			
			$('#hewgridtable').jqGrid('navGrid', '#hewgridpager', 
					{edit: false, add: false, del: false, search: false}
				);

            $('#hewgridtable').jqGrid('navButtonAdd', '#hewgridpager',
                    {
                        caption: 'Add',
                        buttonicon: 'ui-icon-pencil',
                        onClickButton: function() {
                            addHewRow();
                        },
                        position: 'last',
                        title: 'Add HEW',
                        cursor: 'pointer'
                    }
            );

            $('#hewgridtable').jqGrid('navButtonAdd', '#hewgridpager',
				{
					caption: 'Edit',
					buttonicon: 'ui-icon-pencil',
					onClickButton: function() {
						editHewRow();
					},
					position: 'last',
					title: 'Edit HEW',
					cursor: 'pointer'
				}		
			);	
				
				
			var tPostId = $('#tPostId').val();
			$('#transportergridtable').jqGrid({
					url: '/ancmessenger/admin/transporter/gettransporters?postId=' + tPostId,
					datatype: 'json',
					mtype: 'GET',
					colNames: ['Transporter Id', 'Full Name', 'Phone Number'],
					colModel: [
					           	{name: 'transporterId', index: 'transporterId', editable: true, hidden: true},
					           	{name: 'fullName', index: 'fullName', width: 300, editable: true, required: true},
					          	{name: 'phoneNumber', index: 'phoneNumber', width: 200, editable: true, required: true}					          	
					],
					height: "auto",
					rowNum: 5,
					rowList: [5, 10, 15],
					rownumbers: true,
					pager: '#transportergridpager',
					caption: 'Existing Transporters',
					emptyrecords: 'No data available',
					loadonce: false,
                    ondblClickRow: editTransporterRow,
					jsonReader: {
						root: 'rows',
					    page: 'page',
					    total: 'total',
					    records: 'records',
						repeatitems: false,
						cell: 'cell',
						id: 'id'
					}
			});
				
				
			$('#transportergridtable').jqGrid('navGrid', '#transportergridpager', 
						{edit: false, add: false, del: false, search: false}
			);

            $('#transportergridtable').jqGrid('navButtonAdd', '#transportergridpager',
                    {
                        caption: 'Add',
                        buttonicon: 'ui-icon-star',
                        onClickButton: function() {
                            addTransporterRow();
                        },
                        position: 'last',
                        title: 'Add Row',
                        cursor: 'pointer'
                    }
            );

            $('#transportergridtable').jqGrid('navButtonAdd', '#transportergridpager',
				{
					caption: 'Edit',
					buttonicon: 'ui-icon-pencil',
					onClickButton: function() {
						editTransporterRow();
					},
					position: 'last',
					title: 'Edit Row',
					cursor: 'pointer'
				}		
			);	
			
			$('#gottInputForm').dialog({
				autoOpen: false,
				height: 200,
				width: 350,
				modal: true,
                resizable: false,
				fontSize: 10,
				buttons: {
					"Save": function() {
						$('#gottForm').submit();
						
					},
					Cancel: function() {
						$('#gottForm').clearForm();
						$('#gottInputForm').dialog('close');
					}
				}
			});

            $('#hewInputArea').dialog({
                autoOpen: false,
                height: 300,
                width: 350,
                modal: true,
                resizable: false,
                fontSize: 10,
                buttons: {
                    "Save": function() {
                        $('#hewForm').submit();

                    },
                    Cancel: function() {
                        $('#hewForm').clearForm();
                        $('#hewInputArea').dialog('close');
                    }
                }
            });

            $('#transporterInputArea').dialog({
                autoOpen: false,
                height: 300,
                width: 350,
                modal: true,
                resizable: false,
                fontSize: 10,
                buttons: {
                    "Save": function() {
                        $('#transporterForm').submit();

                    },
                    Cancel: function() {
                        $('#transporterForm').clearForm();
                        $('#transporterInputArea').dialog('close');
                    }
                }
            });
		});
		
		function addGottRow() {
			$('#gottInputForm').dialog('open');
		}

        function addHewRow() {
            $('#hewInputArea').dialog('open');
        }

        function addTransporterRow() {
            $('#transporterInputArea').dialog('open');
        }

		function editGottRow() {
			var row = $('#gottgridtable').jqGrid('getGridParam','selrow');
			if (row != null)
				$('#gottgridtable').jqGrid('editGridRow', row,
					{	
						url: "/ancmessenger/admin/gott/editgott", 
						editData: {
						},
						recreateForm: true,
						beforeShowForm: function(form) {
						},
						closeAfterEdit: true,
						reloadAfterSubmit:false,
						afterSubmit : function(response, postdata) { 
				            var result = eval('(' + response.responseText + ')');
							var errors = "";
							
				            if (!result.success) {
								for (var i = 0; i < result.message.length; i++) {
									errors +=  result.message[i] + "<br/>";
								}
				            }  else {
				            	$('#dialog').text('Entry has been edited successfully');
								$('#dialog').dialog( 
										{	
											title: 'Success',
											modal: true,
											buttons: {"Ok": function()  {
												$(this).dialog("close");
											}}
									});
			                }
				            return [result.success, errors, null];
						}
					});	
				else $('#dialogSelectRow').dialog();			
		}	
		
		function editHewRow() {
			var row = $('#hewgridtable').jqGrid('getGridParam','selrow');
			if (row != null)
				$('#hewgridtable').jqGrid('editGridRow', row,
					{	
						url: "/ancmessenger/admin/healthworker/edithealthworker", 
						editData: {
						},
						recreateForm: true,
						beforeShowForm: function(form) {
						},
						closeAfterEdit: true,
						reloadAfterSubmit:false,
						afterSubmit : function(response, postdata) { 
				            var result = eval('(' + response.responseText + ')');
							var errors = "";
							
				            if (!result.success) {
								for (var i = 0; i < result.message.length; i++) {
									errors +=  result.message[i] + "<br/>";
								}
				            }  else {
				            	$('#dialog').text('Entry has been edited successfully');
								$('#dialog').dialog( 
										{	
											title: 'Success',
											modal: true,
											buttons: {"Ok": function()  {
												$(this).dialog("close");
											}}
									});
			                }
				            return [result.success, errors, null];
						}
					});	
				else $('#dialogSelectRow').dialog();			
		}
		
		function editTransporterRow() {
			var row = $('#transportergridtable').jqGrid('getGridParam','selrow');
			if (row != null)
				$('#transportergridtable').jqGrid('editGridRow', row,
					{	
						url: "/ancmessenger/admin/transporter/edittransporter", 
						editData: {
						},
						recreateForm: true,
						beforeShowForm: function(form) {
						},
						closeAfterEdit: true,
						reloadAfterSubmit:false,
						afterSubmit : function(response, postdata) { 
				            var result = eval('(' + response.responseText + ')');
							var errors = "";
							
				            if (!result.success) {
								for (var i = 0; i < result.message.length; i++) {
									errors +=  result.message[i] + "<br/>";
								}
				            }  else {
				            	$('#dialog').text('Entry has been edited successfully');
								$('#dialog').dialog( 
										{	
											title: 'Success',
											modal: true,
											buttons: {"Ok": function()  {
												$(this).dialog("close");
											}}
									});
			                }
				            return [result.success, errors, null];
						}
					});	
				else $('#dialogSelectRow').dialog();			
		}
	</script>
</head>
<body>
	<p>
		<b>Details of <em>${healthPost.postName}</em></b>
	</p>
	<div class="detail" style="width: 500px">
		<table class="detailtable" cellspacing="0" border="1">
			<tr>
				<td class="label"><b>Health Post Id: </b>${healthPost.postId}</td>
				<td class="label"><b>Health Post Name: </b>${healthPost.postName}</td>
				<td class="label"><b>Kebele: </b>${healthPost.kebele}</td>
			</tr>
			<tr>
				<td colspan="3" style="text-align: right">
					<a href="/ancmessenger/admin/healthpost?pid=${healthPost.healthCenter.centerId}">
						<b>Back to Health Post Management</b>
					</a>
				</td>
			</tr>
		</table>
	</div>
	<br />
	<hr style="width: 98%"/>
	<br />
	<div id="accordion" style="width: 90%; margin: 0 auto" class="ui-jqgrid">
		<h3><a href="#">Gotts</a></h3>
		<div>
			<div id="gottInputForm">
				<div id="inputArea" style="width: 300px">
					<f:form id="gottForm" modelAttribute="gottDTO" method="POST" action="/ancmessenger/admin/gott/createajax">
						<f:hidden path="postId" id="gottPostId" name="gottPostId" />
						<f:hidden path="gottId" />
						
						<label for="gottName">Gott Name: </label>
						<div>
						<f:input path="gottName" style="width: 250px" />
						</div>
					</f:form>
				</div>
			</div>
			
			<table id="gottgridtable">
				
			</table>
			<div id="gottgridpager"></div>
		</div>
		
		<h3><a href="#">Health Extension Workers</a></h3>
		<div>
			<div id="hewInputArea" style="width: 300px; text-align: left">
				<f:form id="hewForm" modelAttribute="healthWorkerDTO" method="POST" action="/ancmessenger/admin/healthworker/create">
					<f:hidden path="postId" id="hewPostId" name="hewPostId" />
					<f:hidden path="workerId"/>
	
					<label for="fullName">Full Name: </label>
					<div><f:input path="fullName" id="fullName" name="fullName" style="width: 250px"/></div>
					
					<label for="sex">Sex: </label>
					<div>
						<f:select path="sex" id="sex" name="sex" style="width: 50px">
							<f:option value="F">F</f:option>
							<f:option value="M">M</f:option>
						</f:select>
					</div>
					
					<label for="phoneNumber">Phone Number: </label>
					<table>
						<tr>
							<td>
								<input id="hewprefix" type="text" value="+251" style="width: 40px" />
							</td>
							<td>
								-
							</td>
							<td>
								<f:input path="phoneNumber" id="phoneNumber" name="phoneNumber" style="width: 210px" />	
							</td>
						</tr>
					</table>
				</f:form>
			</div>

			
			<table id="hewgridtable">
				
			</table>
			<div id="hewgridpager"></div>
		</div>
		<h3><a href="#">Transporters</a></h3>
		<div>

			<div id="transporterInputArea" style="width: 300px; text-align: left">
				<f:form id="transporterForm" modelAttribute="transporterDTO" method="POST" action="/ancmessenger/admin/transporter/create">
					<f:hidden path="postId" id="tPostId" name="tPostId" />
					<f:hidden path="transporterId"/>
	
					<label for="fullName">Full Name: </label>
					<div><f:input path="fullName" id="fullName" name="fullName" style="width: 250px"/></div>
					
					<label for="gott">Gott Name</label>
					<div>
					<f:select path="gott" style="width: 250px">
						<f:options items="${gotts}" itemLabel="gottName" itemValue="gottName" />
					</f:select>
					</div>
					
					<label for="phoneNumber">Phone Number: </label>
					<table>
						<tr>
							<td>
								<input type="text" id="transporterprefix" value="+251" style="width: 40px" />
							</td>
							<td>
								-
							</td>
							<td>
								<f:input path="phoneNumber" id="phoneNumber" name="phoneNumber" style="width: 210px"/>		
							</td>
						</tr>
					</table>
				</f:form>
			</div>

			<table id="transportergridtable">
				
			</table>
			<div id="transportergridpager"></div>
		</div>
	</div>
</body>
</html>