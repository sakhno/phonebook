/**
 * Created by Anton Sakhno <antonsakhno.work@gmail.com> on 17.04.2016
 */
$(document).on("submit", "#registration", function (event) {
    var $form = $(this);
    $.ajax({
        type: "POST",
        url:"/registration",
        data: $form.serialize(),
        success: function () {
            window.location.href = '/registration/success';
        },
        error: function (jqXHR, textStatus, errorThrown) {
            var responseJson = jQuery.parseJSON(jqXHR.responseText);
            $(".remove").remove();
            $(".has-error").removeClass("has-error");
            $.each(responseJson, function (key, value) {
                $("#" + key).attr("placeholder", value);
                $("#" + key).parent().addClass("has-error");
                $("#" + key).parent().append($("<span>").addClass("help-block remove").text(value));
            });
        }
    });
    event.preventDefault();
});
