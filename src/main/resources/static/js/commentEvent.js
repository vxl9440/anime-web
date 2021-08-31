
let commentEvent = {}
commentEvent.nextPage = 2;
commentEvent.noMoreComment = false;


function makeCommentComponent(author,date,content){
    let entity = document.createElement('div');
    entity.setAttribute('class','comment-entity');
    let usernameSpan = document.createElement('span');
    usernameSpan.innerText = author+'  ';
    let dateSpan = document.createElement('span');
    dateSpan.innerText = date;
    let contentDiv = document.createElement('div');
    let contentSpan = document.createElement('span');
    contentSpan.innerText = content;
    contentDiv.appendChild(contentSpan);
    entity.appendChild(usernameSpan);
    entity.appendChild(dateSpan);
    entity.appendChild(contentDiv);
    return entity;
}

function appendRequestedComments(data){
    for (let i = 0; i < data.length; i++) {
        $('.comment-display-area').append(makeCommentComponent(data[i]['username'],data[i]['postDate'],data[i]['content']));
    }
}

function sendCommentInfo(requestUrl){
    let dataSet = {
        "animeId":$('#anime-id').text(),
        "content":$('#comment-content').val()
    }

    $.post({
        url:requestUrl,
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(dataSet),
        beforeSend:function (){
            show_loading('.comment-write-area',$('.spinner-div').first());
        },
        success:function (data){
            hide_loading('.comment-write-area',$('.spinner-div').first());
            if(data['status'] === -1){
                alert("评论提交失败");
            }else{
                $('#comment-content').val('');
                $('.no-comment-area').addClass('d-none');
                $('.comment-display-area').prepend(makeCommentComponent(data['data']['username'],
                        data['data']['postDate'],data['data']['content']));
            }
        },
        error:function (){
            hide_loading('.comment-write-area',$('.spinner-div').first());
            alert("出错了");
        }
    });
}

function requestComments(requestUrl){
    let dataSet = {
        "animeId":$('#anime-id').text(),
        "nextPageNumber":commentEvent.nextPage
    }
    // let formData = new FormData();
    // formData.append("animeId",$('#anime-id').text());
    // formData.append("nextPageNumber",commentEvent.nextPage);

    $.post({
        url:requestUrl,
        data: dataSet,
        beforeSend:function (){
            show_loading('.comment-display-wrapper',$('.spinner-div').last());
        },
        success:function (data){
            hide_loading('.comment-display-wrapper',$('.spinner-div').last());
            if(data['status'] === 1){
                if(data['data'].length > 0){
                    appendRequestedComments(data['data']);
                    commentEvent.nextPage++;
                }else{
                    commentEvent.noMoreComment = true;
                    $('.view-more-comment>span').text('没有更多评论了');
                }
            }else{
                alert("评论拉取失败");
            }
        },
        error:function (){
            hide_loading('.comment-display-wrapper',$('.spinner-div').last());
            alert("出错了");
        }
    });
}

$(document).ready(function (){
     $('.view-more-comment>span').click(function (){
         if(!commentEvent.noMoreComment){
             requestComments("/moreComment");
         }
     });

     $('#btn-comment-submit').click(function (){
        if($('#comment-content').val().length > 0){
            sendCommentInfo("/insertComment");
        }else{
            alert("评论不能为空");
        }
     });
});