function clickEat() {
    let request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (request.readyState === request.DONE) {
            let parse = JSON.parse(request.responseText);
            alert(parse.name);
        }
    };

    request.open("GET", "./user/eat");
    request.send();
}

function clickLearn() {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === request.DONE) {
            let responseObject = JSON.parse(request.responseText);
            alert(responseObject.name);
        }
    };

    request.open("GET", "./user/learn");
    request.send();
}

function clickSleep() {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === request.DONE) {
            let responseObject = JSON.parse(request.responseText);
            alert(responseObject.name);
        }
    };

    request.open("GET", "./user/sleep");
    request.send();
}

function handleResult(request) {
    if (request.readyState === request.DONE) {
        let responseObject = JSON.parse(request.responseText);
        alert(responseObject.name);
    }
}
