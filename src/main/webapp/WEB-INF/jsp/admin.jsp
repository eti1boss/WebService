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


<%@include file="/WEB-INF/modal.html" %>


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
