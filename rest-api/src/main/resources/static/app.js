var host = "54.245.105.12:8080";
//var host = "localhost:8080";

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

       displayInfo(response);

	    },
	     error: function(response) {
        alert("Can't get data!");
            }
	})
}

function displayInfo(response) {
var obj =JSON.parse(response);

       var curtain1 = document.getElementById("curtain1");
       curtain1.style.display = 'block';
       curtain1.innerHTML= "<a href='"+obj.input_image+"'>Input image</a>" ;
       var curtain2 = document.getElementById("curtain2");
       curtain2.style.display = 'block';
       curtain2.innerHTML= "<a href='"+obj.output_image+"'>Output image</a>";
       var curtain3 = document.getElementById("curtain3");
       curtain3.style.display = 'block';
       curtain3.innerHTML= "<a href='"+obj.metadata+"'>Metadata</a>";
}