<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Сброс пароля</title>
    <link rel="stylesheet" href="<c:url value='/css/register.css'/>">
</head>
<body>
<div class="container">
    <div class="content">
        <h1>Сброс пароля</h1>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <form action="<c:url value='/reset-password'/>" method="POST">
            <input type="hidden" name="token" value="${token}">

            <div class="form-group">
                <label for="newPassword">Новый пароль:</label>
                <input id="newPassword" name="newPassword" type="password" placeholder="Введите новый пароль" required>
                <spring:bind path="resetPasswordRequest.newPassword">
                    <c:if test="${not empty status.errorMessage}">
                        <span style="color: red">${status.errorMessage}</span>
                    </c:if>
                </spring:bind>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Подтвердите пароль:</label>
                <input id="confirmPassword" name="confirmPassword" type="password" placeholder="Повторите новый пароль" required>
                <spring:bind path="resetPasswordRequest.confirmPassword">
                    <c:if test="${not empty status.errorMessage}">
                        <span style="color: red">${status.errorMessage}</span>
                    </c:if>
                </spring:bind>
            </div>

            <div class="form-buttons">
                <button type="submit" class="btn btn-primary">Изменить пароль</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>