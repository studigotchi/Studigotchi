let isEating = false;

function onLoaded() {
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
        jQuery("#stars" + index).attr("src", "assets/star.png");
        stars -= 1;
        index += 1;
    }

    if (stars >= 0.5) {
        jQuery("#stars" + index).attr("src", "assets/star_half.png");
        index += 1;
    }

    while(index <= 5) {
        jQuery("#stars" + index).attr("src", "assets/star_empty.png");
        index += 1;
    }

    index = 1;
    if(hearts > 0 && hearts < 0.5) {
        hearts += 0.5;
    }

    while (hearts >= 1) {
        jQuery("#hearts" + index).attr("src", "assets/heart.png");
        hearts -= 1;
        index += 1;
    }

    if (hearts >= 0.5) {
        jQuery("#hearts" + index).attr("src", "assets/heart_half.png");
        index += 1;
    }

    while(index <= 5) {
        jQuery("#hearts" + index).attr("src", "assets/heart_empty.png");
        index += 1;
    }
}

function sendRequest(urlEnd) {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === request.DONE) {
            //handleResult(request.responseText);
            handleResult(JSON.parse(request.responseText));
        }
    };

    request.open("GET", "./user/" + urlEnd);
    request.send();
}


function handleResult(responseObject) {
    //let responseObject = JSON.parse(result);
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
        image.attr("src", "assets/Eat.gif");
        learnButton.attr("disabled", "");
        sleepButton.attr("disabled", "");
        eatButton.attr("disabled", "");
    } else if (status === "JUST_BE") {
        image.attr("src", "assets/Idle-schwarzweiss.gif");
        learnButton.removeAttr("disabled");
        sleepButton.removeAttr("disabled");
        eatButton.removeAttr("disabled");
    } else if (status === "LEARN") {
        image.attr("src", "assets/Learn-schwarzweiss.gif");
        learnButton.removeAttr("disabled");
        sleepButton.attr("disabled", "");
        eatButton.attr("disabled", "");
    } else if (status === "SLEEP") {
        image.attr("src", "assets/Sleeping-schwarzweiss.gif");

        learnButton.attr("disabled", "");
        sleepButton.removeAttr("disabled");
        eatButton.attr("disabled", "");
    }
}
