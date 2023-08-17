/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
/**
 *
 * @author Paula
 */
public class HamburguesaJuego {

    private CintaTransportadora cinta;
    private Orden[] ordenes;
    private int puntaje;
    private int tiempo;
    private Timer juegoTimer; // Timer para el juego
    
   public HamburguesaJuego() {
        cinta = new CintaTransportadora(5);
        ordenes = new Orden[3];
        puntaje = 0;
        tiempo = 60; 
        iniciarJuego();
    }

    private void iniciarJuego() {
        // Crear un Timer para el juego
        juegoTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actualizar el juego en cada tick del Timer
                if (tiempo > 0) {
                    // Lógica del juego
                    for (int i = 0; i < ordenes.length; i++) {
                        Orden orden = ordenes[i];
                        if (orden != null) {
                            String[] ingredientesTomados = new String[orden.getIngredientes().length];
                            for (int j = 0; j < ingredientesTomados.length; j++) {
                                ingredientesTomados[j] = cinta.tomarIngrediente();
                            }
                            if (orden.verificarHamburguesa(ingredientesTomados)) {
                                puntaje += orden.getPuntaje();
                                ordenes[i] = null;
                            }
                        }
                    }
                    tiempo--;
                } else {
                    // Detener el juego al agotarse el tiempo
                    juegoTimer.stop();
                    mostrarPuntajeFinal();
                }
            }
        });

        // Generar órdenes cada 20 segundos
        Timer ordenesTimer = new Timer(20000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarOrdenes();
            }
        });
        ordenesTimer.start();

        // Mostrar tiempo restante cada segundo
        Timer tiempoTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTiempo();
            }
        });
        tiempoTimer.start();

        // Iniciar el juego
        juegoTimer.start();
    }

    private void generarOrdenes() {
        for (int i = 0; i < ordenes.length; i++) {
            if (ordenes[i] == null) {
                String tipoHamburguesa = generarTipoHamburguesaAleatoria();
                String[] ingredientes = generarIngredientesAleatorios(tipoHamburguesa);
                int puntaje = calcularPuntaje(tipoHamburguesa);
                ordenes[i] = new Orden(tipoHamburguesa, ingredientes, puntaje);
            }
        }
        mostrarOrdenes();
    }

    private String generarTipoHamburguesaAleatoria() {
        String[] tipos = {"Hamburguesa de carne", "Hamburguesa con queso", "Hamburguesa clásica"};
        int randomIndex = (int) (Math.random() * tipos.length);
        return tipos[randomIndex];
    }

    private String[] generarIngredientesAleatorios(String tipoHamburguesa) {
        String[] ingredientes = null;
        switch (tipoHamburguesa) {
            case "Hamburguesa de carne":
                ingredientes = new String[]{"Pan", "Carne"};
                break;
            case "Hamburguesa con queso":
                ingredientes = new String[]{"Pan", "Carne", "Queso"};
                break;
            case "Hamburguesa clásica":
                ingredientes = new String[]{"Pan", "Carne", "Lechuga", "Queso"};
                break;
        }
        return ingredientes;
}

    private int calcularPuntaje(String tipoHamburguesa) {
        int puntaje = 0;
        switch (tipoHamburguesa) {
            case "Hamburguesa de carne":
                puntaje = 5;
                break;
            case "Hamburguesa con queso":
                puntaje = 10;
                break;
            case "Hamburguesa clásica":
                puntaje = 15;
                break;
        }
        return puntaje;
    }

    private void mostrarOrdenes() {
        StringBuilder mensaje = new StringBuilder("Órdenes generadas:\n");
        for (Orden orden : ordenes) {
            if (orden != null) {
                mensaje.append(orden.toMensajeString()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, mensaje.toString());
    }

    private void mostrarTiempo() {
        JOptionPane.showMessageDialog(null, "Tiempo restante: " + tiempo + " segundos");
    }

    public int getPuntaje() {
        return puntaje;
    }
  private void mostrarPuntajeFinal() {
        JOptionPane.showMessageDialog(null, "¡Tiempo agotado! Puntaje final: " + puntaje);
    }
}