
import java.util.Random;


/**
 *
 * @author Paula
 */
public class Ingrediente {
    private String nombre;
    private String imagen;

    public Ingrediente() {
        String[] opciones = {"lechuga.png", "pan.png", "queso.png", "carne.png"};
        Random random = new Random();
        int indiceAleatorio = random.nextInt(opciones.length);
        imagen = opciones[indiceAleatorio];
        nombre = imagen.replace(".png", "");
    }

    public Ingrediente(String tipo) {
        String[] opciones = {"lechuga.png", "pan.png", "queso.png", "carne.png"};
        if (tipo.equals("lechuga")) {
            imagen = opciones[0];
            nombre = imagen.replace(".png", "");
        } else if (tipo.equals("pan")) {
            imagen = opciones[1];
            nombre = imagen.replace(".png", "");
        } else if (tipo.equals("queso")) {
            imagen = opciones[2];
            nombre = imagen.replace(".png", "");
        } else if (tipo.equals("carne")) {
            imagen = opciones[3];
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
