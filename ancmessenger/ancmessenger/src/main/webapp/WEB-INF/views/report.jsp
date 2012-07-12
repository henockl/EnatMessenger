<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href='<s:url value="/resources/css/jquery/start/jquery-ui-1.8.16.custom.css" />' />

    <script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-1.6.2.min.js" />'></script>
    <script type="text/javascript" src='<s:url value="/resources/js/jquery/jquery-ui-1.8.16.custom.min.js" />'></script>
    <title>Send Quarterly Report</title>

    <script type="text/javascript">
        $(document).ready(function() {
            $('input:submit, input:button').button();
            $('#viewButton').click(function() {
                var year = $('#year').val();

                $.get('/ancmessenger/admin/report/show', {year: year},
                        function(data) {
                            $('#reportResult').html(data);
                        }
                );
            });
        });
    </script>
</head>
<body>
    <h2>Send Quarterly Report</h2>
    <form id="reportForm" method="POST" action="/ancmessenger/admin/report/create">
        <table width="200px">
            <tr>
                <td style="align: right">
                    Select Year:
                </td>
                <td style="align: left">
                    <select id="year" name="year" style="width: 60px">
                        <c:forEach items="${years}" var="year">
                            <option value="${year}">${year}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <input type="button" id="viewButton" name="viewButton" value="View" />

                </td>
                <td style="align: left">
                    <input type="submit" value="Send" />
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <strong style="color: green">
                        ${responseText}
                    </strong>
                </td>
            </tr>
        </table>
    </form>
    <br/>
    <hr/>
    <br/>
    <div id="reportResult">

    </div>
</body>
</html>