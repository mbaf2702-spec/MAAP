/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maap.tiendaderopa.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Usuario
 */
public class Factura {
    private String numeroFactura;
    private Date fecha;
    private String nombreCliente;
    private List<Producto> productos;
    private double total;

    public Factura(String numeroFactura, Date fecha, String nombreCliente) {
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.productos = new ArrayList<>();
        this.total = 0.0;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        calcularTotal();
    }

    private void calcularTotal() {
        total = 0.0;
        for (Producto p : productos) {
            total += p.getPrecio() * p.getCantidad();
        }
    }

    // Getters
    public String getNumeroFactura() { return numeroFactura; }
    public Date getFecha() { return fecha; }
    public String getNombreCliente() { return nombreCliente; }
    public List<Producto> getProductos() { return productos; }
    public double getTotal() { return total; }
    
    // Método para obtener cantidad de productos (NUEVO MÉTODO)
    public int getCantidadProductos() {
        return productos.size();
    }
    
    @Override
    public String toString() {
        return "Factura #" + numeroFactura + " - Cliente: " + nombreCliente + " - Total: $" + String.format("%.2f", total);
    }
}
