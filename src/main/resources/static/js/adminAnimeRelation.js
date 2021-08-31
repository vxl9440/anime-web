
function sendRelationInfo(requestUrl,addNewRelation,dataSet,divSelector,spinnerSelector,action){
    $.post({
        url:requestUrl,
        contentType:"application/json;charset=utf-8",
        data:JSON.stringify(dataSet),
        beforeSend:function (){
            show_loading(divSelector,spinnerSelector);
        },
        success:function (data){
            hide_loading(divSelector,spinnerSelector);
            if(data['status'] === 1 && addNewRelation){
                alert('添加成功');
                window.location.href = '/admin/adminAnimeRelation';
            }else if(data['status'] === 1 && !addNewRelation){
                alert('操作成功');
                action();
            }else if(data['status'] !== 1 && addNewRelation){
                alert('添加失败:'+data['message']);
            }else{
                alert('失败:'+data['message']);
            }
        },
        error:function (){
            hide_loading(divSelector,spinnerSelector);
            alert('出错了');
        }
    })
}

$(document).ready(function () {
    $('.relation-edit-btn').click(function (){
        let relationName = $(this).parent().parent().children('h5:eq(0)').children('span')[0].innerText;
        let relationId = $(this).parent().parent().children('h5:eq(1)').children('span')[0].innerText;
        $('#current-relation-id').text(relationId);
        $('#current-relation-name').val(relationName);
    });

    /* add new relation */
    $('#relation-add-btn').click(function (){
        let relationName = $('#relation-name').val().trim();
        if(relationName.length > 0){
            let dataSet = {
                'relationId': 10086,
                'relationName': relationName
            }
            let div = $('#relation-add-modal');
            let spinner = $('#relation-add-modal').children('div').children('div').children('div:last-of-type');
            sendRelationInfo("/admin/createRelation",true,dataSet,div,spinner);
        }else{
            alert('关联名字不能为空');
        }
    });

    /* delete entirely relation */
    $('.relation-delete-btn').click(function (){
       if(confirm('确认删除整个关联?')){
           let dataSet = {
               'relationId': parseInt($(this).parent().parent().children('h5:eq(1)').children('span')[0].innerText),
               'relationName': "dummyName"
           }
           let div = $(this).parent().parent();
           let spinner = $(this).parent().parent().children('div:last-of-type');
           let tmp = this;
           sendRelationInfo("/admin/deleteRelation",false,dataSet,div,spinner,function (){
               $(tmp).parent().parent().remove();
           });
       }
    });

    /* update relation name */
    $('#relation-name-change-btn').click(function (){
        let relationName = $('#current-relation-name').val().trim();
        if(relationName.length > 0){
            let dataSet = {
               'relationId': parseInt($('#current-relation-id').text()),
               'relationName': relationName
            }
            let div = $('#relation-edit-modal');
            let spinner = $('#relation-edit-modal').children('div').children('div').children('div:last-of-type');
            let tmp = this;
            sendRelationInfo("/admin/changeRelationName",false,dataSet,div,spinner,function (){
                // nothing
            });
        } else{
            alert('关联名字不能为空');
        }
    });

    /* add new relation anime */
    $('#relation-anime-add-btn').click(function (){
        let animeId = $('#new-relation-animeId').val().trim();
        if(animeId.length > 0){
            let dataSet = {
                'relationId': parseInt($('#current-relation-id').text()),
                'animeId': animeId
            }
            let div = $('#relation-edit-modal');
            let spinner = $('#relation-edit-modal').children('div').children('div').children('div:last-of-type');
            sendRelationInfo("/admin/createRelationAnime",false,dataSet,div,spinner,function (){
                // nothing
            });
        }else{
            alert('动画ID不能为空');
        }
    });

    /* delete relation anime */
    $('.relation-anime-delete-btn').click(function (){
        if(confirm("确认删除关联动漫?")){
            let dataSet = {
                'animeId':$(this).parent().children('span:eq(0)')[0].innerText,
                'relationId':$(this).parent().parent().parent().children('h5:eq(1)').children('span')[0].innerText
            }
            let div = $(this).parent().parent().parent();
            let spinner = $(this).parent().parent().parent().children('div:last-of-type');
            let tmp = this;
            sendRelationInfo("/admin/deleteRelationAnime",false,dataSet,div,spinner,function (){
                $(tmp).parent().remove();
            });
        }
    });

});