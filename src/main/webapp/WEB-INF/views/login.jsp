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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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

            <span class="forgot-password-link" onclick="openForgotPasswordModal()">Забыли пароль?</span>

            <div class="form-buttons">
                <button type="submit" class="btn btn-primary">Войти</button>
            </div>

        </form>
        <p>Еще нет учетной записи? <a href="<c:url value="/register"/>" class="link">Зарегистрироваться</a></p>
    </div>
</div>

<!-- Модальное окно восстановления пароля -->
<div id="forgotPasswordModal">
    <div class="modal-content">
        <span class="close-modal" onclick="closeForgotPasswordModal()">×</span>
        <h2>Восстановление пароля</h2>
        <p>Введите адрес электронной почты, указанный при регистрации. Мы отправим вам инструкции по восстановлению пароля.</p>

        <form id="forgotPasswordForm">
            <input type="email" id="resetEmail" name="email" placeholder="Ваш email" required>
            <button type="submit">Отправить инструкции</button>
        </form>

        <div id="forgotPasswordMessage"></div>
    </div>
</div>

<script>
    // Открытие модального окна
    function openForgotPasswordModal() {
        $('#forgotPasswordModal').fadeIn();
    }

    // Закрытие модального окна
    function closeForgotPasswordModal() {
        $('#forgotPasswordModal').fadeOut();
        $('#forgotPasswordMessage').hide().removeClass('success-message error-message');
    }

    $('#forgotPasswordForm').submit(function(e) {
        e.preventDefault();
        const email = $('#resetEmail').val();

        if (!email) {
            showMessage('Введите email', 'error');
            return;
        }

        $('#forgotPasswordMessage').html('<div class="loading">Отправка...</div>').show();

        $.ajax({
            url: '/api/auth/forgot-password',
            type: 'POST',
            data: { email: email },
            success: function(response) {
                if (response.success) {
                    showMessage(response.message, 'success');
                } else {
                    showMessage(response.message, 'error');
                }
            },
            error: function(xhr) {
                let msg = "Ошибка сервера";
                if (xhr.status === 429) {
                    msg = "Слишком много попыток. Попробуйте позже.";
                } else if (xhr.responseJSON?.message) {
                    msg = xhr.responseJSON.message;
                }
                showMessage(msg, 'error');
            }
        });
    });
    <%--$('#forgotPasswordForm').submit(function(e) {--%>
    <%--    e.preventDefault();--%>
    <%--    const email = $('#resetEmail').val();--%>

    <%--    if (!email) {--%>
    <%--        showForgotPasswordMessage('Пожалуйста, введите email', 'error');--%>
    <%--        return;--%>
    <%--    }--%>

    <%--    console.log("Отправка запроса на восстановление пароля для email:", email);--%>

    <%--    $('#forgotPasswordMessage').html('<div class="loading">Отправка запроса...</div>').fadeIn();--%>

    <%--    $.ajax({--%>
    <%--        url: '<c:url value="/api/auth/forgot-password"/>',--%>
    <%--        type: 'POST',--%>
    <%--        contentType: 'application/x-www-form-urlencoded',--%>
    <%--        data: { email: email },--%>
    <%--        success: function(response, status, xhr) {--%>
    <%--            console.log("Успешный ответ от сервера:", response);--%>
    <%--            if (response.success) {--%>
    <%--                showForgotPasswordMessage(response.message, 'success');--%>
    <%--                $('#resetEmail').val('');--%>
    <%--                setTimeout(closeForgotPasswordModal, 3000);--%>
    <%--            } else {--%>
    <%--                showForgotPasswordMessage(response.message, 'error');--%>
    <%--            }--%>
    <%--        },--%>
    <%--        error: function(xhr, status, error) {--%>
    <%--            console.error("Ошибка AJAX:", status, error);--%>
    <%--            console.error("Детали ошибки:", xhr.responseText);--%>

    <%--            let errorMsg = 'Произошла ошибка при отправке запроса';--%>
    <%--            try {--%>
    <%--                const response = JSON.parse(xhr.responseText);--%>
    <%--                if (response.message) {--%>
    <%--                    errorMsg = response.message;--%>
    <%--                }--%>
    <%--            } catch (e) {--%>
    <%--                console.error("Ошибка парсинга JSON:", e);--%>
    <%--            }--%>

    <%--            if (xhr.status === 0) {--%>
    <%--                errorMsg = 'Нет соединения с сервером';--%>
    <%--            } else if (xhr.status === 500) {--%>
    <%--                errorMsg = 'Ошибка сервера: ' + errorMsg;--%>
    <%--            }--%>

    <%--            showForgotPasswordMessage(errorMsg, 'error');--%>
    <%--        }--%>
    <%--    });--%>
    <%--});--%>
    // Обработка отправки формы восстановления пароля
    <%--$('#forgotPasswordForm').submit(function(e) {--%>
    <%--    e.preventDefault();--%>
    <%--    const email = $('#resetEmail').val();--%>

    <%--    if (!email) {--%>
    <%--        showForgotPasswordMessage('Пожалуйста, введите email', 'error');--%>
    <%--        return;--%>
    <%--    }--%>

    <%--    // Показываем индикатор загрузки--%>
    <%--    $('#forgotPasswordMessage').html('<div class="loading">Отправка запроса...</div>').fadeIn();--%>

    <%--    $.ajax({--%>
    <%--        url: '<c:url value="/api/auth/forgot-password"/>',--%>
    <%--        type: 'POST',--%>
    <%--        contentType: 'application/x-www-form-urlencoded',--%>
    <%--        data: { email: email },--%>
    <%--        success: function(response) {--%>
    <%--            if (response.success) {--%>
    <%--                showForgotPasswordMessage(response.message, 'success');--%>
    <%--                $('#resetEmail').val('');--%>
    <%--                setTimeout(closeForgotPasswordModal, 3000);--%>
    <%--            } else {--%>
    <%--                showForgotPasswordMessage(response.message, 'error');--%>
    <%--            }--%>
    <%--        },--%>
    <%--        error: function(xhr) {--%>
    <%--            let errorMsg = 'Произошла ошибка при отправке запроса';--%>
    <%--            if (xhr.responseJSON && xhr.responseJSON.message) {--%>
    <%--                errorMsg = xhr.responseJSON.message;--%>
    <%--            } else if (xhr.status === 0) {--%>
    <%--                errorMsg = 'Нет соединения с сервером';--%>
    <%--            } else if (xhr.status === 500) {--%>
    <%--                errorMsg = 'Ошибка сервера';--%>
    <%--            }--%>
    <%--            showForgotPasswordMessage(errorMsg, 'error');--%>
    <%--            console.error("Ошибка:", xhr.status, xhr.responseText);--%>
    <%--        }--%>
    <%--    });--%>
    <%--});--%>

    // Показать сообщение в модальном окне
    function showForgotPasswordMessage(message, type) {
        const $message = $('#forgotPasswordMessage');
        $message.text(message)
            .removeClass('success-message error-message')
            .addClass(type + '-message')
            .fadeIn();
    }

    // Закрытие модального окна при клике вне его
    $(window).click(function(e) {
        if ($(e.target).is('#forgotPasswordModal')) {
            closeForgotPasswordModal();
        }
    });
</script>
</body>
</html>
