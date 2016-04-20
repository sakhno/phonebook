<%--
  Created by IntelliJ IDEA.
  User: antonsakhno
  Date: 16.04.16
  Time: 23:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Регистрация</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="../../resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="../../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script src="../../resources/js/phonebook.js" type="text/javascript"></script>
    <link href="../../resources/css/phonebook.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-6 autowindow">
                <form class="form-horizontal" id="registration" action="/registration" method="post">
                    <h3>Регистрация</h3>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <label for="name" class="control-label">Ф.И.О.</label>
                        </div>
                        <div class="col-sm-7">
                            <input name="name" type="text" class="form-control" id="name"
                                   placeholder="Ваши фамилия, имя и отчество">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <label for="login" class="control-label">Логин</label>
                        </div>
                        <div class="col-sm-7">
                            <input name="login" type="text" class="form-control" id="login"
                                   placeholder="Логин">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <label for="password" class="control-label">Пароль</label>
                        </div>
                        <div class="col-sm-7">
                            <input name="password" type="password" class="form-control" id="password"
                                   placeholder="Пароль">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-5">
                            <label for="passwordconfirmation" class="control-label">Подтверждение пароля</label>
                        </div>
                        <div class="col-sm-7">
                            <input name="passwordconfirmation" type="password" class="form-control" id="passwordconfirmation"
                                   placeholder="Подтвердите пароль">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <input name="submit" class="btn btn-primary col-sm-12" type="submit" value="Зарегестрироваться">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div id="messagefield"></div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
