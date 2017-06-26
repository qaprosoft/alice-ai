var host = "54.245.105.12:8080";
//var host = "localhost:8080";

function downloadFile() {
$.ajax({
        url: "/download",
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


function getOrders() {
	var data = '{' +
    	'"url":"' + $("#url").val() + '"' +
    	', "model":"' + $("#model").val() + '"' +
    	'}';
    	$.ajax({
    		url: 'http://'+host+'/request',
    	    type : "POST",
    	    dataType : 'text',
    	    contentType : 'application/json',
    	    data : data,
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