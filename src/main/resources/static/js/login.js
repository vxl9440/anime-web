let login = {};
login.mode = 0;

function sendAccountInfo(){
    let dataSet = {
        "username": $('#username').val(),
        "password": $('#password').val(),
        "oldPassword":'dummyPassword'
    }
    $.post({
        url: "/signup",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(dataSet),
        beforeSend: function () {
            show_loading('form',$('.spinner-div').last());
        },
        success: function (data) {
            hide_loading($('form')[0],$('.spinner-div').last());
            if (data['status'] === -1) {
                alert(data['message']);
            } else {
                alert("注册成功");
                window.location.href = '/login';
            }

        },
        error: function () {
            hide_loading('form',$('.spinner-div').last())
            alert("出错了");
        }
    });
}

$(document).ready(function () {
    $('#switch-mode').click(function () {
        if(login.mode === 0){
            $(this).text("点击登录");
            $('#re-password-container').removeClass('d-none');
            $('#re-password').attr('required',true);
            $('#remember-me-container').addClass('d-none');
            $('form>p:first-of-type').text("注册");
            $('#btn-account-submit').val('注册').attr('type','button');

            login.mode = 1;
        }else{
            $(this).text("没有账号?点击注册");
            $('#re-password-container').addClass('d-none');
            $('#re-password').attr('required',false).val('');
            $('#remember-me-container').removeClass('d-none');
            $('form>p:first-of-type').text("登录");
            $('#btn-account-submit').val('登录').attr('type','submit');
            login.mode = 0;
        }
    });

    $('#btn-account-submit').click(function (){
        if(login.mode === 1){
            let username = $('#username').val();
            let password = $('#password').val();
            let rePassword = $('#re-password').val();
            if(username.length < 6 || password.length < 6){
                alert("账号密码长度在6-20之间");
                return;
            }
            if(password !== rePassword){
                alert("两个密码不相同");
                return;
            }
            sendAccountInfo();
        }
    });

});