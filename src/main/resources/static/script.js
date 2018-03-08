var file;

function loadFile()
{
    var filesSelected = document.getElementById("inputFile").files;
    if (filesSelected.length > 0)
    {
        var fileToLoad = filesSelected[0];
        file = fileToLoad;
        if (fileToLoad.type.match("image.*"))
        {
            var fileReader = new FileReader();
            fileReader.onload = fileLoadedEvent =>
                document.getElementById("targetImg").src = fileLoadedEvent.target.result;
            fileReader.readAsDataURL(fileToLoad);
        }
    }
}

function apply() {
    var formData = new FormData();
    formData.append('file', file);
    
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
        response.json().then(function(data) {
            alert(data);
        });
    });
}