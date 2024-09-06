   document.addEventListener("DOMContentLoaded", function() {
        fetch('http://localhost:8080/Leave_Mgmt/header.html')
            .then(response => response.text())
            .then(data => {
                document.body.insertAdjacentHTML('afterbegin', data);
            })
            .catch(error => console.error('Error loading header and modal:', error));
    });