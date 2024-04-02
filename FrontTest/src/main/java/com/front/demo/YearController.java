package com.front.demo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class YearController {

    @GetMapping("/year")
    public String showYearForm() {
        return "year";
    }
    
    // Método para manejar el envío del formulario
    // Implementa la lógica para procesar la solicitud y generar la respuesta
}

