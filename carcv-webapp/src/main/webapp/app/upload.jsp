<!DOCTYPE html>
<html>
    <head>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>
        <script src="/resources/jquery.html5_upload.js" type="text/javascript"></script>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <link rel="SHORTCUT ICON"
                href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg">
        <link rel="icon" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg"
                type="image/ico">
        <title>CarCV - Upload</title>
        <link rel="stylesheet" type="text/css" href="/resources/site.css">
    </head>

    <body>
        <div class="header">
            <div class="logo"><a href="/app/index.jsp" target="_top">
                <img src="/resources/carcv-logo.png" alt="OpenCV"/></a>
            </div>
            <div class="topRow"><strong>
                <span class="link"><a href="/app/index.jsp" target="_top">Home</a></span>
                <span class="link"><a href="/info/features.jsp" target="_top">Features</a></span>
                <span class="link"><a href="/info/contribute.jsp">Contribute</a></span>
                <span class="link"><a href="/info/contact_us.jsp" target="_top">Contact us</a></span>
            </strong>
            </div>
        </div>

        <div class="leftPanel">
            <div class="panelItem"><a href="/servlet/Recognize" target="_top">Refresh DB</a></div>
            <div class="panelItem"><a href="/app/upload.jsp" target="_top">Upload</a></div>
            <div class="panelItem"><a href="/servlet/Logout" target="_top">Log out</a></div>
        </div>
        <div class="mainColumn">
            <input type="file" multiple="multiple" id="upload_field"/>

            <div id="progress_report">
                <div id="progress_report_name"></div>
                <div id="progress_report_status" style="font-style: italic;"></div>
                <div id="progress_report_bar_container" style="width: 90%; height: 5px;">
                    <div id="progress_report_bar" style="background-color: blue; width: 0; height: 100%;"></div>
                </div>
            </div>
            <script type="text/javascript">
                $(function () {
                    $("#upload_field").html5_upload({
                        url: function (number) {
                            return "/servlet/Upload";
                        },
                        sendBoundary: window.FormData || $.browser.mozilla,
                        onStart: function (event, total) {
                            return true;
                            return confirm("You are trying to upload " + total + " files. Are you sure?");
                        },
                        onProgress: function (event, progress, name, number, total) {
                            console.log(progress, number);
                        },
                        setName: function (text) {
                            $("#progress_report_name").text(text);
                        },
                        setStatus: function (text) {
                            $("#progress_report_status").text(text);
                        },
                        setProgress: function (val) {
                            $("#progress_report_bar").css('width', Math.ceil(val * 100) + "%");
                        },
                        onFinishOne: function (event, response, name, number, total) {
                            //alert(response);
                        },
                        onError: function (event, name, error) {
                            alert('error while uploading file ' + name);
                        }
                    });
                });
            </script>
        </div>

        <div id="footer"></div>

    </body>
</html>