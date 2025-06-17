<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru-RU">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ваш гороскоп</title>
    <link rel="stylesheet" href="<c:url value ='/css/main.css'/>">
    <script src="<c:url value='/js/scripts.js' />"></script>
</head>
<body>
<div class="container">
    <jsp:include page="/WEB-INF/views/sidebarMenu.jsp" />
    <button class="menu-btn" onclick="toggleMenu()">☰ Меню</button>

    <div class="header">
        <h1>Ваш гороскоп</h1>
    </div>


    <div class="sign-info">
        <span class="sign-name">${zodiac.signName}</span>
        <img src="${zodiac.imagePath}" alt="${zodiac.signName} символ" class="zodiac-sign-image">
    </div>
    <h2>Характеристики:</h2>

    <h3>Сильная сторона:</h3>
    <div class="advice-info">
        ${zodiac.strongSide}
    </div>

    <h3>Слабая сторона:</h3>
    <div class="advice-info">
        ${zodiac.weakness}
    </div>

    <h2>Информация о знаке зодиака:</h2>
    <div class="advice-info">
        ${zodiac.info}
    </div>

    <div class="stones-section">
        <h2 class="stones-title">Камни для ${zodiac.signName}</h2>

        <!-- Добавляем кнопку для запроса к внешнему сервису -->
        <button id="getStoneFact" class="orange-button" style="margin-bottom: 20px;">
            Получить интересный факт о камнях
        </button>

        <div id="stoneFact" class="advice-info" style="display: none;"></div>

        <div class="stones-container">
            <c:forEach var="stone" items="${zodiac.stones}">
                <div class="stone-card">

                    <div class="stone-name">${stone.stoneName}</div>
                    <div class="stone-description">${stone.description}</div>
                    <div class="stone-power" data-stone="${stone.stoneName}">
                        <span class="power-label">Сила камня:</span>
                        <span class="power-value">Загрузка...</span>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        // Проверка доступности сервиса
        function checkService() {
            return $.ajax({
                url: 'http://localhost:5001/stone-fact',
                timeout: 2000
            });
        }

        checkService().then(function() {
            // Сервис доступен - инициализируем логику
            initStoneFeatures();
        }).catch(function() {
            // Сервис недоступен - скрываем элементы
            $('.stone-power, #getStoneFact').hide();
        });

        function initStoneFeatures() {
            $('.stone-power').each(function() {
                const stoneName = $(this).data('stone');
                const powerElement = $(this).find('.power-value');

                $.get('http://localhost:5001/stone-power?name=' + encodeURIComponent(stoneName))
                    .done(function(data) {
                        powerElement.text(data.power);
                    })
                    .fail(function() {
                        powerElement.text('Сервис недоступен');
                    });
            });

            $('#getStoneFact').click(function() {
                $.get('http://localhost:5001/stone-fact')
                    .done(function(data) {
                        $('#stoneFact').text(data.fact).fadeIn();
                    })
                    .fail(function() {
                        $('#stoneFact').text('Сервис временно недоступен').fadeIn();
                    });
            });
        }
    });
</script>

</body>
</html>



