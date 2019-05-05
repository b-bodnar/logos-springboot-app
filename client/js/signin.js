$(document).ready(function () {

    $('#btnLogin').on('click', function () {
        console.log("'click");
        singin();
    });
});


function singin() {

    let userLogin = {
        email: $('#userEmail').val(),
        password: $('#userPassword').val()
    };

    $.ajax({
        url: SERVER_URL + 'auth/signin',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(userLogin),
        complete: function (res) {
            let result = res.responseJSON;

            if (res.status == 200) {
                if (result.token) {
                    localStorage.setItem('auth_token', result.token);
                    window.location.href = 'index.html';
                }
            }
        }
    })
}