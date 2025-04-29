function reservar() {
    const paciente = document.getElementById("paciente").value;
    const idDoctor = document.getElementById("doctor").value;
    const fechaHoraInput = document.getElementById("fechaHora").value;

    if (!paciente || !idDoctor || !fechaHoraInput) {
        alert("Por favor, completa todos los campos");
        return;
    }


    const fechaHoraFormateada = fechaHoraInput.replace("T", " ") + ":00";

    const http = new XMLHttpRequest();
    http.open("POST", "http://localhost:8080/CitasMedicas/Cita", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.onload = function () {
        alert(http.responseText);
    };
    http.send(`paciente=${paciente}&idDoctor=${idDoctor}&fechaHora=${fechaHoraFormateada}`);
}

function verCitas() {
    const http = new XMLHttpRequest();
    http.open("GET", "http://localhost:8080/CitasMedicas/Cita", true);
    http.onload = function () {
        document.getElementById("resultados").innerHTML = http.responseText;
    };
    http.send();
}

