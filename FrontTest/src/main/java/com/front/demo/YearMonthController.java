package com.front.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class YearMonthController {

    @GetMapping("/year-month")
    public String showYearMonthForm() {
        return "year-month";
    }
    
    // Método para manejar el envío del formulario
    // Implementa la lógica para procesar la solicitud y generar la respuesta
}
