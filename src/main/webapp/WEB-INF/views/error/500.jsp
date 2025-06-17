<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Ошибка сервера</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
</head>
<body>
<div class="container-profile">
    <div class="header">
        <h1>Ошибка 500</h1>
    </div>
    <div class="error-content">
        <p>Увы, на сервере произошла ошибка :(</p>
        <p>Попробуйте повторить позже или обратитесь в поддержку</p>
        <a href="${pageContext.request.contextPath}/" class="orange-button">Вернуться на главную</a>
    </div>
</div>
</body>
</html>