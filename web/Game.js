let isEating = false;
let currentStatus;
let writingExam = false;
let currentSemester;

function onLoaded() {
    window.setInterval(function () {
        sendRequest("data");
    }, 1000);
}


function clickEat() {
    isEating = true;
    jQuery(".button-eat").attr("disabled", "");
    sendRequest("eat");
    window.setTimeout(function () {
        isEating = false;
    }, 1700);
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

    while (index <= 5) {
        jQuery("#stars" + index).attr("src", "assets/star_empty.png");
        index += 1;
    }

    index = 1;
    if (hearts > 0 && hearts < 0.5) {
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

    while (index <= 5) {
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

    if(+currentSemester !== +responseObject.semester) {
        writingExam = true;
        window.setTimeout(function () {
            return writingExam = false;
        }, 1700);
        currentSemester = +responseObject.semester;
    }

    let foundStatus = isEating ? "EATING" : responseObject.status || "JUST_BE";
    if (writingExam) {
        foundStatus = "EXAM";
    }

    let image = jQuery("#student_bild");
    let learnButton = jQuery(".button-learn");
    let sleepButton = jQuery(".button-sleep");
    let eatButton = jQuery(".button-eat");

    setBilder(responseObject);
    if (currentStatus !== foundStatus) {
        if (foundStatus === "EATING") {
            image.attr("src", "assets/Eat.gif");
            learnButton.attr("disabled", "");
            sleepButton.attr("disabled", "");
            eatButton.attr("disabled", "");
        } else if (foundStatus === "JUST_BE") {
            image.attr("src", "assets/Idle-schwarzweiss.gif");
            learnButton.removeAttr("disabled");
            sleepButton.removeAttr("disabled");
            eatButton.removeAttr("disabled");
        } else if (foundStatus === "LEARN") {
            image.attr("src", "assets/Learn-schwarzweiss.gif");
            learnButton.removeAttr("disabled");
            sleepButton.attr("disabled", "");
            eatButton.attr("disabled", "");
        } else if (foundStatus === "SLEEP") {
            image.attr("src", "assets/Sleeping-schwarzweiss.gif");
            learnButton.attr("disabled", "");
            sleepButton.removeAttr("disabled");
            eatButton.attr("disabled", "");
        } else if (foundStatus === "DEAD") {
            image.attr("src", "assets/Dead-Desk.gif");
            learnButton.attr("disabled", "");
            sleepButton.attr("disabled", "");
            eatButton.attr("disabled", "");
        } else if (foundStatus === "DEAD_HANGING") {
            image.attr("src", "assets/Dead-Hanging.gif");
            learnButton.attr("disabled", "");
            sleepButton.attr("disabled", "");
            eatButton.attr("disabled", "");
        } else if (foundStatus === "WON") {
            image.attr("src", "assets/Won.gif");
            learnButton.attr("disabled", "");
            sleepButton.attr("disabled", "");
            eatButton.attr("disabled", "");
        } else if (foundStatus === "EXAM") {
            image.attr("src", "assets/Exam.gif");
            learnButton.attr("disabled", "");
            sleepButton.attr("disabled", "");
            eatButton.attr("disabled", "");
        }

        currentStatus = foundStatus;
    }
}
