
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author Paula
 */
public class ChefHamburguesas {
    private Orden[] ordenesPendientes;
    private Ingrediente[] cintaTransportadora;
    private Timer temporizadorOrdenes;
    private int tiempoRestante;

    public ChefHamburguesas() {
        ordenesPendientes = new Orden[5];
        cintaTransportadora = new Ingrediente[]{new Ingrediente("Pan"),
            new Ingrediente("Carne"), new Ingrediente("Lechuga"),
            new Ingrediente("Tomate")};
        tiempoRestante = 120; // 2 minutos en segundos

        // Temporizador para agregar nuevas órdenes cada 5 segundos
        temporizadorOrdenes = new Timer(5000, (e) -> agregarNuevaOrden());
        temporizadorOrdenes.start();
    }

    public ChefHamburguesas(Orden[] ordenesPendientes,
            Ingrediente[] cintaTransportadora, Timer temporizadorOrdenes,
            int tiempoRestante) {
        this.ordenesPendientes = ordenesPendientes;
        this.cintaTransportadora = cintaTransportadora;
        this.temporizadorOrdenes = temporizadorOrdenes;
        this.tiempoRestante = tiempoRestante;
    }

    public Orden[] getOrdenesPendientes() {
        return ordenesPendientes;
    }

    public void setOrdenesPendientes(Orden[] ordenesPendientes) {
        this.ordenesPendientes = ordenesPendientes;
    }

    public Ingrediente[] getCintaTransportadora() {
        return cintaTransportadora;
    }

    public void setCintaTransportadora(Ingrediente[] cintaTransportadora) {
        this.cintaTransportadora = cintaTransportadora;
    }

    public Timer getTemporizadorOrdenes() {
        return temporizadorOrdenes;
    }

    public void setTemporizadorOrdenes(Timer temporizadorOrdenes) {
        this.temporizadorOrdenes = temporizadorOrdenes;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    private Ingrediente[] generarIngredientesAleatorios() {
        Ingrediente[] ingredientesAleatorios = new Ingrediente[3];
        Random random = new Random();
        for (int i = 0; i < ingredientesAleatorios.length; i++) {
            ingredientesAleatorios[i]
                    = cintaTransportadora[random.nextInt(cintaTransportadora.length)];
        }
        return ingredientesAleatorios;
    }

    private String generarTipoHamburguesaAleatorio() {
        String[] tiposHamburguesas = {"Hamburguesa de carne",
            "Hamburguesa con queso", "Hamburguesa clásica"};
        Random random = new Random();
        return tiposHamburguesas[random.nextInt(tiposHamburguesas.length)];
    }

    private void agregarNuevaOrden() {
        for (int i = 0; i < ordenesPendientes.length; i++) {
            if (ordenesPendientes[i] == null) {
                Ingrediente[] ingredientes = generarIngredientesAleatorios();
                String tipoHamburguesa = generarTipoHamburguesaAleatorio();
                Orden orden = new Orden(tipoHamburguesa, ingredientes);
                ordenesPendientes[i] = orden;
                break;
            }
        }
    }
}