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
  <div id="cd-logo"><a href="#0"><img style="width:330px;margin-top:-20px" src="${pageContext.request.contextPath}/lib/login/img/cuploud.png" alt="Logo"></a></div>

    <nav class="open main-nav">
        <ul>


            <c:choose>
                <c:when test="${empty user}">
                    <li><a class="cd-signin" id="pic" href="upload">Upload now !</a></li>
                    <li><a class="open cd-signin" href="#0">Sign in</a></li>
                    <li><a class="open cd-signup" href="#0">Sign up</a></li>
                </c:when>
                <c:otherwise>
                    <li><span style="color: white; ">${user}</span></li>
                    <li><a class="cd-signin" id="pic" href="upload">Upload now !</a></li>
                    <c:if test="${admin}">
                        <li><a class="cd-signin" href="admin">Admin</a></li>
                    </c:if>
                    <li><a class="cd-signup" href="logout">Logout</a></li>
                </c:otherwise>
            </c:choose>

        </ul>
    </nav>
</header>

<div style="margin-top: 150px">
${message}
</div>


<%@include file="/WEB-INF/modal.html" %>

</body>
</html>
