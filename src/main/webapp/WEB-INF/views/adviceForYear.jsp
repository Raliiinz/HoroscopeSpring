<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Совет на год</title>
    <link rel="stylesheet" href="<c:url value ='/css/main.css'/>">
    <script src="<c:url value='/js/scripts.js' />"></script>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/sidebarMenu.jsp" />
    <button class="menu-btn" onclick="toggleMenu()">☰ Меню</button>

<form action="<c:url value="adviceForYear"/>" method="get">

    <div class="header">
        <h1>Совет на ${advice.adviceYear} год</h1>
    </div>

    <h2>Основной совет:</h2>
    <div class="advice-info">
        ${advice.adviceText}
    </div>
</form>
</div>

</body>
</html>
