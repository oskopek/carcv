<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="SHORTCUT ICON" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg">
<link rel="icon" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg" type="image/ico">
<title>CarCV Car Table</title>
<link rel="stylesheet" type="text/css" href="/resources/mystyle.css">
<style type="text/css">
#tabulator {
    text-align: center;
    height: 25px
}
</style>

<script type="text/javascript" language="javascript" src="/resources/jquery-1.11.1.min.js"></script>
<script type="text/javascript" language="javascript" src="/resources/jquery.dataTables.min.js"></script>
<script type="text/javascript" class="init">
$(document).ready(function() {
	var table = $('#carTable').DataTable();

	$('#carTable tbody').on( 'click', 'tr', function () {
		$(this).toggleClass('selected');
    } );

	$('#deleteButton').click( function () {
        if (!confirm("Are you sure you want to delete?")) {
            return;
        }
        var rows = table.rows('.selected')
        window.parent.location.replace("/admin/servlet/RemoveEntry?entry_id=" + rows.indexes());
		//table.rows('.selected').remove().draw( false );
	} );
} );
	</script>

</head>
<body>
    <c:if test="${isAdmin}"><button id="deleteButton">Delete selected rows</button></c:if><br>
    <table id="carTable" style="border: 1px solid #C0C0C0;" class="display" cellspacing="0" width="100%">
    <thead>
        <tr>
            <th style="width: 5%; height: 15px; background-color: #B0C4DE;">ID</th>
            <th style="width: 160px; height: 15px; background-color: #B0C4DE;">Car preview</th>
            <th style="width: 10%; height: 15px; background-color: #B0C4DE;">Date</th>
            <th style="width: 15%; height: 15px; background-color: #B0C4DE;">License plate</th>
            <th style="width: 20%; height: 15px; background-color: #B0C4DE;">Location</th>
            <th style="width: 15%; height: 15px; background-color: #B0C4DE;">Video</th>
            <th style="width: 15%; height: 15px; background-color: #B0C4DE;">Pictures</th>
            <th style="width: 15%; height: 15px; background-color: #B0C4DE;">Report</th>
        </tr>
    </thead>

    <tfoot>
            <tr>
                <th style="width: 5%; height: 15px; background-color: #B0C4DE;">ID</th>
                <th style="width: 160px; height: 15px; background-color: #B0C4DE;">Car preview</th>
                <th style="width: 10%; height: 15px; background-color: #B0C4DE;">Date</th>
                <th style="width: 15%; height: 15px; background-color: #B0C4DE;">License plate</th>
                <th style="width: 20%; height: 15px; background-color: #B0C4DE;">Location</th>
                <th style="width: 15%; height: 15px; background-color: #B0C4DE;">Video</th>
                <th style="width: 15%; height: 15px; background-color: #B0C4DE;">Pictures</th>
                <th style="width: 15%; height: 15px; background-color: #B0C4DE;">Report</th>
            </tr>
        </tfoot>

        <tbody>
        <c:forEach var="member" items="${wrtmList}">
            <tr>
                <td>${member.entryId}</td>
                <td><img src="/servlet/DisplayImage?path=${member.previewPath}&width=150" style="border: 2px" width="150"
                    alt="Car"></td>
                <td>${member.date}<br> ${member.time}
                </td>
                <td>${member.licensePlate}</td>
                <td>${member.location}</td>
                <td><a href="/servlet/GenerateVideo?entry_id=${member.entryId}" target="_top">View video</a></td>
                <td><a href="/servlet/DisplayImage?path=${member.previewPath}" target="_top">View preview</a></td>
                <td><a href="/servlet/GenerateReport?entry_id=${member.entryId}&timezone=${member.timeZone}" target="_top">Generate
                        report</a></td>
            </tr>
            <!-- TODO put script to addROw here -->
        </c:forEach>
        </tbody>
    </table>
</body>
</html>