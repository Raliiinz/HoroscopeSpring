<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Восстановление пароля</title>
    <link rel="stylesheet" href="<c:url value='/css/register.css'/>">
</head>
<body>
<div class="container">
    <div class="content">
        <h1>Восстановление пароля</h1>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <form action="<c:url value='/forgot-password'/>" method="POST">
            <div class="form-group">
                <label for="email">Email:</label>
                <input id="email" name="email" type="email" placeholder="Введите ваш email"
                       value="${not empty forgotPasswordRequest.email ? forgotPasswordRequest.email : ''}" required>
                <spring:bind path="forgotPasswordRequest.email">
                    <c:if test="${not empty status.errorMessage}">
                        <span style="color: red">${status.errorMessage}</span>
                    </c:if>
                </spring:bind>
            </div>

            <div class="form-buttons">
                <button type="submit" class="btn btn-primary">Отправить ссылку</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>