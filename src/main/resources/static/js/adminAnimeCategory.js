
$(document).ready(function (){

    //window width detect
    checkWindowWidthSize();
    window.addEventListener('resize',function () {
        checkWindowWidthSize();
    });

    addComponentListener("/admin/adminAnimeCategory/");

    //regex for input
    addRegex(document.getElementById('page-input'),/[^0-9]/);
});





