var host = "54.245.105.12:8080";
//var host = "localhost:8080";

function downloadFile(){
 var radio = $('input[name=responseType]:checked').val();
if (radio == 'xml') {
    downloadFileXML();
}else
if (radio == 'json') {
    downloadFileJSON();
}else
downloadFileImage();

}


function resetText(){
var curtain = document.getElementById("curtain");
curtain.innerHTML= "<h1></h1>" ;
curtain.style.display = 'none';
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
	    var curtain = document.getElementById("curtain");
                   curtain.style.display = 'block';
                   curtain.innerHTML= "<h1>All is ok!</h1>" ;

	    },
	     error: function(response) {
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
	    var curtain = document.getElementById("curtain");
                   curtain.style.display = 'block';
                   curtain.innerHTML= "<h1>All is ok! </h1>" ;

	    },
	     error: function(response) {
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
	    var curtain = document.getElementById("curtain");
                   curtain.style.display = 'block';
                   curtain.innerHTML= "<h1>All is ok! </h1>" ;





	    },
	     error: function(response) {
                var curtain = document.getElementById("curtain");
                   curtain.style.display = 'block';
                   curtain.innerHTML= "<h1>Can't get data! </h1>" ;
        }
	})
}
