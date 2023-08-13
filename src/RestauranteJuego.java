/**
 *
 * @author Paula
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class RestauranteJuego extends JFrame {

    private static final int ANCHO_CINTA = 5;
    private static final int TIEMPO_ORDEN = 20000; // 20 segundos en milisegundos
    private static final int TIEMPO_TOTAL = 300000; // 5 minutos en milisegundos

    private Queue<Orden> ordenesEnCola;
    private List<Ingrediente> cintaTransportadora;
    private int puntos;
    private Timer ordenTimer;
    private Timer cintaTimer;
    private JLabel tiempoLabel;
    private JLabel puntajeLabel;
    private JPanel cintaPanel;

    public RestauranteJuego() {
        this.ordenesEnCola = new LinkedList<>();
        this.cintaTransportadora = new ArrayList<>();
        this.puntos = 0;

        // Configuración de la interfaz gráfica
        setTitle("Restaurante de Hamburguesas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        tiempoLabel = new JLabel("Tiempo: " + (TIEMPO_TOTAL / 1000) + " segundos");
        puntajeLabel = new JLabel("Puntaje: " + puntos);
        cintaPanel = new JPanel();

        add(tiempoLabel, BorderLayout.NORTH);
        add(cintaPanel, BorderLayout.CENTER);
        add(puntajeLabel, BorderLayout.SOUTH);

        ordenTimer = new Timer(TIEMPO_ORDEN, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarOrden();
            }
        });

        cintaTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverCinta();
            }
        });

        ordenTimer.start();
        cintaTimer.start();
        setVisible(true);
    }

    private void generarOrden() {
        if (ordenesEnCola.size() < 3) {
            Random random = new Random();
            int tipoHamburguesa = random.nextInt(3);

            String tipo;
            List<Ingrediente> ingredientes = new ArrayList<>();
            int puntos;

            switch (tipoHamburguesa) {
                case 0:
                    tipo = "Hamburguesa de carne";
                    ingredientes.add(new Ingrediente("pan"));
                    ingredientes.add(new Ingrediente("carne"));
                    puntos = 5;
                    break;
                case 1:
                    tipo = "Hamburguesa con queso";
                    ingredientes.add(new Ingrediente("pan"));
                    ingredientes.add(new Ingrediente("carne"));
                    ingredientes.add(new Ingrediente("queso"));
                    puntos = 10;
                    break;
                default:
                    tipo = "Hamburguesa clásica";
                    ingredientes.add(new Ingrediente("pan"));
                    ingredientes.add(new Ingrediente("carne"));
                    ingredientes.add(new Ingrediente("lechuga"));
                    ingredientes.add(new Ingrediente("queso"));
                    puntos = 15;
            }

            Orden nuevaOrden = new Orden(tipo, ingredientes, puntos);
            ordenesEnCola.add(nuevaOrden);
            actualizarInterfaz();
        }
    }

    private void actualizarInterfaz() {
        cintaPanel.removeAll();

        // Mostrar cinta transportadora
        for (Ingrediente ingrediente : cintaTransportadora) {
            JLabel ingredienteLabel = new JLabel(ingrediente.getNombre());
            cintaPanel.add(ingredienteLabel);
        }

        // Mostrar órdenes en cola
        for (Orden orden : ordenesEnCola) {
            JLabel ordenLabel = new JLabel(orden.getTipoHamburguesa());
            cintaPanel.add(ordenLabel);
        }

        tiempoLabel.setText("Tiempo: " + (TIEMPO_TOTAL / 1000) + " segundos");
        puntajeLabel.setText("Puntaje: " + puntos);

        revalidate();
        repaint();
    }

    private void moverCinta() {
        
    }

    private void tomarIngrediente() {
        
    }

    private void completarOrden() {
        
    }

    private void tirarIngrediente() {
       
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RestauranteJuego();
            }
        });
    }
}
