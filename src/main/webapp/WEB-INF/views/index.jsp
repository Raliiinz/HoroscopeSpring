<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
    ServletContext context = request.getServletContext();
    String appPath = context.getContextPath();
%>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Гороскоп</title>
    <link rel="stylesheet" href="<c:url value ='/css/register.css'/>">
</head>
<body>
    <div class="container">
        <div class="content">
            <div class="description">
                <h1>Добро пожаловать на сайт гороскопов!</h1>
                <p>Здесь вы найдете точные прогнозы и советы для каждого знака зодиака. Наш сайт предоставляет информацию о любви, карьере, финансовых перспективах и других важных аспектах жизни.</p>
                <p>Мы стремимся помочь вам лучше понять себя и окружающий мир. Используйте наш сайт как руководство к принятию правильных решений и раскрытию своего потенциала.</p>
            </div>
            <div class="form-buttons">
                <a href="<%=appPath + "/login"%>" class="btn btn-primary">Войти</a>
                <a href="<%=appPath + "/register"%>" class="btn btn-primary">Зарегистрироваться</a>
            </div>
        </div>
    </div>
</body>
</html>

