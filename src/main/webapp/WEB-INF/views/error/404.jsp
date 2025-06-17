<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Страница не найдена</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
</head>
<body>
<div class="container-profile">
    <div class="header">
        <h1>Ошибка 404</h1>
    </div>
    <div class="error-content">
        <p>Увы, такой страницы не существует :(</p>
        <a href="${pageContext.request.contextPath}/" class="orange-button">Вернуться на главную</a>
    </div>
</div>
</body>
</html>