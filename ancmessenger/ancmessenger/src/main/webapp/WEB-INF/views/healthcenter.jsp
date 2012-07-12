<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Health Center Main Page</title>
	<link rel="stylesheet" href='<s:url value="/resources/css/jquery/start/jquery-ui-1.8.16.custom.css" />' />
	<link rel="stylesheet" href='<s:url value="/resources/css/jqgrid/ui.jqgrid.css" />' />
	
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-1.6.2.min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery.validate.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-ui-1.8.16.custom.min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jqgrid/grid.locale-en.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jqgrid/jquery.jqGrid.min.js" />'></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$('#prefix').attr("disabled", true);
			
			$('#centerForm').validate({
				rules: {
					centerName: {
						required: true	
					},
					centerPhone: {
						required: true,
						minlength: 9,
						maxlength: 9,
						number: true
					}
				},
				messages: {
					centerName: {
						required: "Please enter a name."
					},
					centerPhone: {
						required: "Please enter phone number.",
						minlength: "You must enter a 9 digit number.",
						maxlength: "You must enter a 9 digit number.",
						number: "You must enter only numbers."
					}
				}
			});
			/*
			$('#accordion').accordion({
				collapsible: true,
				autoHeight: false,
				active: false
			});
			
			$('#gridtable').jqGrid({
				url: 'healthcenter/gethealthcenters',
				datatype: 'json',
				mtype: 'GET',
				colNames: ['Center Id', 'Health Center', 'Emergency Phone'],
				colModel: [
					{name: 'centerId', index: 'centerId', editable: true, hidden: true},
					{name: 'centerName', index: 'centerName', width: 300, editable: true},
					{name: 'centerPhone', index: 'centerPhone', width: 200, editable: true}
				],
				rowNum: 5,
				rowList: [5, 10, 15],
				rownumbers: true,
				pager: '#gridpager',
				caption: 'Existing Health Centers',
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
			
			$('#gridtable').jqGrid('navGrid', '#gridpager', {edit: false, add: false, del: false, search: false});
			
			$('#gridtable').jqGrid('navButtonAdd', '#gridpager',
					{
						caption: 'Edit',
						buttonicon: 'ui-icon-pencil',
						onClickButton: function() {
							editRow();
						},
						position: 'last',
						title: 'Edit Row',
						cursor: 'pointer'
					}		
			);
				
			$('#gridtable').jqGrid('navButtonAdd', '#gridpager',
					{
						caption: 'Details',
						buttonicon: 'ui-icon-star',
						onClickButton: function() {
							showDetails();
						},
						position: 'last',
						title: 'Show Details',
						cursor: 'pointer'
					}		
			);
			*/
		});
		/*
		function editRow() {
			var row = $('#gridtable').jqGrid('getGridParam','selrow');
			if (row != null)
				$('#gridtable').jqGrid('editGridRow', row,
					{	
						url: "/ancmessenger/healthcenter/edithealthcenter", 
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
							
				            if (result.success == false) {
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

		
		function showDetails() {	
			var row = $('#gridtable').jqGrid('getGridParam','selrow');
			if (row != null) {
				var cell = $('#gridtable').getCell(row, 'centerId');
				var s = '/ancmessenger/healthpost?pid=' + cell;
				window.location = s;
			}
		}
		
		*/
	</script>
	</head>
	
	<body>
		<div style="text-align: center">
			<h3>Edit Health Center Data</h3>
		</div>
		<br/>
		<div style="width: 90%; margin: 0 auto">
			<div id="titleBar" style="width: 300px">New Health Center</div>
			<div id="inputArea" style="width: 300px">
				<f:form id="centerForm" method="POST" modelAttribute="healthCenter" action="/ancmessenger/admin/healthcenter/create">
					<f:hidden path="centerId"/>
		
					<label for="centerName">Health Center Name: </label>
					<div><f:input path="centerName" id="centerName" name="centerName" style="width: 250px"/></div>
					
					<label for="centerPhone">Emergency Phone: </label>
					
					<table>
						<tr>
							<td>
								<input id="prefix" type="text" style="width: 30px" value="+251" />
							</td>
							<td>
								-
							</td>
							<td>
								<f:input path="centerPhone" id="centerPhone" name="centerPhone" style="width: 210px;"/>		
							</td>
						</tr>
					</table>
					
					
					<input type="submit" value="Save" id="saveBtn" />
				</f:form>
			</div>
		</div>
		
	</body>
</html>