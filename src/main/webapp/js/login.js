/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function hsRestUrl() {
    return "/aims/rest/";
}
$('#login-form').submit(function (event) {
    event.preventDefault();

    const loginUrl = hsRestUrl() + 'auth/login';
    const loginData = {
        email: $('#login-form input[name="username"]').val(),
        password: $('#login-form input[name="password"]').val()
    };

    $.ajax({
        method: 'POST',
        url: loginUrl,
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(loginData),
        success: function (response) {
            window.location.href(response);
        },
        error: function (error) {
            // clear fields
            $('#login-form input[name="password"]').val('');
            console.warn(error);
        }
    });
});

