<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Uploadify test</title>
        <script src="uploadify/jquery-1.3.2.min.js"></script>
        <script src="uploadify/swfobject.js"></script>
        <script src="uploadify/jquery.uploadify.v2.1.0.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#uploadify').uploadify({
                    'uploader': 'uploadify/uploadify.swf',
                    'script': 'servlet/UploadServlet',
                    'cancelImg': 'uploadify/cancel.png'
                });
                $('#upload').click(function() {
                    $('#uploadify').uploadifyUpload();
                    return false;
                });
            });
        </script>
    </head>
    <body>
        <input id="uploadify" type="file">
        <a id="upload" href="#">Upload</a>
    </body>
</html>