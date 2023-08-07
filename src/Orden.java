/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Paula
 */
public class Orden {
    private String tipoHamburguesa;
    private Ingrediente[] ingredientes;

    public Orden(String tipoHamburguesa, Ingrediente[] ingredientes) {
        this.tipoHamburguesa = tipoHamburguesa;
        this.ingredientes = ingredientes;
    }

    public String getTipoHamburguesa() {
        return tipoHamburguesa;
    }

    public void setTipoHamburguesa(String tipoHamburguesa) {
        this.tipoHamburguesa = tipoHamburguesa;
    }

    public Ingrediente[] getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Ingrediente[] ingredientes) {
        this.ingredientes = ingredientes;
    }

}

