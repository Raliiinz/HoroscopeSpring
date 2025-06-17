<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Проверка совместимости</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>
<div class="container-profile">
    <jsp:include page="/WEB-INF/views/sidebarMenu.jsp" />
    <button class="menu-btn" onclick="toggleMenu()">☰ Меню</button>


    <div class="header">
        <h1>Проверка совместимости</h1>
    </div>

    <div class="profile-info">
        <label for="manBirthDate">День рождения мужчины:</label><br>
        <input type="date" id="manBirthDate" name="manBirthDate"><br><br>
    </div>

    <div class="profile-info">
        <label for="womanBirthDate">День рождения женщины:</label><br>
        <input type="date" id="womanBirthDate" name="womanBirthDate"><br><br>
    </div>

    <button id="checkButton" class="orange-button">Проверить</button>

    <div id="resultContainer" style="display: none; margin: 0 auto; max-width: 800px; padding: 0 20px;">
        <h2>Результат совместимости</h2>

        <div class="advice-info">
            <h3>Соотношение совместимости</h3>
            <p id="percentInfo"></p>
        </div>

        <div class="advice-info">
            <h3>Любовь</h3>
            <p id="loveInfo"></p>
        </div>

        <div class="advice-info">
            <h3>Семья и брак</h3>
            <p id="familyInfo"></p>
        </div>

        <div class="advice-info">
            <h3>Дружба</h3>
            <p id="friendsInfo"></p>
        </div>

        <div class="advice-info">
            <h3>Работа и бизнес</h3>
            <p id="workInfo"></p>
        </div>
    </div>

    <div id="errorContainer" class="error-message" style="display: none;"></div>
</div>

<script>
    function toggleMenu() {
        const sidebar = document.getElementById('sidebar');
        if (sidebar) {
            sidebar.classList.toggle('open');
        } else {
            console.error("Sidebar не найден");
        }
    }

    $(document).ready(function() {
        // Обработчик изменения дат
        $('#manBirthDate, #womanBirthDate').change(function() {
            checkCompatibility();
        });

        // Обработчик кнопки
        $('#checkButton').click(function() {
            checkCompatibility();
        });

        function checkCompatibility() {
            const manDate = $('#manBirthDate').val();
            const womanDate = $('#womanBirthDate').val();

            if (!manDate || !womanDate) {
                return;
            }

            $.ajax({
                url: '${pageContext.request.contextPath}/compatibilityCheck',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    manBirthDate: manDate,
                    womanBirthDate: womanDate
                }),
                success: function(response) {
                    if (response.error) {
                        $('#errorContainer').text(response.error).show();
                        $('#resultContainer').hide();
                    } else {
                        $('#percentInfo').text(response.percentInfo);
                        $('#loveInfo').text(response.loveInfo);
                        $('#familyInfo').text(response.familyInfo);
                        $('#friendsInfo').text(response.friendsInfo);
                        $('#workInfo').text(response.workInfo);

                        $('#resultContainer').show();
                        $('#errorContainer').hide();
                    }
                },
                error: function(xhr) {
                    let errorMsg = 'Произошла ошибка при проверке совместимости';
                    if (xhr.responseJSON && xhr.responseJSON.error) {
                        errorMsg = xhr.responseJSON.error;
                    }
                    $('#errorContainer').text(errorMsg).show();
                    $('#resultContainer').hide();
                }
            });
        }
    });
</script>
</body>
</html>