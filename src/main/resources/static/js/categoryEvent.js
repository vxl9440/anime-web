// first tier (w > 768) w=210 h=293
// second tier (576 < w <= 768) w=175 h=245
// third tier (400 < w <= 576) w=125 h=175

// window size on change event
function checkWindowWidthSize(){
    let width;
    let height;
    if(window.innerWidth > 768){
        width = 210;
        height = 293;
    }else if(576 < window.innerWidth && window.innerWidth  <= 768){
        width = 175;
        height = 245;
    }else {
        width = 125;
        height = 175;
    }
    $('.anime-cover>a>img').prop('width',width+'').prop('height',height+'');
    $('.anime-information').css('height',height+'px');
}

// 返回当前已选的
function getSelectedCategory(page){
    //year
    let year = $('#category-year>ul>li[class=selected]').text();
    let season = $('#category-season>ul>li[class=selected]').text();
    let type = $('#category-type>ul>li[class=selected]').text();
    let status = $('#category-status>ul>li[class=selected]').text();
    let sort = $('#category-sort>ul>li[class=selected]').text();
    return year+'-'+season+'-'+type+'-'+status+'-'+sort+'-'+page;
}


function addComponentListener(url){

    //category on click
    $('#category-container>li>ul>li').click(function () {
        $(this).parent().children(".selected").removeClass("selected");
        $(this).addClass("selected");
        window.location.href = url+getSelectedCategory(1);
    });


    // pagination
    $('.pagination-container>span:first-of-type').click(function (){ // go to first page
        if($('#page-input').val()!== '1'){
            window.location.href = url+getSelectedCategory(1);
        }
    });

    $('.pagination-container>span:nth-of-type(2)').click(function (){ // go to previous page
        let currentPage = parseInt($('#page-input').val());
        if(currentPage - 1 >= 1){
            window.location.href = url+getSelectedCategory(currentPage - 1);
        }
    });

    $('.pagination-container>span:nth-of-type(5)').click(function () { // go to next page
        let totalPage = parseInt($('.pagination-container>span:nth-of-type(4)').text());
        let currentPage = parseInt($('#page-input').val());
        if(currentPage + 1 <= totalPage){
            window.location.href = url+getSelectedCategory(currentPage + 1);
        }
    });

    $('.pagination-container>span:last-of-type').click(function (){ // go to last page
        let totalPage = parseInt($('.pagination-container>span:nth-of-type(4)').text());
        let currentPage = parseInt($('#page-input').val());
        if(currentPage !== totalPage){
            window.location.href = url+getSelectedCategory(totalPage);
        }
    });


    $('#page-input').keydown(function (e) { // key event
        if(e.keyCode === 13){
            let totalPage = parseInt($('.pagination-container>span:nth-of-type(4)').text());
            let currentPage = parseInt($('#page-input').val());
            if(currentPage >= 1 && currentPage <= totalPage){
                window.location.href = url+getSelectedCategory(currentPage);
            }
        }
    });
}