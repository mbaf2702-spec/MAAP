/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maap.tiendaderopa.models;

/**
 *
 * @author Usuario
 */
public class Producto {
    private String nombre;
    private String tipo; // Camisa, Pantalón, etc.
    private String talla;
    private int cantidad;
    private double precio;

    public Producto(String nombre, String tipo, String talla, int cantidad, double precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.talla = talla;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getTalla() { return talla; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }
    
    public double getSubtotal() {
        return precio * cantidad;
    }
    @Override
    public String toString() {
        return nombre + " (" + tipo + ", Talla: " + talla + ") x" + cantidad + " = $" + String.format("%.2f", getSubtotal());
    }   
}
