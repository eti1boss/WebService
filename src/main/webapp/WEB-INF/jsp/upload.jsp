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
          <c:if test="${admin}">
            <li><a class="cd-signin" href="admin">Admin</a></li>
          </c:if>
          <li><a class="cd-signup" href="logout">Logout</a></li>
        </c:otherwise>
      </c:choose>



    </ul>
  </nav>
</header>
<div style="margin-left: 50px; margin-right: 50px; margin-top: 100px">
  <form action="/cuploud/upload" class="dropzone" id="myDrop">
  </form>
</div>

<%@include file="/WEB-INF/modal.html" %>

</body>
</html>