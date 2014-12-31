<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link rel="SHORTCUT ICON" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg">
    <link rel="icon" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg"
          type="image/ico">
    <title>CarCV - Login</title>
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

<div class="mainColumn">
    <div><h2>Log in</h2></div>
    <form method="post" target="_top" action="j_security_check">
        <p>Username: <input type="text" name="j_username"/></p>

        <p>Password: <input type="password" name="j_password"/></p>

        <p><input type="submit" value="Log in"/></p>
    </form>
</div>

<div id="footer"></div>

</body>
</html>