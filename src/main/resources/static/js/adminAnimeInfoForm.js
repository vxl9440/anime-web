

/*ajax send anime information to server*/
function sendAnimeInfo(requestPath,isSave,afterSuccess,afterFail){
    let typeList = [];
    typeList = getAllSelectedTags(typeList,'#type-area>ul');
    let dataSet = {
        chineseName:$('#anime-CN-name').val(),
        originalName:$('#anime-original-name').val(),
        releasedDate:$('#anime-released-date').val(),
        status:$('#inputGroup-status').val(),
        types:typeList,
        introduction:$('#anime-introduction').val()
    };

    if(isSave){
        dataSet.animeId = $('#anime-id').text();
    }

    $.post({
        url:requestPath,
        data:JSON.stringify(dataSet),
        contentType:"application/json;charset=utf-8",
        beforeSend:function (){
            show_loading('.center-wrapper',$('.spinner-div').last());
        },
        success:function (data){
            hide_loading('.center-wrapper',$('.spinner-div').last());
            let result = data['status'];
            if(result === 1 && isSave){ // 1 for success
                messageAnimation('保存成功');
            }else if(result !== 1 && isSave){
                messageAnimation('Oops 保存失败');
            }else if(result === 1 && !isSave){
                afterSuccess(data);
            }else{
                afterFail(data['message']);
            }
        },
        error:function (){
            hide_loading('.center-wrapper',$('.spinner-div').last());
            if(isSave){
                messageAnimation('Oops 保存失败');
            }else{
                afterFail('Oops 出错了');
            }
        }
    });
}

/************************ data check ****************************/
/* check input value */
function checkInput(){
    if($('#anime-CN-name').val().trim().length === 0) return false;
    if($('#anime-original-name').val().trim().length === 0) return false;
    if($('#anime-released-date').val().trim().length === 0) return false;
    if($('#anime-introduction').val().trim().length === 0) return false;
    let typeList = [];
    typeList = getAllSelectedTags(typeList,'#type-area>ul');
    return typeList.length !== 0;
}

/*change btn property*/
function changeBtnProperties(){
    if(checkInput()){
        $('#form-btn').prop('disabled',false).
        removeClass('btn-secondary').addClass('btn-primary');
    }else{
        $('#form-btn').prop('disabled',true).
        removeClass('btn-primary').addClass('btn-secondary');
    }
}

/* add listener */
function addInputChangeListener(selector){
    $(selector).keyup(function (){
        changeBtnProperties();
    });
}

/* type tag event */
$('#inputGroup-type').change(function (){
    let flag = false;
    let tmp = this.value;
    let selectedTagList = [];
    selectedTagList = getAllSelectedTags(selectedTagList,'#type-area>ul');
    selectedTagList.forEach(function (value){
        if(value === tmp){
            flag = true;
        }
    });
    if(!flag){
        createTypeTag([tmp],changeBtnProperties);
        changeBtnProperties();
    }
});

/* save button activate detect */
addInputChangeListener('#anime-CN-name');
addInputChangeListener('#anime-original-name');
addInputChangeListener('#anime-introduction');

$('#anime-released-date').change(function (){
    changeBtnProperties();
});