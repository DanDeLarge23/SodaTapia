/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.JOptionPane;
/**
 *
 * @author Paula
 */
public class Orden {

    private String tipoHamburguesa;
    private String[] ingredientes;
    private int puntaje;

    public Orden(String tipoHamburguesa, String[] ingredientes, int puntaje) {
        this.tipoHamburguesa = tipoHamburguesa;
        this.ingredientes = ingredientes;
        this.puntaje = puntaje;
    }

    public String getTipoHamburguesa() {
        return tipoHamburguesa;
    }

    public String[] getIngredientes() {
        return ingredientes;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public boolean verificarHamburguesa(String[] ingredientesTomados) {
        if (ingredientesTomados.length != ingredientes.length) {
            return false;
        }
        for (String ingrediente : ingredientes) {
            boolean encontrado = false;
            for (String ingredienteTomado : ingredientesTomados) {
                if (ingrediente.equals(ingredienteTomado)) {
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                return false;
            }
        }
        return true;
    }

    public String toMensajeString() {
        return "Orden: " + tipoHamburguesa + " (" + String.join(", ", ingredientes) + ")";
    }

    public void mostrarEnVentana() {
        JOptionPane.showMessageDialog(null, toMensajeString());
    }
}