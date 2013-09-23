<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="SHORTCUT ICON"
    href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg">
<link rel="icon"
    href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg"
    type="image/ico">
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
		<a href="http://www.opencv.org" target="_top"><img
			src="/resources/opencv-logo.png" width="150"
			style="border: 0; margin-top: 5px; margin-bottom: 5px; text-align: left; position: relative; top: -10px"
			alt="OpenCV" /></a>
		<p
			style="position: absolute; right: 8%; top: 2%; font-size: 11pt; word-spacing: .5em;">
			<b> <a href="/" target="_top" style="text-decoration: none">Home</a>
				<a href="/" target="_top" style="text-decoration: none">Features</a>
				<a href="/" style="text-decoration: none">Contribute</a> <a href="/"
				target="_top" style="text-decoration: none; word-spacing: 0em;">Contact
					us</a>
			</b>
		</p>
	</div>

	<div id="panel">
		<p style="line-height: 200%; word-break: normal;">
			<!--spravit tabulku-->
		</p>
		<table style="border: 0px;">
			<tr style="text-align: center;">
				<td><a href="/" target="_top"
					style="position: absolute; left: 25%; text-align: center; text-decoration: none">Car
						database</a><br></td>
			</tr>
			<tr style="text-align: center;">
				<td><a href="/" target="_top"
					style="position: absolute; left: 35%; text-decoration: none">Settings</a><br>
				</td>
			</tr>
			<tr>
				<td><a href="/" target="_top"
					style="position: absolute; left: 7%; text-decoration: none">Account
						administration</a><br></td>
			</tr>
			<tr>
				<td><a href="/servlet/LogoutServlet" target="_top"
					style="position: absolute; left: 35%; text-decoration: none">Log
						out</a><br></td>
			</tr>
		</table>
	</div>

	<div id="center-norm">
		<iframe style="width: 99%; height: 98.5%; border: 5px solid #B0C4DE"
			src="/servlet/CarTableServlet"></iframe>
	</div>

	<div id="footer"></div>

</body>
</html>

