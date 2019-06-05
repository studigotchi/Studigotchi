window.onload = function () {
    window.setInterval(function () {
        //TODO query ./data for status updates
        sendRequest("data");
    }, 1000);
};


function clickEat() {
    sendRequest("eat");
}

function clickLearn() {
    sendRequest("learn");
}

function clickSleep() {
    sendRequest("sleep");
}

function sendRequest(urlEnd) {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === request.DONE) {
            let responseObject = JSON.parse(request.responseText);
            jQuery("#life_id").text(responseObject.hearts || "NO LIFE MAN");
            jQuery("#progress_id").text(responseObject.stars || "YOU'RE TOO BAD");
            jQuery("#semester_id").text(responseObject.semester || "NOT ENROLLED");
            jQuery("#time_id").text(responseObject.time || "OVERDUE");
        }
    };

    request.open("GET", "./user/" + urlEnd);
    request.send();
}
