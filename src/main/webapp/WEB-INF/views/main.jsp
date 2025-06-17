<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Главная страница - Совет дня</title>
    <link rel="stylesheet" href="<c:url value ='/css/main.css'/>">
    <script src="<c:url value='/js/scripts.js' />"></script>
</head>
<body>
<div class="container">
    <jsp:include page="sidebarMenu.jsp" />
    <button class="menu-btn" onclick="toggleMenu()">☰ Меню</button>
    <div class="header">
        <h1>Совет дня</h1>
    </div>
        <div class="moon-info">
            <h2>Фаза Луны: ${moonPhaseAdvice.moonPhase}</h2>
                <strong>Общая информация:</strong> ${moonPhaseAdvice.generalInfo}
                <button class="toggle-button" onclick="toggleDetails('moonPhaseDetails')">Читать подробнее</button>
                <div id="moonPhaseDetails" class="details" style="display: none;">
                    <h3>Подробности по фазе луны:</h3>
                    <c:if test="${moonPhaseAdvice.affairs != null}"><p><strong>Дела:</strong> ${moonPhaseAdvice.affairs}</p></c:if>
                    <c:if test="${moonPhaseAdvice.work != null}"><p><strong>Работа:</strong> ${moonPhaseAdvice.work}</p></c:if>
                    <c:if test="${moonPhaseAdvice.homeWork != null}"><p><strong>Домашние дела:</strong> ${moonPhaseAdvice.homeWork}</p></c:if>
                    <c:if test="${moonPhaseAdvice.money != null}"><p><strong>Деньги:</strong> ${moonPhaseAdvice.money}</p></c:if>
                    <c:if test="${moonPhaseAdvice.loveRelationships != null}"><p><strong>Отношения:</strong> ${moonPhaseAdvice.loveRelationships}</p></c:if>
                    <c:if test="${moonPhaseAdvice.communication != null}"><p><strong>Общение:</strong> ${moonPhaseAdvice.communication}</p></c:if>
                    <c:if test="${moonPhaseAdvice.travel != null}"><p><strong>Путешествия:</strong> ${moonPhaseAdvice.travel}</p></c:if>
                    <c:if test="${moonPhaseAdvice.hairCare != null}"><p><strong>Уход за волосами:</strong> ${moonPhaseAdvice.hairCare}</p></c:if>
                    <c:if test="${moonPhaseAdvice.beautySelfCare != null}"><p><strong>Уход за собой:</strong> ${moonPhaseAdvice.beautySelfCare}</p></c:if>
                    <c:if test="${moonPhaseAdvice.health != null}"><p><strong>Здоровье:</strong> ${moonPhaseAdvice.health}</p></c:if>
                    <c:if test="${moonPhaseAdvice.nutrition != null}"><p><strong>Питание:</strong> ${moonPhaseAdvice.nutrition}</p></c:if>
                </div>
        </div>

        <div class="moon-info">
            <h2>Знак Зодиака Луны: ${zodiacSignAdvice.zodiacSign}</h2>
            <div>
                <strong>Общая информация:</strong> ${zodiacSignAdvice.generalInfo}
                <button class="toggle-button" onclick="toggleDetails('zodiacSignDetails')">Читать подробнее</button>
                <div id="zodiacSignDetails" class="details" style="display: none;">
                    <h3>Подробности по знаку зодиака:</h3>
                    <c:if test="${zodiacSignAdvice.possibilities != null}"><p><strong>Возможности:</strong> ${zodiacSignAdvice.possibilities}</p></c:if>
                    <c:if test="${zodiacSignAdvice.prohibitions != null}"><p><strong>Запреты:</strong> ${zodiacSignAdvice.prohibitions}</p></c:if>
                    <c:if test="${zodiacSignAdvice.mood != null}"><p><strong>Настроение:</strong> ${zodiacSignAdvice.mood}</p></c:if>
                    <c:if test="${zodiacSignAdvice.initiatives != null}"><p><strong>Инициативы:</strong> ${zodiacSignAdvice.initiatives}</p></c:if>
                    <c:if test="${zodiacSignAdvice.workBusiness != null}"><p><strong>Работа и бизнес:</strong> ${zodiacSignAdvice.workBusiness}</p></c:if>
                    <c:if test="${zodiacSignAdvice.finances != null}"><p><strong>Финансы:</strong> ${zodiacSignAdvice.finances}</p></c:if>
                    <c:if test="${zodiacSignAdvice.loveFamily != null}"><p><strong>Семья и любовь:</strong> ${zodiacSignAdvice.loveFamily}</p></c:if>
                    <c:if test="${zodiacSignAdvice.health != null}"><p><strong>Здоровье:</strong> ${zodiacSignAdvice.health}</p></c:if>
                    <c:if test="${zodiacSignAdvice.creativityEducation != null}"><p><strong>Творчество и образование:</strong> ${zodiacSignAdvice.creativityEducation}</p></c:if>

                </div>
            </div>

            <c:if test="${not empty error}">
                <p class="error-message">${error}</p>
            </c:if>
        </div>
</div>

</body>
</html>