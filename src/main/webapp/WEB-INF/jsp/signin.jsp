<%--
  Created by IntelliJ IDEA.
  User: antonsakhno
  Date: 16.04.16
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Авторизация</title>
    <script src="../../resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="../../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../../resources/css/phonebook.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-4 autowindow">
            <form class="form-horizontal" id="signin" action="/j_spring_security_check" method="post">
                <h3>Выполните вход</h3>
                <div class="form-group">
                    <div class="col-sm-4">
                        <label for="j_username" class="control-label">Логин</label>
                    </div>
                    <div class="col-sm-8">
                        <input name="j_username" type="text" class="form-control" id="j_username"
                               placeholder="email">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-4">
                        <label for="j_password" class="control-label">Пароль</label>
                    </div>
                    <div class="col-sm-8">
                        <input name="j_password" type="password" class="form-control" id="j_password"
                               placeholder="password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-6">
                        <a class="btn btn-primary col-sm-12" href="/registration">Регистрация</a>
                    </div>
                    <div class="col-sm-6 rightbutton">
                        <input class="btn btn-primary col-sm-12" type="submit" value="Войти">
                    </div>
                </div>
            </form>
            <c:if test="${param.error!=null}">
                <div class="alert alert-warning">
                        ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                </div>
            </c:if>
            <c:if test="${param.logout!=null}">
                <div class="alert alert-warning">
                    Вы разлогиниись.
                </div>
            </c:if>
            <c:if test="${not empty message}">
                <div class="alert alert-success">
                        ${message}
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
