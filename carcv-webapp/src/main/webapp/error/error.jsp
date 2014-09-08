<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="date" class="java.util.Date" />
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <link rel="SHORTCUT ICON" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg">
        <link rel="icon" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg" type="image/ico">
        <title>CarCV - Error</title>
        <link rel="stylesheet" type="text/css" href="/resources/mystyle.css">
        <style type="text/css">
    </head>
    <body>
    <div id="header">
            <a href="/app/index.jsp" target="_top"><img src="/resources/carcv-logo.png" width="150"
                id="logo"
                alt="OpenCV" /></a>
            <p style="position: absolute; right: 8%; top: 2%; font-size: 11pt; word-spacing: .5em;">
                <strong> <a href="/app/index.jsp" target="_top" style="text-decoration: none">Home</a> <a
                    href="/info/features.jsp" target="_top" style="text-decoration: none">Features</a> <a href="/info/contribute.jsp"
                    target="_top" style="text-decoration: none">Contribute</a> <a href="/info/contact_us.jsp" target="_top"
                    style="text-decoration: none; word-spacing: 0em;">Contact us</a>
                </strong>
            </p>
        </div>

        <div id="center-login">
            <h1>Error</h1>
            <p>Unfortunately an unexpected error has occurred. Below you can find the error details.</p>
            <h2>Details</h2>
            <ul>
                <li>Timestamp: <fmt:formatDate value="${date}" type="both" dateStyle="long" timeStyle="long" />
                <li>Action: <c:out value="${requestScope['javax.servlet.forward.request_uri']}" />
                <li>Exception: <c:out value="${requestScope['javax.servlet.error.exception']}" />
                <li>Message: <c:out value="${requestScope['javax.servlet.error.message']}" />
                <li>Status code: <c:out value="${requestScope['javax.servlet.error.status_code']}" />
                <li>User agent: <c:out value="${header['user-agent']}" />
            </ul>
        </div>

        <div id="footer"></div>
    </body>
</html>