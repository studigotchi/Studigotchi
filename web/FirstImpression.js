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
    }, 800);
}

function clickLearn() {
    sendRequest("learn");
}

function clickSleep() {
    sendRequest("sleep");
}


function setBilder(jsonObject) {
    let hearts = jsonObject.hearts;
    let stars = jsonObject.stars;

    let index = 1;
    while (stars >= 1) {
        jQuery("#stars" + index).attr("src", "star.png");
        stars -= 1;
        index += 1;
    }

    if (stars >= 0.5) {
        jQuery("#stars" + index).attr("src", "star_half.png");
        index += 1;
    }

    while(index <= 5) {
        jQuery("#stars" + index).attr("src", "star_empty.png");
        index += 1;
    }

    index = 1;
    if(hearts > 0 && hearts < 0.5) {
        hearts += 0.5;
    }

    while (hearts >= 1) {
        jQuery("#hearts" + index).attr("src", "heart.png");
        hearts -= 1;
        index += 1;
    }

    if (hearts >= 0.5) {
        jQuery("#hearts" + index).attr("src", "heart_half.png");
        index += 1;
    }

    while(index <= 5) {
        jQuery("#hearts" + index).attr("src", "heart_empty.png");
        index += 1;
    }
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

            setBilder(responseObject);

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
