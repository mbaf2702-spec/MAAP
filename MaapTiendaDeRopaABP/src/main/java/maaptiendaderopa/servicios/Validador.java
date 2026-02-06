/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maaptiendaderopa.servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author Usuario
 */
public class Validador {
    
    public static boolean validarNumeroFactura(String numero) {
        return numero != null && !numero.trim().isEmpty() && numero.trim().length() >= 3;
    }
    
    public static boolean validarNombre(String nombre) {
        return nombre != null && nombre.trim().length() >= 2;
    }
    
    public static boolean validarFecha(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            return false;
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // No permite fechas inválidas como 31/02/2023
            sdf.parse(fechaStr.trim());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    public static Date convertirFecha(String fechaStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(fechaStr.trim());
        } catch (ParseException e) {
            return new Date(); // Retorna fecha actual si hay error
        }
    }
    
    public static boolean validarProducto(String nombre, String tipo, String talla, String cantidadStr, String precioStr) {
        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (tipo == null || tipo.trim().isEmpty()) return false;
        if (talla == null || talla.trim().isEmpty()) return false;
        
        // Validar cantidad (debe ser número entero positivo)
        try {
            int cantidad = Integer.parseInt(cantidadStr.trim());
            if (cantidad <= 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        
        // Validar precio (debe ser número positivo)
        try {
            double precio = Double.parseDouble(precioStr.trim());
            if (precio <= 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        // Validación simple de email
        return email.contains("@") && email.contains(".") && email.length() >= 5;
    }
    public static boolean esEnteroPositivo(String str) {
        try {
            int valor = Integer.parseInt(str.trim());
            return valor > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }    
}
