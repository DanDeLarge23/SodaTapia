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

    public void moverIzquierda() {
        if (posicionActual == 0) {
            posicionActual = capacidadMaxima - 1;
        } else {
            posicionActual--;
        }
    }

    public int getCantidadIngredientes() {
        int count = 0;
        for (String ingrediente : ingredientes) {
            if (ingrediente != null) {
                count++;
            }
        }
        return count;
    }

    public void agregarIngredienteAleatorio() {
        int nextPos = (posicionActual + 1) % capacidadMaxima;
        if (ingredientes[nextPos] == null) {
            ingredientes[nextPos] = generarIngredienteAleatorio();
        }
    }

    private void llenarCinta() {
        for (int i = 0; i < capacidadMaxima; i++) {
            ingredientes[i] = generarIngredienteAleatorio();
        }
    }

    private String generarIngredienteAleatorio() {
        String[] ingredientesPosibles = {"Pan", "Carne", "Lechuga", "Queso"};
        int indiceAleatorio = random.nextInt(ingredientesPosibles.length);
        return ingredientesPosibles[indiceAleatorio];
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
}