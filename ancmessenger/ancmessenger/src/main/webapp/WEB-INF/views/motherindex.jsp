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
	<style type="text/css">
        .rowColorRed {
            background-color: #f25917;
        }

        .rowColorGreen {
			background-color: #a2ff97;
		}

        .rowColorWhite {
            background-color: white;
            color: purple;
            font-weight: bold !important;
            font-style: italic;
        }

        .ui-jqgrid {
            font-size: 13px;
        }
	</style>
	
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-1.6.2.min.js" />'></script>
	 
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery.form.js" />'></script>
	
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery.validate.js" />'></script>
	<!--
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-blink.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery.scrollTo-1.4.2-min.js" />'></script>
	-->
	<script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-ui-1.8.16.custom.min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jqgrid/grid.locale-en.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/jqgrid/jquery.jqGrid.min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/resources/js/DateManipulation.js" />'></script>

	<script type="text/javascript">
		var mode = "";
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
			
			$('#motherForm').clearForm();
			$('#edd').attr("readonly", true);
			
			$('#lmpMonth').change(function() {
		    	
				var source = '#lmpMonth';
		        var target = '#lmpDay';
		        var year = $('#lmpYear').val();
		
		        var month = $(source).val();
		        var yearChecker = parseInt(year) + 1;
		        days = getEthiopianDaysForMonth(month, (yearChecker % 4) == 0);
		        $(target).empty();
		        $.each(days, function (index, optionData) {
		            $(target).append('<option value="' + optionData + '">'
		                                          + optionData + '</option>');
		        });
                setEddValue();
			});

            $('#lmpYear').change(function() {
                setEddValue();
            });

            $('#lmpDay').change(function() {
                setEddValue();
            });
			
			$('#edd').focus(function() {
				setEddValue();
				
			});	
			
			$('#postId').change(function() {
				retrieveGotts();
				retrieveHEWs();
			});
			
			
			var rowsToColor = [];
			
			$('#gridtable').jqGrid({
				url: '/ancmessenger/user/mother/getmothers?postId=0&workerId=0&status=ALL&time=ALL',
				datatype: 'json',
				mtype: 'GET',
                height: 'auto',
				gridview: true,
				colNames: ['Seq Id', 'MRN', 'Full Name', 'EDD', 'Passed', 'Health Post', 'Gott', 'HEW', 'HEW Telephone',
                    'First', 'Second', 'Delivery Status', 'FirstConf', 'SecondConf'],
				colModel: [
				    {name: 'seqId', index: 'seqId', editable: true, hidden: true},
				    {name: 'motherId', index: 'motherId', width: 70, editable: true, required: true},
                    {name: 'fullName', index: 'fullName', width: 150, editable: true, required: true},
				    {name: 'edd', index: 'edd', width: 90, editable: true},
                    {name: 'passed', index: 'passed', editable: true, hidden: true},
                    {name: 'healthPost', index: 'healthPost', width: 90, editable: false},
				    {name: 'gott', index: 'gott', width: 80, editable: false},
                    {name: 'workerName', index: 'workerName', width: 120, editable: false},
                    {name: 'workerPhone', index: 'workerPhone', width: 120, editable: false},
                    {name: 'firstReminder', index: 'firstReminder', width: 60, editable: false, formatter: 'checkbox'},
                    {name: 'secondReminder', index: 'secondReminder', width: 60, editable: false, formatter: 'checkbox'},
				    {name: 'deliveryStatus', index: 'deliveryStatus', width: 120, editable: false},
				    {name: 'firstConfirmation', index: 'firstConfirmation', hidden: true, editable: false},
                    {name: 'secondConfirmation', index: 'secondConfirmation', hidden: true, editable: false}
				],
				rowNum: 15,
				rowList: [15, 30, 50],
				rownumbers: true,
				pager: '#gridpager',
				caption: 'Existing Mothers',
				emptyrecords: 'No data available',
                viewrecords: true,
				loadonce: false,
				ondblClickRow: editMother,
				jsonReader: {
					root: 'rows',
			        page: 'page',
			        total: 'total',
			        records: 'records',
					repeatitems: false,
					cell: 'cell',
					id: 'id'
				},
				loadComplete: function() {
					var rowIDs = $('#gridtable').getDataIDs(); 
				    for (var i = 0; i < rowIDs.length; i++) { 
				        rowData = $('#gridtable').getRowData(rowIDs[i]);
				        var trElement = $("#"+ rowIDs[i], $('#gridtable'));
                        $('#gridtable').jqGrid('setCell', rowIDs[i], 'firstReminder', '', {'text-align':'center'});
                        $('#gridtable').jqGrid('setCell', rowIDs[i], 'secondReminder', '', {'text-align':'center'});
				        if (rowData.firstConfirmation == 'UNCONFIRMED') {
                            $('#gridtable').jqGrid('setCell', rowIDs[i], 'firstReminder', '', 'rowColorRed');
				            //trElement.removeClass('ui-widget-content');
				            //trElement.addClass('rowColorRed');

				        } else if (rowData.firstConfirmation == 'CONFIRMED') {
                            $('#gridtable').jqGrid('setCell', rowIDs[i], 'firstReminder', '', 'rowColorGreen');
				            //trElement.removeClass('ui-widget-content');
				            //trElement.addClass('rowColorGreen');
				        }
                        //if (rowData.secondReminder == 'true') {
                        if (rowData.secondConfirmation == 'UNCONFIRMED') {
                            $('#gridtable').jqGrid('setCell', rowIDs[i], 'secondReminder', '', 'rowColorRed');
                            //trElement.removeClass('ui-widget-content');
                            //trElement.addClass('rowColorRed');

                        } else if (rowData.secondConfirmation == 'CONFIRMED') {
                            $('#gridtable').jqGrid('setCell', rowIDs[i], 'secondReminder', '', 'rowColorGreen');
                            //trElement.removeClass('ui-widget-content');
                            //trElement.addClass('rowColorGreen');
                        }
                        //}
                        if (rowData.passed == 'true' && rowData.deliveryStatus == 'Not Delivered') {
                            $('#gridtable').jqGrid('setCell', rowIDs[i], 'deliveryStatus', '', 'rowColorWhite');
                        }
				        	
				      }
				}
			});
			
			$('#gridtable').jqGrid('navGrid', '#gridpager', 
					{edit: false, add: false, del: false, search: false}
			);
			
			$('#gridtable').jqGrid('navButtonAdd', '#gridpager',
				{
					caption: 'New',
					buttonicon: 'ui-icon-star',
					onClickButton: function() {
						addNew();
					},
					position: 'last',
					title: 'New Mother',
					cursor: 'pointer'
				}		
			);
			
			$('#gridtable').jqGrid('navButtonAdd', '#gridpager',
					{
						caption: 'Edit',
						buttonicon: 'ui-icon-pencil',
						onClickButton: function() {
							editMother();
						},
						position: 'last',
						title: 'Edit Mother',
						cursor: 'pointer'
					}		
				);
			
			//Get health posts initially
			retrieveHealthPosts();
			
			//Handle health post drop down selection change event
			$('#healthPostFilter').change(function() {
				var postId = $('#healthPostFilter').val();
				var workerId = $('#hewFilter').val();
				var status = $('#status').val();
				var time = $('#time').val();
				
				var target = $('#hewFilter');
				var newUrl = '/ancmessenger/user/mother/getmothers?';
				
				newUrl += 'postId=' + postId + '&workerId=' + workerId + '&status=' + status + '&time=' + time;
				retrieveHewList(postId, target);

				$('#gridtable').jqGrid().setGridParam({url: newUrl}).trigger('reloadGrid');

				$('#hewFilter').removeAttr('disabled');
				$('#hewFilter').empty();
				$('#hewFilter').append('<option value="0"> -- All -- </option>');
				 
			});
			
			//Handle hew drop down change event.
			$('#hewFilter').change(function() {
				refreshMotherList();
			});
			
			$('#status').change(function() {
				refreshMotherList();
			});
			
			$('#time').change(function() {
				refreshMotherList();
			});
			
			$('#motherForm').validate({
	        	rules: {
	        		motherId: {
	        			required: true,
	        			maxlength: 10
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
	        			maxlength: "MRN exceeds 10 characters."
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
			
			$('#motherEntry').dialog({
				autoOpen: false,
				height: 400,
				width: 645,
				modal: true,
                resizable: false,
				fontSize: 10,
				buttons: {
					"Save": function() {
						if ($('#motherForm').validate().form()) {

							if (mode == 'edit') {
								$.post('/ancmessenger/user/mother/editmother', $('#motherForm').serialize(), function() {
									$('#gridtable').jqGrid().trigger('reloadGrid');
									$('#motherForm').clearForm();	
									
									$('#postId').removeAttr('disabled', 'disabled');
									$('#gott').removeAttr('disabled', 'disabled');
									$('#workerId').removeAttr('disabled', 'disabled');
									
									$('#gestationalAge').attr('disabled', 'disabled');
									$('#lmpYear').removeAttr('disabled', 'disabled');
									$('#lmpMonth').removeAttr('disabled', 'disabled');
									$('#lmpDay').removeAttr('disabled', 'disabled');
									
									$('#motherEntry').dialog('close');
								}).error(function() {
									alert("Error entering data!!");
									$('motherEntry').dialog('close');
								});
							} else {
								$.post('/ancmessenger/user/mother/createmotherajax', $('#motherForm').serialize(),
                                        function(data) {
                                    if (data != 'Success') {
                                        alert(data);
                                    } else {
                                        $('#gridtable').jqGrid().trigger('reloadGrid');
                                        $('#motherForm').clearForm();

                                        $('#postId').removeAttr('disabled', 'disabled');
                                        $('#gott').removeAttr('disabled', 'disabled');
                                        $('#workerId').removeAttr('disabled', 'disabled');

                                        $('#gestationalAge').attr('disabled', 'disabled');
                                        $('#lmpYear').removeAttr('disabled', 'disabled');
                                        $('#lmpMonth').removeAttr('disabled', 'disabled');
                                        $('#lmpDay').removeAttr('disabled', 'disabled');

                                        $('#motherEntry').dialog('close');
                                    }
								}).error(function() {
									alert("Error entering data!!");
									$('motherEntry').dialog('close');
								});
							}
							
						}
						
					},
					Cancel: function() {
						$('#motherForm').clearForm();
						
						$('#postId').removeAttr('disabled', 'disabled');
						$('#gott').removeAttr('disabled', 'disabled');
						$('#workerId').removeAttr('disabled', 'disabled');
						
						$('#gestationalAge').attr('disabled', 'disabled');
						$('#lmpYear').removeAttr('disabled', 'disabled');
						$('#lmpMonth').removeAttr('disabled', 'disabled');
						$('#lmpDay').removeAttr('disabled', 'disabled');
						
						$('#motherEntry').dialog('close');
					}
				}
			});
			
		});
	</script>
	
	<script type="text/javascript">
		
		function setEddValue() {
            var eddDate;

            if ($('#gAge').is(':checked')) {

                var gestationalAge = $('#gestationalAge').val();

                if (gestationalAge == '') {
                    alert('Please enter the gestational age.');
                } else {
                    $.get('/ancmessenger/user/mother/geteddfromgestationalage', {gestationalAge: gestationalAge},
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
                    //alert('Please enter the full lmp data.');
                } else {
                    var day = parseInt(dayText);
                    var month = parseInt(monthText);
                    var year = parseInt(yearText);
                    eddDate = calculateExpectedDateOfDelivery(day, month, year);
                    $('#edd').val(eddDate);
                }
            }
        }


        function retrieveGotts() {
			var postId = $('#postId').val();
			$.getJSON('/ancmessenger/user/mother/getgottsajax', {postId: postId},
				function(data) {
					$('#gott').empty();
					$.each(data, function(index, optionData) {
						$('#gott').append('<option value="' + optionData.gottName + '">'
								+ optionData.gottName + '</option>');
					});
				}	
			);
		}
		
		
		function retrieveGottsById(postId) {
			//var postId = $('#postId').val();
			$.getJSON('/ancmessenger/user/mother/getgottsajax', {postId: postId},
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
			$.getJSON('/ancmessenger/user/mother/gethealthpostsajax',
					function(data) {
						$('#healthPostFilter').empty();
						$('#healthPostFilter').append('<option value="0">-- All --</option>');
						$('#healthPostFilter').append('<option value="10000">Urban</option>');
						$.each(data, function(index, optionData) {
							$('#healthPostFilter').append('<option value="' + optionData.postId + '">'
								+ optionData.postName + '</option>'
							);
						});
					}
			);
		}
		
		
		function retrieveHewList(postId, target) {
			if (postId != 0) {
                $.getJSON('/ancmessenger/user/mother/gethealthworkersajax', {postId: postId},
                        function(data) {
                            $(target).empty();
                            $(target).append('<option value="0">-- Select HEW --</option>');
                            $.each(data, function(index, optionData) {
                                $(target).append('<option value="' + optionData.workerId + '">'
                                        + optionData.fullName + '</option>');
                            });
                        }
                );
            }
		}
		
		function retrieveHEWs() {
			var postId = $('#postId').val();
			$.getJSON('/ancmessenger/user/mother/gethealthworkersajax', {postId: postId},
				function(data) {
					$('#workerId').empty();
					$.each(data, function(index, optionData) {
						$('#workerId').append('<option value="' + optionData.workerId + '">'
								+ optionData.fullName + '</option>');
					});
				}
			);
		}
		
		function retrieveHEWsById(postId) {
			//var postId = $('#postId').val();
			$.getJSON('/ancmessenger/user/mother/gethealthworkersajax', {postId: postId},
				function(data) {
					$('#workerId').empty();
					$.each(data, function(index, optionData) {
						$('#workerId').append('<option value="' + optionData.workerId + '">'
								+ optionData.fullName + '</option>');
					});
				}
			);
		}
		
		function refreshMotherList() {
			var postId = $('#healthPostFilter').val();
			//if (postId != 0) {
			var workerId = $('#hewFilter').val();
			var status = $('#status').val();
			var time = $('#time').val();
			var newUrl = '/ancmessenger/user/mother/getmothers?postId=' + postId + '&workerId=' + workerId +
							'&status=' + status + '&time=' + time;
			$('#gridtable').jqGrid().setGridParam({url: newUrl}).trigger('reloadGrid');

		}
		
		function addNew() {
			mode = 'new';
			$('#statusTd').hide();
            $('#motherForm').clearForm();
			$('#motherEntry').dialog('open');
			$('#motherEntry').dialog('option', 'title', 'Create New Mother');
		}
		
		function editMother() {
			mode = 'edit';
			var row = $('#gridtable').jqGrid('getGridParam','selrow');
			if (row != null) {
				var cell = $('#gridtable').getCell(row, 'seqId');
				var s = '/ancmessenger/user/mother/getmother';
				$.getJSON(s, {seqId: cell},
						function(data) {
							
							$('#seqId').val(data.seqId);
							$('#motherId').val(data.motherId);
                            $('#fullName').val(data.fullName);
                            $('#age').val(data.age);
							$('#statusTd').show();
							$('#deliveryStatus').val(data.outcome);

                            $('#motherEntry').dialog('open');
							$('#motherEntry').dialog('option', 'title', 'Edit Mother');

							var postId = data.healthPostId;
							if (postId != null) {
								$('#postId').val(postId);
								$.getJSON('/ancmessenger/user/mother/getgottsajax', {postId: postId},
									function(data) {
										$('#gott').empty();
										$.each(data, function(index, optionData) {
											$('#gott').append('<option value="' + optionData.gottName + '">'
													+ optionData.gottName + '</option>');
										});
										
									}	
								).complete(function() {
									$('#gott').val(data.gott);
								});
								
								$.getJSON('/ancmessenger/user/mother/gethealthworkersajax', {postId: postId},
									function(data) {
										$('#workerId').empty();
										$.each(data, function(index, optionData) {
											$('#workerId').append('<option value="' + optionData.workerId + '">'
													+ optionData.fullName + '</option>');
										});
									}
								).complete(function() {
									$('#workerId').val(data.workerId);
								});
								
							} else {
								$('#urban').prop('checked', true);
								$('#postId').attr('disabled', 'disabled');
								$('#gott').attr('disabled', 'disabled');
								$('#workerId').attr('disabled', 'disabled');
							}
							
							if (data.gestationalAge == null) {
								$('#lmpYear').val(data.lmpYear);
								$('#lmpMonth').val(data.lmpMonth);
								
								var source = '#lmpMonth';
						        var target = '#lmpDay';
						        var year = $('#lmpYear').val();
						
						        var month = $(source).val();
						        var yearChecker = parseInt(year) + 1;
						        days = getEthiopianDaysForMonth(month, (yearChecker % 4) == 0);
						        $(target).empty();
						        $.each(days, function (index, optionData) {
						            $(target).append('<option value="' + optionData + '">'
						                                          + optionData + '</option>');
						        });
						        $('#lmpDay').val(data.lmpDay);
							} else {
								$('#gAge').prop('checked', true);
								$('#gestationalAge').removeAttr('disabled');
								$('#gestationalAge').val(data.gestationalAge);
								$('#lmpYear').attr('disabled', 'disabled');
								$('#lmpMonth').attr('disabled', 'disabled');
								$('#lmpDay').attr('disabled', 'disabled');
							}
							setEddValue();
						}	
				);
			}
		}
	</script>

</head>
<body>
	<div style="text-align: center">
		<h3>Mother Data Management</h3>
	</div>
	<br/>
	
	<div style="width: 90%">
		<fieldset style="text-align: left; width: 90%; padding: 10px">
			<legend>Filter</legend>
			<label for="healthPostFilter">Health Post:</label>
			<select id="healthPostFilter">
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<label for="hewFilter">HEW:</label>
			<select id="hewFilter">
				<option value="0">-- Select HEW --</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<label for="status">Confirmation: </label>
			<select id="status">
				<option value="ALL">All</option>
				<option value="CONFIRMED">Confirmed</option>
				<option value="UNCONFIRMED">Unconfirmed</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<label for="time">Message: </label>
			<select id="time">
				<option value="ALL">All</option>
				<option value="MONTHLY">First</option>
				<option value="WEEKLY">Second</option>
			</select>
			&nbsp;&nbsp;
			<img id="loader" name="loader" alt="loading" style="display: none" 
					src='<s:url value="/resources/images/ajax-loader.gif" />' />
		</fieldset>
		<br/>
		
		<table id="gridtable">
		</table>
		<div id="gridpager"></div>
        <fieldset style="width: 400px; padding-left: 10px">
            <legend><b>Key</b></legend>
            <br/>
            <table>
                <tr>
                    <td class="rowColorRed">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>Unconfirmed Messages</td>
                </tr>
                <tr>
                    <td class="rowColorGreen"></td>
                    <td>Confirmed Messages</td>
                </tr>
                <tr>
                    <td style="background-color: purple"></td>
                    <td>EDD has passed but delivery status is not set</td>
                </tr>
            </table>
            <br/>
        </fieldset>
	</div>
	
	<div id="motherEntry" title="Create New Mother">
		<div id="inputArea" style="width: 600px">
			<form id="motherForm" method="POST"  action="/user/mother/createmotherajax">
				<table width="100%">
					<tr>
						<td>
							<input type="hidden" id="seqId" name="seqId" />
							<label for="motherId">MRN: </label>
							<div><input type="text" id="motherId" name="motherId" style="width: 150px"/></div>
						</td>
						<td>
							<label for="fullName">Full Name: </label>
							<div><input type="text" id="fullName" name="fullName" style="width: 150px"/></div>
						</td>
						<td>
							<label for="age">Age: </label>
							<div>
								<input type="text" id="age" name="age" style="width: 50px" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<label for="postId">Health Post: </label>
							<div>
								<select id="postId" name="postId" style="width: 150px">
									<c:forEach items="${healthPosts}" var="healthPost">
										<option value="${healthPost.postId}">${healthPost.postName}</option>
									</c:forEach>
								</select>
							</div>
							
						</td>		
						<td>			
							<label for="gott">Gott: </label>
							<div>
								<select id="gott" name="gott" style="width: 150px">
									
								</select>
							</div>
						</td>
						<td>
							<label for="workerId">HEW: </label>
							<div>
								<select id="workerId" name="workerId" style="width: 150px">
									
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<!--<input type="checkbox" id="urban" /> Urban </br>-->
							<input type="checkbox" id="gAge" /> Use Gestational Age	
						</td>
						<td colspan="2">
							<label for="gestationalAge">Gestational Age:</label>
							<div>
								<input type="text" id="gestationalAge" name="gestationalAge" style="width: 80px" />
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
												<select id="lmpYear" name="lmpYear" style="width: 80px">
													<c:forEach items="${years}" var="year">
														<option value="${year}">${year}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td>
											<label for="lmpMonth">Month</label>
											<div>
												<select id="lmpMonth" name="lmpMonth" style="width: 90px">
													<c:forEach items="${months}" var="month">
														<option value="${month.number}">${month.name}</option>
													</c:forEach>
												</select>
											</div>
										</td>
										<td style="padding-right: 5px">
											<label for="lmpDay">Day</label>
											<div>
												<select id="lmpDay" name="lmpDay" style="width: 50px">
												</select>
											</div>
										</td>
										<td style="border-left: solid 2px gray; padding-left: 5px">
											<label for="edd">EDD</label>
											<div><input type="text" id="edd" name="edd" style="width: 100px"/></div>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
						<td id="statusTd">
							<label for="deliveryStatus">Delivery Status</label>
							<div>
								<select id="deliveryStatus" name="deliveryStatus" style="width: 120px">
								<option value="">Not Delivered</option>
								<option value="Delivered at Home">Delivered at Home</option>
                                <option value="Delivered at HP">Delivered at HP</option>
								<option value="Delivered at HC">Delivered at HC</option>
								<option value="Referred">Referred</option>
                                <option value="Unknown">Unknown</option>
							</select>
							</div>
						</td>
					</tr>
				</table>	
			</form>
		</div>
	</div>
</body>
</html>