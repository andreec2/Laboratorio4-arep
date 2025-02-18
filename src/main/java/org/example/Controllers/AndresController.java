package org.example.Controllers;

import org.example.annotations.GetMapping;
import org.example.annotations.RequestParam;
import org.example.annotations.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AndresController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/nombre")
    public static String nombre(@RequestParam(value = "name", defaultValue = "Andres") String name,
                                @RequestParam(value = "tipo", defaultValue = "vodka") String tipo)
    {
        return "Bienvenido señor " + name + " le apetece un shot de " + tipo;
    }

    @GetMapping("/sum")
    public String sum(@RequestParam(value = "a", defaultValue = "0") String aStr,
                      @RequestParam(value = "b", defaultValue = "0") String bStr) {
        try {
            int a = Integer.parseInt(aStr);
            int b = Integer.parseInt(bStr);
            int result = a + b;
            return Integer.toString(result);
        } catch (NumberFormatException e) {
            return "Entrada incorrecta. Por favor ingresa enteros";
        }
    }

    @GetMapping("/res")
    public String res(@RequestParam(value = "a", defaultValue = "0") String aStr,
                      @RequestParam(value = "b", defaultValue = "0") String bStr) {
        try {
            int a = Integer.parseInt(aStr);
            int b = Integer.parseInt(bStr);
            int result = a - b;
            return Integer.toString(result);
        } catch (NumberFormatException e) {
            return "Entrada incorrecta. Por favor ingresa enteros";
        }
    }

    @GetMapping("/mul")
    public String mul(@RequestParam(value = "a", defaultValue = "0") String aStr,
                      @RequestParam(value = "b", defaultValue = "0") String bStr) {
        try {
            int a = Integer.parseInt(aStr);
            int b = Integer.parseInt(bStr);
            int result = a * b;
            return Integer.toString(result);
        } catch (NumberFormatException e) {
            return "Entrada incorrecta. Por favor ingresa enteros";
        }
    }






}
