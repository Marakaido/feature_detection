var file;

function loadFile()
{
    var filesSelected = document.getElementById("inputFile").files;
    if (filesSelected.length > 0)
    {
        fileToLoad = filesSelected[0];
        if (fileToLoad.type.match("image.*"))
        {
            var fileReader = new FileReader();
            fileReader.onload = fileLoadedEvent => {
                var img = new Image();
                img.onload = function(){
                    var canvas = document.getElementById("targetImg");
                    canvas.style.width = img.width;
                    canvas.style.height = img.height;
                    canvas.width = img.width;
                    canvas.height = img.height;
                    canvas.getContext("2d").drawImage(img, 0, 0);
                }
                img.src = fileLoadedEvent.target.result;
            }
            fileReader.readAsDataURL(fileToLoad);
        }
    }
}

function apply() {
    var formData = new FormData();
    formData.append('file', fileToLoad);
    
    fetch("harris", {
        body: formData, // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, *omit
        headers: {
            'Accept': 'application/json'
        },
        method: 'POST', // *GET, PUT, DELETE, etc.
        mode: 'same-origin', // no-cors, *same-origin
        redirect: 'follow', // *manual, error
        referrer: 'no-referrer', // *client
    })
    .then(function(response) {
        response.json().then(displayCorners);
    });
}

function displayCorners(data) {
    var canvas = document.getElementById("targetImg");
    var ctx = canvas.getContext("2d");
    ctx.fillStyle = "#FF0000"; 
    for (let i = 0; i < data.length; i+=2) {
        ctx.fillRect(data[i] - 5,data[i+1] - 5,10,10); 
    }
}