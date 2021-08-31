function changePassword(requestUrl){
    let dataSet = {
        "username":"dummyUsername",
        "password":$('#new-password').val(),
        "oldPassword":$('#old-password').val(),
    }

    $.post({
        url:requestUrl,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(dataSet),
        beforeSend:function (){
            show_loading('.main-account-container',$('.spinner-div').last());
        },
        success:function (data){
            hide_loading('.main-account-container',$('.spinner-div').last());
            if(data['status'] === 1){
                alert('修改成功，点击确认重新登录');
                window.location.href = '/logout';
            }else{
                alert('失败:'+data['message']);
            }
        },
        error:function (){
            hide_loading('.main-account-container',$('.spinner-div').last());
            alert("Oops 发生错误");
        }
    });
}

$(document).ready(function () {
    $('#password-change-btn').click(function () {
        let oldPassword = $('#old-password').val();
        let newPassword = $('#new-password').val();
        if(oldPassword.length === 0 || newPassword.length === 0){
            alert('密码不能为空');
            return;
        }
        let reNewPassword = $('#re-new-password').val();
        if(newPassword !== reNewPassword){
            alert('两个新密码不匹配');
            return;
        }
        changePassword('/changePassword');
    });

    $('.like-delete-btn').click(function (){
        if(confirm('确定删除？')){
            let animeId = $(this).parent().parent().attr('aria-label');
            let tmp = this;
            sendLikeInfo("/deleteLike",animeId,function () {
                $(tmp).attr('pointer-events','none');
            },function () {
                $(tmp).attr('pointer-events','auto');
            },function () {
                $(tmp).parent().parent().remove();
            })
        }
    });
});