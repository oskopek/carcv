<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="SHORTCUT ICON" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg">
<link rel="icon" href="http://upload.wikimedia.org/wikipedia/commons/f/f0/Car_with_Driver-Silhouette.svg" type="image/ico">
<title>CarCV - Contribute</title>
<link rel="stylesheet" type="text/css" href="/resources/mystyle.css">
</head>

<body>
    <div id="header">
        <a href="/app/index.jsp" target="_top"><img src="/resources/opencv-logo.png" width="150"
            style="border: 0; margin-top: 5px; margin-bottom: 5px; text-align: left; position: relative; top: -10px"
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
        <h2>Contributing</h2>
        <p><strong>Everyone</strong> is encouraged to help improve this project.

        <p>Here are some ways <strong>you</strong> can contribute:

        <ul>
        <li> by using alpha, beta, and pre-release versions
        <li> by reporting bugs
        <li> by suggesting new features
        <li> by implementing <a href="https://github.com/oskopek/carcv/blob/master/docs/goals.adoc">planned features</a>
        <li> by translating to a new language
        <li> by <a href="https://github.com/oskopek/carcv/blob/master/docs/howto-write-documentation.adoc">writing or editing documentation</a>
        <li> by writing specifications
        <li> by writing code (<strong>no patch is too small</strong>: fix typos, add comments, clean up inconsistent whitespace)
        <li> by refactoring code
        <li> by closing <a href="https://github.com/oskopek/carcv/issues">issues</a>
        <li> by reviewing patches
        </ul>

        <h3>Submitting an Issue</h3>
        <p>We use the <a href="https://github.com/oskopek/carcv/issues">GitHub issue tracker</a> to track bugs and features. Before
        submitting a bug report or feature request, check to make sure it hasn't
        already been submitted. When submitting a bug report, please include a <a href="https://gist.github.com/">Gist</a>
        that includes a stack trace and any details that may be necessary to reproduce
        the bug, including your Java version and operating system.

        <h3>Submitting a Pull Request</h3>
        <p><ul>
        <li> <a href="http://help.github.com/fork-a-repo/">Fork the repository</a>
        <li> <a href="http://learn.github.com/p/branching.html">Create a topic branch</a>
        <li> Implement your feature or bug fix
        <li> If applicable, add tests for your feature or bug fix
        <li> Run <samp>mvn clean install -Pit</samp>
        <ul>
            <li> If you contributed to <strong>carcv-webapp</strong>, run: <samp>mvn clean install -Pit,wildfly-it</samp>
        </ul>
        <li> If the tests fail, return to step 3 and 4
        <li> Add, commit, and push your changes
        <li> <a href="http://help.github.com/send-pull-requests/">Submit a pull request</a>
        </ul>
    </div>

    <div id="footer"></div>

</body>
</html>