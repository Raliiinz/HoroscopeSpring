<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Проверка совместимости</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">

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

    document.addEventListener('DOMContentLoaded', function() {
        // Обработчик изменения дат
        document.getElementById('manBirthDate').addEventListener('change', checkCompatibility);
        document.getElementById('womanBirthDate').addEventListener('change', checkCompatibility);

        // Обработчик кнопки
        document.getElementById('checkButton').addEventListener('click', checkCompatibility);

        function checkCompatibility() {
            const manDate = document.getElementById('manBirthDate').value;
            const womanDate = document.getElementById('womanBirthDate').value;

            if (!manDate || !womanDate) {
                return;
            }

            fetch('${pageContext.request.contextPath}/compatibilityCheck', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    manBirthDate: manDate,
                    womanBirthDate: womanDate
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.error) {
                        document.getElementById('errorContainer').textContent = data.error;
                        document.getElementById('errorContainer').style.display = 'block';
                        document.getElementById('resultContainer').style.display = 'none';
                    } else {
                        document.getElementById('percentInfo').textContent = data.percentInfo;
                        document.getElementById('loveInfo').textContent = data.loveInfo;
                        document.getElementById('familyInfo').textContent = data.familyInfo;
                        document.getElementById('friendsInfo').textContent = data.friendsInfo;
                        document.getElementById('workInfo').textContent = data.workInfo;

                        document.getElementById('resultContainer').style.display = 'block';
                        document.getElementById('errorContainer').style.display = 'none';
                    }
                })
                .catch(error => {
                    let errorMsg = 'Произошла ошибка при проверке совместимости';
                    document.getElementById('errorContainer').textContent = errorMsg;
                    document.getElementById('errorContainer').style.display = 'block';
                    document.getElementById('resultContainer').style.display = 'none';
                    console.error('Error:', error);
                });
        }
    });
</script>
</body>
</html>