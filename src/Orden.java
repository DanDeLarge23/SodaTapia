/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Paula
 */
public class Orden {

    private String nombre;
    private Lista<Ingrediente> ingredientes = new Lista<Ingrediente>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Lista<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Lista<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Orden(String tipoHamburguesa) {
        this.nombre = tipoHamburguesa;
        if (tipoHamburguesa.equals("HCarne")) {
            this.ingredientes.insertar(new Ingrediente("pan"));
            this.ingredientes.insertar(new Ingrediente("carne"));
        } else if (tipoHamburguesa.equals("HQueso")) {
            this.ingredientes.insertar(new Ingrediente("pan"));
            this.ingredientes.insertar(new Ingrediente("carne"));
            this.ingredientes.insertar(new Ingrediente("queso"));
        } else if (tipoHamburguesa.equals("HClasica")) {
            this.ingredientes.insertar(new Ingrediente("pan"));
            this.ingredientes.insertar(new Ingrediente("carne"));
            this.ingredientes.insertar(new Ingrediente("queso"));
            this.ingredientes.insertar(new Ingrediente("lechuga"));
        }
    }
        
    public void EliminarIngrediente (String nombreIngrediente) {
        for(int i=0; i<this.ingredientes.tamano();i++){
            if (this.ingredientes.obtener(i).getNombre().equals(nombreIngrediente)){
                this.ingredientes.eliminar(i);
                System.out.println("Ingrediente " + nombreIngrediente + " eliminado.");
                return;
            }
        }
        System.out.println("Ingrediente " + nombreIngrediente + " no encontrado en la orden.");  
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getTipoHamburguesa() {
        return nombre;
    }

    public void setTipoHamburguesa(String tipoHamburguesa) {
        this.nombre = tipoHamburguesa;
    }

}
