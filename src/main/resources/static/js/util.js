


/******************* image **********************/
function verifyPicFileSize(file,maxSize,minSize) {
    if (file.size > maxSize) {
        alert("文件大小不能大于15MB！");
        return false;
    } else if (file.size <= minSize) {
        alert("文件大小不能为0M！");
        return false;
    } else{
        return true;
    }
}

/*********************** tag creation ***********************/
function createTypeTag(tagList,closeEvent){
    tagList.forEach(function (tag){
        let li = document.createElement('li');
        li.innerText = tag;
        let xMark = document.createElement('span');
        xMark.innerText = 'x';
        xMark.onclick = function (){
            li.parentElement.removeChild(li);
            if(closeEvent !== undefined) closeEvent();
        };
        li.appendChild(xMark)
        $('#type-area>ul').append(li);
    });
}


function getAllSelectedTags(selectTagsList,selector){
    let allTags = $(selector).children();
    for(let i = 0;i < allTags.length;i++){
        selectTagsList.push(allTags[i].innerText.
        replaceAll('x','').trim());
    }
    return selectTagsList;
}

/***************** input limit *******************/
function addRegex(element,regex){
    element.oninput = function(){
        element.value= element.value.replace(regex,'');
    }
}


/***************** data loading *******************/
function show_loading(divSelector,spinnerSelector){
    $(divSelector).css('pointer-events','none').css("opacity","0.6");
    $(spinnerSelector).css("display","block");
}

function hide_loading(divSelector,spinnerSelector){
    $(divSelector).css('pointer-events','auto').css("opacity","1.0");
    $(spinnerSelector).css("display","none");
}

/***************** message animation *******************/
function messageAnimation(message){
    $('#submit-msg').text(message).css('display','block');
    let t = setInterval(function (){
        let current_opacity = parseFloat($('#submit-msg').css('opacity'));
        current_opacity -= 0.1;
        if(current_opacity <= 0.00000001){
            clearInterval(t);
            $("#submit-msg").css("display","none").css("opacity","1.0");
        }else{
            $("#submit-msg").css("opacity",current_opacity+"");
        }
    },250)
}

