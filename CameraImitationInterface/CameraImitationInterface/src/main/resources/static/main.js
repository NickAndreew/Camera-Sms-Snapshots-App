const Http = new XMLHttpRequest();

function startSending(){
    var url = "http://localhost:8080/startSending"
    Http.open("GET", url);
    Http.setRequestHeader("Access-Control-Allow-Origin", "http://localhost:8181/");

    Http.send();
    console.log("Sending has started!");
}