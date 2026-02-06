package maap.tiendaderopa.ui;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import maap.tiendaderopa.models.Factura;
import maap.tiendaderopa.models.Producto;
import maaptiendaderopa.servicios.Validador;
import maaptiendaderopa.servicios.GestorFacturas;
/**
 *
 * @author sistemas
 */
public class VentanaRegistro extends javax.swing.JFrame {
    
    // Variables de negocio
    private GestorFacturas gestor;
    private Factura facturaActual;
    private DefaultTableModel modeloTabla;
    private JTable tablaProductos;
    private JScrollPane scrollTabla;
    
    // Constructor
    public VentanaRegistro() {
        initComponents();
        gestor = new GestorFacturas(); 
        facturaActual = null;
        inicializarComponentesAdicionales();
        configurarFechaActual();
        setLocationRelativeTo(null);
        setTitle("MAAP - Registro de Facturas");
    }
    
    private void inicializarComponentesAdicionales() {
        // Inicializar tabla de productos
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Producto");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Talla");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Precio Unit.");
        modeloTabla.addColumn("Subtotal");
        
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(25);
        scrollTabla = new JScrollPane(tablaProductos);
        
        // Posicionar la tabla en la interfaz
        scrollTabla.setBounds(450, 280, 400, 150);
        getContentPane().add(scrollTabla);
        
        // Agregar ActionListener para Enter en campos
        agregarListenersEnter();
        
        // Deshabilitar inicialmente campos de productos
        habilitarCamposProductos(false);
        
        // Agregar listeners a los botones que no los tienen
        agregarListenersABotones();
    }
    private void agregarListenersABotones() {
        // Asegurar que todos los botones tengan listeners
        if (jButton2 != null && jButton2.getActionListeners().length == 0) {
            jButton2.addActionListener(e -> registrarFacturaCompleta());
        }
        
        if (jButton4 != null && jButton4.getActionListeners().length == 0) {
            jButton4.addActionListener(e -> volverAlMenu());
        }
    }    
    private void configurarFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        jTextField2.setText(sdf.format(new Date()));
    }
    
    private void agregarListenersEnter() {
        // Hacer que Enter funcione como Tab
        java.awt.event.KeyAdapter enterKeyAdapter = new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    evt.consume();
                    transferFocus();
                }
            }
        };
    
        jTextField1.addKeyListener(enterKeyAdapter);
        jTextField2.addKeyListener(enterKeyAdapter);
        jTextField3.addKeyListener(enterKeyAdapter);
        jTextField4.addKeyListener(enterKeyAdapter);
        jTextField5.addKeyListener(enterKeyAdapter);
        jTextField6.addKeyListener(enterKeyAdapter);
        jTextField7.addKeyListener(enterKeyAdapter);
    }
    
  // Agregar ActionListener para Enter en campos
        agregarListenersEnter();
        
        // Deshabilitar inicialmente campos de productos
        habilitarCamposProductos(false);
    }
    
    private void configurarFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        jTextField2.setText(sdf.format(new Date()));
    }
    
    private void agregarListenersEnter() {
        // Hacer que Enter funcione como Tab
        java.awt.event.KeyAdapter enterKeyAdapter = new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    evt.consume();
                    transferFocus();
                }
            }
        };
        
        jTextField1.addKeyListener(enterKeyAdapter);
        jTextField2.addKeyListener(enterKeyAdapter);
        jTextField3.addKeyListener(enterKeyAdapter);
        jTextField4.addKeyListener(enterKeyAdapter);
        jTextField5.addKeyListener(enterKeyAdapter);
        jTextField6.addKeyListener(enterKeyAdapter);
        jTextField7.addKeyListener(enterKeyAdapter);
    }
    // MÉTODOS PARA LOS BOTONES DE PRODUCTOS
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // "Agregar P." - Agrega producto y limpia campos para nuevo ingreso
        agregarProductoALaFactura();
    }
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // "Registrar Producto" - Agrega producto y muestra resumen
        if (agregarProductoALaFactura()) {
            // Si se agregó correctamente, mostrar resumen
            mostrarResumenProductos();
        }
    }
    
    private boolean agregarProductoALaFactura() {
        // Retorna true si se agregó correctamente, false si hubo error
        
        if (facturaActual == null) {
            JOptionPane.showMessageDialog(this,
                "⚠️ ERROR: Primero debe crear una factura\n\n" +
                "Siga estos pasos:\n" +
                "1. Complete los datos de factura\n" +
                "2. Haga clic en 'Registrar Factura'\n" +
                "3. Luego agregue productos",
                "Factura No Creada", JOptionPane.WARNING_MESSAGE);
            jTextField1.requestFocus();
            return false;
        }
        
        // Obtener valores de los campos
        String nombreProducto = jTextField6.getText().trim();
        String talla = jTextField4.getText().trim();
        String cantidadStr = jTextField5.getText().trim();
        String precioStr = jTextField7.getText().trim();
        
        // Validar que los campos no estén vacíos
        if (nombreProducto.isEmpty()) {
            mostrarErrorCampoVacio("Producto", jTextField6);
            return false;
        }
        
        if (talla.isEmpty()) {
            mostrarErrorCampoVacio("Talla", jTextField4);
            return false;
        }
        
        if (cantidadStr.isEmpty()) {
            mostrarErrorCampoVacio("Cantidad", jTextField5);
            return false;
        }
        
        if (precioStr.isEmpty()) {
            mostrarErrorCampoVacio("Precio unitario", jTextField7);
            return false;
        }
        
        // Validar que cantidad sea un número entero positivo
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this,
                    "❌ La cantidad debe ser un número mayor que 0",
                    "Valor Inválido", JOptionPane.ERROR_MESSAGE);
                jTextField5.requestFocus();
                jTextField5.selectAll();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "❌ La cantidad debe ser un número entero\n" +
                "Ejemplo: 1, 2, 3, etc.",
                "Valor Inválido", JOptionPane.ERROR_MESSAGE);
            jTextField5.requestFocus();
            jTextField5.selectAll();
            return false;
        }
        
        // Validar que precio sea un número positivo
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this,
                    "❌ El precio debe ser un número mayor que 0",
                    "Valor Inválido", JOptionPane.ERROR_MESSAGE);
                jTextField7.requestFocus();
                jTextField7.selectAll();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "❌ El precio debe ser un número válido\n" +
                "Ejemplo: 25.50, 100.00, 45.99",
                "Valor Inválido", JOptionPane.ERROR_MESSAGE);
            jTextField7.requestFocus();
            jTextField7.selectAll();
            return false;
        }
        
        // Tipo de prenda (valor por defecto)
        String tipoPrenda = "Prenda";
        
        // Crear el producto
        Producto nuevoProducto = new Producto(nombreProducto, tipoPrenda, talla, cantidad, precio);
        
        // Agregar producto a la factura
        facturaActual.agregarProducto(nuevoProducto);
        
        // Agregar a la tabla
        Object[] fila = {
            nombreProducto,
            tipoPrenda,
            talla,
            cantidad,
            String.format("$%.2f", precio),
            String.format("$%.2f", nuevoProducto.getSubtotal())
        };
        modeloTabla.addRow(fila);
        
        // Actualizar total general
        actualizarTotal();
        
        // Limpiar campos para nuevo producto
        limpiarCamposProducto();
        
        // Enfocar campo de producto para siguiente entrada
        jTextField6.requestFocus();
        
        // Desplazar la tabla para mostrar el último producto agregado
        if (tablaProductos != null) {
            int lastRow = modeloTabla.getRowCount() - 1;
            tablaProductos.scrollRectToVisible(tablaProductos.getCellRect(lastRow, 0, true));
        }
        
        return true;
    }
    
    private void mostrarResumenProductos() {
        if (facturaActual == null || facturaActual.getProductos().isEmpty()) {
            return;
        }
        
        StringBuilder resumen = new StringBuilder();
        resumen.append("📋 RESUMEN DE PRODUCTOS\n\n");
        resumen.append("Factura: ").append(facturaActual.getNumeroFactura()).append("\n");
        resumen.append("Cliente: ").append(facturaActual.getNombreCliente()).append("\n\n");
        resumen.append("Productos agregados:\n");
        
        int contador = 1;
        double totalProductos = 0;
        
        for (Producto p : facturaActual.getProductos()) {
            double subtotal = p.getSubtotal();
            totalProductos += subtotal;
            
            resumen.append(String.format("%2d. %-20s (Talla: %-4s) x%-3d = $%8.2f\n",
                contador,
                p.getNombre(),
                p.getTalla(),
                p.getCantidad(),
                subtotal));
            contador++;
        }
        
        resumen.append("\n").append("-".repeat(50)).append("\n");
        resumen.append(String.format("Total productos: %d\n", facturaActual.getCantidadProductos()));
        resumen.append(String.format("💰 TOTAL FACTURA: $%.2f", facturaActual.getTotal()));
        
        JOptionPane.showMessageDialog(this, resumen.toString(),
            "Resumen de Productos", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // MÉTODO PARA EL BOTÓN "Registrar Factura" (jButton5)
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String numero = jTextField1.getText().trim();
        String fecha = jTextField2.getText().trim();
        String cliente = jTextField3.getText().trim();
        
        // Validaciones
        if (!Validador.validarNumeroFactura(numero)) {
            JOptionPane.showMessageDialog(this, 
                "❌ Número de factura inválido\nDebe tener al menos 3 caracteres",
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jTextField1.requestFocus();
            return;
        }
        
        if (!Validador.validarFecha(fecha)) {
            JOptionPane.showMessageDialog(this,
                "❌ Fecha inválida\nUse formato dd/MM/yyyy (ej: 15/01/2024)",
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jTextField2.requestFocus();
            return;
        }
        
        if (!Validador.validarNombre(cliente)) {
            JOptionPane.showMessageDialog(this,
                "❌ Nombre de cliente inválido\nDebe tener al menos 2 caracteres",
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jTextField3.requestFocus();
            return;
        }
        
        // Verificar si ya existe la factura - CORREGIDO
        if (gestor.existeFactura(numero)) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Ya existe una factura con el número: " + numero + "\nUse un número diferente",
                "Factura Existente", JOptionPane.WARNING_MESSAGE);
            jTextField1.requestFocus();
            return;
        }
        
        // Crear la factura
        Date fechaDate = Validador.convertirFecha(fecha);
        facturaActual = new Factura(numero, fechaDate, cliente);
        
        JOptionPane.showMessageDialog(this,
            "✅ FACTURA CREADA EXITOSAMENTE\n\n" +
            "📋 Número: " + numero + "\n" +
            "👤 Cliente: " + cliente + "\n" +
            "📅 Fecha: " + fecha + "\n\n" +
            "Ahora puede agregar productos",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        // Habilitar campos de productos
        habilitarCamposProductos(true);
        jTextField6.requestFocus();
    }
    
    private void registrarFacturaCompleta() {
        if (facturaActual == null) {
            JOptionPane.showMessageDialog(this,
                "❌ No hay factura para guardar\n" +
                "Primero cree una factura y agregue productos",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (facturaActual.getProductos().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "❌ La factura no tiene productos\n" +
                "Agregue al menos un producto antes de guardar",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Mostrar resumen final antes de guardar
        StringBuilder resumenFinal = new StringBuilder();
        resumenFinal.append("¿ESTÁ SEGURO DE GUARDAR ESTA FACTURA?\n\n");
        resumenFinal.append("📋 Factura: ").append(facturaActual.getNumeroFactura()).append("\n");
        resumenFinal.append("👤 Cliente: ").append(facturaActual.getNombreCliente()).append("\n");
        resumenFinal.append("🛍️ Productos: ").append(facturaActual.getCantidadProductos()).append("\n");
        resumenFinal.append("💰 Total: $").append(String.format("%.2f", facturaActual.getTotal())).append("\n\n");
        resumenFinal.append("¿Confirmar guardado?");
        
        int confirm = JOptionPane.showConfirmDialog(this,
            resumenFinal.toString(),
            "Confirmar Guardado de Factura",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Guardar factura
        boolean exito = gestor.agregarFactura(facturaActual);
        
        if (exito) {
            String mensaje = String.format(
                "✅ ¡FACTURA GUARDADA EXITOSAMENTE!\n\n" +
                "📋 Número: %s\n" +
                "👤 Cliente: %s\n" +
                "📅 Fecha: %s\n" +
                "🛍️ Productos: %d\n" +
                "💰 Total: $%.2f\n\n" +
                "La factura ha sido registrada en el sistema",
                facturaActual.getNumeroFactura(),
                facturaActual.getNombreCliente(),
                new SimpleDateFormat("dd/MM/yyyy").format(facturaActual.getFecha()),
                facturaActual.getCantidadProductos(),
                facturaActual.getTotal()
            );
            
            JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar todo para nueva factura
            limpiarTodo();
        } else {
            JOptionPane.showMessageDialog(this,
                "❌ Error al guardar la factura\n" +
                "Ya existe una factura con el número: " + facturaActual.getNumeroFactura(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if (facturaActual != null && !facturaActual.getProductos().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "⚠️ ¿Crear nueva factura?\n\n" +
                "La factura actual no ha sido guardada.\n" +
                "¿Desea descartar los cambios y crear una nueva factura?",
                "Confirmar Nueva Factura",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        limpiarTodo();
        JOptionPane.showMessageDialog(this,
            "📝 Listo para nueva factura\nComplete los datos de factura",
            "Nueva Factura", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // MÉTODOS AUXILIARES
    
    private void mostrarErrorCampoVacio(String nombreCampo, JTextField campo) {
        JOptionPane.showMessageDialog(this,
            "❌ El campo '" + nombreCampo + "' no puede estar vacío",
            "Campo Requerido", JOptionPane.ERROR_MESSAGE);
        campo.requestFocus();
    }
    
    private void actualizarTotal() {
        if (facturaActual != null) {
            double total = facturaActual.getTotal();
            jLabel11.setText(String.format("Total: $%.2f", total));
            
            // Cambiar estilo según el monto
            if (total > 1000) {
                jLabel11.setForeground(Color.RED);
                jLabel11.setFont(new Font("Arial", Font.BOLD, 14));
            } else if (total > 500) {
                jLabel11.setForeground(new Color(255, 140, 0)); // Naranja
                jLabel11.setFont(new Font("Arial", Font.BOLD, 14));
            } else {
                jLabel11.setForeground(Color.BLACK);
                jLabel11.setFont(new Font("Arial", Font.PLAIN, 12));
            }
        }
    }
    
    private void limpiarCamposProducto() {
        jTextField6.setText("");  // Producto
        jTextField4.setText("");  // Talla
        jTextField5.setText("");  // Cantidad
        jTextField7.setText("");  // Precio unitario
        jTextField6.requestFocus(); // Enfocar el primer campo
    }
    
    private void limpiarTodo() {
        jTextField1.setText("");
        jTextField3.setText("");
        limpiarCamposProducto();
        
        if (modeloTabla != null) {
            modeloTabla.setRowCount(0);
        }
        
        jLabel11.setText("Total: $0.00");
        jLabel11.setForeground(Color.BLACK);
        jLabel11.setFont(new Font("Arial", Font.PLAIN, 12));
        
        configurarFechaActual();
        facturaActual = null;
        
        habilitarCamposProductos(false);
        jTextField1.requestFocus();
    }
    
    private void habilitarCamposProductos(boolean habilitar) {
        jTextField6.setEnabled(habilitar);
        jTextField4.setEnabled(habilitar);
        jTextField5.setEnabled(habilitar);
        jTextField7.setEnabled(habilitar);
        jButton6.setEnabled(habilitar);
        jButton1.setEnabled(habilitar);
        
        // Cambiar color de fondo para indicar estado
        Color colorFondo = habilitar ? Color.WHITE : new Color(240, 240, 240);
        jTextField6.setBackground(colorFondo);
        jTextField4.setBackground(colorFondo);
        jTextField5.setBackground(colorFondo);
        jTextField7.setBackground(colorFondo);
    }
    
    private void volverAlMenu() {
        if (FacturaActual != null && !FacturaActual.getProductos().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "⚠️ ¿Volver al menú principal?\n\n" +
                "La factura actual no ha sido guardada.\n" +
                "¿Desea descartar los cambios y volver al menú?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        VentanaMenu ventanaMenu = new VentanaMenu();
        ventanaMenu.setVisible(true);
        this.dispose();
    }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Sistema de facturacion-MAAP");

        jLabel2.setText("Registar Factura");

        jLabel3.setText("Numero de Factura");

        jLabel4.setText("Fecha");

        jLabel5.setText("Nombre del cliente ");

        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jTextField2.addActionListener(this::jTextField2ActionPerformed);

        jTextField3.addActionListener(this::jTextField3ActionPerformed);

        jButton5.setBackground(new java.awt.Color(255, 255, 51));
        jButton5.setText("Registrar Factura");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(271, 271, 271)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(213, 213, 213)
                                .addComponent(jButton5)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jLabel7.setText("Producto");

        jLabel8.setText("Talla");

        jLabel9.setText("Cantidad");

        jLabel10.setText("Precio unitario");

        jTextField4.addActionListener(this::jTextField4ActionPerformed);

        jTextField5.addActionListener(this::jTextField5ActionPerformed);

        jTextField6.addActionListener(this::jTextField6ActionPerformed);

        jTextField7.addActionListener(this::jTextField7ActionPerformed);

        jLabel6.setBackground(new java.awt.Color(51, 204, 0));
        jLabel6.setText("Datos del Producto");

        jButton6.setBackground(new java.awt.Color(255, 255, 102));
        jButton6.setText("Registrar Producto");
        jButton6.addActionListener(this::jButton6ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(200, 200, 200)
                        .addComponent(jButton6)))
                .addContainerGap(166, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(267, 267, 267)
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Agregar P.");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Registrar F.");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setText("Salir");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton4)
                .addGap(0, 113, Short.MAX_VALUE))
        );

        jLabel11.setText("Total: $0.00");

        jButton3.setBackground(new java.awt.Color(255, 204, 51));
        jButton3.setText("Nueva Factura");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(162, 162, 162))
            .addGroup(layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
       // Enter en número de factura pasa a fecha
        jTextField2.requestFocus();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // Enter en precio: puedes hacer que agregue automáticamente
        // o simplemente pasar el foco al botón "Agregar P."
        jButton1.requestFocus();
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // Enter en producto pasa a talla
        jTextField4.requestFocus();
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // Enter en talla pasa a cantidad
        jTextField5.requestFocus();
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // "Agregar P." - Agrega producto y limpia campos para nuevo ingreso
        agregarProductoALaFactura();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // Enter en cliente pasa a producto (si está habilitado) o se queda
        if (jTextField6.isEnabled()) {
            jTextField6.requestFocus();
        }
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
            if (facturaActual != null && !facturaActual.getProductos().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "⚠️ ¿Crear nueva factura?\n\n" +
                "La factura actual no ha sido guardada.\n" +
                "¿Desea descartar los cambios y crear una nueva factura?",
                "Confirmar Nueva Factura",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        limpiarTodo();
        JOptionPane.showMessageDialog(this,
            "📝 Listo para nueva factura\nComplete los datos de factura",
            "Nueva Factura", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // MÉTODOS AUXILIARES
    
    private void mostrarErrorCampoVacio(String nombreCampo, JTextField campo) {
        JOptionPane.showMessageDialog(this,
            "❌ El campo '" + nombreCampo + "' no puede estar vacío",
            "Campo Requerido", JOptionPane.ERROR_MESSAGE);
        campo.requestFocus();
    }
    
    private String obtenerTipoPrenda() {
        // Por ahora devolvemos un valor por defecto
        // Puedes agregar un JComboBox para seleccionar tipo
        return "Prenda";
    }
    
    private void actualizarTotal() {
        if (facturaActual != null) {
            double total = facturaActual.getTotal();
            jLabel11.setText(String.format("Total: $%.2f", total));
            
            // Cambiar estilo según el monto
            if (total > 1000) {
                jLabel11.setForeground(Color.RED);
                jLabel11.setFont(new Font("Arial", Font.BOLD, 14));
            } else if (total > 500) {
                jLabel11.setForeground(new Color(255, 140, 0)); // Naranja
                jLabel11.setFont(new Font("Arial", Font.BOLD, 14));
            } else {
                jLabel11.setForeground(Color.BLACK);
                jLabel11.setFont(new Font("Arial", Font.PLAIN, 12));
            }
        }
    }
    
    private void limpiarCamposProducto() {
        jTextField6.setText("");  // Producto
        jTextField4.setText("");  // Talla
        jTextField5.setText("");  // Cantidad
        jTextField7.setText("");  // Precio unitario
        jTextField6.requestFocus(); // Enfocar el primer campo
    }
    
    private void limpiarTodo() {
        jTextField1.setText("");
        jTextField3.setText("");
        limpiarCamposProducto();
        
        if (modeloTabla != null) {
            modeloTabla.setRowCount(0);
        }
        
        jLabel11.setText("Total: $0.00");
        jLabel11.setForeground(Color.BLACK);
        jLabel11.setFont(new Font("Arial", Font.PLAIN, 12));
        
        configurarFechaActual();
        facturaActual = null;
        
        habilitarCamposProductos(false);
        jTextField1.requestFocus();
    }
    
    private void habilitarCamposProductos(boolean habilitar) {
        jTextField6.setEnabled(habilitar);
        jTextField4.setEnabled(habilitar);
        jTextField5.setEnabled(habilitar);
        jTextField7.setEnabled(habilitar);
        jButton6.setEnabled(habilitar);
        jButton1.setEnabled(habilitar);
        
        // Cambiar color de fondo para indicar estado
        Color colorFondo = habilitar ? Color.WHITE : new Color(240, 240, 240);
        jTextField6.setBackground(colorFondo);
        jTextField4.setBackground(colorFondo);
        jTextField5.setBackground(colorFondo);
        jTextField7.setBackground(colorFondo);
    }
    
    private void volverAlMenu() {
        if (facturaActual != null && !facturaActual.getProductos().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "⚠️ ¿Volver al menú principal?\n\n" +
                "La factura actual no ha sido guardada.\n" +
                "¿Desea descartar los cambios y volver al menú?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        VentanaMenu ventanaMenu = new VentanaMenu();
        ventanaMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String numero = jTextField1.getText().trim();
        String fecha = jTextField2.getText().trim();
        String cliente = jTextField3.getText().trim();
        
        // Validaciones
        if (!Validador.validarNumeroFactura(numero)) {
            JOptionPane.showMessageDialog(this, 
                "❌ Número de factura inválido\nDebe tener al menos 3 caracteres",
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jTextField1.requestFocus();
            return;
        }
        
        if (!Validador.validarFecha(fecha)) {
            JOptionPane.showMessageDialog(this,
                "❌ Fecha inválida\nUse formato dd/MM/yyyy (ej: 15/01/2024)",
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jTextField2.requestFocus();
            return;
        }
        
        if (!Validador.validarNombre(cliente)) {
            JOptionPane.showMessageDialog(this,
                "❌ Nombre de cliente inválido\nDebe tener al menos 2 caracteres",
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
            jTextField3.requestFocus();
            return;
        }
        
        // Verificar si ya existe la factura
        if (gestor.existeFactura(numero)) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Ya existe una factura con el número: " + numero + "\nUse un número diferente",
                "Factura Existente", JOptionPane.WARNING_MESSAGE);
            jTextField1.requestFocus();
            return;
        }
        
        // Crear la factura
        Date fechaDate = Validador.convertirFecha(fecha);
        facturaActual = new Factura(numero, fechaDate, cliente);
        
        JOptionPane.showMessageDialog(this,
            "✅ FACTURA CREADA EXITOSAMENTE\n\n" +
            "📋 Número: " + numero + "\n" +
            "👤 Cliente: " + cliente + "\n" +
            "📅 Fecha: " + fecha + "\n\n" +
            "Ahora puede agregar productos",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
        
        // Habilitar campos de productos
        habilitarCamposProductos(true);
        jTextField6.requestFocus();
    }
    
    private void registrarFacturaCompleta() {
        if (facturaActual == null) {
            JOptionPane.showMessageDialog(this,
                "❌ No hay factura para guardar\n" +
                "Primero cree una factura y agregue productos",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (facturaActual.getProductos().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "❌ La factura no tiene productos\n" +
                "Agregue al menos un producto antes de guardar",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Mostrar resumen final antes de guardar
        StringBuilder resumenFinal = new StringBuilder();
        resumenFinal.append("¿ESTÁ SEGURO DE GUARDAR ESTA FACTURA?\n\n");
        resumenFinal.append("📋 Factura: ").append(facturaActual.getNumeroFactura()).append("\n");
        resumenFinal.append("👤 Cliente: ").append(facturaActual.getNombreCliente()).append("\n");
        resumenFinal.append("🛍️ Productos: ").append(facturaActual.getProductos().size()).append("\n");
        resumenFinal.append("💰 Total: $").append(String.format("%.2f", facturaActual.getTotal())).append("\n\n");
        resumenFinal.append("¿Confirmar guardado?");
        
        int confirm = JOptionPane.showConfirmDialog(this,
            resumenFinal.toString(),
            "Confirmar Guardado de Factura",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Guardar factura
        boolean exito = gestor.agregarFactura(facturaActual);
        
        if (exito) {
            String mensaje = String.format(
                "✅ ¡FACTURA GUARDADA EXITOSAMENTE!\n\n" +
                "📋 Número: %s\n" +
                "👤 Cliente: %s\n" +
                "📅 Fecha: %s\n" +
                "🛍️ Productos: %d\n" +
                "💰 Total: $%.2f\n\n" +
                "La factura ha sido registrada en el sistema",
                facturaActual.getNumeroFactura(),
                facturaActual.getNombreCliente(),
                new SimpleDateFormat("dd/MM/yyyy").format(facturaActual.getFecha()),
                facturaActual.getCantidadProductos(),
                facturaActual.getTotal()
            );
            
            JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar todo para nueva factura
            limpiarTodo();
        } else {
            JOptionPane.showMessageDialog(this,
                "❌ Error al guardar la factura\n" +
                "Ya existe una factura con el número: " + facturaActual.getNumeroFactura(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // "Registrar Producto" - Agrega producto y muestra resumen
        if (agregarProductoALaFactura()) {
            // Si se agregó correctamente, mostrar resumen
            mostrarResumenProductos();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // Enter en cantidad pasa a precio
        jTextField7.requestFocus();
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // Enter en fecha pasa a cliente
        jTextField3.requestFocus();
    }//GEN-LAST:event_jTextField2ActionPerformed

    /**
     * @param args the command line arguments
     */

    private boolean agregarProductoALaFactura() {
        // Retorna true si se agregó correctamente, false si hubo error
        
        if (facturaActual == null) {
            JOptionPane.showMessageDialog(this,
                "⚠️ ERROR: Primero debe crear una factura\n\n" +
                "Siga estos pasos:\n" +
                "1. Complete los datos de factura\n" +
                "2. Haga clic en 'Registrar Factura'\n" +
                "3. Luego agregue productos",
                "Factura No Creada", JOptionPane.WARNING_MESSAGE);
            jTextField1.requestFocus();
            return false;
        }
        
        // Obtener valores de los campos
        String nombreProducto = jTextField6.getText().trim();
        String talla = jTextField4.getText().trim();
        String cantidadStr = jTextField5.getText().trim();
        String precioStr = jTextField7.getText().trim();
        
        // Validar que los campos no estén vacíos
        if (nombreProducto.isEmpty()) {
            mostrarErrorCampoVacio("Producto", jTextField6);
            return false;
        }
        
        if (talla.isEmpty()) {
            mostrarErrorCampoVacio("Talla", jTextField4);
            return false;
        }
        
        if (cantidadStr.isEmpty()) {
            mostrarErrorCampoVacio("Cantidad", jTextField5);
            return false;
        }
        
        if (precioStr.isEmpty()) {
            mostrarErrorCampoVacio("Precio unitario", jTextField7);
            return false;
        }
        
        // Validar que cantidad sea un número entero positivo
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this,
                    "❌ La cantidad debe ser un número mayor que 0",
                    "Valor Inválido", JOptionPane.ERROR_MESSAGE);
                jTextField5.requestFocus();
                jTextField5.selectAll();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "❌ La cantidad debe ser un número entero\n" +
                "Ejemplo: 1, 2, 3, etc.",
                "Valor Inválido", JOptionPane.ERROR_MESSAGE);
            jTextField5.requestFocus();
            jTextField5.selectAll();
            return false;
        }
        
        // Validar que precio sea un número positivo
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this,
                    "❌ El precio debe ser un número mayor que 0",
                    "Valor Inválido", JOptionPane.ERROR_MESSAGE);
                jTextField7.requestFocus();
                jTextField7.selectAll();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "❌ El precio debe ser un número válido\n" +
                "Ejemplo: 25.50, 100.00, 45.99",
                "Valor Inválido", JOptionPane.ERROR_MESSAGE);
            jTextField7.requestFocus();
            jTextField7.selectAll();
            return false;
        }
        
        // Tipo de prenda (valor por defecto o puedes agregar un campo)
        String tipoPrenda = obtenerTipoPrenda();
        
        // Crear el producto
        Producto nuevoProducto = new Producto(nombreProducto, tipoPrenda, talla, cantidad, precio);
        
        // Agregar producto a la factura
        facturaActual.agregarProducto(nuevoProducto);
        
        // Agregar a la tabla
        Object[] fila = {
            nombreProducto,
            tipoPrenda,
            talla,
            cantidad,
            String.format("$%.2f", precio),
            String.format("$%.2f", nuevoProducto.getSubtotal())
        };
        modeloTabla.addRow(fila);
        
        // Actualizar total general
        actualizarTotal();
        
        // Limpiar campos para nuevo producto
        limpiarCamposProducto();
        
        // Enfocar campo de producto para siguiente entrada
        jTextField6.requestFocus();
        
        // Desplazar la tabla para mostrar el último producto agregado
        if (tablaProductos != null) {
            int lastRow = modeloTabla.getRowCount() - 1;
            tablaProductos.scrollRectToVisible(tablaProductos.getCellRect(lastRow, 0, true));
        }
        
        return true;
    }
    
    private void mostrarResumenProductos() {
        if (facturaActual == null || facturaActual.getProductos().isEmpty()) {
            return;
        }
        
        StringBuilder resumen = new StringBuilder();
        resumen.append("📋 RESUMEN DE PRODUCTOS\n\n");
        resumen.append("Factura: ").append(facturaActual.getNumeroFactura()).append("\n");
        resumen.append("Cliente: ").append(facturaActual.getNombreCliente()).append("\n\n");
        resumen.append("Productos agregados:\n");
        
        int contador = 1;
        double totalProductos = 0;
        
        for (Producto p : facturaActual.getProductos()) {
            double subtotal = p.getSubtotal();
            totalProductos += subtotal;
            
            resumen.append(String.format("%2d. %-20s (Talla: %-4s) x%-3d = $%8.2f\n",
                contador,
                p.getNombre(),
                p.getTalla(),
                p.getCantidad(),
                subtotal));
            contador++;
        }
        
        resumen.append("\n").append("-".repeat(50)).append("\n");
        resumen.append(String.format("Total productos: %d\n", facturaActual.getProductos().size()));
        resumen.append(String.format("💰 TOTAL FACTURA: $%.2f", facturaActual.getTotal()));
        
        JOptionPane.showMessageDialog(this, resumen.toString(),
            "Resumen de Productos", JOptionPane.INFORMATION_MESSAGE);
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
