<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Mother Main Page</title>
	<link rel="stylesheet" href='<s:url value="/resources/css/jquery/start/jquery-ui-1.8.16.custom.css" />' />
	<link rel="stylesheet" href='<s:url value="/resources/css/jqgrid/ui.jqgrid.css" />' />
	
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-1.6.2.min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery.form.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery.validate.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-blink.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery.scrollTo-1.4.2-min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-ui-1.8.16.custom.min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jqgrid/grid.locale-en.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jqgrid/jquery.jqGrid.min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/DateManipulation.js" />'></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$('#gestationalAge').attr('disabled', 'disabled');
			$('#urban').click(function() {
				if ($(this).is(':checked')) {
					$('#postId').attr('disabled', 'disabled');
					$('#gott').attr('disabled', 'disabled');
					$('#workerId').attr('disabled', 'disabled');
				} else {
					$('#postId').removeAttr('disabled', 'disabled');
					$('#gott').removeAttr('disabled', 'disabled');
					$('#workerId').removeAttr('disabled', 'disabled');
				}
					
			});
			$('#gAge').click(function() {
				if ($(this).is(':checked')) {
					$('#gestationalAge').removeAttr('disabled');
					$('#lmpYear').attr('disabled', 'disabled');
					$('#lmpMonth').attr('disabled', 'disabled');
					$('#lmpDay').attr('disabled', 'disabled');
				} else {
					$('#gestationalAge').attr('disabled', 'disabled');
					$('#lmpYear').removeAttr('disabled');
					$('#lmpMonth').removeAttr('disabled');
					$('#lmpDay').removeAttr('disabled');
				}
			});
			checkUnconfirmed();
			$('#motherForm').clearForm();
			$('#edd').attr("readonly", true);
			$('#accordion').accordion({
				collapsible: true,
				autoHeight: false,
				active: false
			});
			$('#tabs').tabs();
			
			var month = $('#lmpMonth').val();
	        var target = '#lmpDay';
	        var year = $('#lmpYear').val();	
	        
	        days = getEthiopianDaysForMonth(month, ((year + 1) % 4) == 0);
	    	
	        $(target).empty();

	        $.each(days, function (index, optionData) {
	            $(target).append('<option value="' + optionData + '">'
	                                      + optionData + '</option>');
	        });
	        
			//retrieveGotts();
			//retrieveHEWs();
			retrieveHealthPosts();
			//$('#gridtable').trigger('reloadGrid');
			
			$('#motherForm').validate({
        	rules: {
        		motherId: {
        			required: true,
        			minlength: 5,
        			maxlength: 5,
        			number: true
        		},
        		fullName: {
        			required: true
        		},
        		
        		gott: {
        			required: true
        		},
        		lmpYear: {
					required: true
				},
				lmpMonth: {
					required: true
				},
				lmpDay: {
					required: true
				},
				age: {
					required: true,
					min: 12,
					max: 49
				},
				edd: {
					required: true
				}
        	},
        	messages: {
        		motherId: {
        			required: "Pleaase enter MRN",
        			minlength: "MRN must be five digits.",
        			maxlength: "MRN must be five digits.",
        			number: "MRN accepts only numeric digits."
        		},
        		fullName: {
        			required: "Please enter a name."
        		},
        		
        		gott: {
        			required: "Please select a gott."
        		},
        		lmpYear: {
					required: "Please select a year value."
				},
				lmpMonth: {
					required: "Please select a month value."
				},
				lmpDay: {
					required: "Please select a day value."
				},
				age: {
					required: "Please enter an age value",
					min: "Please enter an age value between [12, 49]",
					max: "Please enter an age value between [12, 49]"
				},
				edd: {
					required: "Please click on the EDD box!"
				}
        	}
        	
        	
        });
		
		$('#motherForm').ajaxForm(
			{
				beforeSubmit: function() {
					return $('#motherForm').validate().form();
				},
				success: function() {
					alert("Data Saved Successfully!!");
					$('#gridtable').jqGrid().trigger('reloadGrid');
					$('#motherForm').clearForm();
					$('#postId').removeAttr('disabled');
					$('#gott').removeAttr('disabled');
					$('#workerId').removeAttr('disabled');
					$('#gestationalAge').attr('disabled', 'disabled');
					$('#lmpYear').removeAttr('disabled');
					$('#lmpMonth').removeAttr('disabled');
					$('#lmpDay').removeAttr('disabled');
				}
			}
		);
		
			$('#hpFilter').change(function() {
				var postId = $('#hpFilter').val();
				var target = '#hewFilter';
				if (postId == '') {
					$(target).empty();
					$(target).append('<option value="">-- Select --</option>');
				} else if (postId == 'urban') {
					$(target).attr('disabled', 'disabled');
					var newUrl = '/ancmessenger/mother/getmothers?workerId=0';
					$('#gridtable').jqGrid().setGridParam({url: newUrl}).trigger('reloadGrid');		
				} else {
					$(target).removeAttr('disabled');
					retrieveHewList(postId, target);
				}
				
			});
			
			$('#hewFilter').change(function() {
				var workerId = $('#hewFilter').val();
				if (workerId != '') {
					var newUrl = '/ancmessenger/mother/getmothers?workerId=' + workerId;
					$('#gridtable').jqGrid().setGridParam({url: newUrl}).trigger('reloadGrid');
				} else {
					$('#gridtable').clearGridData();
				}
			});
			
		});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$('#lmpMonth').change(function() {
		    	
				var source = '#lmpMonth';
		        var target = '#lmpDay';
		        var year = $('#lmpYear').val();
		
		        var month = $(source).val();
		        var yearChecker = parseInt(year) + 1;
		        //alert(yearChecker);
		        days = getEthiopianDaysForMonth(month, (yearChecker % 4) == 0);
		        $(target).empty();
		            //$(target).append('<option value="">-- Day --</option>');
		        $.each(days, function (index, optionData) {
		            $(target).append('<option value="' + optionData + '">'
		                                          + optionData + '</option>');
		        });
		        
			});
			
			$('#edd').focus(function() {
				var eddDate;
				
				if ($('#gAge').is(':checked')) {
					
					var gestationalAge = $('#gestationalAge').val();
				
					if (gestationalAge == '') {
						alert('Please enter the gestational age.');
					} else {
						$.get('/ancmessenger/mother/geteddfromgestationalage', {gestationalAge: gestationalAge},
								function(data) {
									$('#edd').val(data);	
								}
						);	
					}
					
				} else {
					var dayText = $('#lmpDay').val();
					var monthText = $('#lmpMonth').val();
					var yearText = $('#lmpYear').val();
					if (dayText == null || monthText == null || yearText == null) {
						alert('Please enter the full lmp data.');
					} else {
						var day = parseInt(dayText);
						var month = parseInt(monthText);
						var year = parseInt(yearText);
						eddDate = calculateExpectedDateOfDelivery(day, month, year);
						$('#edd').val(eddDate);	
					}
				}
				
			});	
			
			$('#postId').change(function() {
				retrieveGotts();
				retrieveHEWs();
				//$('#gridtable').trigger('reloadGrid');
			});
			
			/*
			$('#reminderType').change(function() {
				var type = $('#reminderType').val();
				if (type == '') {
					$('#datagridtable').clearGridData();
				}
				else if (type == '1') {
					var newUrl = '/ancmessenger/mother/getunconfirmedmonthly';
					var caption = 'List of unvisited mothers with 1 month to EDD';
					$('#datagridtable').jqGrid().setGridParam({url: newUrl, caption: caption}).trigger('reloadGrid');
				} else {
					var newUrl = '/ancmessenger/mother/getunconfirmedweekly';
					var caption = 'List of unvisited mothers with 1 week to EDD';
					$('#datagridtable').jqGrid().setGridParam({url: newUrl, caption: caption}).trigger('reloadGrid');
				}
			});
			*/
		});
	</script>
	
	<script type="text/javascript">
		var dataUrl = '';
		$(document).ready(function() {
			$('#gridtable').jqGrid({
				url: dataUrl,
				postData: {
					//workerId: function() {$('#workerId option:selected').val();}
				},
				editurl: 'mother/editmother',
				datatype: 'json',
				mtype: 'GET',
				colNames: ['Seq Id', 'MRN', 'Full Name', 'Age', 'LMP Year', 'LMP Month', 'LMP Day', 'LMP', 'EDD'],
				colModel: [
				           	{name: 'seqId', index: 'seqId', editable: true, hidden: true},
				           	{name: 'motherId', index: 'motherId', width: 100, editable: true, required: true},
				           	{name: 'fullName', index: 'fullName', width: 200, editable: true, required: true},
				          	{name: 'age', index: 'age', width: 50, editable: true, required: true},
				          	{name: 'lmpYear', index: 'lmpYear', editable: true, edittype: 'select', hidden: true, editrules: {edithidden: true}, 
				          			editoptions: {dataUrl: '/ancmessenger/mother/getyearsajax',
				          				buildSelect: function(data) {
				          					var response = $.parseJSON(data);
				                            var s = '<select>';
				                            if (response && response.length) {
				                                for (var i = 0, l=response.length; i<l ; i++) {
				                                    var ri = response[i].year;
				                                    s += '<option value="'+ri+'">'+ri+'</option>';
				                                }
				                            }
				                            return s + "</select>";
				                        }
				          			}
				          	},
				          	{name: 'lmpMonth', index: 'lmpMonth', editable: true, edittype: 'select', hidden: true, editrules: {edithidden: true},
				          			editoptions: {dataUrl: '/ancmessenger/mother/getmonthsajax',
				          				buildSelect: function(data) {
				          					var response = $.parseJSON(data);
				          					var s = '<select>';
				          					if (response && response.length) {
				                                for (var i = 0, l=response.length; i<l ; i++) {
				                                    var ri = response[i].number;
				                                    var rii = response[i].name;
				                                    s += '<option value="' + ri + '">' + rii + '</option>';
				                                }
				                            }
				                            return s + "</select>";
				          				}
				          			}
				          	},
				          	{name: 'lmpDay', index: 'lmpDay', editable: true, edittype: 'select', hidden: true, editrules: {edithidden: true},
				          			editoptions: {
				          				value: '1: 1; 2: 2; 3: 3; 4: 4' 
				          			}
				          	},
				          	{name: 'lmp', index: 'lmp', width: 100, editable: false, formatter: function(cellvalue, options, rowObject) {
				          			return rowObject['lmpDay'] + '/' + rowObject['lmpMonth'] + '/' + rowObject['lmpYear'];
				          		}
				          	},
				          	{name: 'edd', index: 'edd', editable: true, required: true, 
				          		editoptions: {
				          			dataEvents: [
				          				{
				          					type: 'focus',
				          					fn: function() {
				          						var day = parseInt($('select#lmpDay option:selected').val());
				          						var month = parseInt($('select#lmpMonth option:selected').val());
				          						var year = parseInt($('select#lmpYear option:selected').val());
				          						
				          						var eddDate = calculateExpectedDateOfDelivery(day, month, year);
				          						$('input#edd').val(eddDate);
				          					}
				          				}             
				          			]
				          	
				          		}
				          	}
				],
				rowNum: 5,
				rowList: [5, 10, 15],
				rownumbers: true,
				pager: '#gridpager',
				caption: 'Existing Mothers',
				emptyrecords: 'No data available',
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
			
			$('#gridtable').jqGrid('navGrid', '#gridpager', 
					{edit: false, add: false, del: false, search: false}
			);
			
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
			
			$('#datagridtable').jqGrid({
				url: '/ancmessenger/mother/getunconfirmedmonthly',
				postData: {
					//workerId: function() {$('#workerId option:selected').val();}
				},
				//editurl: 'mother/editmother',
				datatype: 'json',
				mtype: 'GET',
				colNames: ['MRN', 'Full Name', 'EDD', 'HEW Name', 'Phone Number', 'Date Sent'],
				colModel: [
				           	{name: 'motherId', index: 'motherId', width: 50},
				           	{name: 'fullName', index: 'fullName', width: 130},
				          	{name: 'edd', index: 'edd', width: 80},
				          	{name: 'hewName', index: 'hewName', width: 130},
				          	{name: 'phoneNumber', index: 'phoneNumber', width: 120},
				          	{name: 'sentOn', index: 'sentOn', width: 90}	
				],
				rowNum: 5,
				rowList: [5, 10, 15],
				rownumbers: true,
				pager: '#datagridpager',
				caption: '',
				emptyrecords: 'No data available',
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
			
			$('#datagridtable').jqGrid('navGrid', '#datagridpager', 
					{edit: false, add: false, del: false, search: false}
			);
			
			
			$('#datagridtable1').jqGrid({
				url: '/ancmessenger/mother/getunconfirmedweekly',
				postData: {
					//workerId: function() {$('#workerId option:selected').val();}
				},
				//editurl: 'mother/editmother',
				datatype: 'json',
				mtype: 'GET',
				colNames: ['MRN', 'Full Name', 'EDD', 'HEW Name', 'Phone Number', 'Date Sent'],
				colModel: [
				           	{name: 'motherId', index: 'motherId', width: 50},
				           	{name: 'fullName', index: 'fullName', width: 130},
				          	{name: 'edd', index: 'edd', width: 80},
				          	{name: 'hewName', index: 'hewName', width: 130},
				          	{name: 'phoneNumber', index: 'phoneNumber', width: 120},
				          	{name: 'sentOn', index: 'sentOn', width: 90}	
				],
				rowNum: 5,
				rowList: [5, 10, 15],
				rownumbers: true,
				pager: '#datagridpager1',
				caption: '',
				emptyrecords: 'No data available',
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
			
			$('#datagridtable1').jqGrid('navGrid', '#datagridpager1', 
					{edit: false, add: false, del: false, search: false}
			);
			
			
			$('#emergencygridtable').jqGrid({
				url: '/ancmessenger/mother/getemergencies',
				postData: {
					//workerId: function() {$('#workerId option:selected').val();}
				},
				//editurl: 'mother/editmother',
				datatype: 'json',
				mtype: 'GET',
				colNames: ['MRN', 'Full Name', 'EDD', 'Date Sent'],
				colModel: [
				           	{name: 'motherId', index: 'motherId', width: 70},
				           	{name: 'fullName', index: 'fullName', width: 200},
				          	{name: 'edd', index: 'edd', width: 80},
				          	{name: 'sentOn', index: 'sentOn', width: 100}	
				],
				rowNum: 5,
				rowList: [5, 10, 15],
				rownumbers: true,
				pager: '#emergencygridpager',
				caption: 'Emergencies',
				emptyrecords: 'No data available',
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
			
			$('#emergencygridtable').jqGrid('navGrid', '#emergencygridpager', 
					{edit: false, add: false, del: false, search: false}
			);
			
		});
		
	</script>
	
	<script type="text/javascript">
		function retrieveGotts() {
			var postId = $('#postId').val();
			$.getJSON('/ancmessenger/gott/getgottsajax', {postId: postId},
				function(data) {
					$('#gott').empty();
					$.each(data, function(index, optionData) {
						$('#gott').append('<option value="' + optionData.gottName + '">'
								+ optionData.gottName + '</option>');
					});
				}	
			);
		}
		
		function retrieveHealthPosts() {
			$.getJSON('/ancmessenger/healthpost/gethealthpostsajax',
					function(data) {
						$('#hpFilter').empty();
						$('#hpFilter').append('<option value="">-- Select --</option>');
						$('#hpFilter').append('<option value="urban">Urban</option>');
						$.each(data, function(index, optionData) {
							$('#hpFilter').append('<option value="' + optionData.postId + '">'
								+ optionData.postName + '</option>'
							);
						});
					}
			);
		}
		
		function retrieveHewList(postId, target) {
			$.getJSON('/ancmessenger/healthworker/gethealthworkersajax', {postId: postId},
					function(data) {
						$(target).empty();
						$(target).append('<option value="">-- Select --</option>');
						$.each(data, function(index, optionData) {
							$(target).append('<option value="' + optionData.workerId + '">'
									+ optionData.fullName + '</option>');
						});
					}
			);
		}
		
		function retrieveHEWs() {
			var postId = $('#postId').val();
			$.getJSON('/ancmessenger/healthworker/gethealthworkersajax', {postId: postId},
				function(data) {
					$('#workerId').empty();
					$.each(data, function(index, optionData) {
						$('#workerId').append('<option value="' + optionData.workerId + '">'
								+ optionData.fullName + '</option>');
					});
				}
			);
		}
		
		function idElem(value, options) {
			return $('<input type="text" value="' + value + '" disabled="disabled" />');
		}
		
		function idValue(elem) {
			return elem.val();
		}
		
		function editRow() {
			var row = $('#gridtable').jqGrid('getGridParam','selrow');
			if (row != null)
				$('#gridtable').jqGrid('editGridRow', row,
					{	
						url: "/ancmessenger/mother/editmother", 
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
												jq(this).dialog("close");
											}}
									});
			                }
				            return [result.success, errors, null];
						}
					});	
				else $('#dialogSelectRow').dialog();			
		}
		
		function details() {
			var row = $('#gridtable').jqGrid('getGridParam','selrow');
		}
		
		function checkUnconfirmed() {
			$.getJSON('/ancmessenger/mother/countunconfirmed', function(data) {
		
				if (data.monthly > 0 || data.weekly > 0) {
		
					$('.blink').show();
					$('.blink').blink();
					$('.info').html("<a href='#monthlyanchor' onclick='expandUnconfirmed(0);'>Monthly: <i>" + data.monthly 
							+"</i></a></br><a href='#weeklyanchor' onclick='expandUnconfirmed(1)'>Weekly: <i>" + data.weekly + "</i></a>");
					
				}
			});
		}
		
		function expandUnconfirmed(index) {
			$('#accordion').accordion('activate', 1);
			$.scrollTo( '#monthlyanchor', 800, {easing:'elasout'} );
			//
			$('#tabs').tabs("select", index);
			
		}
	</script>
</head>
<body>
	<div style="text-align: center">
		<h3>Health Center Data Management</h3>
	</div>
	<br/>
	<div>
		<div id="titleBar" style="width: 600px">New Mother</div>
		<div id="inputArea" style="width: 600px">
			<f:form id="motherForm" method="POST" modelAttribute="motherDTO" action="/ancmessenger/mother/createmotherajax">
				<table width="100%">
					<tr>
						<td>
							<f:hidden path="seqId"/>
							<label for="motherId">MRN: </label>
							<div><f:input path="motherId" id="motherId" name="motherId" style="width: 150px"/></div>
						</td>
						<td>
							<label for="fullName">Full Name: </label>
							<div><f:input path="fullName" id="fullName" name="fullName" style="width: 150px"/></div>
						</td>
						<td>
							<label for="age">Age: </label>
							<div>
								<f:input path="age" id="age" name="age" style="width: 50px" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<label for="postId">Health Post: </label>
							<div>
								<f:select path="postId" id="postId" name="postId" style="width: 150px">
									<c:forEach items="${healthPosts}" var="healthPost">
										<f:option value="${healthPost.postId}">${healthPost.postName}</f:option>
									</c:forEach>
								</f:select>
							</div>
							
						</td>		
						<td>			
							<label for="gott">Gott: </label>
							<div>
								<f:select path="gott" id="gott" name="gott" style="width: 150px">
									
								</f:select>
							</div>
						</td>
						<td>
							<label for="workerId">HEW: </label>
							<div>
								<f:select path="workerId" id="workerId" name="workerId" style="width: 150px">
									
								</f:select>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" id="urban" /> Urban </br>
							<input type="checkbox" id="gAge" /> Use Gestational Age	
						</td>
						<td colspan="2">
							<label for="gestationalAge">Gestational Age:</label>
							<div>
								<f:input path="gestationalAge" id="gestationalAge" name="gestationalAge" style="width: 80px" />
							</div>
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<fieldset>
								<legend>LMP</legend>
								<table>
									<tr>
										<td>
											<label for="lmpYear">Year</label>
											<div>
												<f:select path="lmpYear" id="lmpYear" style="width: 80px">
													<c:forEach items="${years}" var="year">
														<f:option value="${year}" />
													</c:forEach>
												</f:select>
											</div>
										</td>
										<td>
											<label for="lmpMonth">Month</label>
											<div>
												<f:select path="lmpMonth" id="lmpMonth" name="lmpMonth" style="width: 90px">
													<c:forEach items="${months}" var="month">
														<f:option value="${month.number}">${month.name}</f:option>
													</c:forEach>
												</f:select>
											</div>
										</td>
										<td style="padding-right: 5px">
											<label for="lmpDay">Day</label>
											<div>
												<f:select path="lmpDay" id="lmpDay" name="lmpDay" style="width: 50px">
												</f:select>
											</div>
										</td>
										<td style="border-left: solid 2px gray; padding-left: 5px">
											<label for="edd">EDD</label>
											<div><f:input path="edd" id="edd" name="edd" style="width: 100px"/></div>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
						<td style="text-align: right">
							<input type="submit" value="Save" id="saveBtn" />
						</td>
					</tr>
				</table>	
			</f:form>
		</div>
	</div>
	<br/>
	<hr/>
	<br/>
	<div id="accordion">
		<h3><a href="#">Edit Mother Data</a></h3>
		<div>
			<fieldset style="text-align: left; margin-left: 20px; padding: 10px">
					<legend>Filter</legend>
					<label for="hpFilter">Health Post:</label>
					<select id="hpFilter">
						
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<label for="hewFilter">HEW:</label>
					<select id="hewFilter">
						<option value="">-- Select --</option>
					</select>
			</fieldset>
			<br/>
			<div style="width: 400px; float: left; margin-left: 20px">
				
				<table id="gridtable">
				</table>
				<div id="gridpager"></div>
			</div>
		</div>
		<h3><a href="#">Unconfirmed Visit Reminders</a></h3>
		<div id='monthlyanchor'>
			<div id="tabs">
				<ul>
					<li><a href="#tabs1">One Month</a></li>
					<li><a href="#tabs2">One Week</a></li>
				</ul>
			
				<div id="tabs1">
					<table id="datagridtable">
					</table>
					<div id="datagridpager"></div>
				</div>
				<div id="tabs2">
					<table id="datagridtable1">
					</table>
					<div id="datagridpager1"></div>
				</div>
			</div>
		</div>
		<h3><a href="#">Emergencies</a></h3>
		<div>
			<div style="width: 400px; float: left; margin-left: 20px">
				<table id="emergencygridtable">
				</table>
				<div id="emergencygridpager"></div>
			</div>
		</div>
	</div>
</body>
</html>