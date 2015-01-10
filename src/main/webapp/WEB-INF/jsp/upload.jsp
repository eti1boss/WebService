<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <script src="${pageContext.request.contextPath}/js/dropzone.js"></script>
  <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dropzone.css"/>
  <script type="text/javascript">
    Dropzone.autoDiscover = true;
    Dropzone.options.myDrop = {
      paramName: "file",
      maxFilesize: 10,
      maxFiles: 10,
      autoProcessQueue: true,
      uploadMultiple : true,
      parallelUploads : 5
    };
  </script>
</head>
<body>
<div>
  login bar
</div>
<div style="margin-left: 50px; margin-right: 50px">
  <form action="/SampleApp/tester/" class="dropzone" id="myDrop">
  </form>
</div>
</body>
</html>