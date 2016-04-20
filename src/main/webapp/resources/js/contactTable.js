/**
 * Created by Anton Sakhno <antonsakhno.work@gmail.com> on 17.04.2016
 */
$(document).ready(function () {
    var table = $('#contacttable').DataTable({
        'select': 'single',
        'info': false,
        'columnDefs': [{
            "targets": [2],
            "visible": false,
            "searchable": false
        }]
    });

    //retriving contact from db and filling form
    $('#contacttable tbody').on('click', 'tr', function () {
        resetEditForm();
        $('#deletecontactbutton').parent().removeClass().addClass('col-sm-6').show();
        $('#submitbutton').parent().removeClass().addClass('col-sm-6');
        var data = table.row(this).data();
        $.get("/contact/" + data[2], function (response) {
            $('#contactformwindow').show();
            $('#formlabel').text("Редактирование контакта");
            $('#lastname').val(response.lastName);
            $('#firstname').val(response.firstName);
            $('#middlename').val(response.middleName);
            $('#mobilephone').val(response.mobilePhone);
            $('#homephone').val(response.homePhone);
            $('#address').val(response.address);
            $('#email').val(response.email);
            $('#id').val(response.id);
        });
    });

    //verifying contact and saving to db
    $(document).on('submit', '#contactform', function (event) {
        var $form = $(this);
        $.post("/contact/verify", $form.serialize(), function (response) {
            if (Object.keys(response).length > 0) {
                resetEditForm();
                $.each(response, function (key, value) {
                    $("#" + key).attr("placeholder", value);
                    $("#" + key).parent().addClass("has-error");
                    $("#" + key).parent().append($("<span>").addClass("help-block remove").text(value));
                });
            } else {
                $.post("/contact", $form.serialize(), function (response) {
                    //checking if contact is new or existing
                    var newContact = false;
                    if ($('#id').val() == 0) {
                        newContact = true;
                        $('#id').val(response);
                    }
                    updateContactTable(newContact);
                });
                resetEditForm();
                $('#success').show();
            }
        });
        event.preventDefault();
    });

    //new contact form
    $(document).on('click', '#newcontactbutton', function (event) {
        openNewContactWindow();
    });

    var openNewContactWindow = function () {
        $('#deletecontactbutton').parent().hide();
        $('#success').hide();
        $('#submitbutton').parent().removeClass().addClass('col-sm-12');
        $('#contactformwindow').show();
        $('#contactform').trigger('reset');
        $('#id').val(0);
        $('#formlabel').text("Добавление нового контакта");
    }

    //deleting contact from db and updating view
    $(document).on('click', '#deletecontactbutton', function () {
        $.ajax({
            url: "/contact/" + $('#id').val(),
            type: 'DELETE',
            success: function (result) {
                table.row('#row_' + $('#id').val()).remove().draw();
                $('#contactform').trigger('reset');
                $('#contactformwindow').hide();
                resetEditForm();
            },
            error: function (msg) {
                alert(msg)
            }
        });
    });

    //updating existing table within saved to db contact, true if new contact, false if after editing existing
    var updateContactTable = function (newContact) {
        var $contactId = $("#id").val();
        var $contactName = $("#lastname").val() + " " + $("#firstname").val();
        var $contactPhone = $("#mobilephone").val();
        if (newContact) {
            var $newRow = table.row.add([
                $contactName,
                $contactPhone,
                $contactId
            ]).draw();
        } else {
            $('#contactname_' + $contactId).html($contactName);
            $('#contactphone_' + $contactId).html($contactPhone);
        }
    };

    var resetEditForm = function () {
        $('#success').hide();
        $(".remove").remove();
        $(".has-error").removeClass("has-error");
        $('#lastname').attr("placeholder", '');
        $('#firstname').attr("placeholder", '');
        $('#middlename').attr("placeholder", '');
        $('#mobilephone').attr("placeholder", '');
        $('#homephone').attr("placeholder", '');
        $('#address').attr("placeholder", '');
        $('#email').attr("placeholder", '');
    }
});