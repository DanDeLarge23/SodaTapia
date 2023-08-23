import javax.swing.JOptionPane;
/**
 *
 * @author Paula
 */
public class Orden {

    private String nombre;
    private Lista<Ingrediente> ingredientes = new Lista<Ingrediente>();
    private int puntos;

    public Orden(String tipoHamburguesa) {
        this.nombre = tipoHamburguesa;
        if (tipoHamburguesa.equals("HCarne")) {
            this.ingredientes.insertar(new Ingrediente("pan"));
            this.ingredientes.insertar(new Ingrediente("carne"));
            this.puntos = 5;
        } else if (tipoHamburguesa.equals("HQueso")) {
            this.ingredientes.insertar(new Ingrediente("pan"));
            this.ingredientes.insertar(new Ingrediente("carne"));
            this.ingredientes.insertar(new Ingrediente("queso"));

            this.puntos = 10;
        } else if (tipoHamburguesa.equals("HClasica")) {
            this.ingredientes.insertar(new Ingrediente("pan"));
            this.ingredientes.insertar(new Ingrediente("carne"));
            this.ingredientes.insertar(new Ingrediente("queso"));
            this.ingredientes.insertar(new Ingrediente("lechuga"));
            this.puntos = 15;
        }
    }

    public void EliminarIngrediente(String nombreIngrediente) {
        for (int i = 0; i < this.ingredientes.tamano(); i++) {
            if (this.ingredientes.obtener(i).getNombre().equals(nombreIngrediente)) {
                this.ingredientes.eliminar(i);
                System.out.println("Ingrediente " + nombreIngrediente + " eliminado.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Ingrediente "
                + nombreIngrediente + " no encontrado en la orden.");
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

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

    public String getTipoHamburguesa() {
        return nombre;
    }

    public void setTipoHamburguesa(String tipoHamburguesa) {
        this.nombre = tipoHamburguesa;
    }

    Orden(String tipoHamburguesa, Ingrediente[] ingredientes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return nombre;
    }
}
