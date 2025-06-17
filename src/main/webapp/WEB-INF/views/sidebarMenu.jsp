<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="sidebar" id="sidebar">
    <button class="close-btn" onclick="toggleMenu()">×</button>
    <ul>
        <li><a href="${pageContext.request.contextPath}/main">Совет дня</a></li>
        <li><a href="${pageContext.request.contextPath}/profile">Профиль</a></li>
        <li><a href="${pageContext.request.contextPath}/signInfo">Информация о знаке зодиака</a></li>
        <li><a href="${pageContext.request.contextPath}/adviceForYear">Совет на год</a></li>
        <li><a href="${pageContext.request.contextPath}/compatibilityCheck">Проверка совместимости</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">Выйти</a></li>
    </ul>
</div>
