<%@ page pageEncoding="UTF-8" contentType="text/html; UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="student" class="studigochi.test.student.Student" scope="session"/>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Studigotchi</title>
    <link rel="icon" href="assets/favicon.ico">
    <link rel="stylesheet" href="Game.css">
    <script rel="script" src="https://code.jquery.com/jquery-3.4.1.js"></script>
    <script rel="script" src="Game.js"></script>
    <script rel="script">
        function firstInput() {
            currentSemester = ${student.semester};
            handleResult(${student.toJSONString()});
            onLoaded();
        }
    </script>
</head>
<body onload="firstInput()">

<div class="header">
    <img id="banner" src="assets/Studigotchi-Banner.png" alt="Hier sollte ein Banner stehen!" draggable="false"/>
</div>
<div class="content">
    <div class="pane-left">
        <h1>Studigotchi</h1>
        <h2>Anleitung</h2>
        <p id="instructions">
            Ziel: Bleibe am Leben und absolviere dein Studium<br/>
        Um das zu schaffen, musst du viel lernen, aber nat&uuml;rlich musst du auch essen und schlafen.
        Finde die richtige Balance, um bis zur n&auml;chsten Klausur vorbereitet zu sein.
        Pro Semester musst du f&uuml;nf Sterne aufbauen, um die Klausur zu bestehen.<br/>
        F&auml;llst du durch, musst du das Semester wiederholen.</p>
        <a href="https://www.google.com/search?q=Tamagotchi">Brauchst Du weitere Hilfe?</a>
        <h2>Impressum</h2>
        <p>Timo Klenk, Lena Katzmann, Annika Na&szlig;, Petra Scherer, Stefanie Sauer<br> <br>
            <b>Kurs</b>: TINF18B5<br> <br>
            Jahr 2019</p>
    </div>
    <div class="main-content">
        <div class="student">
            <img src="assets/Idle-schwarzweiss.gif" alt="Der Student? Kp" style="width: 80%; height: 80%; margin-top: 5%; margin-left: 10%;" draggable="false" id="student_bild">
        </div>
        <div class="buttons">
            <table class="button-table">
                <tbody>
                <tr class="button-row">
                    <td>
                        <button class="button-action button-eat" onclick="clickEat()">
                            <img src="assets/hamburger.png" alt="Essen" class="button-icon" draggable="false"/>
                            <br/>Essen
                        </button>
                    </td>
                    <td>
                        <button class="button-action button-learn" onclick="clickLearn()">
                            <img src="assets/book.png" alt="Lernen" class="button-icon" draggable="false"/>
                            <br/>Lernen
                        </button>
                    </td>
                    <td>
                        <button class="button-action button-sleep" onclick="clickSleep()">
                            <img src="assets/bed.png" alt="Schlafen" class="button-icon" draggable="false"/>
                            <br/>Schlafen
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="pane-right" >
        <h2>Hallo, ${student.userName}!</h2>
        <p> <b>Semester:</b> <span id="semester_id">1</span></p>
        <div class="bar">
            <img id="hearts1" src="assets/heart.png"><img id="hearts2" src="assets/heart.png"><img id="hearts3" src="assets/heart.png"><img id="hearts4" src="assets/heart.png"><img id="hearts5" src="assets/heart.png">
        </div>

        <div class="bar">
            <img id="stars1" src="assets/star.png"><img id="stars2" src="assets/star.png"><img id="stars3" src="assets/star.png"><img id="stars4" src="assets/star.png"><img id="stars5" src="assets/star.png">
        </div>
        <p> <b>Zeit bis Klausur:</b> <span id="time_id">3600s</span></p>

        <a href="./user/logout">Logout</a>
    </div>
</div>
</body>
</html>