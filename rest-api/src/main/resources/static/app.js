var host = "54.245.105.12:8080";
//var host = "localhost:8080";

function downloadFile(){

$('#loader').show();
 var radio = $('input[name=responseType]:checked').val();
if (radio == 'xml') {
    downloadFileXML();
}else
if (radio == 'json') {
    downloadFileJSON();
}else
if (radio == 'img') {
    downloadFileImage();
}
}


function resetText(){
var curtain = document.getElementById("curtain");
curtain.innerHTML= "<h1></h1>" ;
curtain.style.display = 'none';
document.getElementById("ItemPreview").style.display = 'none';
 }


function downloadFileXML() {
resetText();
$.ajax({
        url: "/downloadXML",
        type: "POST",
        data: new FormData($("#fileUploadForm")[0]),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
	    success: function(response) {
	    $('#loader').hide();
	    var curtain = document.getElementById("curtain");
        curtain.style.display = 'block';
        curtain.innerHTML= "<h1>All is ok!</h1>" ;
	    },
	    error: function(response) {
	    $('#loader').hide();
        var curtain = document.getElementById("curtain");
        curtain.style.display = 'block';
        curtain.innerHTML= "<h1>Can't get data! </h1>" ;
        }
	})
}

function downloadFileJSON() {
$.ajax({
        url: "/downloadJSON",
        type: "POST",
        data: new FormData($("#fileUploadForm")[0]),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
	    success: function(response) {
	    $('#loader').hide();
	    var curtain = document.getElementById("curtain");
        curtain.style.display = 'block';
        curtain.innerHTML= "<h1>All is ok! </h1>" ;
	    },
	     error: function(response) {
	     $('#loader').hide();
         var curtain = document.getElementById("curtain");
         curtain.style.display = 'block';
         curtain.innerHTML= "<h1>Can't get data! </h1>" ;
        }
	})
}

function downloadFileImage() {
$.ajax({
        url: "/downloadImage",
        type: "POST",
        data: new FormData($("#fileUploadForm")[0]),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
	    success: function(data) {
	    $('#loader').hide();
	    var curtain = document.getElementById("curtain");
        curtain.style.display = 'block';
        curtain.innerHTML= "<h1>Output Image</h1>" ;
        document.getElementById("ItemPreview").src = "data:image/jpg;base64," + data;
        document.getElementById("ItemPreview").style.display = 'block';
	    },
	    error: function(response) {
	    $('#loader').hide();
        var curtain = document.getElementById("curtain");
        curtain.style.display = 'block';
        curtain.innerHTML= "<h1>Can't get data! </h1>" ;
        }

	})
}


function AlertFilesize(){

    if(window.ActiveXObject){
        var fso = new ActiveXObject("Scripting.FileSystemObject");
        var filepath = document.getElementById('browse').value;
        var thefile = fso.getFile(filepath);
        var sizeinbytes = thefile.size;
    }else{
        var sizeinbytes = document.getElementById('browse').files[0].size;
    }

    var fSExt = new Array('Bytes', 'KB', 'MB', 'GB');
    fSize = sizeinbytes; i=0;while(fSize>900){fSize/=1024;i++;}

       var size = (Math.round(fSize*100)/100)+' ' + fSExt[i];

    if ((fSExt[i]=="MB" || fSExt[i]=="GB" ) && ((Math.round(fSize*100)/100)>10)) {
    alert ( "File is very large! Change another file!");
    document.getElementById("browse").value='';
    }
}

