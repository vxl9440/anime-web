
function validateForm(){
    let animeId = $('#anime-id').val();
    let imgFile = $('#img-file').val();
    if(animeId.trim().length === 0){
        alert('动画ID不能为空');
        return false;
    }
    if(imgFile.length === 0){
        alert('文件不能为空');
        return false;
    }
    return true;
}

function sendSlideInfo(requestUrl,data,before,after,afterSuccess){
    $.post({
        url:requestUrl,
        async:true,
        contentType:false,
        cache:false,
        processData: false,
        data: data,
        beforeSend:function (){
            before();
        },
        success:function (data){
            after();
            if(data['status'] === 1){
                if(afterSuccess !== undefined){
                    afterSuccess();
                }
            }else{
                alert("失败:"+data['message']);
            }
        },
        error:function (){
            after();
            messageAnimation("发生错误");
        }
    });
}

$(document).ready(function () {
    $('#slide-add-btn').click(function () {
        if(validateForm()){
            let formData = new FormData();
            formData.append("animeId",$('#anime-id').val().trim());
            formData.append("file",$('#img-file')[0].files[0]);
            sendSlideInfo("/admin/insertSlide",formData,function (){
                show_loading('body','.spinner-div');
            },function () {
                hide_loading('body','.spinner-div');
            },function () {
                window.location.href = '/admin/adminSlide';
            });
        }
    });

    $('.delete-slide-btn').click(function () {
        if(confirm("确认删除?")){
            let formData = new FormData();
            let animeId = $(this).parent().parent().children('p').children('span')[1].innerText;
            formData.append("animeId",animeId);
            sendSlideInfo("/admin/deleteSlide",formData,function () {
                show_loading('body','.spinner-div');
            },function () {
                hide_loading('body','.spinner-div');
            },function (){
                window.location.href = '/admin/adminSlide';
            });
        }
    });
});