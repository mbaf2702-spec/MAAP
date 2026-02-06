/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package maap.maaptiendaderopaabp;
import maap.tiendaderopa.ui.VentanaMenu;
/**
 *
 * @author Usuario
 */
public class MaapTiendaDeRopaABP {
    public static void main(String args[]) {
            try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(MaapTiendaDeRopaABP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(() -> {
        // Cambia esto para iniciar el MENÚ PRINCIPAL
        new VentanaMenu().setVisible(true);
    });
    }
}