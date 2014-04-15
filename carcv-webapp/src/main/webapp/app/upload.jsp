<!DOCTYPE html>
<html>
<head>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>
<script src="/resources/jquery.html5_upload.js" type="text/javascript"></script>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="SHORTCUT ICON" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg">
<link rel="icon" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg" type="image/ico">
<title>CarCV - Upload</title>
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
        <input type="file" multiple="multiple" id="upload_field" />
        <div id="progress_report">
            <div id="progress_report_name"></div>
            <div id="progress_report_status" style="font-style: italic;"></div>
            <div id="progress_report_bar_container" style="width: 90%; height: 5px;">
                <div id="progress_report_bar" style="background-color: blue; width: 0; height: 100%;"></div>
            </div>
        </div>
        <script type="text/javascript">
            $(function() {
                $("#upload_field").html5_upload({
                    url: function(number) {
                        return "/servlet/Upload";
                    },
                    sendBoundary: window.FormData || $.browser.mozilla,
                    onStart: function(event, total) {
                        return true;
                        return confirm("You are trying to upload " + total + " files. Are you sure?");
                    },
                    onProgress: function(event, progress, name, number, total) {
                        console.log(progress, number);
                    },
                    setName: function(text) {
                        $("#progress_report_name").text(text);
                    },
                    setStatus: function(text) {
                        $("#progress_report_status").text(text);
                    },
                    setProgress: function(val) {
                        $("#progress_report_bar").css('width', Math.ceil(val*100)+"%");
                    },
                    onFinishOne: function(event, response, name, number, total) {
                        //alert(response);
                    },
                    onError: function(event, name, error) {
                        alert('error while uploading file ' + name);
                    }
                });
            });
        </script>
    </div>

    <div id="footer"></div>

</body>
</html>