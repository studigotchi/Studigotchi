<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login or Register</title>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="Login_Register.css">
</head>
<body>
<div class="header">
    <img id="banner" src="assets/Studigotchi-Banner.png" alt="Hier sollte ein Banner stehen!" draggable="false"/>
</div>
<div class="content">
    <form action="login" method="post">
        <table>
            <tbody>
            <tr>
                <td><label for="user_name">UserName</label></td>
                <td><input type="text" id="user_name" name="userName" required></td>
            </tr>
            <tr>
                <td><label for="login_radio">Login</label><input type="radio" name="type" value="login" id="login_radio"
                                                                 required></td>
                <td><label for="register_radio">Register</label><input type="radio" name="type" value="register"
                                                                       id="register_radio" required></td>
            </tr>
            <tr>
                <td><label for="password">Password</label></td>
                <td><input type="password" id="password" name="password"></td>
            </tr>
            </tbody>

        </table>
        <button type="submit">Send</button>
    </form>
</div>
</body>
</html>
