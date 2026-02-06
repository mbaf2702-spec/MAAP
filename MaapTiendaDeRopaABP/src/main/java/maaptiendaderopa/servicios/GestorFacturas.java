/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maaptiendaderopa.servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import maap.tiendaderopa.models.Factura;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Usuario
 */
public class GestorFacturas {
    // HashMap para almacenar facturas por número (acceso rápido)
    private Map<String, Factura> facturas;
    
    public GestorFacturas() {
        facturas = new HashMap<>();
        // Datos de ejemplo para pruebas
        cargarDatosEjemplo();
    }
    private void cargarDatosEjemplo() {
        // Datos de ejemplo (opcional)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         try {
             Factura f1 = new Factura("FAC-001", sdf.parse("01/01/2024"), "Juan Pérez");
             facturas.put("FAC-001", f1);
         } catch (ParseException e) {}
    }    
    
    // Agregar factura - Devuelve true si se agregó, false si ya existe
    public boolean agregarFactura(Factura factura) {
        if (factura == null || factura.getNumeroFactura() == null) {
            return false;
        }
        
        if (!facturas.containsKey(factura.getNumeroFactura())) {
            facturas.put(factura.getNumeroFactura(), factura);
            return true;
        }
        return false; // Ya existe una factura con ese número
    }
    
    // Consultar factura por número
    public Factura consultarFactura(String numero) {
        return facturas.get(numero);
    }
    
    // Método para verificar si existe factura (NUEVO MÉTODO)
    public boolean existeFactura(String numero) {
        return facturas.containsKey(numero);
    }
    
    // Método para listar todas las facturas
    public List<Factura> listarFacturas() {
        return new ArrayList<>(facturas.values());
    }
    
    // Método para obtener cantidad de facturas
    public int getTotalFacturas() {
        return facturas.size();
    }
}
