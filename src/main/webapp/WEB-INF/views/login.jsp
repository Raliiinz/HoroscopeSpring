<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%--<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<html lang="ru">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>Вход в систему</title>--%>
<%--    <link rel="stylesheet" href="<c:url value ='/css/register.css'/>">--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container">--%>
<%--    <div class="content">--%>
<%--        <h1>Вход в систему</h1>--%>

<%--        <c:if test="${not empty error}">--%>
<%--            <p class="error">${error}</p>--%>
<%--        </c:if>--%>

<%--        <form action="<c:url value="/login"/>" method="post">--%>
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
<%--            <div class="form-buttons">--%>
<%--                <button type="submit" class="btn btn-primary">Войти</button>--%>
<%--            </div>--%>
<%--        </form>--%>
<%--        <p>Еще нет учетной записи? <a href="<c:url value="/register"/>" class="link">Зарегистрироваться</a></p>--%>

<%--    </div>--%>
<%--</div>--%>

<%--</body>--%>
<%--</html>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход в систему</title>
    <link rel="stylesheet" href="<c:url value ='/css/register.css'/>">
</head>
<body>
<div class="container">
    <div class="content">
        <h1>Вход в систему</h1>

        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <form action="<c:url value="/login"/>" method="post">
            <!-- Email -->
            <div class="form-group">
                <label for="email">Почта:</label>
                <input id="email" name="email" type="email" placeholder="Введите адрес электронной почты"
                       value="${not empty loginClientRequest.email ? loginClientRequest.email : (not empty email ? email : '')}">
                <spring:bind path="loginClientRequest.email">
                    <c:if test="${not empty status.errorMessage}">
                        <span style="color: red">${status.errorMessage}</span>
                    </c:if>
                </spring:bind>
            </div>

            <!-- Пароль -->
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input id="password" name="password" type="password" placeholder="Введите пароль">
                <spring:bind path="loginClientRequest.password">
                    <c:if test="${not empty status.errorMessage}">
                        <span style="color: red">${status.errorMessage}</span>
                    </c:if>
                </spring:bind>
            </div>

            <div class="form-buttons">
                <button type="submit" class="btn btn-primary">Войти</button>
            </div>
            <div class="form-footer">
                <a href="/forgot-password">Забыли пароль?</a>
            </div>
        </form>
        <p>Еще нет учетной записи? <a href="<c:url value="/register"/>" class="link">Зарегистрироваться</a></p>
    </div>
</div>
</body>
</html>
