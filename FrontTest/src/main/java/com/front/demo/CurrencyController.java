package com.front.demo;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


@Controller
public class CurrencyController {

    private final String apiUrl = "https://mindicador.cl/api"; 

    @GetMapping("/")
    public String showCurrencyForm(Model model) {
        model.addAttribute("currencyForm", new CurrencyForm());
        return "index";
    }

    @GetMapping("/currency")
    public ResponseEntity<String> getCurrencyInfo(@RequestParam String codigo, @RequestParam String fecha) {
      
    	if (!isValidDateFormat(fecha)) {
            return new ResponseEntity<>("Error: El formato de la fecha debe ser dd-mm-yyyy", HttpStatus.BAD_REQUEST);
        }
    	
    	  double valorMoneda = obtenerValorMoneda(codigo, fecha);

    	    
    	    if (Double.isNaN(valorMoneda)) {
    	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	    }

    	   
    	    CurrencyInfo currencyInfo = new CurrencyInfo(codigo, fecha, valorMoneda);
    	    System.out.println(currencyInfo);

      
        String url = apiUrl + "/" + codigo + "/" + fecha;
        
        RestTemplate restTemplate = new RestTemplate();
        String CurrencyInfo = restTemplate.getForObject(url, String.class);
        System.out.println(CurrencyInfo);

        return new ResponseEntity<>(CurrencyInfo, HttpStatus.OK);
    }
    
    @GetMapping("/currency/year")
    public String getCurrencyInfoForYear(@RequestParam int fecha, @RequestParam String nombre, Model model) {
        RestTemplate restTemplate = new RestTemplate();
		String url = apiUrl + "/" + nombre + "/" + fecha; 
        Map<String, List<Map<String, Object>>> currencyDataForYear = restTemplate.getForObject(apiUrl, HashMap.class);

        // Lógica para calcular el promedio del valor de cada mes
        Map<String, Double> monthlyAverages = new HashMap<>();
        for (String currencyCode : currencyDataForYear.keySet()) {
            List<Map<String, Object>> dataList = currencyDataForYear.get(currencyCode);
            double totalValue = 0.0;
            for (Map<String, Object> data : dataList) {
                totalValue += (double) data.get("valor");
            }
            double averageValue = totalValue / dataList.size();
            monthlyAverages.put(currencyCode, averageValue);
        }

        model.addAttribute("year", fecha);
        model.addAttribute("monthlyAverages", monthlyAverages);

        return "currency_info_template";
    }

	private double obtenerValorMoneda(String nombre, String fecha) {
	       String apiUrl = "https://mindicador.cl/api";
		        String url = apiUrl + "/" + nombre + "/" + fecha;

		        RestTemplate restTemplate = new RestTemplate();
		        CurrencyInfo currencyInfo = restTemplate.getForObject(url, CurrencyInfo.class);

		        return currencyInfo.getValor();
		    }

    private boolean isValidDateFormat(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        try {
            Date fecha = sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
}

