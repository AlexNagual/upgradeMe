/**
 * Created by Alexander on 06.12.2017.
 */

function connect(json) {
    $.ajax ({
        type: 'POST',
        contentType: 'application/json',
        url: '/',
        data: JSON.stringify(json),
        datatype: 'json',
        success: function (data) {
            //alert("Hello, " + data["photos"].length);
            changeValues(data);
        }
    });
}

function setUrl() {
    var jsonObject = new Object();
    jsonObject.url = document.getElementById("urlProfile").value;
    connect(jsonObject)
}

function changeValues(data) {
    document.getElementById("username").innerHTML = data.username;
    document.getElementById("userId").innerHTML = data.userId;
    document.getElementById("numberOfPhotos").innerHTML = data.photos.length;
    //alert(data.tags);
    tags = "";
    categories = "";
    for (i in data.tags){
        tags += "<p>" + data.photoId[i] + " : " + data.tags[i] + " - " + "<b>" + data.categories[i] + "</b>" +"</p>";
    }
    document.getElementById("tags").innerHTML = tags;
    //document.getElementById("categories").innerHTML = categories;
}