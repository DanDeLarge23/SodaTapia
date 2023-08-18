/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import javax.swing.JOptionPane;
/**
 *
 * @author Paula
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "¡Bienvenido al Juego de Hamburguesas!");

        HamburguesaJuego juego = new HamburguesaJuego();

        // Esperar 1 minuto antes de mostrar el mensaje final y salir
        try {
            Thread.sleep(60000); // 1 minuto en milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "¡Gracias por jugar!");

        System.exit(0);
     
        CintaTransportadora cinta = new CintaTransportadora(5);
        cinta.mostrarCintaEnVentana();
    }
}
