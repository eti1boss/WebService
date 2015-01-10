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
<html>
<head>
    <title></title>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <!--script type="text/javascript" src="${pageContext.request.contextPath}/js/nailthumb.js"></script-->
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/modernizr.custom.js"></script>

  <!--link href="${pageContext.request.contextPath}/css/nailthumb.css" type="text/css" rel="stylesheet" /-->
  <link href="${pageContext.request.contextPath}/css/default.css" type="text/css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/css/component.css" type="text/css" rel="stylesheet" />
  <style type="text/css" media="screen">
    .nailthumb-container{
    }
    .mySize { width: 400px; height: 300px; }
  </style>

</head>
<body>
<h1>Your Pictures !</h1>
<a href="logout">Logout</a>
<br/>

<ul class="grid cs-style-7">
<c:set var="zindex" value="10000"/>
  <c:forEach items="${file}" var="entry">
    <c:set var="zindex" value="${zindex-1}"/>
  <li style="z-index: ${zindex}">
      <figure>
        <div class="nailthumb-container mySize">
          <img width="20%" src="${pageContext.request.contextPath}<c:out value="${entry.value[0]}"/>" title="<c:out value="${entry.key}"/>" />
        </div>
        <figcaption>
          <h3>${ fn:substringAfter(entry.key, "_") }</h3>
          <a href="${pageContext.request.contextPath}<c:out value="${entry.value[2]}"/>">High</a>
          <a href="${pageContext.request.contextPath}<c:out value="${entry.value[1]}"/>">Medium</a>
          <a href="${pageContext.request.contextPath}<c:out value="${entry.value[0]}"/>">Low</a>
        </figcaption>
      </figure>

    </li>
  </c:forEach>

<!--script type="text/javascript">
  $(document).ready(function() {
    $('.nailthumb-container').nailthumb();
  });
</script-->
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/toucheffects.js"></script>

</body>
</html>
