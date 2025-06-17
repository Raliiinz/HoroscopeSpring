<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%--<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html lang="ru">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>Регистрация</title>--%>
<%--    <link rel="stylesheet" href="<c:url value ='/css/register.css'/>">--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container">--%>
<%--    <div class="content">--%>
<%--        <h1>Регистрация</h1>--%>

<%--        <c:if test="${not empty error}">--%>
<%--            <p class="error">${error}</p>--%>
<%--        </c:if>--%>

<%--        <form action="<c:url value="/register"/>" method="POST">--%>
<%--            <div class="form-group">--%>
<%--                <label for="email">Почта:</label>--%>
<%--                <input id="email" name="email" type="email" placeholder="Введите адрес электронной почты"--%>
<%--                       value="${not empty email ? email : ''}">--%>
<%--                <c:if test="${not empty errorEmail}">--%>
<%--                    <span style="color: red">${errorEmail}</span>--%>
<%--                </c:if>--%>
<%--            </div>--%>
<%--            <div class="form-group">--%>
<%--                <label for="password">Пароль:</label>--%>
<%--                <input id="password" name="password" type="password" placeholder="Введите пароль">--%>
<%--                <c:if test="${not empty errorPassword}">--%>
<%--                    <span style="color: red">${errorPassword}</span>--%>
<%--                </c:if>--%>
<%--            </div>--%>
<%--            <div class="form-group">--%>
<%--                <label for="confirmPassword">Подтвердите пароль:</label>--%>
<%--                <input id="confirmPassword" name="confirmPassword" type="password" placeholder="Введите повторно пароль">--%>
<%--                <c:if test="${not empty errorConfirmPassword}">--%>
<%--                    <span style="color: red">${errorConfirmPassword}</span>--%>
<%--                </c:if>--%>
<%--            </div>--%>
<%--            <div class="form-group">--%>
<%--                <label for="userName">Имя:</label>--%>
<%--                <input id="userName" name="userName" type="text" placeholder="Введите имя пользователя"--%>
<%--                       value="${not empty userName ? userName : ''}">--%>
<%--                <c:if test="${not empty errorUserName}">--%>
<%--                    <span style="color: red">${errorUserName}</span>--%>
<%--                </c:if>--%>
<%--            </div>--%>
<%--            <div class="form-group">--%>
<%--                <label for="birthDate">Дата рождения:</label>--%>
<%--                <input id="birthDate" name="birthDate" type="date" placeholder="Выберите дату рождения"--%>
<%--                       value="${not empty birthDate ? birthDate : ''}">--%>
<%--                <c:if test="${not empty errorBirthDate}">--%>
<%--                    <span style="color: red">${errorBirthDate}</span>--%>
<%--                </c:if>--%>
<%--            </div>--%>
<%--            <div class="form-buttons">--%>
<%--                <button type="submit" class="btn btn-primary">Зарегистрироваться</button>--%>
<%--            </div>--%>
<%--        </form>--%>
<%--    </div>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
    <link rel="stylesheet" href="<c:url value='/css/register.css'/>">
</head>
<body>
<div class="container">
    <div class="content">
        <h1>Регистрация</h1>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <form action="<c:url value="/register"/>" method="POST">
            <!-- Email -->
            <div class="form-group">
                <label for="email">Почта:</label>
                <input id="email" name="email" type="email" placeholder="Введите адрес электронной почты"
                       value="${not empty registerClientRequest.email ? registerClientRequest.email : (not empty email ? email : '')}">
                <spring:bind path="registerClientRequest.email">
                    <c:if test="${not empty status.errorMessage}">
                        <span style="color: red">${status.errorMessage}</span>
                    </c:if>
                </spring:bind>
            </div>

            <!-- Пароль -->
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input id="password" name="password" type="password" placeholder="Введите пароль">
                <spring:bind path="registerClientRequest.password">
                    <c:if test="${not empty status.errorMessage}">
                        <span style="color: red">${status.errorMessage}</span>
                    </c:if>
                </spring:bind>
            </div>

            <!-- Подтверждение пароля -->
            <div class="form-group">
                <label for="confirmPassword">Подтвердите пароль:</label>
                <input id="confirmPassword" name="confirmPassword" type="password" placeholder="Введите повторно пароль">
                <spring:bind path="registerClientRequest.confirmPassword">
                    <c:if test="${not empty status.errorMessage}">
                        <span style="color: red">${status.errorMessage}</span>
                    </c:if>
                </spring:bind>
            </div>

            <!-- Имя -->
            <div class="form-group">
                <label for="userName">Имя:</label>
                <input id="userName" name="userName" type="text" placeholder="Введите имя пользователя"
                       value="${not empty registerClientRequest.userName ? registerClientRequest.userName : (not empty userName ? userName : '')}">
                <spring:bind path="registerClientRequest.userName">
                    <c:if test="${not empty status.errorMessage}">
                        <span style="color: red">${status.errorMessage}</span>
                    </c:if>
                </spring:bind>
            </div>

            <!-- Дата рождения -->
            <div class="form-group">
                <label for="birthDate">Дата рождения:</label>
                <input id="birthDate" name="birthDate" type="date"
                       value="${not empty registerClientRequest.birthDate ? registerClientRequest.birthDate : (not empty birthDate ? birthDate : '')}">
                <spring:bind path="registerClientRequest.birthDate">
                    <c:if test="${not empty status.errorMessage}">
                        <span style="color: red">${status.errorMessage}</span>
                    </c:if>
                </spring:bind>
            </div>

            <div class="form-buttons">
                <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>