<%--
  Created by IntelliJ IDEA.
  User: bacht
  Date: 18/7/2023
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <script>
        function validateForm() {
            var username = document.getElementById("username").value;
            var password = document.getElementById("password").value;

            if (username === "" || password === "") {
                alert("Username and password are required fields.");
                return false;
            }
        }
    </script>
</head>
<body>
<h2>Login Page</h2>

<form method="post" action="index" onsubmit="return validateForm();">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <input type="submit" value="Login">
</form>

<%-- Display error message if authentication fails --%>
<% String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) { %>
<p style="color: red;"><%= errorMessage %>
</p>
<% } %>
</body>
</html>
