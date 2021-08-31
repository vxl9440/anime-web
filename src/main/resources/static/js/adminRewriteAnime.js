
function customizeShowOption(info,cover,video){
    $('#anime-info').css('display',info?'block':'none');
    $('#anime-cover').css('display',cover?'block':'none');
    $('#anime-video').css('display',video?'block':'none');
}

function videoUploadValidate(){
    $(".video-submit-msg").css('display','none');
    if($('#video-title').val().length === 0){
        $(".video-submit-msg:eq(0)").css('display','block');
        return false;
    }
    if($('#video-episode').val().length === 0){
        $(".video-submit-msg:eq(1)").css('display','block');
        return false;
    }
    if($('#video-file').val().length === 0){
        $(".video-submit-msg:eq(2)").css('display','block');
        return false;
    }
    return true;
}

function clearVideoUploadModal(){
    $('#video-file').val('');
    $('#video-title').val('');
    $('#video-episode').val('');
    $('#video-path').text('');
    $('.progress-bar').css('width','0').
    text('0').
    prop('aria-valuenow',0);
    $('.video-submit-msg').css('display','none');
}

function uploadCover(requestUrl){
    let formData = new FormData();
    formData.append("animeId",$('#anime-id').text());
    formData.append("file",$('#cover-file')[0].files[0]);

    $.post({
        url:requestUrl,
        async:true,
        contentType:false,
        cache:false,
        processData: false,
        data: formData,
        beforeSend:function (){
            show_loading('.center-wrapper',$('.spinner-div').last());
        },
        success:function (data){
            hide_loading('.center-wrapper',$('.spinner-div').last());
            if(data['status'] === -1){
                messageAnimation("上传失败: "+data['message']);
            }else{
                messageAnimation("上传成功");
                // display uploaded image
                $('#current-cover').attr('src',window.URL.createObjectURL($('#cover-file')[0].files[0]));

            }
        },
        error:function (){
            hide_loading('.center-wrapper',$('.spinner-div').last());
            messageAnimation("发生错误");
        }
    });
}

function uploadVideoForm(requestUrl){

    let formData = new FormData();
    formData.append("episodeTitle",$('#video-title').val());
    formData.append("episodeNumber",$('#video-episode').val());
    formData.append("animeId",$('#anime-id').text());
    formData.append("file",$('#video-file')[0].files[0]);


    $.ajax({
        type:'post',
        url:requestUrl,
        async:true,
        contentType:false,
        cache:false,
        processData: false,
        data:formData,
        xhr: function () { // progress bar update
            let myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) {
                myXhr.upload.addEventListener('progress', function (e) {
                    let percentage = Math.ceil(e.loaded / e.total * 100);
                    $('.progress-bar').css('width',percentage+'%').
                    text(percentage+'%').
                    prop('aria-valuenow',percentage);
                }, false);
            }
            return myXhr;
        },
        beforeSend:function (){
            $('#modal-content').css('pointer-events','none').css('opacity','0.7');
        },
        success:function (data){
            $('#modal-content').css('pointer-events','auto').css('opacity','1.0');
            if(data['status']){
                alert("上传成功");
                clearVideoUploadModal();
            }else{
                alert("上传失败:"+data['message']);
            }
        },
        error:function (){
            $('#modal-content').css('pointer-events','auto').css('opacity','1.0');
            alert("Oops 发生错误");
        }
    });
}

function updateOrDeleteVideo(requestUrl,isUpdate,selector){
    let md5 = $(selector).parent().parent().children('p:eq(0)').children('span')[0].innerText;
    let videoType = $(selector).parent().parent().children('p:eq(1)').children('span')[0].innerText;
    let episodeTitle = $(selector).parent().parent().children().children('input')[0].value;
    let episodeNumber = $(selector).parent().parent().children().children('input')[1].value;
    if(isUpdate){
        if(episodeTitle.length === 0 || episodeNumber.length === 0){
            alert("标题和集数不能为空");
            return;
        }
    }else{
        episodeTitle = "NULL";
        episodeNumber = "NULL";
    }

    let divSelector = $(selector).parent().parent()[0];
    let spinnerSelector = $(selector).parent().parent().children('div:last-of-type')[0];

    let dataSet = {
        "animeId":$('#anime-id').text(),
        "md5":md5,
        "videoType":videoType,
        "episodeTitle":episodeTitle,
        "episodeNumber":episodeNumber
    }

    $.post({
        url:requestUrl,
        contentType:"application/json;charset=utf-8",
        data:JSON.stringify(dataSet),
        beforeSend:function (){
            show_loading(divSelector,spinnerSelector);
        },
        success:function (data){
            hide_loading(divSelector,spinnerSelector);
            if(data['status'] === 1 && isUpdate){
                alert("更新成功");
            }else if(data['status'] === 1 && !isUpdate){
                $(selector).parent().parent().remove();
                alert("删除成功");
            }else if(data['status'] === -1 && isUpdate){
                alert("更新失败:"+data['message']);
            }else{
                alert("删除失败:"+data['message']);
            }
        },
        error:function (){
            hide_loading(divSelector,spinnerSelector);
            alert("发生错误");
        }
    });
}

$(document).ready(function () {
    /*nav display control*/
    $('.nav-manage').click(function (){
        $('.nav-manage').removeClass('active');
        this.classList.add('active');
        let id = this.id;

        if(id === 'info-nav'){
            customizeShowOption(true,false,false);
        }else if(id === 'cover-nav'){
            customizeShowOption(false,true,false);
        }else{
            customizeShowOption(false,false,true);
        }
    });

    $('#type-area>ul>li>span').click(function (){
        $(this).parent().remove();
    });

    //anime status lock
    $('#lock-status').click(function () {
       let currentStatus = this.getAttribute("aria-label");
       let dataSet = {
           'animeId': $('#anime-id').text()
       }
       if(currentStatus === 'on'){
           dataSet['lockStatus'] = 0;
       }else{
           dataSet['lockStatus'] = 1;
       }

       let tmp = this;
       $.post({
           url: "/admin/updateAnimeLockStatus",
           data: dataSet,
           beforeSend:function (){
               $(tmp).attr('disabled',true);
           },
           success:function (data){
               $(tmp).attr('disabled',false);
               if(data['status'] === 1){
                   if(dataSet['lockStatus'] === 1){
                       alert('开启成功');
                       $(tmp).text('点击关闭').attr('aria-label','on');
                   }else{
                       alert('关闭成功');
                       $(tmp).text('点击开启').attr('aria-label','off');
                   }
               }else{
                   alert('修改失败');
               }
           },
           error:function (){
               $(tmp).attr('disabled',false);
               alert('Oops 发送错误');
           }
       });
    });

    //anime info upload
    $('#form-btn').click(function (){
        sendAnimeInfo('/admin/updateAnimeInfo',true);
    });

    //anime delete
    $('#anime-delete-btn').click(function (){
        if(confirm("确认删除该动漫？")){
            window.location.href = "/admin/deleteAnime/"+$('#anime-id').text();
        }
    });

    //cover upload
    $('#cover-file').change(function (){
        let filePath = $(this).val();
        console.log(filePath);
        if(filePath !== "" && filePath.length > 0){
            uploadCover("/admin/uploadCover");
        }
    });

    // video upload
    $('#btn-video-upload').click(function (){
        if(videoUploadValidate()){
            uploadVideoForm('/admin/uploadVideo')
        }
    });


    /* uploaded video and delete event*/
    $('.btn-edit-save').click(function (){
        let label = this.getAttribute('aria-label')
       if(label === "edit"){
           $(this).parent().parent().children().children('input').attr("disabled",false);
           this.setAttribute('aria-label','save');
           $(this).text('保存');
       }else{
           updateOrDeleteVideo("/admin/updateVideoInfo",true,this);
           this.setAttribute('aria-label','edit');
           $(this).parent().parent().children().children('input').attr("disabled",true);
           $(this).text('编辑');
       }
    });

    $('.btn-delete').click(function (){
        if(confirm("确定要删除吗？")){
            updateOrDeleteVideo("/admin/deleteVideo",false,this);
        }
    });


    $('#video-edit-modal').on('hidden.bs.modal',function (e){
        // clearVideoUploadModal();
    });

});


