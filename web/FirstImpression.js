let isEating = false;

window.onload = function () {
    window.setInterval(function () {
        sendRequest("data");
    }, 1000);
};


function clickEat() {
    isEating = true;
    jQuery(".button-eat").attr("disabled", "");
    sendRequest("eat");
    window.setTimeout(function () {
        isEating = false;
    }, 5000);
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

            let status = responseObject.status || "JUST_BE";
            let image = jQuery("#student_bild");
            let learnButton = jQuery(".button-learn");
            let sleepButton = jQuery(".button-sleep");
            let eatButton = jQuery(".button-eat");

            if (isEating) {
                image.attr("src", "Eat.gif");
                learnButton.attr("disabled", "");
                sleepButton.attr("disabled", "");
                eatButton.attr("disabled", "");
            } else if (status === "JUST_BE") {
                image.attr("src", "Idle-schwarzweiß.gif");
                learnButton.removeAttr("disabled");
                sleepButton.removeAttr("disabled");
                eatButton.removeAttr("disabled");
            } else if (status === "LEARN") {
                image.attr("src", "Learn-schwarzweiß.gif");
                learnButton.removeAttr("disabled");
                sleepButton.attr("disabled", "");
                eatButton.attr("disabled", "");
            } else if (status === "SLEEP") {
                image.attr("src", "Sleeping-schwarzweiß.gif");

                learnButton.attr("disabled", "");
                sleepButton.removeAttr("disabled");
                eatButton.attr("disabled", "");
            }

        }
    };

    request.open("GET", "./user/" + urlEnd);
    request.send();
}
