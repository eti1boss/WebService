<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <script src="${pageContext.request.contextPath}/js/jquery.js"></script>

  <script src="${pageContext.request.contextPath}/lib/dropzone/js/dropzone.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/dropzone/css/dropzone.css"/>

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/login/css/reset.css"/>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/login/css/style.css"/>
  <script src="${pageContext.request.contextPath}/lib/login/js/main.js"></script>
  <script src="${pageContext.request.contextPath}/lib/login/js/modernizr.js"></script>

  <script type="application/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
  <script type="application/javascript" src="${pageContext.request.contextPath}/js/core-min.js"></script>
  <script type="application/javascript" src="${pageContext.request.contextPath}/js/enc-utf16-min.js"></script>
  <script type="application/javascript" src="${pageContext.request.contextPath}/js/enc-base64-min.js"></script>
  <script type="application/javascript" src="${pageContext.request.contextPath}/js/hmac-sha1.js"></script>

  <script type="text/javascript">
    Dropzone.autoDiscover = true;
    Dropzone.options.myDrop = {
      paramName: "file",
      maxFilesize: 10,
      maxFiles: 10,
      autoProcessQueue: true,
      uploadMultiple : true,
      parallelUploads : 10
    };
  </script>
</head>
<body>

<header role="banner">
  <div id="cd-logo"><a href="#0"><img src="${pageContext.request.contextPath}/lib/login/img/cd-logo.svg" alt="Logo"></a></div>

  <nav class="open main-nav">
    <ul>
      <!-- inser more links here -->
      <c:choose>
        <c:when test="${empty user}">
          <c:choose>
            <c:when test="${empty isPublic}">
              <li><a class="cd-signin" style="display: none" id="pic" href="pictures">Your pictures</a></li>
            </c:when>
            <c:otherwise>
              <li><a class="cd-signin" id="pic" href="pictures">Your pictures</a></li>
            </c:otherwise>
          </c:choose>

          <li><a class="open cd-signin" href="#0">Sign in</a></li>
          <li><a class="open cd-signup" href="#0">Sign up</a></li>
        </c:when>
        <c:otherwise>
          <li><span style="color: white; ">${user}</span></li>
          <li><a class="cd-signin" id="pic" href="pictures">Your pictures</a></li>
          <li><a class="cd-signup" href="logout">Logout</a></li>
        </c:otherwise>
      </c:choose>



    </ul>
  </nav>
</header>
<div style="margin-left: 50px; margin-right: 50px; margin-top: 100px">
  <form action="/SampleApp/tester/upload" class="dropzone" id="myDrop">
  </form>
</div>

<div class="cd-user-modal"> <!-- this is the entire modal form, including the background -->
  <div class="cd-user-modal-container"> <!-- this is the container wrapper -->
    <ul class="cd-switcher">
      <li><a href="#0">Sign in</a></li>
      <li><a href="#0">New account</a></li>
    </ul>

    <div id="cd-login"> <!-- log in form -->

      <form id="getData" class="cd-form">
        <p class="fieldset">
          <label class="image-replace cd-email" for="email">E-mail</label>
          <input required="required" class="full-width has-padding has-border" id="email" type="email" placeholder="E-mail">
          <span class="cd-error-message">Error message here!</span>
        </p>

        <p class="fieldset">
          <label class="image-replace cd-password" for="password">Password</label>
          <input class="full-width has-padding has-border" id="password" type="password"  placeholder="Password">
          <span class="cd-error-message">Error message here!</span>
        </p>

        <p class="fieldset">
          <input type="checkbox" id="remember-me" checked>
          <label for="remember-me">Remember me</label>
        </p>

        <p class="fieldset">
          <input class="full-width" type="submit" value="Login">
        </p>
      </form>

      <p class="cd-form-bottom-message"><a href="#0">Forgot your password?</a></p>
      <!-- <a href="#0" class="cd-close-form">Close</a> -->
    </div> <!-- cd-login -->

    <div id="cd-signup"> <!-- sign up form -->
      <form action="/SampleApp/tester/register" method="post" class="cd-form">

        <p class="fieldset">
          <label class="image-replace cd-email" for="email">E-mail</label>
          <input class="full-width has-padding has-border" name="email" id="email" type="email" placeholder="E-mail">
          <span class="cd-error-message">Error message here!</span>
        </p>

        <p class="fieldset">
          <label class="image-replace cd-password" for="password">Password</label>
          <input class="full-width has-padding has-border" name="password" id="password" type="text"  placeholder="Password">
          <a href="#0" class="hide-password">Hide</a>
          <span class="cd-error-message">Error message here!</span>
        </p>

        <p class="fieldset">
          <input type="checkbox" id="accept-terms">
          <label for="accept-terms">I agree to the <a href="#0">Terms</a></label>
        </p>

        <p class="fieldset">
          <input class="full-width has-padding" type="submit" value="Create account">
        </p>
      </form>

      <!-- <a href="#0" class="cd-close-form">Close</a> -->
    </div> <!-- cd-signup -->

    <div id="cd-reset-password"> <!-- reset password form -->
      <p class="cd-form-message">Lost your password? Please enter your email address. You will receive a link to create a new password.</p>

      <form class="cd-form">
        <p class="fieldset">
          <label class="image-replace cd-email" for="reset-email">E-mail</label>
          <input class="full-width has-padding has-border" id="reset-email" type="email" placeholder="E-mail">
          <span class="cd-error-message">Error message here!</span>
        </p>

        <p class="fieldset">
          <input class="full-width has-padding" type="submit" value="Reset password">
        </p>
      </form>

      <p class="cd-form-bottom-message"><a href="#0">Back to log-in</a></p>
    </div> <!-- cd-reset-password -->
    <a href="#0" class="cd-close-form">Close</a>
  </div> <!-- cd-user-modal-container -->
</div> <!-- cd-user-modal -->


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