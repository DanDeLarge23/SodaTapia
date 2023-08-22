
import java.util.Random;


/**
 *
 * @author Paula
 */
public class Ingrediente {

    private String nombre;
    private String imagen;

    public Ingrediente() {
        inicializarIngredienteAleatorio();
    }

    public Ingrediente(String tipo) {
        inicializarIngrediente(tipo);
    }

    private void inicializarIngredienteAleatorio() {
        String[] opciones = {"lechuga.png", "pan.png", "queso.png", "carne.png"};
        Random random = new Random();
        int indiceAleatorio = random.nextInt(opciones.length);
        inicializarIngrediente(opciones[indiceAleatorio].replace(".png", ""));
    }

    private void inicializarIngrediente(String tipo) {
        String[] opciones = {"lechuga.png", "pan.png", "queso.png", "carne.png"};
        int indiceOpcion = -1;
        if (tipo.equals("lechuga")) {
            indiceOpcion = 0;
        } else if (tipo.equals("pan")) {
            indiceOpcion = 1;
        } else if (tipo.equals("queso")) {
            indiceOpcion = 2;
        } else if (tipo.equals("carne")) {
            indiceOpcion = 3;
        }

        if (indiceOpcion != -1) {
            imagen = opciones[indiceOpcion];
            nombre = imagen.replace(".png", "");
        }
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
