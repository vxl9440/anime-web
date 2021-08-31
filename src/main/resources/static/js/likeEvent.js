
function sendLikeInfo(requestUrl,animeId,before,after,afterSuccess){
    $.post({
        url:requestUrl,
        data:{
            "animeId":animeId
        },
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
                alert('失败:'+data['message']);
            }
        },
        error: function (){
            after();
            alert("Oops 发送错误");
        }
    });
}