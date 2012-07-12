<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Health Post Main Page</title>
	<link rel="stylesheet" href='<s:url value="/resources/css/jquery/start/jquery-ui-1.8.16.custom.css" />' />
	<link rel="stylesheet" href='<s:url value="/resources/css/jqgrid/ui.jqgrid.css" />' />

    <style type="text/css">
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
            $('#healthPostForm').validate({
                rules: {
                    postName: {
                        required: true,
                        number: false
                    },
                    kebele: {
                        required: true
                    }
                },
                messages: {
                    postName: {
                        required: "Please enter post name.",
                        number: "Please enter a valid name."
                    },
                    kebele: {
                        required: "Please enter kebele."
                    }
                }

            });

            $('#inputArea').dialog({
                autoOpen: false,
                height: 230,
                width: 300,
                modal: true,
                resizable: false,
                fontSize: 10,
                buttons: {
                    "Save": function() {
                        if ($('#healthPostForm').validate().form()) {
                            $('#healthPostForm').submit();
                        }
                    },
                    Cancel: function() {
                        $('#healthPostForm').clearForm();
                        $('#inputArea').dialog('close');
                    }
                }
            });

            $('#healthPostForm').ajaxForm({
                beforeSubmit: function() {
                    return $('#healthPostForm').validate().form();
                },
                success: function(data) {
                    $('#gridtable').jqGrid().trigger('reloadGrid');
                    $('#healthPostForm').clearForm();
                    $('#inputArea').dialog('close');
                }
            });

			var centerId = $('#centerId').val();
			$('#gridtable').jqGrid({
				url: 'healthpost/gethealthposts?centerId=' + centerId,
				editurl: 'healthpost/edithealthpost',
				datatype: 'json',
				mtype: 'GET',
				colNames: ['Post Id', 'Health Post', 'Kebele'],
				colModel: [
				           	{name: 'postId', index: 'postId', editable: true, hidden: true},
				          	{name: 'postName', index: 'postName', width: 400, editable: true, required: true},
				          	{name: 'kebele', index: 'kebele', width: 250, editable: true, required: false}
				],
				height: "auto",
				rowNum: 10,
				rowList: [10, 20, 30],
				rownumbers: true,
				pager: '#gridpager',
				caption: 'Existing Health Posts',
				emptyrecords: 'No data available',
				loadonce: false,
				ondblClickRow: details,
				
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
                        caption: 'Add',
                        buttonicon: 'ui-icon-plus',
                        onClickButton: function() {
                            addRow();
                        },
                        position: 'last',
                        title: 'Add Row',
                        cursor: 'pointer'
                    }
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
			
			$('#gridtable').jqGrid('navButtonAdd', '#gridpager',
				{
					caption: 'Details',
					buttonicon: 'ui-icon-star',
					onClickButton: function() {
						details();
					},
					position: 'last',
					title: 'See Details',
					cursor: 'pointer'
				}
			);
			
		});

        function addRow() {
            $('#inputArea').dialog('open');
        }

		function editRow() {
			var row = $('#gridtable').jqGrid('getGridParam','selrow');
			if (row != null)
				$('#gridtable').jqGrid('editGridRow', row,
					{	
						url: "/ancmessenger/admin/healthpost/edithealthpost", 
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
		
		function details() {
			var row = $('#gridtable').jqGrid('getGridParam','selrow');
			if (row != null) {
				var cell = $('#gridtable').getCell(row, 'postId');
                window.location = '/ancmessenger/admin/healthpost/entries?pid=' + cell;
			}
		}
	
	</script>
	
</head>
<body>
    <div style="text-align: center">
        <h3>Health Post Management</h3>
    </div>
    <br/>
    <h4>Health Center Details</h4>
    <table class="detailtable" cellspacing="0" border="1">
        <tr>
            <td class="label"><b>Health Center Id: </b></td>
            <td>${healthCenter.centerId}</td>
            <td class="label"><b>Health Center Name: </b></td>
            <td>${healthCenter.centerName}</td>
            <td class="label"><b>Total Health Posts: </b></td>
            <td>${healthCenter.healthPosts.size()}</td>
        </tr>
        <tr>
            <td colspan="6" style="text-align: right">
                <a href="/ancmessenger/admin/healthcenter/${healthCenter.centerId}"><b>Edit Health Center</b></a>
            </td>
        </tr>

    </table>

    <br/>
    <br/>
    <div id="inputArea" style="width: 300px">
        <f:form id="healthPostForm" method="POST" modelAttribute="healthPostDTO" action="/ancmessenger/admin/healthpost/create">
            <f:hidden path="postId"/>
            <f:hidden path="centerId" />
            <label for="postName">Health Post Name: </label>
            <div><f:input path="postName" id="postName" name="postName" style="width: 250px"/></div>

            <label for="kebele">Kebele: </label>
            <div><f:input path="kebele" id="kebele" name="kebele" style="width: 250px"/></div>
        </f:form>
    </div>

    <div>
        <table id="gridtable"></table>
        <div id="gridpager"></div>
    </div>

</body>
</html>