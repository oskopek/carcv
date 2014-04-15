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
</head>
<body>
    <c:set var = "admin" value = "admin" />
    <c:set var = "isAdmin" value = "${request.isUserInRole(admin)}" />
    <c:out value = "IsAdmin: ${isAdmin}" />
    <table style="border: 1px solid #C0C0C0;">
        <tr>
            <th style="width: 160px; height: 15px; background-color: #B0C4DE;">Car preview</th>
            <th style="width: 10%; height: 15px; background-color: #B0C4DE;">Date</th>
            <th style="width: 15%; height: 15px; background-color: #B0C4DE;">License plate</th>
            <th style="width: 20%; height: 15px; background-color: #B0C4DE;">Location</th>
            <th style="width: 15%; height: 15px; background-color: #B0C4DE;">Video</th>
            <th style="width: 15%; height: 15px; background-color: #B0C4DE;">Pictures</th>
            <th style="width: 15%; height: 15px; background-color: #B0C4DE;">Report</th>
            <c:if test="${isAdmin}">
                <th style="width: 10%; height: 15px; background-color: #B0C4DE;">Delete</th>
            </c:if>
        </tr>

        <c:forEach var="member" items="${wrtmList}">
            <tr>
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
                <c:if test="${isAdmin}">
                    <td>
                    <button onclick="confirmRemove()">Delete</button>
                    <script>
                        function confirmRemove() {
                            var result = confirm("Are you sure you want to delete?");
                            if (result) {
                                window.location.replace("/admin/servlet/RemoveEntry?entry_id=${member.entryId}");
                            }
                        }
                    </script>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</body>
</html>