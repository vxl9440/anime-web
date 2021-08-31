
function formValidate(isUpdate){
    if(isUpdate){
        let oldPassword = $('#old-password').val();
        let newPassword = $('#new-password').val();
        if(oldPassword.length < 6 || newPassword.length < 6){
            alert('密码长度不得小于6');
            return false;
        }
        let reNewPassword = $('#re-new-password').val();
        if(newPassword !== reNewPassword){
            alert('两个新密码不相同');
            return false;
        }
    }else{
        let account = $('#username-create').val();
        let password = $('#password-create').val();
        if(account.length < 6 || password.length < 6){
            alert('账号和密码长度不得小于6');
            return false;
        }
        let rePassword = $('#re-password-create').val();
        if(rePassword !== password){
            alert('两个新密码不相同');
            return false;
        }
    }
    return true;
}

function createOrUpdateAccountInfo(requestUrl,isUpdate){
    let dataSet = {};

    if(isUpdate){
        dataSet['username'] = 'dummyUsername';
        dataSet['password'] = $('#new-password').val();
        dataSet['oldPassword'] = $('#old-password').val();
    }else{
        dataSet['username'] = $('#username-create').val();
        dataSet['password'] = $('#password-create').val();
        // dataSet['oldPassword'] = 'dummyPassword';
    }

    $.post({
        url:requestUrl,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(dataSet),
        beforeSend:function (){
            show_loading('.center-wrapper',$('.spinner-div').last());
        },
        success:function (data){
            hide_loading('.center-wrapper',$('.spinner-div').last());
            if(data['status'] === 1 && isUpdate){
                alert("修改成功,请重新登录");
                window.location.href = '/admin/adminLogin';
            }else if(data['status'] === 1){
                alert("创建成功");
                $('#username-create').val('');
                $('#password-create').val('');
                $('#re-password-create').val('');
            }else if(data['status'] === -1 && isUpdate){
                alert("修改失败:"+data['message']);
            }else{
                alert("创建失败:"+data['message']);
            }
        },
        error:function (){
            hide_loading('.center-wrapper',$('.spinner-div').last());
            alert("Oops 发生错误");
        }
    });
}

$(document).ready(function () {
    $('#btn-account-change').click(function (){
       if(formValidate(true)){
           createOrUpdateAccountInfo("/admin/updateAccount",true);
       }
    });

    $('#btn-account-create').click(function (){
        if(formValidate(false)){
            createOrUpdateAccountInfo("/admin/createAccount",false);
        }
    });
});