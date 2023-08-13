

/**
 *
 * @author Paula
 */
import java.util.List;
public class Orden {
    private String tipoHamburguesa;
    private List<Ingrediente> ingredientes;
    private int puntos;

    public Orden(String tipoHamburguesa, List<Ingrediente> ingredientes, int puntos) {
        this.tipoHamburguesa = tipoHamburguesa;
        this.ingredientes = ingredientes;
        this.puntos = puntos;
    }

    public String getTipoHamburguesa() {
        return tipoHamburguesa;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public int getPuntos() {
        return puntos;
    }
}

