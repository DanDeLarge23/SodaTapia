/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Paula
 */
public class NodoIngrediente {
    private String ingrediente;
    private NodoIngrediente siguiente;

    public NodoIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
        this.siguiente = null;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public NodoIngrediente getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoIngrediente siguiente) {
        this.siguiente = siguiente;
    }
}