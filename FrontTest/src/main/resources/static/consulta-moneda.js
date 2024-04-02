function consultarMoneda() {
    var fechaInput = document.getElementById('valorAnterior').value;
    var nombreInput = document.getElementById('moneda').value;
    var valorAnterior = parseFloat(document.getElementById('valorAnterior').value);

	var fechaHoy = new Date();
    var dia = fechaHoy.getDate();
    var mes = fechaHoy.getMonth() + 1;
    var año = fechaHoy.getFullYear();

	 

    
    if (!isValidDateFormat(fechaInput)) {
        alert('Error: El formato de la fecha debe ser dd-MM-yyyy');
        return;
    }

    // Realizar la consulta a través de AJAX 
 
     $.ajax({
         url: '/api/consultarMoneda',
         method: 'GET',
         data: { fecha: fechaInput, nombre: nombreInput },
         success: function(response) {
          var valorActual = ("0" + dia).slice(-2) + "-" + ("0" + mes).slice(-2) + "-" + año;
            
            // Calcular el porcentaje de variación y la diferencia en valor
            var porcentajeVariacion = calcularPorcentajeVariacion(valorAnterior, valorActual);
            var diferenciaValor = calcularDiferenciaValor(valorAnterior, valorActual);

            // Mostrar los resultados en la consola
            console.log("Porcentaje de variación: " + porcentajeVariacion.toFixed(3) + "%");
            console.log("Diferencia en valor: " + diferenciaValor.toFixed(3));

            // Mostrar los resultados en el HTML
            document.getElementById('resultado').innerHTML = "<p>Porcentaje de variación: " + porcentajeVariacion.toFixed(2) + "%</p>" +
                                                               "<p>Diferencia en valor: " + diferenciaValor.toFixed(2) + "</p>";
        },
        error: function() {
            alert('Error al obtener el valor de la moneda para el día de hoy.');
        }
    });
}

function isValidDateFormat(dateStr) {
    // Lógica para validar el formato de la fecha (opcional)
    return true; // Cambiar esto según tus requerimientos
}

function calcularPorcentajeVariacion(valorAnterior, valorActual) {
    var diferenciaValor = valorActual - valorAnterior;
    var porcentajeVariacion = (diferenciaValor / valorAnterior) * 100;
    return porcentajeVariacion;
}

function calcularDiferenciaValor(valorAnterior, valorActual) {
    var diferenciaValor = valorActual - valorAnterior;
    return diferenciaValor;
}

