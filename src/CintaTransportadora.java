/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Random;
import javax.swing.JOptionPane;
/**
 *
 * @author Paula
 */
public class CintaTransportadora {
    private String[] ingredientes;
    private int capacidadMaxima;
    private int posicionActual;
    private Random random;

    public CintaTransportadora(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        ingredientes = new String[capacidadMaxima];
        random = new Random();
        llenarCinta();
        posicionActual = 0;
    }

    public String tomarIngrediente() {
        if (posicionActual > 0) {
            String ingrediente = ingredientes[posicionActual - 1];
            ingredientes[posicionActual - 1] = null;
            posicionActual--;
            return ingrediente;
        }
        return null;
    }

    private void llenarCinta() {
        for (int i = 0; i < capacidadMaxima; i++) {
            ingredientes[i] = generarIngredienteAleatorio();
        }
    }

    private String generarIngredienteAleatorio() {
        String[] ingredientesPosibles = {"Pan", "Carne", "Lechuga", "Queso",
                "Tomate", "Cebolla", "Salsa"};
        int indiceAleatorio = random.nextInt(ingredientesPosibles.length);
        return ingredientesPosibles[indiceAleatorio];
    }

    public void agregarIngrediente(String ingrediente) {
        if (posicionActual < capacidadMaxima) {
            ingredientes[posicionActual] = ingrediente;
            posicionActual++;
        }
    }

    public void mostrarCintaEnVentana() {
        StringBuilder cintaText = new StringBuilder("Cinta Transportadora:\n");
        for (String ingrediente : ingredientes) {
            if (ingrediente != null) {
                cintaText.append(ingrediente).append(", ");
            }
        }
        JOptionPane.showMessageDialog(null, cintaText.toString());
    }

    // ... Otros métodos y funciones según tus necesidades

    public static void main(String[] args) {
        CintaTransportadora cinta = new CintaTransportadora(5);
        cinta.mostrarCintaEnVentana();
    }
}