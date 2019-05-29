function clickEat() {
    //alert("Eating!!");

    let request = new XMLHttpRequest();

    request.onreadystatechange = function() {
        if(request.readyState === request.DONE) {
            let parse = JSON.parse(request.responseText);
            alert(parse.name);
        }
    };

    request.open("GET", "./user");
    request.send();
}

function clickLearn() {
    alert("Learning!!");
}

function clickSleep() {
    alert("Sleeping");
}
