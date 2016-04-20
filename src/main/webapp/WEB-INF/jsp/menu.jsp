<%--
  Created by IntelliJ IDEA.
  User: antonsakhno
  Date: 19.04.16
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="../../resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="../../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script src="../../resources/js/phonebook.js" type="text/javascript"></script>
    <link href="../../resources/css/phonebook.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<nav class="navbar navbar-default" id="menu">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/home"><span class="glyphicon glyphicon-book" aria-hidden="true"></span>
                 <c:out value="${username}"/></a>
        </div>
        <div class="collapse navbar-collapse navbar-right" id="navbar" >
            <ul class="nav navbar-nav">
                <li><a href="/logout">Выход</a></li>
            </ul>
        </div>
    </div>
</nav>

</body>
</html>
