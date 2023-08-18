/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Paula
 */
public class ColaCinta {
   private NodoCinta frente;
    private NodoCinta ultimo;
    private int largo;

    public ColaCinta() {
        this.frente = null;
        this.ultimo = null;
        this.largo = 0;
    }
        
    public void encola(NodoCinta nuevoNodo){
        if(frente == null){
            frente = nuevoNodo;
            ultimo = nuevoNodo;                    
        } else {
            ultimo.setSiguiente(nuevoNodo);
            ultimo = nuevoNodo;
        }
        largo++;
    }
    
    public NodoCinta atiende(){
        NodoCinta aux = frente;
        if(frente != null){
            frente = frente.getSiguiente();
            aux.setSiguiente(null);
            largo--;
        }
        return aux;
    }
    
    public boolean encuentra(String ingrediente){
        NodoCinta aux = frente;
        while (aux != null) {
            if (aux.getIngrediente().equals(ingrediente)) {
                return true;
            }
            aux = aux.getSiguiente();
        }
        return false;
    }
    
    public String imprimir(){
        StringBuilder s = new StringBuilder();
        NodoCinta aux = frente;
        while (aux != null) {
            s.append(aux.getIngrediente()).append("\n");
            aux = aux.getSiguiente();
        }
        return s.toString();
    }
} 

