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
        var rows = table.rows('.selected');
        if (typeof rows == 'undefined' || rows.length <= 0) {
            alert("The selection is empty!")
            return;
        }
        var idString = "".concat(rows[0][0]);
        for (i = 1; i < rows.length; i++) {
            idString = idString.concat(",", rows[i][0]);
        }
        window.parent.location.replace("/admin/servlet/RemoveEntry?entry_id=" + idString);
		//table.rows('.selected').remove().draw( false );
	} );
} );
	</script>

</head>
<body>
    <c:if test="${isAdmin}"><button id="deleteButton">Delete selected rows</button></c:if><br>
    <table id="carTable" style="border: 1px solid #C0C0C0; background-color: #B0C4DE;" class="display" cellspacing="0" width="100%">
    <thead>
        <tr>
            <th style="width: 10%;">ID</th>
            <th style="width: 20%;">Picture</th>
            <th style="width: 15%;">Date</th>
            <th style="width: 15%;">License plate</th>
            <th style="width: 20%;">Location</th>
            <th style="width: 10%;">Video</th>
            <th style="width: 10%;">Report</th>
        </tr>
    </thead>

    <tfoot>
        <tr>
            <th style="width: 5%;">ID</th>
            <th style="width: 25%;">Picture</th>
            <th style="width: 15%;">Date</th>
            <th style="width: 20%;">License plate</th>
            <th style="width: 25%;">Location</th>
            <th style="width: 5%;">Video</th>
            <th style="width: 5%;">Report</th>
        </tr>
    </tfoot>

    <tbody>
        <c:forEach var="member" items="${wrtmList}">
            <tr>
                <td>${member.entryId}</td>
                <td><a href="/servlet/DisplayImage?path=${member.previewPath}" target="_top">
                <img src="/servlet/DisplayImage?path=${member.previewPath}&width=1000" style="border: 2px; width: 100%"
                    alt="Car"></a></td>
                <td>${member.date}<br> ${member.time}</td>
                <td>${member.licensePlate}</td>
                <td>${member.location}</td>
                <td><a href="/servlet/GenerateVideo?entry_id=${member.entryId}" target="_top">View</a></td>
                <td><a href="/servlet/GenerateReport?entry_id=${member.entryId}&timezone=${member.timeZone}" target="_top">Generate</a></td>
            </tr>
            <!-- TODO put script to addRow here -->
        </c:forEach>
    </tbody>
    </table>
</body>
</html>