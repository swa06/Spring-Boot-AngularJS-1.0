/**
 * Created by syed.ahmad on 2/15/2017.
 */
//document.getElementById('file').onchange = function() {
$(document).ready(function (){
    $('#file').on('change',function(){
        var test = '';
        var inp = document.getElementById('file');
        for (var i = 0;i<inp.files.length; i++){
            var name = inp.files.item(i).name;
            test = test+'<li>'+name+'</li>';
        }
        document.getElementById("fileListPrint").innerHTML = '<ul>'+test+'</ul>';


    });

    $('#file2').on('change',function(){
        var test = '';
        var inp = document.getElementById('file2');
        for (var i = 0;i<inp.files.length; i++){
            var name = inp.files.item(i).name;
            test = test+'<li>'+name+'</li>';
        }
        document.getElementById("fileListPrintEdit").innerHTML = '<ul>'+test+'</ul>';
    });

});
