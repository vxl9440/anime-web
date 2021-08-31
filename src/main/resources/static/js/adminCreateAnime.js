
function createSuccess(data){
    $('#anime-info').css('display','none');
    $('#return-btn').text('继续创建');
    $('#return-page').css('display','block');
    $('#return-page>div>h4').text('创建成功');
    symbolDisplay(false);
    $('#return-msg').css('display','none');
    $('#go-edit-btn').css('display','inline').
    attr('href','/admin/adminRewriteAnime/'+data['data']);

    // clear all input
    $('#anime-CN-name').val('');
    $('#anime-original-name').val('');
    $('#anime-released-date').val('');
    $('#anime-introduction').val('');
    $('#inputGroup-status>option:first-child').prop('selected',true);
    $('#type-area').empty();
}

function symbolDisplay(isFail){
    $('#symbol-fail').css('display',isFail?'inline-block':'none');
    $('#symbol-success').css('display',!isFail?'inline-block':'none');
}

function createFail(returnMsg){
    $('#anime-info').css('display','none');
    $('#return-btn').text('返回修改');
    $('#go-edit-btn').css('display','none')
    $('#return-page').css('display','block');
    $('#return-page>div>h4').text('创建失败');
    symbolDisplay(true);
    $('#return-msg').text(returnMsg).css('display','inline-block');
}

$(document).ready(function () {
    $('#form-btn').click(function (){
        sendAnimeInfo('/admin/createAnime',false,createSuccess,createFail);
    });

    $('#return-btn').click(function (){
        $('#anime-info').css('display','block');
        $('#return-page').css('display','none');
    });

    $('#go-edit-btn').click(function () {

    });
});