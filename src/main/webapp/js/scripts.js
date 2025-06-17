function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('open');
}

function togglePasswordChange() {
    const passwordSection = document.getElementById("passwordSection");
    const changePasswordCheckbox = document.getElementById("changePassword");

    if (changePasswordCheckbox.checked) {
        passwordSection.style.display = "block";
    } else {
        passwordSection.style.display = "none";
    }
}

function toggleDetails(detailsId) {
    const detailsElement = document.getElementById(detailsId);
    if (detailsElement.style.display === "none" || detailsElement.style.display === "") {
        detailsElement.style.display = "block";
    } else {
        detailsElement.style.display = "none";
    }
}
