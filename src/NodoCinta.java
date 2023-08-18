/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Paula
 */
public class NodoCinta {
    private String ingrediente;
    private NodoCinta siguiente;

    public NodoCinta(String ingrediente) {
        this.ingrediente = ingrediente;
        this.siguiente = null;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public NodoCinta getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCinta siguiente) {
        this.siguiente = siguiente;
    }
}