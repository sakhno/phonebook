<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Контакты</title>
    <jsp:include page="/WEB-INF/jsp/menu.jsp"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="../../resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="../../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script src="../../resources/js/phonebook.js" type="text/javascript"></script>
    <link href="../../resources/css/phonebook.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="https://cdn.datatables.net/t/bs/dt-1.10.11,se-1.1.2/datatables.min.css"/>
    <script type="text/javascript" src="https://cdn.datatables.net/t/bs/dt-1.10.11,se-1.1.2/datatables.min.js"></script>
    <script type="text/javascript" src="../../resources/js/contactTable.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-5" style="margin-top: 20px">
            <table class="table table-striped table-bordered" id="contacttable">
                <thead>
                <tr>
                    <th class="contactname col-sm-7">Ф.И.О.</th>
                    <th class="contactphone col-sm-5">Номер телефона</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="contact" items="${contacts}">
                    <tr class="clickable-row" id="${'row_'.concat(contact.id)}">
                        <td id="${'contactname_'.concat(contact.id)}">${contact.lastName} ${contact.firstName}</td>
                        <td id="${'contactphone_'.concat(contact.id)}">${contact.mobilePhone}</td>
                        <td>${contact.id}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-sm-5">
            <a class="btn btn-success col-sm-4" id="newcontactbutton" style="margin: 20px 0 0 20px">Добавить контакт</a>
            <div class="col-sm-12 autowindow" style="display: none" id="contactformwindow">
                <form class="form-horizontal" id="contactform">
                    <h3 id="formlabel"></h3>
                    <div class="alert alert-success" role="alert" style="display: none" id="success">
                        Контакт успешно сохранен
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <label class="control-label">Фамилия</label>
                        </div>
                        <div class="col-sm-8">
                            <input name="lastname" class="form-control" id="lastname">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <label class="control-label">Имя</label>
                        </div>
                        <div class="col-sm-8">
                            <input name="firstname" class="form-control" id="firstname">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <label class="control-label">Отчество</label>
                        </div>
                        <div class="col-sm-8">
                            <input name="middlename" class="form-control" id="middlename">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <label class="control-label">Моб. телефон</label>
                        </div>
                        <div class="col-sm-5">
                            <input name="mobilephone" class="form-control" id="mobilephone">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <label class="control-label">Дом. телефон</label>
                        </div>
                        <div class="col-sm-8">
                            <input name="homephone" class="form-control" id="homephone">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <label class="control-label">Адрес</label>
                        </div>
                        <div class="col-sm-8">
                            <input name="address" class="form-control" id="address">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4">
                            <label class="control-label">Email</label>
                        </div>
                        <div class="col-sm-8">
                            <input name="email" class="form-control" id="email">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-6">
                            <a class="btn btn-danger col-sm-12" id="deletecontactbutton">Удалить</a>
                        </div>
                        <div class="col-sm-6">
                            <input name="submit" class="btn btn-primary col-sm-12" type="submit" id="submitbutton"
                                   value="Сохранить">
                        </div>
                    </div>
                    <input type="hidden" name="id" id="id">
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>