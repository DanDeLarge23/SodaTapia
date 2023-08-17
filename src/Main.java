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

        JOptionPane.showMessageDialog(null, "¡Tiempo agotado! Puntaje final: " + juego.getPuntaje());
        JOptionPane.showMessageDialog(null, "¡Gracias por jugar!");

        System.exit(0);
    }
}