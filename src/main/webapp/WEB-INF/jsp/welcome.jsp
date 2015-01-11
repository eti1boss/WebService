<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>LOGIN !</title>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/core-min.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/enc-utf16-min.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/enc-base64-min.js"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/js/hmac-sha1.js"></script>
</head>
<body>
<h1>Login !</h1>
<div id="login"></div>
<form id="getData">
    <input type="text" id="email" required="required" value="e.bossuet@gmail.com"/>
    <input type="password" id="password" required="required" value="toto"/>
    <input type="submit" value="Log In"/>
</form>

<script type="text/javascript">
    var a = "ok";
    $("#getData").on('submit',function(e){

        e.preventDefault();

        var user = $("#email").val();//"e.bossuet@gmail.com";        // Récupéré depuis la page d'authentification.
        var password = $("#password").val();//"toto";
        //password = "toto";   // Récupéré depuis la page d'authentification.
        const salt = CryptoJS.SHA1(user); // Connu côté client dès le début.
        var encryptedPassword = CryptoJS.SHA1(CryptoJS.SHA1(password)+salt);
        var httpVerb = "GET";
        var currentTime = Date.now();
//        var url = "http://localhost:8080/SampleApp/api/user-service/users?email=" + user + "&timestamp=" + currentTime;

// GLASSFISH
        var url = "/SampleApp/tester/login?email=" + user + "&timestamp=" + currentTime;
//        var url = "https://localhost:8181/SampleApp/up?email=" + user + "&timestamp=" + currentTime;
// TOMCAT
//        var url = "http://localhost:8080/up?email=" + user + "&timestamp=" + currentTime;
//        var url = "https://localhost:8443/up?email=" + user + "&timestamp=" + currentTime;
        var httpUrl = httpVerb + ":" + url;
        var signature= CryptoJS.HmacSHA1(httpUrl.toString(),encryptedPassword.toString()).toString(CryptoJS.enc.Base64);
        signature = (encodeURIComponent(signature));
        url = url + "&signature=" + signature;

window.location = url;
    });

</script>
</body>
</html>

<!--asadmin> set configs.config.server-config.network-config.network-listeners.network-listener.http-listener-1.enabled=false-->