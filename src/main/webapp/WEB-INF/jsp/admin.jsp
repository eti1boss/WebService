<%--
  Created by IntelliJ IDEA.
  User: Bob
  Date: 09/01/2015
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title></title>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <!--script type="text/javascript" src="${pageContext.request.contextPath}/js/nailthumb.js"></script-->
  <script type="text/javascript" src="${pageContext.request.contextPath}/lib/caption/js/modernizr.custom.js"></script>

  <!--link href="${pageContext.request.contextPath}/css/nailthumb.css" type="text/css" rel="stylesheet" /-->
  <link href="${pageContext.request.contextPath}/lib/caption/css/default.css" type="text/css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/lib/caption/css/component.css" type="text/css" rel="stylesheet" />

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/login/css/reset.css"/>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/lib/login/css/style.css"/>
  <script src="${pageContext.request.contextPath}/lib/login/js/main.js"></script>
  <script src="${pageContext.request.contextPath}/lib/login/js/modernizr.js"></script>

  <script type="application/javascript" src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
  <script type="application/javascript" src="${pageContext.request.contextPath}/js/core-min.js"></script>
  <script type="application/javascript" src="${pageContext.request.contextPath}/js/enc-utf16-min.js"></script>
  <script type="application/javascript" src="${pageContext.request.contextPath}/js/enc-base64-min.js"></script>
  <script type="application/javascript" src="${pageContext.request.contextPath}/js/hmac-sha1.js"></script>

  <style type="text/css" media="screen">
    .nailthumb-container{
    }
    .mySize { width: 400px; height: 300px; }
  </style>

</head>
<body>

<header role="banner">
  <div id="cd-logo"><a href="#0"><img src="${pageContext.request.contextPath}/lib/login/img/cd-logo.svg" alt="Logo"></a></div>

  <nav class="open main-nav">
    <ul>

          <li><span style="color: white; ">${user}</span></li>
      <li><a class="cd-signin" id="pic" href="upload">Upload now !</a></li>
      <li><a class="cd-signin" id="pic" href="pictures">Your pictures</a></li>
          <li><a class="cd-signup" href="logout">Logout</a></li>


    </ul>
  </nav>
</header>

<form id="test" action="/SampleApp/tester/delete" method="get">
  <input type="hidden" name="pictureName" value=""/>
  <input type="hidden" name="admin" value="true"/>
</form>

<div style="position : absolute;margin-top: 150px">
  <c:forEach items="${file}" var="lvl1">
    <h1><a onclick="deletePic('/uploads/${lvl1.key}')" href="#">${lvl1.key}</a></h1>
      <c:forEach items="${lvl1.value}" var="lvl2">
        <h2>&nbsp;&nbsp;<a onclick="deletePic('/uploads/${lvl1.key}/${lvl2.key}')" href="#">${lvl2.key}</a></h2>
          <c:forEach items="${lvl2.value}" var="lvl3">
            <c:set var="zindex" value="/uploads/${lvl1.key}/${lvl2.key}/${lvl3}"/>
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="deletePic('${zindex}')" href="#"><span style="color:dodgerblue" onmouseover="show('${pageContext.request.contextPath}/${zindex}/050_${ fn:substringAfter(lvl3, '_') }')">${lvl3}</span></a></h3>
          </c:forEach>
      </c:forEach>
  </c:forEach>
</div>

<img id="img" src="" style="max-width:800px; max-height:800px; position: fixed; top: 200px;margin-left: 800px"/>

<script type="application/javascript">
  function show(path) {
    $("#img").prop('src',path);
//    alert(path);
  }
</script>

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
        <form class="cd-form">

          <p class="fieldset">
            <label class="image-replace cd-email" for="signup-email">E-mail</label>
            <input class="full-width has-padding has-border" id="signup-email" type="email" placeholder="E-mail">
            <span class="cd-error-message">Error message here!</span>
          </p>

          <p class="fieldset">
            <label class="image-replace cd-password" for="signup-password">Password</label>
            <input class="full-width has-padding has-border" id="signup-password" type="text"  placeholder="Password">
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


<!--script type="text/javascript">
  $(document).ready(function() {
    $('.nailthumb-container').nailthumb();
  });
</script-->
  <script>
    function deletePic(name){
      //alert(name);
      $("input[name=pictureName]").val(name);
      $("#test").submit();
    }
  </script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/lib/caption/js/toucheffects.js"></script>

</body>
</html>
