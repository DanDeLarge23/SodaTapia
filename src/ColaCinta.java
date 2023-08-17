/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.JOptionPane;
/**
 *
 * @author Paula
 */
public class ColaCinta {

    private NodoCinta inicio;
    private NodoCinta fin;
    private int tamano;
    private int capacidadMaxima = 5;
    private int capacidad = 3; // Inicialmente 3 para cumplir con los requerimientos
    private FabricaIngredientes fabrica = new FabricaIngredientes();

    public ColaCinta() {
        inicio = null;
        fin = null;
        tamano = 0;
    }

    public boolean estaVacia() {
        return tamano == 0;
    }

    public int getTamano() {
        return tamano;
    }

    public void agregar(String ingrediente) {
        NodoCinta nuevoNodo = new NodoCinta(ingrediente);
        if (inicio == null) {
            inicio = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.setSiguiente(nuevoNodo);
            fin = nuevoNodo;
        }
        tamano++;
        if (tamano > capacidad) {
            quitar();
        }
    }

    public String quitar() {
        if (estaVacia()) {
            return null;
        }
        String ingrediente = inicio.getIngrediente();
        inicio = inicio.getSiguiente();
        tamano--;
        return ingrediente;
    }

    public void llenarAleatoriamente() {
        while (tamano < capacidadMaxima) {
            String ingrediente = fabrica.generarIngredienteAleatorio();
            agregar(ingrediente);
        }
    }

    public void moverCinta() {
        if (tamano <= capacidadMaxima - 2) {
            agregar(fabrica.generarIngredienteAleatorio());
            agregar(fabrica.generarIngredienteAleatorio());
        }
    }

    public void imprimir() {
        NodoCinta actual = inicio;
        while (actual != null) {
            System.out.print(actual.getIngrediente() + " ");
            actual = actual.getSiguiente();
        }
        System.out.println();
    }

    public void tirarIngrediente() {
        if (!estaVacia()) {
            String ingredienteTirado = quitar();
            System.out.println("Ingrediente tirado: " + ingredienteTirado);
        } else {
            System.out.println("La cinta está vacía");
        }
    }
}