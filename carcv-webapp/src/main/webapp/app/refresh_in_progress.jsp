<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="SHORTCUT ICON" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg">
<link rel="icon" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg" type="image/ico">
<title>CarCV</title>
<link rel="stylesheet" type="text/css" href="/resources/mystyle.css">
<style type="text/css">
#tabulator {
    text-align: center;
    height: 25px
}
</style>
</head>

<body>
    <div id="header">
        <a href="/app/index.jsp" target="_top"><img src="/resources/opencv-logo.png" width="150"
            style="border: 0; margin-top: 5px; margin-bottom: 5px; text-align: left; position: relative; top: -10px"
            alt="OpenCV" /></a>
        <p style="position: absolute; right: 8%; top: 2%; font-size: 11pt; word-spacing: .5em;">
            <strong> <a href="/app/index.jsp" target="_top" style="text-decoration: none">Home</a> <a
                href="/info/features.jsp" target="_top" style="text-decoration: none">Features</a> <a href="/info/contribute.jsp"
                style="text-decoration: none">Contribute</a> <a href="/info/contact_us.jsp" target="_top"
                style="text-decoration: none; word-spacing: 0em;">Contact us</a>
            </strong>
        </p>
    </div>

    <div id="panel">
        <p style="line-height: 200%; word-break: normal;">
            <!--spravit tabulku-->
        </p>
        <table style="border: 0px;">
            <tr style="text-align: center;">
                <td><a href="/servlet/Recognize" target="_top"
                    style="position: absolute; left: 30%; text-align: center; text-decoration: none">Refresh DB</a><br></td>
            </tr>
            <tr style="text-align: center;">
                <td><a href="/app/upload.jsp" target="_top" style="position: absolute; left: 37%; text-decoration: none">Upload</a><br>
                </td>
            </tr>
            <tr>
                <td><a href="/app/index.jsp" target="_top" style="position: absolute; left: 40%; text-decoration: none">Admin</a><br></td>
            </tr>
            <tr>
                <td><a href="/servlet/Logout" target="_top" style="position: absolute; left: 35%; text-decoration: none">Log
                        out</a><br></td>
            </tr>
        </table>
    </div>

    <div id="center-login">
        <h1>Refresh is in progress...</h1>
        <br>
        <h3>
            <a href="/app/index.jsp">Go back</a> to the main page, the results will be automatically updated when the refresh
            finishes.
        </h3>
    </div>

    <div id="footer"></div>

</body>
</html>